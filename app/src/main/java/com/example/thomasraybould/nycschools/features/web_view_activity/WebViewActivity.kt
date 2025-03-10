package com.example.thomasraybould.nycschools.features.web_view_activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.thomasraybould.nycschools.R

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_view_activity)

        val webView = findViewById<WebView>(R.id.webView)

        webView.webViewClient = WebViewClient()
        intent.getStringExtra(URL_KEY)
            ?.let { webpageUrl ->
                if (webpageUrl.contains("http")) {
                    webView.loadUrl(webpageUrl)
                }else{
                    webView.loadUrl("https://$webpageUrl")
                }
            }
    }

    companion object {
        private const val URL_KEY = "url_key"

        fun startWebViewActivity(context: Context, url: String) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(URL_KEY, url)
            context.startActivity(intent)
        }
    }

}