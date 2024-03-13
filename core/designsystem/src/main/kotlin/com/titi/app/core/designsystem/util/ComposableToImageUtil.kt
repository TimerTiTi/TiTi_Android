package com.titi.app.core.designsystem.util

import android.content.Context
import android.content.Intent
import android.content.Intent.createChooser
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Picture
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import java.io.File
import java.util.UUID
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine

fun Modifier.createCaptureImageModifier(picture: Picture): Modifier = this.drawWithCache {
    val width = this.size.width.toInt()
    val height = this.size.height.toInt()
    onDrawWithContent {
        val pictureCanvas = Canvas(
            picture.beginRecording(
                width,
                height,
            ),
        )
        draw(this, this.layoutDirection, pictureCanvas, this.size) {
            this@onDrawWithContent.drawContent()
        }
        picture.endRecording()

        drawIntoCanvas { canvas ->
            canvas.nativeCanvas.drawPicture(picture)
        }
    }
}

suspend fun saveBitmapFromComposable(picture: Picture, context: Context): Uri {
    val bitmap = createBitmapFromPicture(picture)
    return bitmap.saveToDisk(context)
}

fun shareBitmapFromComposable(picture: Picture, context: Context): Uri {
    val bitmap = createBitmapFromPicture(picture)
    return bitmap.getUri(context)
}

private fun createBitmapFromPicture(picture: Picture): Bitmap {
    val bitmap = Bitmap.createBitmap(
        picture.width,
        picture.height,
        Bitmap.Config.ARGB_8888,
    )

    val canvas = android.graphics.Canvas(bitmap)
    canvas.drawColor(Color.WHITE)
    canvas.drawPicture(picture)
    return bitmap
}

private suspend fun Bitmap.saveToDisk(context: Context): Uri {
    val file = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
        "screenshot-${UUID.randomUUID()}.png",
    )

    file.writeBitmap(this, Bitmap.CompressFormat.PNG, 100)

    return scanFilePath(context, file.path) ?: throw Exception("File could not be saved")
}

private fun Bitmap.getUri(context: Context): Uri {
    val cachePath = File(context.cacheDir, "images")
    cachePath.mkdirs()

    val file = File(cachePath, "screenshot-${UUID.randomUUID()}.png")
    file.writeBitmap(this, Bitmap.CompressFormat.PNG, 100)

    return FileProvider.getUriForFile(context, context.packageName + ".provider", file)
}

private suspend fun scanFilePath(context: Context, filePath: String): Uri? {
    return suspendCancellableCoroutine { continuation ->
        MediaScannerConnection.scanFile(
            context,
            arrayOf(filePath),
            arrayOf("image/png"),
        ) { _, scannedUri ->
            if (scannedUri == null) {
                continuation.cancel(Exception("File $filePath could not be scanned"))
            } else {
                continuation.resume(scannedUri)
            }
        }
    }
}

private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
    outputStream().use { out ->
        bitmap.compress(format, quality, out)
        out.flush()
    }
}

fun shareBitmaps(context: Context, uris: ArrayList<Uri>) {
    val intent = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
        type = "image/png"
        putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    startActivity(context, createChooser(intent, "Share your image"), null)
}

fun shareBitmap(context: Context, uri: Uri) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "image/png"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    startActivity(context, createChooser(intent, "Share your image"), null)
}
