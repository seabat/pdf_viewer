package dev.seabat.android.composepdfviewer.domain.repository

import android.net.Uri
import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.domain.entity.PdfResourceEntity
import dev.seabat.android.composepdfviewer.domain.exception.PdfViewerException

interface LocalFileRepositoryContract {

    /**
     * アプリのプライベート領域の files ディレクトリに格納されたファイル一覧を取得する
     *
     * @return PdfListEntity
     */
    suspend fun fetch(): PdfListEntity

    /**
     * Uri に配置されたファイルをアプリのプライベート領域の files ディレクトリに追加する
     *
     * @param uri
     */
    @Throws(PdfViewerException::class)
    suspend fun add(uri: Uri): PdfResourceEntity

    /**
     * アセットファイルをアプリのプライベート領域の files ディレクトリに追加する
     *
     * @return
     */
    @Throws(PdfViewerException::class)
    suspend fun importAssetsFile(): PdfResourceEntity

    /**
     * アプリのプライベート領域の files ディレクトリに格納されたファイルを削除する
     *
     * @param fileName ex. sample.pdf
     */
    suspend fun remove(fileName: String)
}