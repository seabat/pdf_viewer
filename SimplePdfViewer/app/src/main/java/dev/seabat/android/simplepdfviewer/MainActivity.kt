package dev.seabat.android.simplepdfviewer

import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var pdfRenderer: PdfRenderer
    private lateinit var adapter: PDFPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)

        // PDFファイルをアプリの内部ストレージにコピー
        copyPdfToInternalStorage()

        // PDFをレンダリング
        renderPDF()
    }

    override fun onDestroy() {
        super.onDestroy()
        pdfRenderer.close()
    }
    private fun copyPdfToInternalStorage() {
        val outputFile = File(filesDir, "sample.pdf")
        assets.open("sample.pdf").use { inputStream ->
            outputFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }

    private fun renderPDF() {
        val file = File(filesDir, "sample.pdf")
        try {
            val parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
            pdfRenderer = PdfRenderer(parcelFileDescriptor)

            // アダプターを初期化してViewPager2に設定
            adapter = PDFPagerAdapter(this, pdfRenderer)
            viewPager.adapter = adapter
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}