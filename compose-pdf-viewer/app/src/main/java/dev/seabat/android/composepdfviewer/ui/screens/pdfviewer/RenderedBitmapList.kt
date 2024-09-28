package dev.seabat.android.composepdfviewer.ui.screens.pdfviewer

import android.graphics.Bitmap
import java.util.LinkedList

data class RenderedBitmap(val pageIndex: Int, val bitmap: Bitmap)

/**
 * レンダリング済みのビットマップを保持するリスト
 *
 * - maxSizeで指定されたサイズを持つリストを保持する
 *
 * @property maxSize
 */
class RenderedBitmapList(private val maxSize: Int) {

    /**
     * 要素の新旧を管理する List
     */
    private val list = LinkedList<RenderedBitmap>()

    /**
     * ページインデックスで要素を検索するための Map
     */
    private val map = LinkedHashMap<Int, RenderedBitmap>()

    /**
     * リストにレンダリング済みのビットマップを追加する
     *
     * - リストとマップそれぞれに要素を追加する
     * - maxSizeを超える場合は、先頭の要素を削除する
     * - 既に同じpageIndexを持つRenderedBitmapが存在する場合は何もしない
     *
     * @param renderedBitmap
     */
    fun add(renderedBitmap: RenderedBitmap) {
        // 既に同じpageIndexを持つRenderedBitmapが存在する場合は何もしない
        if (map.containsKey(renderedBitmap.pageIndex)) {
            return
        }

        // リストのサイズが maxSize を超える場合は、先頭の要素を削除
        if (list.size >= maxSize) {
            val removedBitmap = list.removeFirst()
            map.remove(removedBitmap.pageIndex)
        }

        // リストの最後に要素を追加
        list.add(renderedBitmap)
        map[renderedBitmap.pageIndex] = renderedBitmap
    }

    /**
     * ページインデックスでレンダリング済みのビットマップを検索する
     *
     * @param pageIndex
     * @return
     */
    fun get(pageIndex: Int): RenderedBitmap? = map[pageIndex]

    /**
     * ページインデックスに相当するレンダリング済みのビットマップが存在するかを判定する
     *
     * @param pageIndex
     * @return
     */
    fun contains(pageIndex: Int): Boolean = map.containsKey(pageIndex)
}