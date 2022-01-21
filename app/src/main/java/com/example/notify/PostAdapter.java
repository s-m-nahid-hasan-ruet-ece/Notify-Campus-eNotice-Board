package com.example.notify;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.firebase.database.ServerValue;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> implements  FBReactionsDialog.onReactionListener{


        Context mContext;
        List<PostData> mData ;
        MaterialButton reactButton;


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


    holder.subject.setText(mData.get(position).getSubject());
    String deadlineDate = mData.get(position).getDeadlineDay()+" "+ mData.get(position).getDeadlineMonth();
    holder.deadlineChip.setText(deadlineDate);
    String timeOfPost = getPostTime( mData.get(position).getTimeStamp());
    holder.postTime.setText(timeOfPost);

    holder.userName.setText(mData.get(position).getUserName());
    Glide.with(mContext).load(mData.get(position).getPicture()).into(holder.imgPost);
    Glide.with(mContext).load(mData.get(position).getUserPhoto()).into(holder.imgPostProfile);

    holder.reactBtn.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            Toast.makeText(mContext,"React Button Pressed",Toast.LENGTH_SHORT).show();
          //  getReactionDialog();
            return false;
        }
    });

        }

@Override
public int getItemCount() {
        return mData.size();
        }

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView tvTitle;
    TextView userName;
    TextView subject;
    TextView postTime;
    MaterialButton reactBtn,commentButton,reminderButton;
    ImageView imgPost;
    ImageView imgPostProfile;
    Chip deadlineChip;



    public MyViewHolder(View itemView) {
        super(itemView);

        tvTitle = itemView.findViewById(R.id.post_text);
        imgPost = itemView.findViewById(R.id.post_pic);
        imgPostProfile = itemView.findViewById(R.id.post_owner_pic);
        userName = itemView.findViewById(R.id.user_name);
        subject = itemView.findViewById(R.id.subject);
        postTime = itemView.findViewById(R.id.post_time);
        deadlineChip = itemView.findViewById(R.id.chip_deadline);
        reactBtn = itemView.findViewById(R.id.react_btn);
        commentButton = itemView.findViewById(R.id.comment_btn);
        reminderButton = itemView.findViewById(R.id.reminder_btn);


        reactButton = reactBtn;



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
                postDetailActivity.putExtra("subject",mData.get(position).getSubject());
                postDetailActivity.putExtra("day",mData.get(position).getDeadlineDay());
                postDetailActivity.putExtra("month",mData.get(position).getDeadlineMonth());
                postDetailActivity.putExtra("hour",mData.get(position).getDeadlineHour());
                postDetailActivity.putExtra("min",mData.get(position).getDeadlineMinute());


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
                postDetailActivity.putExtra("subject",mData.get(position).getSubject());
                postDetailActivity.putExtra("day",mData.get(position).getDeadlineDay());
                postDetailActivity.putExtra("month",mData.get(position).getDeadlineMonth());
                postDetailActivity.putExtra("hour",mData.get(position).getDeadlineHour());
                postDetailActivity.putExtra("min",mData.get(position).getDeadlineMinute());

                long timestamp  = (long) mData.get(position).getTimeStamp();
                postDetailActivity.putExtra("postDate",timestamp) ;
                mContext.startActivity(postDetailActivity);

            }
        });

        commentButton.setOnClickListener(new View.OnClickListener() {
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
                postDetailActivity.putExtra("subject",mData.get(position).getSubject());
                postDetailActivity.putExtra("day",mData.get(position).getDeadlineDay());
                postDetailActivity.putExtra("month",mData.get(position).getDeadlineMonth());
                postDetailActivity.putExtra("hour",mData.get(position).getDeadlineHour());
                postDetailActivity.putExtra("min",mData.get(position).getDeadlineMinute());

                long timestamp  = (long) mData.get(position).getTimeStamp();
                postDetailActivity.putExtra("postDate",timestamp) ;
                mContext.startActivity(postDetailActivity);
            }
        });

        reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent postDetailActivity = new Intent(mContext,Reminder.class);
                int position = getAdapterPosition();

                //  postDetailActivity.putExtra("title",mData.get(position).getTitle());
                postDetailActivity.putExtra("postImage",mData.get(position).getPicture());
                postDetailActivity.putExtra("description",mData.get(position).getDescription());
                postDetailActivity.putExtra("postKey",mData.get(position).getPostKey());
                postDetailActivity.putExtra("userPhoto",mData.get(position).getUserPhoto());
                postDetailActivity.putExtra("userName",mData.get(position).getUserName());
                postDetailActivity.putExtra("subject",mData.get(position).getSubject());
                postDetailActivity.putExtra("day",mData.get(position).getDeadlineDay());
                postDetailActivity.putExtra("month",mData.get(position).getDeadlineMonth());
                postDetailActivity.putExtra("hour",mData.get(position).getDeadlineHour());
                postDetailActivity.putExtra("min",mData.get(position).getDeadlineMinute());


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


    private DialogFragment getReactionDialog(){
        FBReactionsDialog fbReactionsDialog = new FBReactionsDialog();
        fbReactionsDialog.show(((FragmentActivity)mContext).getSupportFragmentManager(),fbReactionsDialog.getClass().getSimpleName());
        return fbReactionsDialog;
    }

    public boolean checkEligible(String data, String key)
{
    if("All Faculty".equals(key) || "All Department".equals(key) || "All Batch".equals(key) || "All Section".equals(key) )
        return true;

    // check substring
        return  true;
}


