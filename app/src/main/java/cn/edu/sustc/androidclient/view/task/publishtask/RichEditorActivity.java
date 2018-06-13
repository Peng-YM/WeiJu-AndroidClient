package cn.edu.sustc.androidclient.view.task.publishtask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.scrat.app.richtext.RichEditText;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.databinding.ActivityRichEditorBinding;

public class RichEditorActivity extends AppCompatActivity {
    private RichEditText taskEditor;
    private ActivityRichEditorBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rich_editor);
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_rich_editor);
        taskEditor = binding.knife;
        taskEditor.setSelection(taskEditor.getEditableText().length());
        taskEditor.fromHtml(
                "<blockquote>猫照片收集</blockquote>" +
                        "<ul>" +
                        "<li>要可爱</li>" +
                        "<li>多个角度</li>" +
                        "<li>gif或者视频皆可</li>" +
                        "</ul>" +
                        "<p>实例图片</p>"+
                        "<img src=\"http://biuugames.huya.com/221d89ac671feac1.gif\"><br><br>" +
                        "<img src=\"http://biuugames.huya.com/5-160222145918.jpg\"><br><br>");
        setUpEditor();
    }

    private void setUpEditor() {
        setupBold();
        setupItalic();
        setupUnderline();
        setupStrikethrough();
        setupBullet();
        setupQuote();
        setupLink();
        setupClear();
    }

    private void setupBold() {
        ImageButton bold = binding.bold;

        bold.setOnClickListener(v -> taskEditor.bold(!taskEditor.contains(RichEditText.FORMAT_BOLD)));

        bold.setOnLongClickListener(v -> {
            Toast.makeText(RichEditorActivity.this, R.string.toast_bold, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupItalic() {
        ImageButton italic = binding.italic;

        italic.setOnClickListener(v -> taskEditor.italic(!taskEditor.contains(RichEditText.FORMAT_ITALIC)));

        italic.setOnLongClickListener(v -> {
            Toast.makeText(RichEditorActivity.this, R.string.toast_italic, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupUnderline() {
        ImageButton underline = binding.underline;

        underline.setOnClickListener(v -> taskEditor.underline(!taskEditor.contains(RichEditText.FORMAT_UNDERLINED)));

        underline.setOnLongClickListener(v -> {
            Toast.makeText(RichEditorActivity.this, R.string.toast_underline, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupStrikethrough() {
        ImageButton strikethrough = binding.strikeThrough;

        strikethrough.setOnClickListener(v -> taskEditor.strikethrough(!taskEditor.contains(RichEditText.FORMAT_STRIKETHROUGH)));

        strikethrough.setOnLongClickListener(v -> {
            Toast.makeText(RichEditorActivity.this, R.string.toast_strikethrough, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupBullet() {
        ImageButton bullet = binding.bullet;

        bullet.setOnClickListener(v -> taskEditor.bullet(!taskEditor.contains(RichEditText.FORMAT_BULLET)));


        bullet.setOnLongClickListener(v -> {
            Toast.makeText(RichEditorActivity.this, R.string.toast_bullet, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupQuote() {
        ImageButton quote = binding.quote;

        quote.setOnClickListener(v -> taskEditor.quote(!taskEditor.contains(RichEditText.FORMAT_QUOTE)));

        quote.setOnLongClickListener(v -> {
            Toast.makeText(RichEditorActivity.this, R.string.toast_quote, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupLink() {
        ImageButton link = binding.link;

        link.setOnClickListener(v -> showLinkDialog());

        link.setOnLongClickListener(v -> {
            Toast.makeText(RichEditorActivity.this, R.string.toast_insert_link, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupClear() {
        ImageButton clear = binding.clear;

        clear.setOnClickListener(v -> taskEditor.clearFormats());

        clear.setOnLongClickListener(v -> {
            Toast.makeText(RichEditorActivity.this, R.string.toast_format_clear, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void showLinkDialog() {
        final int start = taskEditor.getSelectionStart();
        final int end = taskEditor.getSelectionEnd();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        View view = getLayoutInflater().inflate(R.layout.dialog_link, null, false);
        final EditText editText = view.findViewById(R.id.edit);
        builder.setView(view);
        builder.setTitle(R.string.dialog_title);

        builder.setPositiveButton(R.string.dialog_button_ok, (dialog, which) -> {
            String link = editText.getText().toString().trim();
            if (TextUtils.isEmpty(link)) {
                return;
            }
            Logger.d("URL：" + link);
            Uri uri = Uri.parse(link);
            taskEditor.image(uri);
        });

        builder.setNegativeButton(R.string.dialog_button_cancel, (dialog, which) -> {
            // DO NOTHING HERE
        });

        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.undo:
                taskEditor.undo();
                break;
            case R.id.redo:
                taskEditor.redo();
                break;
            case R.id.finish:
                AlertDialog dialog = new AlertDialog
                        .Builder(this)
                        .setTitle(R.string.info)
                        .setMessage(R.string.submit_permit)
                        .setPositiveButton(R.string.dialog_button_ok, (dialogInterface, i) -> {
                            // return html back to task publish activity
                            Intent intentHtml = new Intent();
                            intentHtml.putExtra("HTML", taskEditor.toHtml());
                            setResult(2, intentHtml);
                            finish();
                        })
                        .setNegativeButton(R.string.dialog_button_cancel, ((dialogInterface, i) -> {
                            dialogInterface.cancel();
                        }))
                        .create();
                dialog.show();
            default:
                break;
        }
        return true;
    }
}
