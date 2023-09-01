package dev.seabat.android.simplepdfviewer

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException

class PDFPagerAdapter(private val activity: MainActivity, private val pdfRenderer: PdfRenderer) : RecyclerView.Adapter<PDFPagerAdapter.PDFViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PDFViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pdf_page_layout, parent, false)
        return PDFViewHolder(view)
    }

    override fun onBindViewHolder(holder: PDFViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return pdfRenderer.pageCount
    }

    inner class PDFViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val pdfImageView: ImageView = itemView.findViewById(R.id.pdfImageView)

        fun bind(position: Int) {
            val page = pdfRenderer.openPage(position)
            val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            pdfImageView.setImageBitmap(bitmap)
            page.close()
        }
    }
}