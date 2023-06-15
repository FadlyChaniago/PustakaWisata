package com.example.perpusdesa.ui.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.perpusdesa.R;
import com.github.barteksc.pdfviewer.PDFView;

public class DetailHomeActivity extends AppCompatActivity {

    private ImageView imageView;
    private ProgressBar progressBar;

    private static final String PDF_VIEWER_URL = "https://docs.google.com/gview?embedded=true&url=" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_home);

        Intent intent = getIntent();
        String pdfUrl = intent.getStringExtra("pdf");


        if (pdfUrl != null) {
            // Option 1: Using PDFView
            PDFView pdfView = findViewById(R.id.pdfView);
            pdfView.fromUri(Uri.parse(pdfUrl))
                    .defaultPage(0)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    /*
                    .onError(new OnErrorListener() {
                        @Override
                        public void onError(Throwable t) {
                        }
                    })
                    .onLoad(new OnLoadCompleteListener() {
                        @Override
                        public void loadComplete(int nbPages) {
                            progressBar.setVisibility(View.GONE);
                        }
                    })
                    .onPageChange(new OnPageChangeListener() {
                        private boolean isFirstPageRendered = false;
                        @Override
                        public void onPageChanged(int page, int pageCount) {
                            if (!isFirstPageRendered && page == 0) {
                                    progressBar.setVisibility(View.VISIBLE);
                                isFirstPageRendered = true;
                            }
                        }
                    })*/
                    .load();

            // Option 2: Using WebView
            WebView webView = findViewById(R.id.webView);
            final ProgressBar progressBar = findViewById(R.id.progressBar);

            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient() {
                int currentPage = 0;

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    progressBar.setVisibility(View.VISIBLE);
                    currentPage = 0;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    currentPage++;
                    if (currentPage == 1) {
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
//            webView.getSettings().setBuiltInZoomControls(true);
//            webView.getSettings().setDisplayZoomControls(true);
            webView.setWebViewClient(new WebViewClient());
            webView.setOverScrollMode(WebView.OVER_SCROLL_NEVER);

            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.setInitialScale(1);
            webView.loadUrl(PDF_VIEWER_URL + pdfUrl);

        }
    }
}
