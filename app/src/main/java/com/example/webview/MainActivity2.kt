package com.example.webview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity


class MainActivity2 : AppCompatActivity() {
    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        webView = findViewById<WebView>(R.id.webview) as WebView
        webView.loadUrl("http://192.168.0.105:3000")
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.settings.setSupportZoom(true)
        webView.settings.domStorageEnabled = true
        webView.settings.useWideViewPort =true
        onBackPressedDispatcher.addCallback(this.onBackPressedCallback)

        webView.addJavascriptInterface(WebAppInterface(this), "Android")
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (webView.canGoBack()) {
                webView.goBack()
            }
        }
    }
}

/** Instantiate the interface and set the context.  */
class WebAppInterface(private val mContext: Context) {

    /** Show a toast from the web page.  */
    @JavascriptInterface
    fun openPlayer(toast: String) {
        Log.d("TAG", toast)
        val main = Intent(mContext, MainActivity::class.java)
        main.putExtra("url", toast)
        mContext.startActivity(main)
    }
}