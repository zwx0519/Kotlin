package com.example.myshop.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.IOException

public class BitmapUtils {

    /**
     * 图片文件的二次采用
     * @param path
     * @param outW
     * @param outH
     * @return
     */
    @Throws(IOException::class)
    fun getScaleBitmap(path: String?, outW: Int, outH: Int): Bitmap? {
        val file = File(path)
        if (!file.exists()) {
            throw IOException("图片不存在")
        }
        // 设置采用的参数
        val options = BitmapFactory.Options()
        //设置只读图片的边框=宽高
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)
        //原始图片的宽高
        val width = options.outWidth
        val height = options.outHeight
        //图片的缩放比例
        var scale = 1
        while (width / scale > outW || height / scale > outH) {
            scale *= 2
        }
        //设置读取图片内容的参数
        options.inJustDecodeBounds = false
        //设置图片读取的缩放比例
        options.inSampleSize = scale
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        return BitmapFactory.decodeFile(path, options)
    }

}