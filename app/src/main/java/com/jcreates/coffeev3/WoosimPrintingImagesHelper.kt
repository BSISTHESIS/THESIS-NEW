package com.jcreates.coffeev3

import android.graphics.Bitmap
import com.jcreates.printooth.data.PrintingImagesHelper
import com.woosim.printer.WoosimImage

class WoosimPrintingImagesHelper : PrintingImagesHelper{
    override fun getBitmapAsByteArray(bitmap: Bitmap): ByteArray {
        return WoosimImage.printRGBbitmap(0, 0, 384, bitmap.height, bitmap)
    }

}