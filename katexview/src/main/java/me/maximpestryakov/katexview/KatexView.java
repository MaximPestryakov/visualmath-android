package me.maximpestryakov.katexview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class KatexView extends WebView {

    private String text;

    public KatexView(Context context, AttributeSet attrs) {
        super(context, attrs);

        getSettings().setJavaScriptEnabled(true);
        getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        setBackgroundColor(Color.TRANSPARENT);
        loadUrl("file:///android_asset/index.html");
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
        addJavascriptInterface(new JsObject(text), "data");
        /*
        addJavascriptInterface(new Object() {
            @JavascriptInterface
            String getText() {
                Log.d("getText", "I am here");
                return text;
            }
        }, "data");
        */
        reload();
    }

    private static class JsObject {
        private String text;

        JsObject(String text) {
            this.text = text;
        }

        @JavascriptInterface
        public String getText() {
            return text;
        }
    }
}
