package com.titi.app.feature.log.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Picture
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat
import com.titi.app.core.designsystem.util.saveBitmapFromComposable
import com.titi.app.core.designsystem.util.shareBitmap
import com.titi.app.core.designsystem.util.shareBitmapFromComposable
import com.titi.app.core.designsystem.util.shareBitmaps
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun saveDailyGraphWithPermission(
    coroutineScope: CoroutineScope,
    context: Context,
    pictureList: List<Picture>,
    checkedButtonStates: List<Boolean>,
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val message = saveDailyGraph(
            coroutineScope = coroutineScope,
            context = context,
            pictureList = pictureList,
            checkedButtonStates = checkedButtonStates,
        )
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    } else {
        if (
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val message = saveDailyGraph(
                coroutineScope = coroutineScope,
                context = context,
                pictureList = pictureList,
                checkedButtonStates = checkedButtonStates,
            )
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        } else {
            permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }
}

fun saveWeekGraphWithPermission(
    coroutineScope: CoroutineScope,
    context: Context,
    picture: Picture,
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val message = saveWeekGraph(
            coroutineScope = coroutineScope,
            context = context,
            picture = picture,
        )
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    } else {
        if (
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val message = saveWeekGraph(
                coroutineScope = coroutineScope,
                context = context,
                picture = picture,
            )
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        } else {
            permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }
}

fun saveDailyGraph(
    context: Context,
    coroutineScope: CoroutineScope,
    pictureList: List<Picture>,
    checkedButtonStates: List<Boolean>,
): String {
    var message = "모든 사진이 갤러리에 저장되었습니다."
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, _ ->
        message = "갤러리에 저장이 실패하였습니다."
    }
    coroutineScope.launch(Dispatchers.Default + coroutineExceptionHandler) {
        checkedButtonStates.forEachIndexed { index, isChecked ->
            if (isChecked) {
                saveBitmapFromComposable(pictureList[index], context)
            }
        }
    }

    return message
}

fun saveWeekGraph(context: Context, coroutineScope: CoroutineScope, picture: Picture): String {
    var message = "모든 사진이 갤러리에 저장되었습니다."
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, _ ->
        message = "갤러리에 저장이 실패하였습니다."
    }

    coroutineScope.launch(Dispatchers.Default + coroutineExceptionHandler) {
        saveBitmapFromComposable(picture, context)
    }

    return message
}

fun shareDailyGraph(
    context: Context,
    pictureList: List<Picture>,
    checkedButtonStates: List<Boolean>,
) {
    val uris = arrayListOf<Uri>()
    checkedButtonStates.forEachIndexed { index, isChecked ->
        if (isChecked) {
            val uri = shareBitmapFromComposable(pictureList[index], context)
            uris.add(uri)
        }
    }

    shareBitmaps(context, uris)
}

fun shareWeekGraph(context: Context, picture: Picture) {
    val uri = shareBitmapFromComposable(picture, context)

    shareBitmap(context, uri)
}
