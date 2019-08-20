package com.app.ascentsparkmachinetest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ascentsparkmachinetest.R;
import com.app.ascentsparkmachinetest.userDetailsMvp.DetailAdapter;
import com.app.ascentsparkmachinetest.userDetailsMvp.DetailItemClickListener;
import com.app.ascentsparkmachinetest.userDetailsMvp.DetailListContract;
import com.app.ascentsparkmachinetest.userDetailsMvp.DetailListPresenter;
import com.app.ascentsparkmachinetest.model.UserDetailsData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        DetailListContract.View, DetailItemClickListener {
    private static final String TAG = "UserListActivity";
    private DetailListPresenter userListPresenter;
    private RecyclerView rvUserList;
    private List<UserDetailsData> allUserList;
    private DetailAdapter userListAdapter;
    private RelativeLayout relLoading;
    private TextView tvNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        initUI();
        //Initializing presenter
        userListPresenter = new DetailListPresenter(this);
        userListPresenter.requestDataFromServer();
    }

    /**
     * This method will initialize the UI components
     */
    private void initUI() {
        rvUserList = findViewById(R.id.rec_details);
        allUserList = new ArrayList<>();
        userListAdapter = new DetailAdapter(MainActivity.this, allUserList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        rvUserList.setLayoutManager(linearLayoutManager);
        rvUserList.setNestedScrollingEnabled(false);
        rvUserList.setItemAnimator(new DefaultItemAnimator());
        rvUserList.setAdapter(userListAdapter);
        relLoading =findViewById(R.id.rel_loading);
        tvNoData = findViewById(R.id.tv_no_data);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        userListPresenter.onDestroy();
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            // Handle the Dashboard action
            userListAdapter = new DetailAdapter(MainActivity.this,allUserList);// adapter with new data
            rvUserList.setAdapter(userListAdapter);
            userListAdapter.notifyDataSetChanged();

        } else if (id == R.id.nav_details) {
            //Handle the details of User
            if(userListAdapter.getSelectedPosition()!=-1){
                Intent detailIntent = new Intent(this, UserDetailsActivity.class);
                UserDetailsData data = allUserList.get(userListAdapter.getSelectedPosition());
                detailIntent.putExtra("userImage", data.getImage());
                detailIntent.putExtra("userName", data.getName());
                detailIntent.putExtra("userNick", data.getNick());
                detailIntent.putExtra("userStatus", data.getStatus());
                startActivity(detailIntent);
            }else {
                Toast.makeText(MainActivity.this,"Select at least one User to get details",Toast.LENGTH_SHORT).show();
            }

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void OnDetailItemClick(int position) {
        if (position == -1) {
            return;
        }
        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
        intent.putExtra("detailsUrl",allUserList.get(position).getUrl());
        startActivity(intent);
    }

    @Override
    public void showProgress() {
        relLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        relLoading.setVisibility(View.GONE);
    }

    @Override
    public void setDataToRecyclerView(List<UserDetailsData> userArrayList) {
        List<UserDetailsData>aliveDeadArrayList = new ArrayList<>();
        for (UserDetailsData data:userArrayList){
            if (data.getStatus().equals("alive")){
                // add it to alive arrayList
                allUserList.add(data);
            }else {
                //add it to dead arrayList
                aliveDeadArrayList.add(data);
            }
        }
        allUserList.addAll(aliveDeadArrayList);
        userListAdapter.notifyDataSetChanged();

    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
    }

    @Override
    public void showMessage() {
        rvUserList.setVisibility(View.GONE);
        tvNoData.setVisibility(View.VISIBLE);

    }
}
