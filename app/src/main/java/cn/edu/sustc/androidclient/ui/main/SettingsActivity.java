package cn.edu.sustc.androidclient.ui.main;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import cn.edu.sustc.androidclient.common.BaseActivity;


public class SettingsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }
    
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
