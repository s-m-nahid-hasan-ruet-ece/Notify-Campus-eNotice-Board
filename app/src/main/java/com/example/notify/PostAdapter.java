package com.example.notify;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder>{


        Context mContext;
        List<PostData> mData ;


public PostAdapter(Context mContext, List<PostData> mData) {
        this.mContext = mContext;
        this.mData = mData;
        }

@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(mContext).inflate(R.layout.raw_post_with_image,parent,false);
        return new MyViewHolder(row);
        }

@Override
public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


    String text = mData.get(position).getDescription();
    if (text.length()>220) {
        text=text.substring(0,220)+"...";
        holder.tvTitle.setText(Html.fromHtml(text+"<font color='#808080'> View More</font>"));
    }
    else
        holder.tvTitle.setText(mData.get(position).getDescription());


        holder.userName.setText(mData.get(position).getUserName());
        Glide.with(mContext).load(mData.get(position).getPicture()).into(holder.imgPost);
        Glide.with(mContext).load(mData.get(position).getUserPhoto()).into(holder.imgPostProfile);

        }

@Override
public int getItemCount() {
        return mData.size();
        }

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView tvTitle;
    TextView userName;
    ImageView imgPost;
    ImageView imgPostProfile;

    public MyViewHolder(View itemView) {
        super(itemView);

        tvTitle = itemView.findViewById(R.id.post_text);
        imgPost = itemView.findViewById(R.id.post_pic);
        imgPostProfile = itemView.findViewById(R.id.post_owner_pic);
        userName = itemView.findViewById(R.id.user_name);


        imgPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent postDetailActivity = new Intent(mContext,PostDetails.class);
                int position = getAdapterPosition();

              //  postDetailActivity.putExtra("title",mData.get(position).getTitle());
                postDetailActivity.putExtra("postImage",mData.get(position).getPicture());
                postDetailActivity.putExtra("description",mData.get(position).getDescription());
                postDetailActivity.putExtra("postKey",mData.get(position).getPostKey());
                postDetailActivity.putExtra("userPhoto",mData.get(position).getUserPhoto());
                postDetailActivity.putExtra("userName",mData.get(position).getUserName());
                long timestamp  = (long) mData.get(position).getTimeStamp();
                postDetailActivity.putExtra("postDate",timestamp) ;
                mContext.startActivity(postDetailActivity);

            }
        });
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent postDetailActivity = new Intent(mContext,PostDetails.class);
                int position = getAdapterPosition();

                //  postDetailActivity.putExtra("title",mData.get(position).getTitle());
                postDetailActivity.putExtra("postImage",mData.get(position).getPicture());
                postDetailActivity.putExtra("description",mData.get(position).getDescription());
                postDetailActivity.putExtra("postKey",mData.get(position).getPostKey());
                postDetailActivity.putExtra("userPhoto",mData.get(position).getUserPhoto());
                postDetailActivity.putExtra("userName",mData.get(position).getUserName());
                long timestamp  = (long) mData.get(position).getTimeStamp();
                postDetailActivity.putExtra("postDate",timestamp) ;
                mContext.startActivity(postDetailActivity);

            }
        });
/*
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show post owner profile
            }
        });

 */

    }


}
}
