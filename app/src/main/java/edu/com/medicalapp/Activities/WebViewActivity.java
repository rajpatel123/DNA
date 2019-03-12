package edu.com.medicalapp.Activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;



import butterknife.BindView;
import butterknife.ButterKnife;
import edu.com.medicalapp.R;
import edu.com.medicalapp.utils.Utils;

import static android.view.View.GONE;


public class WebViewActivity extends AppCompatActivity {
    private static final String TAG = "WebViewActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.dataWebView)
    WebView mWebView;
    @BindView(R.id.rlNetworkUI)
    RelativeLayout rlNetworkUI;
    @BindView(R.id.rlWebView)
    RelativeLayout rlWebView;

    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_usdetails);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        title = getIntent().getStringExtra("title");
            getSupportActionBar().setTitle(title);
        try {
            initComponent();
        } catch (Exception e) {
            e.printStackTrace();
            String additionalDetail = null;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void initComponent() throws Exception {

        mWebView.setWebViewClient(new myWebClient());
        mWebView.getSettings().setJavaScriptEnabled(true);


        getSupportActionBar().setTitle(title);
        if (!Utils.isInternetConnected(this)) {
            rlWebView.setVisibility(GONE);
            rlNetworkUI.setVisibility(View.VISIBLE);
//            rlNetworkUI.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try {
//                        initComponent();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
            return;
        }

        rlWebView.setVisibility(View.VISIBLE);
        rlNetworkUI.setVisibility(View.GONE);

        switch (title) {
            case "Terms & Conditions":
                progressBar.setVisibility(View.VISIBLE);
                mWebView.loadUrl("http://reddyenterprise.com/education/termsncondition.php");
                break;

            case "Privacy Policy":
                progressBar.setVisibility(View.VISIBLE);
                mWebView.loadUrl("http://reddyenterprise.com/education/privacy-policy.php");
                break;

            case "Read More":
                progressBar.setVisibility(View.VISIBLE);
                mWebView.loadUrl("http://www.readingrockets.org/article/reading-information");
                break;


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

    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            progressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(GONE);
        }
    }
}

