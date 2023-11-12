package dev.seabat.android.composepdfviewer.domain.entity

data class PdfListEntity(val pdfList: MutableList<PdfEntity>): MutableList<PdfEntity> by pdfList
