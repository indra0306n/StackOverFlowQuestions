package com.indra.stackoverflowquestions.view

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.indra.stackoverflowquestions.R
import com.indra.stackoverflowquestions.util.Constants.Companion.INTENT_BUNDLE_WEBVIEW
import kotlinx.android.synthetic.main.activity_web_view.*

class QuestionDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val webUrl = intent.getStringExtra(INTENT_BUNDLE_WEBVIEW)
        if (webUrl != null) {
            loadWebView(webUrl)
        }
    }

    private fun loadWebView(webUrl: String) {
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let { view?.loadUrl(it) }
                return true
            }
        }
        webUrl.let { webView.loadUrl(it) }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}