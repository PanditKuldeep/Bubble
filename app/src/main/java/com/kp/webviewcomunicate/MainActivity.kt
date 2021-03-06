package com.kp.webviewcomunicate

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var list = mutableListOf<Children>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list.add(Children("Olives",4319))
        list.add(Children("Tea",4159))
        list.add(Children("Mashed Potatoes",2583))
        list.add(Children("Boiled Potatoes",2074))
        list.add(Children("Milk",1894))
        list.add(Children("Chicken Salad",1809))
        list.add(Children("Vanilla Ice Cream",1713))

        loadWebView()
    }

    @SuppressLint("SetJavaScriptEnabled", "AddJavascriptInterface")
    private fun loadWebView() {
        webView.settings.javaScriptEnabled = true
        webView.settings.builtInZoomControls = false
        webView.webChromeClient = WebChromeClient()
        webView.addJavascriptInterface(WebAppInterface(), "androidButton")
        webView.loadUrl("file:///android_asset/d3.html")
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                val json = Gson().toJson(list)
                webView.loadUrl("javascript:renderChart('${json}')")
            }
        }
    }
    inner class WebAppInterface {
        @JavascriptInterface
        fun onCapturedButtonClicked(value:String) {
            val arr = value.split("=")
            val name = arr[0]
            val count = arr[1].toInt()
            Log.e("TAGA", "$name : $count")
        }
    }

    data class Children(
        var name:String,
        var count : Int
    )
}