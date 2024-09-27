package dev.seabat.android.composepdfviewer.domain.usecase

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import dev.seabat.android.composepdfviewer.ui.screens.pdfviewer.Dimensions
import dev.seabat.android.composepdfviewer.ui.screens.pdfviewer.ZoomType
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

class RendererPdfUseCase @Inject constructor() : RendererPdfUseCaseContract {

    private val mutex = Mutex()

    override suspend fun invoke(
        renderer: PdfRenderer,
        pageNo: Int,
        displayArea: Dimensions,
        zoomType: ZoomType
    ): Bitmap =
        mutex.withLock {
            withContext(Dispatchers.IO) {
                val page = renderer.openPage(pageNo)
                val dimensions =
                    calculateBitmapDimensions(
                        Dimensions(page.width, page.height),
                        displayArea
                    )

                val bitmap =
                    when (zoomType) {
                        ZoomType.ZoomNone -> {
                            Bitmap.createBitmap(
                                dimensions.width,
                                dimensions.height,
                                Bitmap.Config.ARGB_8888
                            )
                        }
                        ZoomType.ZoomDouble -> {
                            Bitmap.createBitmap(
                                (dimensions.width * 2.0).toInt(),
                                (dimensions.height * 2.0).toInt(),
                                Bitmap.Config.ARGB_8888
                            )
                        }
                    }

                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                page.close()
                bitmap
            }
        }

    private fun calculateBitmapDimensions(
        pdf: Dimensions,
        displayArea: Dimensions
    ): Dimensions {
        val pdfRatio = pdf.height.toDouble() / pdf.width.toDouble()
        val deviceRatio = displayArea.height.toDouble() / displayArea.width.toDouble()
        return if (deviceRatio > pdfRatio) {
            // 端末の方が縦長である場合は横幅を目一杯広げる
            // つまり、生成する Bitmap の width は表示領域と同じなり、
            // height には width の拡大/縮小した比率を適用する。
            Dimensions(
                width = displayArea.width,
                height = ((displayArea.width.toDouble() / pdf.width.toDouble()) * pdf.height)
                    .toInt()
            )
        } else {
            Dimensions(
                width = ((displayArea.height.toDouble() / pdf.height.toDouble()) * pdf.width)
                    .toInt(),
                height = displayArea.height
            )
        }
    }
}