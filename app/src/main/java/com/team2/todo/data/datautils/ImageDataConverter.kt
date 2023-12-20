package com.team2.todo.data.datautils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class ImageDataConverter {

    /* Writes the compressed bitmap version to output stream for allocating bytearray */
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?): ByteArray? {
        return bitmap?.let {
            val stream = ByteArrayOutputStream()
            it.compress(Bitmap.CompressFormat.JPEG, 75, stream)
            stream.toByteArray()
        }
    }

    /* Decodes an immutable bitmap from bytearray for rendering images */
    @TypeConverter
    fun toBitmap(byteArray: ByteArray?): Bitmap? {
        return byteArray?.let {
            BitmapFactory.decodeByteArray(it, 0, it.size)
        }
    }

}