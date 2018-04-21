package cn.edu.sustc.androidclient.view.about;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zzhoujay.richtext.RichText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import cn.edu.sustc.androidclient.R;

public class AboutActivity extends AppCompatActivity {
    public static void start(Context context){
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView textView = findViewById(R.id.about_tv);
        RichText.fromMarkdown(getMarkdown()).into(textView);
    }

    private String getMarkdown(){
        StringBuilder builder = new StringBuilder();
        try{
            InputStream inputStream = getResources().getAssets().open("about.md");
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null){
                builder.append(line + "\n");
            }
            reader.close();
            bufferedReader.close();
            String result = builder.toString();
            Logger.v("Result: %s", result);
            return result;

        }catch (Exception e){
            Logger.e("Cannot Load About File!");
        }
        return "# 关于我们";
    }
}
