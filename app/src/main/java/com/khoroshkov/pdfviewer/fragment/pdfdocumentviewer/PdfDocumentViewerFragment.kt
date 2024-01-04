package com.khoroshkov.pdfviewer.fragment.pdfdocumentviewer

import android.graphics.Color
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.barteksc.pdfviewer.PDFView

private const val PAGE_SPACING_IN_DP = 8
private const val MAX_DOCUMENT_ZOOM = 10F

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PdfDocumentViewerFragment(pdfUriState: MutableState<Uri?>) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = { PdfDocumentTopAppBar() },
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) { innerPadding ->
        PdfDocumentContent(pdfUriState, innerPadding)
    }
}

@Composable
private fun PdfDocumentContent(pdfUriState: MutableState<Uri?>, innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        PdfDocumentView(pdfUriState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PdfDocumentTopAppBar() {
    TopAppBar(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        title = { Text(text = "Document") } // TODO file name
    )
}

@Composable
private fun PdfDocumentView(pdfUriState: MutableState<Uri?>) {
    val pdfUri by pdfUriState
    pdfUri ?: return

    AndroidView(
        modifier = Modifier
            .fillMaxSize(),
        factory = { context ->
            PDFView(context, null)
        },
        update = { pdfView ->
            pdfView.setBackgroundColor(Color.TRANSPARENT)
            pdfView.maxZoom = MAX_DOCUMENT_ZOOM

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
