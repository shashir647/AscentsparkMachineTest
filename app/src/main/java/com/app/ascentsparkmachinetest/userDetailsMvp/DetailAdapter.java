package com.app.ascentsparkmachinetest.userDetailsMvp;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.ascentsparkmachinetest.R;
import com.app.ascentsparkmachinetest.model.UserDetailsData;
import com.app.ascentsparkmachinetest.model.GlideApp;
import com.app.ascentsparkmachinetest.ui.MainActivity;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyViewHolder> {
    private MainActivity userMainActivity;
    private List<UserDetailsData> allUserList;
    private int selectedIndex = -1;

    public DetailAdapter(MainActivity userMainActivity, List<UserDetailsData> allUserList) {
        this.userMainActivity = userMainActivity;
        this.allUserList = allUserList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        UserDetailsData data = allUserList.get(holder.getAdapterPosition());
        if (data.getName().isEmpty()){
            holder.tvDetailsName.setText(userMainActivity.getResources().getString(R.string.no_name));
        }else {
            holder.tvDetailsName.setText(data.getName());
        }
        if (data.getNick().isEmpty()){
            holder.tvDetailsNick.setText(userMainActivity.getResources().getString(R.string.no_nick));
        }else {
            holder.tvDetailsNick.setText(data.getNick());
        }
        // loading userImage using Glide library
        GlideApp.with(this.userMainActivity)
                .load(data.getImage())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.pgImageLoading.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        holder.pgImageLoading.setVisibility(View.GONE);
                        return false;
                    }
                }).apply(new RequestOptions().placeholder(R.drawable.ic_perm_identity_black_24dp).error(R.drawable.ic_perm_identity_black_24dp))
                .into(holder.ivMovieThumb);

        if (position==selectedIndex){
            holder.relView.setBackgroundResource(R.drawable.card_border_selected_drawable);
            holder.tvDetailsName.setTextColor(userMainActivity.getResources().getColor(R.color.colorWhite));
            holder.tvDetailsNick.setTextColor(userMainActivity.getResources().getColor(R.color.colorWhite));
        }else {
            holder.relView.setBackgroundResource(R.drawable.card_border_drawable);
            holder.tvDetailsName.setTextColor(userMainActivity.getResources().getColor(R.color.colorDarkGreyOne));
            holder.tvDetailsNick.setTextColor(userMainActivity.getResources().getColor(R.color.colorDarkGreyOne));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int unSelected=selectedIndex;
                selectedIndex=holder.getAdapterPosition();
                if(unSelected!=-1)
                    notifyItemChanged(unSelected);
                notifyItemChanged(selectedIndex);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        userMainActivity.OnDetailItemClick(selectedIndex);

                    }
                },150);

            }
        });

    }

    @Override
    public int getItemCount() {
        return allUserList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvDetailsName,tvDetailsNick;
        ImageView ivMovieThumb;
        ProgressBar pgImageLoading;
        RelativeLayout relView;

        MyViewHolder(View itemView) {
            super(itemView);
            tvDetailsName = itemView.findViewById(R.id.tv_user_name);
            tvDetailsNick = itemView.findViewById(R.id.tv_user_nick);
            ivMovieThumb = itemView.findViewById(R.id.img_user_icon);
            pgImageLoading = itemView.findViewById(R.id.pb_load_image);
            relView = itemView.findViewById(R.id.rel_view);
        }
    }

    public int getSelectedPosition(){
        return  selectedIndex;
    }
}
