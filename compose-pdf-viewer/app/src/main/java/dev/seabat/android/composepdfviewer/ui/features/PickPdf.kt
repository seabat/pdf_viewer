package dev.seabat.android.composepdfviewer.ui.features

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

class PickPdf : ActivityResultContract<Unit, Uri?>() {
    override fun createIntent(
        context: Context,
        input: Unit
    ) = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "application/pdf"
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        if (resultCode == android.app.Activity.RESULT_OK && intent != null) {
            return intent.data
        }
        return null
    }
}