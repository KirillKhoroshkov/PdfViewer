package com.khoroshkov.pdfviewer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import com.khoroshkov.pdfviewer.fragment.pdfdocumentviewer.PdfDocumentViewerFragment
import com.khoroshkov.pdfviewer.ui.theme.PdfViewerTheme

class MainActivity : ComponentActivity() {

    private val pdfUriState = mutableStateOf<Uri?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PdfViewerTheme {
                PdfDocumentViewerFragment(pdfUriState)
            }
        }

        handleIntentIfNeeded(intent)
    }

    private fun handleIntentIfNeeded(currentIntent: Intent) {
        val pdfUri = when (currentIntent.action) {
            Intent.ACTION_VIEW -> {
                println("DDDDDDDDDDDDDDDD ACTION_VIEW") // TODO delete
                currentIntent.data
            }
            Intent.ACTION_SEND -> {
                println("DDDDDDDDDDDDDDDD ACTION_SEND") // TODO delete
                currentIntent.getParcelableExtra(Intent.EXTRA_STREAM)
            }
            else -> null
        }

        pdfUri ?: return

        pdfUriState.value = pdfUri
    }
}
