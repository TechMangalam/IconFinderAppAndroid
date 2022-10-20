package com.bitaam.iconfinderappandroid

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import okhttp3.ResponseBody
import java.io.File

object FileUtils {
    fun Context.write(contentResolver: ContentResolver, filename: String, format: String, responseBody: ResponseBody) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            val picturesCollection =
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "$filename.$format")
                put(
                    MediaStore.Images.Media.RELATIVE_PATH,
                    "${Environment.DIRECTORY_PICTURES}/IconFinder/"
                )
            }
            contentResolver.insert(picturesCollection, contentValues)?.also { uri ->
                contentResolver.openOutputStream(uri)?.use { outStream ->
                    outStream.write(responseBody.bytes())
                }
            }
        } else {
           val folder = File(Environment.getExternalStorageDirectory(), "${Environment.DIRECTORY_PICTURES}/IconFinder")
            if (folder.exists() || folder.mkdir()) {
                val file = File(folder, "$filename.$format")
                file.writeBytes(responseBody.bytes())
            } else {
                Toast.makeText(this, "Cannot create folder", Toast.LENGTH_SHORT).show()
                throw IllegalStateException("cannot create folder")
            }
        }
    }
}