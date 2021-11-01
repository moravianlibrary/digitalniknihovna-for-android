package cz.mzk.kramerius.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebView = new WebView(this);
        setContentView(mWebView);
        mWebView.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.getAllowFileAccess();
        try {
            Method m1 = WebSettings.class.getMethod("setDomStorageEnabled", new Class[]{Boolean.TYPE});
            m1.invoke(webSettings, Boolean.TRUE);
            Method m2 = WebSettings.class.getMethod("setDatabaseEnabled", new Class[]{Boolean.TYPE});
            m2.invoke(webSettings, Boolean.TRUE);
            Method m3 = WebSettings.class.getMethod("setDatabasePath", new Class[]{String.class});
            m3.invoke(webSettings, "/data/data/" + getPackageName() + "/databases/");
            Method m4 = WebSettings.class.getMethod("setAppCacheMaxSize", new Class[]{Long.TYPE});
            m4.invoke(webSettings, 1024*1024*8);
            Method m5 = WebSettings.class.getMethod("setAppCachePath", new Class[]{String.class});
            m5.invoke(webSettings, "/data/data/" + getPackageName() + "/cache/");
            Method m6 = WebSettings.class.getMethod("setAppCacheEnabled", new Class[]{Boolean.TYPE});
            m6.invoke(webSettings, Boolean.TRUE);
        }
        catch (NoSuchMethodException e) {
        }
        catch (InvocationTargetException e) {
        }
        catch (IllegalAccessException e) {
        }
        mWebView.loadUrl("https://webview.digitalniknihovna.cz");
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if ("webview.digitalnihnihovna.cz".equals(Uri.parse(url).getHost())) {
                return false;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}


