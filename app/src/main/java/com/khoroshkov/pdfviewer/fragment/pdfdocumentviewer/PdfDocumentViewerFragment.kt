package com.khoroshkov.pdfviewer.fragment.pdfdocumentviewer

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.barteksc.pdfviewer.PDFView

private const val PAGE_SPACING_IN_DP = 8

@Composable
fun PdfDocumentViewerFragment(pdfUriState: MutableState<Uri?>) { // TODO show spinner on loading
    val pdfUri by pdfUriState
    pdfUri ?: return

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            PDFView(context, null)
        },
        update = { pdfView ->
            pdfView.fromUri(pdfUri)
                .onError {
                    // TODO show stub with reload button
                }
                .enableDoubletap(false)
                .spacing(PAGE_SPACING_IN_DP)
                .load()
        }
    )
}
