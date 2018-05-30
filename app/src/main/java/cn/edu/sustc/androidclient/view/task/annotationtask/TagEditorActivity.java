package cn.edu.sustc.androidclient.view.task.annotationtask;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.base.BaseActivity;
import cn.edu.sustc.androidclient.common.utils.FileUtils;
import cn.edu.sustc.androidclient.databinding.ActivityTagEditorBinding;
import cn.edu.sustc.androidclient.view.task.annotationtask.form.FormField;

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
    private AwesomeValidation awesomeValidation;
    private ArrayList<FormField> formFields;
    private Button fillUpBtn;

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
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);//创建一个做验证的对象

        fillUpBtn = findViewById(R.id.save_tag_button);
        formFields = new ArrayList<>();
        //动态生成的表单内容
        // TODO: remove this
        String JSONString = FileUtils.readAssetFile(this, "annotationTag.json");
        try {
            tag = new Gson().fromJson(JSONString, AnnotationTag.class);  //生成AnnotationTag这样的对象
        }catch (Exception e){
            Logger.e("Cannot interpret JSON");
            e.printStackTrace();
        }
        Logger.json(JSONString);

        binding.tagName.setText(tag.name);
        binding.tagDescription.setText(tag.description);
        binding.previewTagButton.setOnClickListener(v -> {});
        binding.saveTagButton.setOnClickListener(v -> { // 点击提交按钮的时候引用awesomevalidation验证
            if(awesomeValidation.validate()){
//                fillUpBtn.setBackgroundResource(R.drawable.button_background);
//                fillUpBtn.setTextColor(Color.parseColor("#FFF"));
                getResults();
            }
        });
        addAttributes();
    }

    private void addAttributes(){
        for(Attribute attribute: tag.attributes){
            Logger.v("Add attribute: %s", attribute.name);
            Logger.json(attribute.toString());
            TextView nameTv = new TextView(this);
            nameTv.setText(attribute.name);
            nameTv.setTextSize(20);
            TextView descriptionTv = new TextView(this);
            descriptionTv.setText(attribute.description);
            binding.tagEditorLayout.addView(nameTv);
            binding.tagEditorLayout.addView(descriptionTv);
            FormField field;
            switch(attribute.type){
                case SINGLE_OPTION:{
                    field = new FormField.SingleOptionField(
                            this, binding.tagEditorLayout, attribute.options);
                    break;
                }
                case MULTI_OPTION: {
                    field = new FormField.MultiOptionsField(
                            this, binding.tagEditorLayout, attribute.options);
                    break;
                }
                case BOOLEAN: {
                    field = new FormField.BooleanField(
                            this, binding.tagEditorLayout);
                    break;
                }
                case NUMBER:{
                    field = new FormField.NumberField(
                            this, awesomeValidation, binding.tagEditorLayout);
                    break;
                }
                default:
                case STRING:{
                    field = new FormField.StringField(
                            this, awesomeValidation, binding.tagEditorLayout);
                    break;
                }
            }
            formFields.add(field);
        }
    }

    private void getResults(){
        for (int i = 0; i < tag.attributes.size(); i++){
            FormField field = formFields.get(i);
            tag.attributes.get(i).values = field.getValues();
        }
        Logger.json(new Gson().toJson(tag));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_tag_editor;
    }

}
