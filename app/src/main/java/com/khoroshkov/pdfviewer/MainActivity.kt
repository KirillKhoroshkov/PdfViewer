package com.khoroshkov.pdfviewer

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.mutableStateOf
import com.khoroshkov.pdfviewer.fragment.pdfdocumentviewer.PdfDocumentViewerFragment
import com.khoroshkov.pdfviewer.ui.theme.PdfViewerTheme

class MainActivity : ComponentActivity() {

    private val pdfUriState = mutableStateOf<Uri?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navigationBarStyle = when {
                isSystemInDarkTheme() -> SystemBarStyle.dark(Color.TRANSPARENT)
                else -> SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
            }

            enableEdgeToEdge(navigationBarStyle = navigationBarStyle)

            PdfViewerTheme {
                PdfDocumentViewerFragment(pdfUriState)
            }
        }

        handleIntentIfNeeded(intent)
    }

    private fun handleIntentIfNeeded(currentIntent: Intent) {
        val pdfUri = when (currentIntent.action) {
            Intent.ACTION_VIEW -> currentIntent.data
            Intent.ACTION_SEND -> currentIntent.getParcelableExtra(Intent.EXTRA_STREAM)
            else -> null
        }

        pdfUri ?: return

        pdfUriState.value = pdfUri
    }
}