private String getPostTime(Object time){

    Date date = new Date();
    long timeDiff = date.getTime();
    String x = time.toString();

    long y = Long.parseLong(x);

    long dif = (timeDiff - y)/1000;

    long day, hour, min, sec;


    day = dif/(24*60*60);

    dif = dif% (24*60*60);

    hour = dif/(60*60);

    dif = dif%(60*60);

    min = dif/60;

    dif = dif%60;

    sec = dif;

    Log.e("time",day+ " - " + hour+ " - "+min+ " - "+sec);

    if(day<7)
    {
        if(day!=0)
        {
            return " "+day+" days ago";
        }
        if(hour!=0)
        {
            return " "+hour+" hours ago";
        }
        else if(min!=0)
        {
            return " "+min+" mins ago";
        }
        else
            return " Just now";
    }
    else
    {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(y);
        String dateNow = DateFormat.format("dd-MM-yyyy",calendar).toString();

        String monthNow = dateNow.substring(3,5);
        Log.e("monthN",monthNow+ " -- "+ dateNow);
        String dayNow,yearNow;

        yearNow = dateNow.substring(6,10);

        if(dateNow.charAt(0)=='0')
            dayNow = dateNow.substring(1,2);
        else
            dayNow = dateNow.substring(0,2);

        if(monthNow.equals("01"))
            monthNow = "Jan";
        else if(monthNow.equals("02"))
            monthNow = "Feb";
        else if(monthNow.equals("03"))
            monthNow = "Mar";
        else if(monthNow.equals("04"))
            monthNow = "Apr";
        else if(monthNow.equals("05"))
            monthNow = "May";
        else if(monthNow.equals("06"))
            monthNow = "Jun";
        else if(monthNow.equals("07"))
            monthNow = "Jul";
        else if(monthNow.equals("08"))
            monthNow = "Aug";
        else if(monthNow.equals("09"))
            monthNow = "Sep";
        else if(monthNow.equals("10"))
            monthNow = "Oct";
        else if(monthNow.equals("11"))
            monthNow = "Nov";
        else if(monthNow.equals("12"))
            monthNow = "Dec";


        return " "+dayNow+" "+monthNow+" "+yearNow;
    }

}

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onReactionSelected(int reactiontype){
        switch (reactiontype){
            case 0:
                reactButton.setText("Like");
                reactButton.setIconSize(80);
                reactButton.setIconTint(ColorStateList.valueOf(mContext.getColor(R.color.white)));
                reactButton.setTextColor(mContext.getColor(R.color.primary));
                reactButton.setIconTintMode(PorterDuff.Mode.MULTIPLY);
                reactButton.setIcon(ContextCompat.getDrawable(mContext,R.drawable.ic_like));
                break;
            case 1:
                reactButton.setText("Love");
                reactButton.setIconSize(80);
                reactButton.setIconTint(ColorStateList.valueOf(mContext.getColor(R.color.white)));
                reactButton.setTextColor(mContext.getColor(R.color.primary));
                reactButton.setIconTintMode(PorterDuff.Mode.MULTIPLY);
                reactButton.setIcon(ContextCompat.getDrawable(mContext,R.drawable.ic_heart));
                break;
            case 2:
                reactButton.setText("HaHa");
                reactButton.setIconSize(80);
                reactButton.setIconTint(ColorStateList.valueOf(mContext.getColor(R.color.white)));
                reactButton.setTextColor(mContext.getColor(R.color.primary));
                reactButton.setIconTintMode(PorterDuff.Mode.MULTIPLY);
                reactButton.setIcon(ContextCompat.getDrawable(mContext,R.drawable.ic_happy));
                break;
            case 3:
                reactButton.setText("Angry");
                reactButton.setIconSize(80);
                reactButton.setIconTint(ColorStateList.valueOf(mContext.getColor(R.color.white)));
                reactButton.setTextColor(mContext.getColor(R.color.primary));
                reactButton.setIconTintMode(PorterDuff.Mode.MULTIPLY);
                reactButton.setIcon(ContextCompat.getDrawable(mContext,R.drawable.ic_angry));
                break;
            case 4:
                reactButton.setText("Sad");
                reactButton.setIconSize(80);
                reactButton.setIconTint(ColorStateList.valueOf(mContext.getColor(R.color.white)));
                reactButton.setTextColor(mContext.getColor(R.color.primary));
                reactButton.setIconTintMode(PorterDuff.Mode.MULTIPLY);
                reactButton.setIcon(ContextCompat.getDrawable(mContext,R.drawable.ic_sad));
                break;
        }
    }






}
