package cn.edu.sustc.androidclient.view.profile;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.databinding.ActivityUserProfileBinding;
import cn.edu.sustc.androidclient.model.data.User;
import cn.edu.sustc.androidclient.viewmodel.ProfileViewModel;

public class UserProfileActivity extends AppCompatActivity {
    private boolean isExpanded = false;
    private ConstraintSet layout, expandedLayout;
    private ConstraintLayout constraintLayout;
    private ActivityUserProfileBinding binding;
    private User user;

    public static void start(Context context, User user){
        Intent intent = new Intent(context, UserProfileActivity.class);
        intent.putExtra("user", user);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        // data binding, show be bind before any view initialization
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile);
        ProfileViewModel profileViewModel = new ProfileViewModel(this, user);
        binding.setProfileActivity(this);
        binding.setProfileViewModel(profileViewModel);

        // changing the status bar color to transparent
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        layout = new ConstraintSet();
        expandedLayout = new ConstraintSet();
        constraintLayout = findViewById(R.id.profile_layout);

        layout.clone(constraintLayout);
        expandedLayout.clone(this, R.layout.user_profile_expanded);


    }

    public void change(View view){
        if (!isExpanded){
            TransitionManager.beginDelayedTransition(constraintLayout);
            expandedLayout.applyTo(constraintLayout);
            isExpanded = !isExpanded;
        }else{
            TransitionManager.beginDelayedTransition(constraintLayout);
            layout.applyTo(constraintLayout);
            isExpanded = !isExpanded;
        }
    }



}
