package com.app.ascentsparkmachinetest.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ascentsparkmachinetest.R;
import com.app.ascentsparkmachinetest.model.GlideApp;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class UserDetailsActivity extends AppCompatActivity {
    private ImageView userProfileImage;
    private TextView tvUserName,tvUserNick,tvUserStatus;
    private String strImage,strName,strNick,strStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        getSupportActionBar();
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        initUI();
        initIntentData();
    }

    private void initIntentData() {
        if (getIntent().getStringExtra("userImage") != null) {
            strImage = getIntent().getStringExtra("userImage");
        }
        if (getIntent().getStringExtra("userName") != null) {
            strName = getIntent().getStringExtra("userName");
        }
        if (getIntent().getStringExtra("userNick") != null) {
            strNick = getIntent().getStringExtra("userNick");
        }
        if (getIntent().getStringExtra("userStatus") != null) {
            strStatus = getIntent().getStringExtra("userStatus");
        }

        setDataFromIntent();

    }

    private void setDataFromIntent() {
        // loading album cover using Glide library
        GlideApp.with(UserDetailsActivity.this)
                .load(strImage)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).apply(new RequestOptions().placeholder(R.drawable.no_image).error(R.drawable.no_image))
                .into(userProfileImage);
        //set userName,userNick,userStatus
        if (strName.isEmpty()){
            tvUserName.setText("Name - "+"No Name Available");
        }else {
            tvUserName.setText("Name - "+strName);
        }
        if (strNick.isEmpty()){
            tvUserNick.setText("Nick - "+"No Name Available");
        }else {
            tvUserNick.setText("Nick - "+strNick);
        }
        tvUserStatus.setText("Status - "+strStatus);
    }

    private void initUI() {
        userProfileImage = findViewById(R.id.display_user_image);
        tvUserName = findViewById(R.id.tv_user_name);
        tvUserNick = findViewById(R.id.tv_user_nick);
        tvUserStatus = findViewById(R.id.tv_user_status);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
