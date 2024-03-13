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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

fun saveBitmapFromComposableWithPermission(
    coroutineScope: CoroutineScope,
    context: Context,
    pictureList: List<Picture>,
    checkedButtonStates: List<Boolean>,
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        coroutineScope.launch {
            val message = saveDailyGraph(
                context = context,
                pictureList = pictureList,
                checkedButtonStates = checkedButtonStates,
            )
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    } else {
        if (
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            coroutineScope.launch {
                val message = saveDailyGraph(
                    context = context,
                    pictureList = pictureList,
                    checkedButtonStates = checkedButtonStates,
                )
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        } else {
            permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }
}

suspend fun saveDailyGraph(
    context: Context,
    pictureList: List<Picture>,
    checkedButtonStates: List<Boolean>,
): String = coroutineScope {
    val jobs = mutableListOf<Deferred<Result<Uri>>>()

    checkedButtonStates.forEachIndexed { index, isChecked ->
        if (isChecked) {
            val job = async(Dispatchers.IO) {
                saveBitmapFromComposable(pictureList[index], context)
            }
            jobs.add(job)
        }
    }

    val isCompleted = jobs.awaitAll().all { it.isSuccess }
    if (isCompleted) "모든 사진이 갤러리에 저장되었습니다." else "갤러리에 저장이 실패하였습니다."
}

suspend fun shareDailyGraph(
    context: Context,
    pictureList: List<Picture>,
    checkedButtonStates: List<Boolean>,
) = coroutineScope {
    val jobs = mutableListOf<Deferred<Uri>>()

    checkedButtonStates.forEachIndexed { index, isChecked ->
        if (isChecked) {
            val job = async(Dispatchers.IO) {
                shareBitmapFromComposable(pictureList[index], context)
            }
            jobs.add(job)
        }
    }

    val results = jobs.awaitAll()
    shareBitmap(context, ArrayList(results))
}
