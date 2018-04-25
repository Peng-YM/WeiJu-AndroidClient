package cn.edu.sustc.androidclient.view.task.publishtask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.scrat.app.richtext.RichEditText;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.base.BaseActivity;
import cn.edu.sustc.androidclient.databinding.ActivityTaskPublishBinding;

public class TaskPublishActivity extends BaseActivity<TaskPublishViewModel, ActivityTaskPublishBinding> {
    private RichEditText taskEditor;
    private ActivityTaskPublishBinding binding;

    public static void start(Context context) {
        Intent intent = new Intent(context, TaskPublishActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected Class<TaskPublishViewModel> getViewModel() {
        return TaskPublishViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, TaskPublishViewModel viewModel, ActivityTaskPublishBinding binding) {
        this.binding = binding;

        taskEditor = binding.knife;
        taskEditor.setSelection(taskEditor.getEditableText().length());
        taskEditor.fromHtml(
                "<blockquote>Android 端的富文本编辑器</blockquote>" +
                        "<ul>" +
                        "<li>支持实时编辑</li>" +
                        "<li>支持图片插入,加粗,斜体,下划线,删除线,列表,引用块,超链接,撤销与恢复等</li>" +
                        "<li>使用<u>Glide 4</u>加载图片</li>" +
                        "</ul>" +
                        "<img src=\"http://biuugames.huya.com/221d89ac671feac1.gif\"><br><br>" +
                        "<img src=\"http://biuugames.huya.com/5-160222145918.jpg\"><br><br>"
        );
        setUpEditor();
    }

    private void closeKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
            Toast.makeText(TaskPublishActivity.this, R.string.toast_bold, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupItalic() {
        ImageButton italic = binding.italic;

        italic.setOnClickListener(v -> taskEditor.italic(!taskEditor.contains(RichEditText.FORMAT_ITALIC)));

        italic.setOnLongClickListener(v -> {
            Toast.makeText(TaskPublishActivity.this, R.string.toast_italic, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupUnderline() {
        ImageButton underline = binding.underline;

        underline.setOnClickListener(v -> taskEditor.underline(!taskEditor.contains(RichEditText.FORMAT_UNDERLINED)));

        underline.setOnLongClickListener(v -> {
            Toast.makeText(TaskPublishActivity.this, R.string.toast_underline, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupStrikethrough() {
        ImageButton strikethrough = binding.strikethrough;

        strikethrough.setOnClickListener(v -> taskEditor.strikethrough(!taskEditor.contains(RichEditText.FORMAT_STRIKETHROUGH)));

        strikethrough.setOnLongClickListener(v -> {
            Toast.makeText(TaskPublishActivity.this, R.string.toast_strikethrough, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupBullet() {
        ImageButton bullet = binding.bullet;

        bullet.setOnClickListener(v -> taskEditor.bullet(!taskEditor.contains(RichEditText.FORMAT_BULLET)));


        bullet.setOnLongClickListener(v -> {
            Toast.makeText(TaskPublishActivity.this, R.string.toast_bullet, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupQuote() {
        ImageButton quote = binding.quote;

        quote.setOnClickListener(v -> taskEditor.quote(!taskEditor.contains(RichEditText.FORMAT_QUOTE)));

        quote.setOnLongClickListener(v -> {
            Toast.makeText(TaskPublishActivity.this, R.string.toast_quote, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupLink() {
        ImageButton link = binding.link;

        link.setOnClickListener(v -> showLinkDialog());

        link.setOnLongClickListener(v -> {
            Toast.makeText(TaskPublishActivity.this, R.string.toast_insert_link, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupClear() {
        ImageButton clear = binding.clear;

        clear.setOnClickListener(v -> taskEditor.clearFormats());

        clear.setOnLongClickListener(v -> {
            Toast.makeText(TaskPublishActivity.this, R.string.toast_format_clear, Toast.LENGTH_SHORT).show();
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

            // When RichEditText lose focus, use this method
            taskEditor.link(link, start, end);
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
            case R.id.github:
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.app_repo)));
//                startActivity(intent);
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_task_publish;
    }
}
