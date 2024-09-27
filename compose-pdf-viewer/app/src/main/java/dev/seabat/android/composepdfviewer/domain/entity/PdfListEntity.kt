package dev.seabat.android.composepdfviewer.domain.entity

data class PdfListEntity(val pdfList: MutableList<PdfResourceEntity>) :
    MutableList<PdfResourceEntity> by pdfList
