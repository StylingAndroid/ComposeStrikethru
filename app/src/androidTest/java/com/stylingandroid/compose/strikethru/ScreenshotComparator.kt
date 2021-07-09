/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stylingandroid.compose.strikethru

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.captureToImage
import androidx.test.platform.app.InstrumentationRegistry
import java.io.File
import java.io.FileOutputStream

/**
 * Simple on-device screenshot comparator that uses golden images present in
 * `androidTest/assets`. It's adapted from
 * https://github.com/android/compose-samples/blob/main/Rally/app/src/androidTest/java/com/example/compose/rally/ScreenshotComparator.kt
 *
 * Minimum SDK is O.
 *
 * Screenshots are saved on device in `/data/data/{package}/files`.
 *
 * Screenshot names will have the bitmap size included. This allows for different golden images
 * to be used for different screen densities. You will need to ensure that golden images with the
 * appropriate size in the name is included for all supported densities.
 */
@RequiresApi(Build.VERSION_CODES.O)
fun assertScreenshotMatchesGolden(
    folderName: String,
    goldenName: String,
    node: SemanticsNodeInteraction
) {
    val bitmap = node.captureToImage().asAndroidBitmap()

    // Save screenshot to file for debugging
    saveScreenshot(
        folderName,
        "${goldenName}_${bitmap.width}x${bitmap.height}_${System.currentTimeMillis()}",
        bitmap
    )
    val golden = InstrumentationRegistry.getInstrumentation()
        .context.resources.assets.open(
            "$folderName/${goldenName}_${bitmap.width}x${bitmap.height}.webp"
        )
        .use { BitmapFactory.decodeStream(it) }

    golden.compare(bitmap)
}

private fun saveScreenshot(folderName: String, filename: String, bmp: Bitmap) {
    val path = File(InstrumentationRegistry.getInstrumentation().targetContext.filesDir, folderName)
    if (!path.exists()) {
        path.mkdirs()
    }
    FileOutputStream("$path/$filename.webp").use { out ->
        bmp.compress(Bitmap.CompressFormat.WEBP_LOSSLESS, 100, out)
    }
    println("Saved screenshot to $path/$filename.webp")
}

private fun Bitmap.compare(other: Bitmap) {
    if (this.width != other.width || this.height != other.height) {
        throw AssertionError("Size of screenshot does not match golden file (check device density)")
    }
    // Compare row by row to save memory on device
    val row1 = IntArray(width)
    val row2 = IntArray(width)
    for (column in 0 until height) {
        // Read one row per bitmap and compare
        this.getRow(row1, column)
        other.getRow(row2, column)
        if (!row1.contentEquals(row2)) {
            throw AssertionError("Sizes match but bitmap content has differences")
        }
    }
}

private fun Bitmap.getRow(pixels: IntArray, column: Int) {
    this.getPixels(pixels, 0, width, 0, column, width, 1)
}

internal fun clearExistingImages(folderName: String) {
    val path = File(InstrumentationRegistry.getInstrumentation().targetContext.filesDir, folderName)
    path.deleteRecursively()
}
