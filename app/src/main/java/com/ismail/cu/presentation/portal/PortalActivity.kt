package com.ismail.cu.presentation.portal

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.ismail.cu.R
import com.ismail.cu.base.BaseActivity
import com.ismail.cu.databinding.ActivityPortalBinding
import com.ismail.cu.util.makeGone
import com.ismail.cu.util.makeVisible

class PortalActivity : BaseActivity<ActivityPortalBinding>(
    onInflate = ActivityPortalBinding::inflate
) {
    @SuppressLint("SetJavaScriptEnabled")
    override fun initOnCreate(savedInstanceState: Bundle?) {
        binding.toolBar.title.text = getString(R.string.label_student_portal)

        binding.webView.webViewClient = WebViewClient()
        binding.webView.apply {
            settings.javaScriptEnabled = true
            settings.builtInZoomControls = true
            settings.setSupportZoom(true)
            settings.displayZoomControls = false
            settings.cacheMode = WebSettings.LOAD_NO_CACHE
            settings.domStorageEnabled = true
            settings.allowFileAccess = true
            settings.allowContentAccess = true
            settings.loadsImagesAutomatically = true
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            isVerticalScrollBarEnabled = true
            isHorizontalScrollBarEnabled = true
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(
                    view: WebView?,
                    url: String?,
                    favicon: Bitmap?
                ) {
                    super.onPageStarted(view, url, favicon)
                    binding.primaryLoader.makeVisible()
                }
                override fun onPageFinished(
                    view: WebView?,
                    url: String?
                ) {
                    super.onPageFinished(view, url)
                    binding.primaryLoader.makeGone()
                }
                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    if (request?.isForMainFrame == true) {
                        binding.primaryLoader.makeGone()
                    }
                }
            }
            loadUrl(
                "https://city.pipilikasoft.com/"
            )
        }

        binding.toolBar.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}