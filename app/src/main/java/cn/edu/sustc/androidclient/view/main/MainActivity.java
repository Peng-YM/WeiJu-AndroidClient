package cn.edu.sustc.androidclient.view.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.ActivityCollector;
import cn.edu.sustc.androidclient.common.Status;
import cn.edu.sustc.androidclient.common.base.BaseActivity;
import cn.edu.sustc.androidclient.common.SharePreferenceHelper;
import cn.edu.sustc.androidclient.databinding.ActivityMainBinding;
import cn.edu.sustc.androidclient.databinding.NavHeaderMainBinding;
import cn.edu.sustc.androidclient.model.data.User;
import cn.edu.sustc.androidclient.view.about.AboutActivity;
import cn.edu.sustc.androidclient.view.login.LoginActivity;
import cn.edu.sustc.androidclient.view.main.tasklist.TaskFragment;
import cn.edu.sustc.androidclient.view.profile.UserProfileActivity;
import cn.edu.sustc.androidclient.view.settings.SettingsActivity;
import cn.edu.sustc.androidclient.view.task.CollectionTaskActivity;
import cn.edu.sustc.androidclient.view.task.TaskManagerActivity;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding>
        implements NavigationView.OnNavigationItemSelectedListener {
    // injected modules
    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    private NavHeaderMainBinding headerBinding;

    public static void start(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle instance, MainViewModel viewModel, ActivityMainBinding binding) {
        this.binding = binding;
        this.viewModel = viewModel;

        binding.setMainListener(this);
        Toolbar toolbar = binding.contentMain.toolbar;
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);

        setUpNavigationView();
        // insert fragments
        getFragmentManager()
                .beginTransaction()
                .add(R.id.task_fragment_layout, TaskFragment.getInstance())
                .commit();
    }

    @Override
    protected Class<MainViewModel> getViewModel() {
        return MainViewModel.class;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    /**
     * Set up Navigation view
     * */
    public void setUpNavigationView(){
        headerBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.nav_header_main, binding.navView, false);
        binding.navView.addHeaderView(headerBinding.getRoot());

        viewModel.getLiveCurrentUser().observe(this, userMyResource -> {
            if (userMyResource != null && userMyResource.status == Status.SUCCESS){
                User user = userMyResource.data;
                headerBinding.headerEmail.setText(user.email);
                headerBinding.headerUsername.setText(user.username);
                RequestOptions options = new RequestOptions()
                        .circleCrop()
                        .placeholder(R.drawable.logo)
                        .error(R.drawable.ic_load_error);
                Glide.with(headerBinding.headerAvatar)
                        .load(user.avatar)
                        .apply(options)
                        .into(headerBinding.headerAvatar);
                headerBinding.headerAvatar.
                        setOnClickListener(view ->
                                UserProfileActivity.start(MainActivity.this, user));
            }
        });
    }

    // close the continue task card view
    public void closeCard(View view){
        LinearLayout parent = findViewById(R.id.task_card_parent);
        parent.removeViewAt(0);
    }

    // continue task
    public void continueTask(View view){
        TaskManagerActivity.start(this);
        closeCard(view);
    }

    // logout
    private void logout(){
        // show alert dialog
        new AlertDialog.Builder(this)
                .setTitle(R.string.alert)
                .setMessage(R.string.alert_logout)
                .setIcon(R.drawable.ic_alert)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // delete credentials
                        SharedPreferences preferences = SharePreferenceHelper.getPreferences();
                        preferences.edit().remove("id").remove("token").apply();
                        // go to login page
                        LoginActivity.start(MainActivity.this);
                    }
                })
                .setNegativeButton(R.string.no, null).show();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_exit:
                ActivityCollector.finishAll();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_all_task:
                break;
            case R.id.nav_my_task:
                TaskManagerActivity.start(this);
                break;
            case R.id.gallery:
                CollectionTaskActivity.start(this);
                break;
            case R.id.nav_about:
                AboutActivity.start(this);
                break;
            case R.id.nav_settings:
                SettingsActivity.start(this);
                break;
            case R.id.nav_logout:
                logout();
                break;

            default:
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
