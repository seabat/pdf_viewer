package dev.seabat.android.composepdfviewer

import android.content.Context
import timber.log.Timber

class TimberSetting(
    private val context: Context
): Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, "PDF_VIEWER" + tag, message, t)
    }
    // TAGを生成する。引数のelementからファイル名や行数などを取得できる。
    override fun createStackElementTag(element: StackTraceElement): String {
        return String.format(" [C:%s] [L:%s] [M:%s]",
            super.createStackElementTag(element),
            element.lineNumber,
            element.methodName)
    }

}