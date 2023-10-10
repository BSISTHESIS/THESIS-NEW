package com.jcreates.printooth.data

import android.graphics.Bitmap
import com.jcreates.printooth.utilities.ImageUtils

class DefaultPrintingImagesHelper:PrintingImagesHelper{
    override fun getBitmapAsByteArray(bitmap: Bitmap): ByteArray {
        return ImageUtils.decodeBitmap(bitmap)!!
    }

}