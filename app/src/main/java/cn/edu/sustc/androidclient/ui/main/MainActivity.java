package cn.edu.sustc.androidclient.ui.main;

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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.ActivityCollector;
import cn.edu.sustc.androidclient.common.MyResponse;
import cn.edu.sustc.androidclient.common.SharePreferenceHelper;
import cn.edu.sustc.androidclient.databinding.ActivityMainBinding;
import cn.edu.sustc.androidclient.databinding.NavHeaderMainBinding;
import cn.edu.sustc.androidclient.model.User;
import cn.edu.sustc.androidclient.rest.impl.UserService;
import cn.edu.sustc.androidclient.ui.about.AboutActivity;
import cn.edu.sustc.androidclient.ui.login.LoginActivity;
import cn.edu.sustc.androidclient.ui.profile.ProfileViewModel;
import cn.edu.sustc.androidclient.ui.settings.SettingsActivity;
import cn.edu.sustc.androidclient.ui.task.CollectionTaskActivity;
import cn.edu.sustc.androidclient.ui.task.TaskManagerActivity;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding binding;

    public static void start(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMainListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setUpUserProfile();
        // insert fragments
        getFragmentManager()
                .beginTransaction()
                .add(R.id.task_fragment_layout, TaskFragment.getInstance())
                .commit();

    }

    public void setUpUserProfile(){
        // get id from shared preference
        SharedPreferences preferences = SharePreferenceHelper.getPreferences();
        String id = preferences.getString("id", "DEFAULT");
        Subscriber<MyResponse<User>> subscriber = new Subscriber<MyResponse<User>>() {
            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) {
                // TODO show error dialog
            }

            @Override
            public void onNext(MyResponse<User> response) {
                User user = response.data;
                // setup navigation view header when data is prepared
                NavHeaderMainBinding headerMainBinding = DataBindingUtil.inflate(getLayoutInflater(),
                        R.layout.nav_header_main, binding.navView, false);
                binding.navView.addHeaderView(headerMainBinding.getRoot());
                ProfileViewModel profileViewModel = new ProfileViewModel(MainActivity.this, user);
                headerMainBinding.setUserProfile(profileViewModel);
            }
        };
        UserService.getInstance().getProfile(id, subscriber);
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
                .setTitle(getResources().getString(R.string.alert))
                .setMessage(getResources().getString(R.string.alert_logout))
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
