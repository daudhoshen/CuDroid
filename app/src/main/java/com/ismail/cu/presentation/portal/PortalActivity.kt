package com.ismail.cu.presentation.portal

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.ismail.cu.R
import com.ismail.cu.base.BaseActivity
import com.ismail.cu.databinding.ActivityPortalBinding

class PortalActivity : BaseActivity<ActivityPortalBinding>(
    viewBindingFactory = ActivityPortalBinding::inflate
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

            loadUrl(
                "https://cityuniversity.orbund.com/einstein-freshair/touch/index.jsp"
            )
        }

        binding.toolBar.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}