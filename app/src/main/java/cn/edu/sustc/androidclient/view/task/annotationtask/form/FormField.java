package cn.edu.sustc.androidclient.view.task.annotationtask.form;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.basgeekball.awesomevalidation.AwesomeValidation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.edu.sustc.androidclient.R;

public abstract class FormField {
    public abstract List<String> getValues();

    public abstract boolean filled();

    /*填写数字的表单*/
    public static class NumberField extends FormField {
        private EditText editText;

        public NumberField(Context context, AwesomeValidation validator, LinearLayout layout) {
            editText = new EditText(context);
            editText.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            layout.addView(editText);
            validator.addValidation(editText, s -> s.trim().length() != 0,
                    context.getString(R.string.alert_field_empty));
            validator.addValidation(editText, "^(?=.)([+-]?([0-9]*)(\\.([0-9]+))?)$",
                    context.getString(R.string.alert_field_number));
        }

        @Override
        public List<String> getValues() {
            return Arrays.asList(editText.getText().toString());
        }

        @Override
        public boolean filled() {
            return editText.getText().toString().trim().length() != 0;
        }
    }

    /*填写文本的表单*/
    public static class StringField extends FormField {
        private EditText editText;

        public StringField(Context context, AwesomeValidation validator, LinearLayout layout) {
            editText = new EditText(context);
            editText.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            layout.addView(editText);
            validator.addValidation(editText, s -> s.trim().length() != 0,
                    context.getString(R.string.alert_field_empty));
        }

        @Override
        public List<String> getValues() {
            return Arrays.asList(editText.getText().toString());
        }

        @Override
        public boolean filled() {
            return editText.getText().toString().trim().length() != 0;
        }
    }

    /*radio button的那种*/
    public static class BooleanField extends FormField {
        private RadioGroup group;
        private RadioButton trueButton;
        private RadioButton falseButton;

        public BooleanField(Context context, LinearLayout layout) {
            group = new RadioGroup(context);
            group.setOrientation(RadioGroup.HORIZONTAL);
            trueButton = new RadioButton(context);
            falseButton = new RadioButton(context);

            trueButton.setText(context.getString(R.string.yes));
            falseButton.setText(context.getString(R.string.no));

            group.addView(trueButton);
            group.addView(falseButton);
            layout.addView(group);
        }

        @Override
        public List<String> getValues() {
            int selectedId = group.getCheckedRadioButtonId();
            return Arrays.asList("" + (selectedId == trueButton.getId()));
        }

        @Override
        public boolean filled() {
            return group.getCheckedRadioButtonId() == trueButton.getId() ||
                    group.getCheckedRadioButtonId() == falseButton.getId();
        }
    }

    /*spinner - 下拉选择器*/
    public static class SingleOptionField extends FormField {
        private Spinner spinner;

        public SingleOptionField(Context context, LinearLayout layout, List<String> options) {
            spinner = new Spinner(context);
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, options);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            spinner.setAdapter(adapter);
            spinner.setSelection(0);
            layout.addView(spinner);
        }

        @Override
        public List<String> getValues() {
            return Arrays.asList(spinner.getSelectedItem().toString());
        }

        @Override
        public boolean filled() {
            return spinner.getSelectedItem() != null;
        }
    }

    /*多选的选择器，checkbox*/
    public static class MultiOptionsField extends FormField {
        private ArrayList<CheckBox> checkboxes = new ArrayList<>();

        public MultiOptionsField(Context context, LinearLayout layout, List<String> options) {
            for (String option : options) {
                CheckBox checkBox = new CheckBox(context);
                checkBox.setText(option);
                checkboxes.add(checkBox);
                layout.addView(checkBox);
            }
        }

        @Override
        public List<String> getValues() {
            ArrayList<String> results = new ArrayList<>();
            for (CheckBox checkBox : checkboxes) {
                if (checkBox.isChecked()) {
                    results.add(checkBox.getText().toString());
                }
            }
            return results;
        }

        /*判断是否表单填写完整*/
        @Override
        public boolean filled() {
            for (CheckBox checkBox : checkboxes) {
                if (checkBox.isChecked()) {
                    return true;
                }
            }
            return false;
        }
    }
}
