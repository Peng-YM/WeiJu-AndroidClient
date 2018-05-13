package cn.edu.sustc.androidclient.view.about;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.sustc.androidclient.R;

public class AboutActivity extends AppCompatActivity {
    @BindView(R.id.about_wv)
    public WebView webView;

    public static void start(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        webView.loadUrl("file:///android_asset/index.html");
    }
}
