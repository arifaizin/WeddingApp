package id.hamasah.annisarif.fragment;


import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import id.hamasah.annisarif.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FacebookFragment extends Fragment {


    private int NUMBER_OF_COMMENTS = 100;

    public FacebookFragment() {
        // Required empty public constructor
    }

    private static final String TAG = "FacebookFragment";
    private WebView mWebViewComments;
    private FrameLayout mContainer;
    private ProgressBar progressBar;
    boolean isLoading;
    private WebView mWebviewPop;
    private String postUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_facebook, container, false);

    mWebViewComments = (WebView) view.findViewById(R.id.commentsView);
    mContainer = (FrameLayout) view.findViewById(R.id.webview_frame);
    progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

//    postUrl = getIntent().getStringExtra("url");
    postUrl = "http://www.hamaasa.id";

    // finish the activity in case of empty url
        if (TextUtils.isEmpty(postUrl)) {
        Toast.makeText(getActivity(), "The web url shouldn't be empty", Toast.LENGTH_LONG).show();
//        finish()
    }

    setLoading(true);
    loadComments();
        return view;

    }

    private void loadComments() {
        mWebViewComments.setWebViewClient(new UriWebViewClient());
        mWebViewComments.setWebChromeClient(new UriChromeClient());
        mWebViewComments.getSettings().setJavaScriptEnabled(true);
        mWebViewComments.getSettings().setAppCacheEnabled(true);
        mWebViewComments.getSettings().setDomStorageEnabled(true);
        mWebViewComments.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebViewComments.getSettings().setSupportMultipleWindows(true);
        mWebViewComments.getSettings().setSupportZoom(false);
        mWebViewComments.getSettings().setBuiltInZoomControls(false);
        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 21) {
            mWebViewComments.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager.getInstance().setAcceptThirdPartyCookies(mWebViewComments, true);
        }

        // facebook comment widget including the article url
        String html = "<!doctype html> <html lang=\"en\"> <head></head> <body> " +
//                "<div \n" +
//                "  class=\"fb-post\" \n" +
//                "  data-href=\""+postUrl+"\" \n" +
//                "  data-width=\"500\"></div>"+
                "<div id=\"fb-root\"></div> <script>(function(d, s, id) { var js, fjs = d.getElementsByTagName(s)[0]; if (d.getElementById(id)) return; js = d.createElement(s); js.id = id; js.src = \"//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.6\"; fjs.parentNode.insertBefore(js, fjs); }(document, 'script', 'facebook-jssdk'));</script> " +
                "<div class=\"fb-comments\" data-href=\"" + postUrl + "\" " +
                "data-numposts=\"" + NUMBER_OF_COMMENTS + "\" data-order-by=\"reverse_time\">" +
                "</div> </body> </html>";

        mWebViewComments.loadDataWithBaseURL("http://www.hamaasa.id", html, "text/html", "UTF-8", null);
        mWebViewComments.setMinimumHeight(200);
    }

    private void setLoading(boolean isLoading) {
        this.isLoading = isLoading;

        if (isLoading)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);

    }

private class UriWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        String host = Uri.parse(url).getHost();

        return !host.equals("m.facebook.com");

    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        String host = Uri.parse(url).getHost();
        setLoading(false);
        if (url.contains("/plugins/close_popup.php?reload")) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    mContainer.removeView(mWebviewPop);
                    loadComments();
                }
            }, 600);
        }
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                   SslError error) {
        setLoading(false);
    }
}

class UriChromeClient extends WebChromeClient {

    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog,
                                  boolean isUserGesture, Message resultMsg) {
        mWebviewPop = new WebView(getActivity());
        mWebviewPop.setVerticalScrollBarEnabled(false);
        mWebviewPop.setHorizontalScrollBarEnabled(false);
        mWebviewPop.setWebViewClient(new UriWebViewClient());
        mWebviewPop.setWebChromeClient(this);
        mWebviewPop.getSettings().setJavaScriptEnabled(true);
        mWebviewPop.getSettings().setDomStorageEnabled(true);
        mWebviewPop.getSettings().setSupportZoom(false);
        mWebviewPop.getSettings().setBuiltInZoomControls(false);
        mWebviewPop.getSettings().setSupportMultipleWindows(true);
        mWebviewPop.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mContainer.addView(mWebviewPop);
        WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
        transport.setWebView(mWebviewPop);
        resultMsg.sendToTarget();

        return true;
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage cm) {
        Log.i(TAG, "onConsoleMessage: " + cm.message());
        return true;
    }

    @Override
    public void onCloseWindow(WebView window) {
    }
}


}
