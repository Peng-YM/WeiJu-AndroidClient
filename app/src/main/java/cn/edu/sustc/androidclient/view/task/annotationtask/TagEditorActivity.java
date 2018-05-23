package cn.edu.sustc.androidclient.view.task.annotationtask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.base.BaseActivity;
import cn.edu.sustc.androidclient.common.utils.FileUtils;
import cn.edu.sustc.androidclient.databinding.ActivityTagEditorBinding;

import static cn.edu.sustc.androidclient.model.data.AnnotationCommits.AnnotationTag;
import static cn.edu.sustc.androidclient.model.data.AnnotationCommits.Attribute;
import static cn.edu.sustc.androidclient.model.data.AnnotationCommits.AttributeType.BOOLEAN;
import static cn.edu.sustc.androidclient.model.data.AnnotationCommits.AttributeType.MULTI_OPTION;
import static cn.edu.sustc.androidclient.model.data.AnnotationCommits.AttributeType.NUMBER;
import static cn.edu.sustc.androidclient.model.data.AnnotationCommits.AttributeType.SINGLE_OPTION;
import static cn.edu.sustc.androidclient.model.data.AnnotationCommits.AttributeType.STRING;

public class TagEditorActivity extends BaseActivity<AnnotationTaskViewModel, ActivityTagEditorBinding> {
    private AnnotationTag tag;
    private AnnotationTaskViewModel viewModel;
    private ActivityTagEditorBinding binding;

    public static void start(Context context){
        Intent intent = new Intent(context, TagEditorActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected Class<AnnotationTaskViewModel> getViewModel() {
        return AnnotationTaskViewModel.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState, AnnotationTaskViewModel viewModel, ActivityTagEditorBinding binding) {
        this.viewModel = viewModel;
        this.binding = binding;
        // TODO: remove this
        String JSONString = FileUtils.readAssetFile(this, "annotationTag.json");
        try {
            tag = new Gson().fromJson(JSONString, AnnotationTag.class);
        }catch (Exception e){
            Logger.e("Cannot interpret JSON");
            e.printStackTrace();
        }
        Logger.json(JSONString);

        binding.tagName.setText(tag.name);
        binding.tagDescription.setText(tag.description);
        addAttributes();
    }

    private void addAttributes(){
        for(Attribute attribute: tag.attributes){
            Logger.v("Add attribute: %s", attribute.name);
            Logger.json(attribute.toString());
            TextView nameTv = new TextView(this);
            nameTv.setText(attribute.name);
            TextView descriptionTv = new TextView(this);
            descriptionTv.setText(attribute.description);
            binding.tagEditorLayout.addView(nameTv);
            binding.tagEditorLayout.addView(descriptionTv);
            switch(attribute.type){
                case SINGLE_OPTION:
                    // spinner

                    break;
                case MULTI_OPTION:
                    // check boxes

                    break;
                case BOOLEAN:
                    // radio button
                    
                    break;
                case STRING:{
                    // edit text
                    EditText editText = new EditText(this);
                    editText.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    ));
                    binding.tagEditorLayout.addView(editText);
                    break;
                }
                case NUMBER:{
                    // edit text
                    EditText editText = new EditText(this);
                    editText.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    ));
                    binding.tagEditorLayout.addView(editText);
                    // edit text
                    break;
                }
            }
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_tag_editor;
    }

}
