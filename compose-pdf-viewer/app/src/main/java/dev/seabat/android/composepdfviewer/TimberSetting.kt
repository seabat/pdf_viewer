package dev.seabat.android.composepdfviewer

import timber.log.Timber

class TimberSetting : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, "PDF_VIEWER$tag", message, t)
    }

    // TAGを生成する。引数のelementからファイル名や行数などを取得できる。
    override fun createStackElementTag(element: StackTraceElement): String = String.format(
        " [C:%s] [L:%s] [M:%s]",
        super.createStackElementTag(element),
        element.lineNumber,
        element.methodName
    )
}