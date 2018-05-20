package cn.edu.sustc.androidclient.view.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.Status;
import cn.edu.sustc.androidclient.common.base.BaseActivity;
import cn.edu.sustc.androidclient.databinding.ActivityUserProfileBinding;
import cn.edu.sustc.androidclient.model.data.User;
import cn.edu.sustc.androidclient.view.main.MainViewModel;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class UserProfileActivity extends BaseActivity<MainViewModel, ActivityUserProfileBinding> {
    private boolean isExpanded = false;
    private ConstraintSet layout, expandedLayout;
    private ConstraintLayout constraintLayout;
    private ActivityUserProfileBinding binding;
    private User user;

    public static void start(Context context, User user) {
        Intent intent = new Intent(context, UserProfileActivity.class);
        intent.putExtra("user", user);
        context.startActivity(intent);
    }

    @Override
    protected Class<MainViewModel> getViewModel() {
        return MainViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, MainViewModel viewModel, ActivityUserProfileBinding binding) {
        binding.setProfileActivity(this);
        // changing the status bar color to transparent
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        layout = new ConstraintSet();
        expandedLayout = new ConstraintSet();
        constraintLayout = findViewById(R.id.profile_layout);

        layout.clone(constraintLayout);
        expandedLayout.clone(this, R.layout.user_profile_expanded);
        viewModel.getLiveCurrentUser().observe(this, userMyResource -> {
            if (userMyResource != null && userMyResource.status == Status.SUCCESS) {
                user = userMyResource.data;
                binding.profileUsername.setText(user.username);
                binding.profileEmail.setText(user.email);
                Glide.with(binding.profileBackground.getContext())
                        .load(user.avatar)
                        .apply(RequestOptions.centerCropTransform())
                        .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 1)))
                        .into(binding.profileBackground);

                Glide.with(binding.profilePhoto)
                        .load(user.avatar)
                        .apply(RequestOptions.centerCropTransform())
                        .into(binding.profilePhoto);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_user_profile;
    }

    public void change(View view) {
        if (!isExpanded) {
            TransitionManager.beginDelayedTransition(constraintLayout);
            expandedLayout.applyTo(constraintLayout);
            isExpanded = !isExpanded;
        } else {
            TransitionManager.beginDelayedTransition(constraintLayout);
            layout.applyTo(constraintLayout);
            isExpanded = !isExpanded;
        }
    }


}
