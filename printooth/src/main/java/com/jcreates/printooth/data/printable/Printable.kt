package com.jcreates.printooth.data.printable

import com.jcreates.printooth.data.printer.Printer

interface Printable {
    fun getPrintableByteArray(printer: Printer): List<ByteArray>
}
