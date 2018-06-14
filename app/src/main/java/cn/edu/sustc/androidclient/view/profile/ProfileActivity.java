package cn.edu.sustc.androidclient.view.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;

import java.io.File;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.databinding.ActivityProfileBinding;
import cn.edu.sustc.androidclient.model.data.User;
import cn.edu.sustc.androidclient.view.base.BaseActivity;
import cn.edu.sustc.androidclient.view.main.MainViewModel;
import io.reactivex.annotations.Nullable;

public class ProfileActivity extends BaseActivity<MainViewModel, ActivityProfileBinding>{
    private ActivityProfileBinding binding;
    public User user;

    @Inject
    MainViewModel viewModel;

    private EditMode mode = EditMode.VIEW_MODE;

    public static void start(Context context, User user) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra("user", user);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.user = (User) getIntent().getSerializableExtra("user");
        binding = getBinding();
        setView();
    }

    private void setView(){
        binding.setViewModel(this);
        // floating button to edit profile
        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> {
            // save changes
            if (mode == EditMode.EDIT_MODE){
                user.phone = binding.profileMain.phone.getText().toString();
                user.username = binding.profileMain.userName.getText().toString();
                viewModel.updateUserProfile(user);
            }
            mode = mode == EditMode.EDIT_MODE ? EditMode.VIEW_MODE : EditMode.EDIT_MODE;
            switch (mode){
                case EDIT_MODE:
                    binding.fab.setImageResource(R.drawable.ic_done_black_24dp);
                    binding.profileMain.phone.setEnabled(true);
                    binding.profileMain.userName.setEnabled(true);
                    break;
                case VIEW_MODE:
                    binding.fab.setImageResource(R.drawable.ic_profile_edit);
                    binding.profileMain.phone.setEnabled(false);
                    binding.profileMain.userName.setEnabled(false);
                    break;
                default:
                    break;
            }
        });
        binding.userProfileImage.setOnClickListener(view -> Album.image(this)
                .singleChoice()
                .columnCount(3)
                .camera(true)
                .onResult((requestCode, result) -> {
                    // show selected image
                    AlbumFile selected = result.get(0);
                    RequestOptions options = new RequestOptions()
                            .centerCrop();
                    Glide.with(this)
                            .load(new File(selected.getPath()))
                            .apply(options)
                            .into(binding.userProfileImage);
                    // upload picture to server
                    viewModel.uploadCover(selected.getPath()).observe(this, resource -> {
                        showLoading();
                        switch (resource.status) {
                            case SUCCESS:
                                hideLoading();
                                user.avatar = resource.data;
                                break;
                            case ERROR:
                                hideLoading();
                                showAlertDialog(getString(R.string.error), resource.message);
                                break;
                            default:
                            case LOADING:
                                break;
                        }
                    });
                }).start());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_profile;
    }

    private enum EditMode{
        EDIT_MODE,
        VIEW_MODE
    }
}
