package com.dnamedeg.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dnamedeg.R;

import java.util.HashMap;
import java.util.Map;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;


public class ViewerActivity extends AppCompatActivity implements DownloadFile.Listener {

    WebView webview;
    ProgressBar progressbar;
    LinearLayout pdfView, indexes;
    TextView leftArrow, rightArrow, pageNumber;
    RemotePDFViewPager remotePDFViewPager;

    PDFPagerAdapter adapter;
    private PDFViewPager pdfViewPager;
    Map<String, String> extraHeaders = new HashMap<>();
    private Toolbar toolbar;


    public static void start(Context mContext, Bundle bundle) {
        Intent intent = new Intent(mContext, ViewerActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);



//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setTitle("Notes");

        webview = findViewById(R.id.webview);
        pdfViewPager = findViewById(R.id.pdfViewPager);
        progressbar = findViewById(R.id.progressbar);
        pdfView = findViewById(R.id.pdfView);
        indexes = findViewById(R.id.indexes);

        toolbar = findViewById(R.id.toolbar);
        leftArrow = findViewById(R.id.left_arrow);
        rightArrow = findViewById(R.id.right_arrow);
        pageNumber = findViewById(R.id.pageNumber);

        setSupportActionBar(toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));

        try {
            openDocument();
        } catch (Exception e) {
            progressbar.setVisibility(View.GONE);
            e.printStackTrace();
        }


        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (remotePDFViewPager.getCurrentItem() > 0) {
                    remotePDFViewPager.setCurrentItem(remotePDFViewPager.getCurrentItem() - 1);
                    // pageNumber.setText(remotePDFViewPager.getCurrentItem()+"/"+remotePDFViewPager.getAdapter().getCount());
                }

            }
        });


        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (remotePDFViewPager.getCurrentItem() <= remotePDFViewPager.getAdapter().getCount()) {
                    remotePDFViewPager.setCurrentItem(remotePDFViewPager.getCurrentItem() + 1);
                    //pageNumber.setText(remotePDFViewPager.getCurrentItem()+"/"+remotePDFViewPager.getAdapter().getCount());

                }

            }
        });
    }

    private void openDocument() {
        webview.setVisibility(View.VISIBLE);

        WebSettings settings = webview.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setBuiltInZoomControls(true);


        webview.loadUrl(getIntent().getStringExtra("url"));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progressbar.getVisibility() == View.VISIBLE)
                    progressbar.setVisibility(View.VISIBLE);

            }
        }, 2 * 1000);

        webview.setWebChromeClient(new WebChromeClient());
        webview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                pdfView.setVisibility(View.VISIBLE);
                pdfViewPager.setVisibility(View.VISIBLE);
                webview.setVisibility(View.GONE);
                downloadFile(getIntent().getStringExtra("url"));

            }
        });
    }

    private void downloadFile(String url) {
        remotePDFViewPager = new RemotePDFViewPager(this, url, this, null);
        remotePDFViewPager.setId(R.id.pdfViewPager);


    }


    public static void open(Context context) {
        Intent i = new Intent(context, ViewerActivity.class);
        context.startActivity(i);
    }


    public void updateLayout() {
        pdfView.removeAllViewsInLayout();
        progressbar.setVisibility(View.GONE);
        pdfView.addView(remotePDFViewPager,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (remotePDFViewPager != null) {
            indexes.setVisibility(View.VISIBLE);
            pageNumber.setText("1/" + remotePDFViewPager.getAdapter().getCount());


            remotePDFViewPager.setVerticalScrollBarEnabled(true);
            remotePDFViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int position) {

                    pageNumber.setText((position + 1) + "/" + remotePDFViewPager.getAdapter().getCount());

                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
        }
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        adapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        remotePDFViewPager.setAdapter(adapter);
        progressbar.setVisibility(View.GONE);

        updateLayout();
    }

    @Override
    public void onFailure(Exception e) {
        e.printStackTrace();
        pdfViewPager.setVisibility(View.GONE);

    }

    @Override
    public void onProgressUpdate(int progress, int total) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (adapter != null) {
            adapter.close();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();

        return super.onOptionsItemSelected(item);
    }

}
