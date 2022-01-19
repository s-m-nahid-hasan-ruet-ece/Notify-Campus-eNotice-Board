package com.example.notify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amrdeveloper.reactbutton.ReactButton;
import com.amrdeveloper.reactbutton.Reaction;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class Test extends AppCompatActivity implements FBReactionsDialog.onReactionListener{

    private final String[] strings = {"like", "love", "laugh", "wow", "sad", "angry"};
    Button btn;
    ImageView react_iv;
  //  private ReactionView reactionView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_kotline);


         btn = findViewById(R.id.btn);
         react_iv = findViewById(R.id.react_iv);


        btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                getReactionDialog();
                return false;
            }
        });


    }



    private DialogFragment getReactionDialog(){
        FBReactionsDialog fbReactionsDialog = new FBReactionsDialog();
        fbReactionsDialog.show(getSupportFragmentManager(),fbReactionsDialog.getClass().getSimpleName());
        return fbReactionsDialog;
    }


    public void onReactionSelected(int reactiontype){
        switch (reactiontype){
            case 0:
                btn.setText("Like");
                react_iv.setImageResource(R.drawable.ic_like);
                break;
            case 1:
                btn.setText("Love");
                react_iv.setImageResource(R.drawable.ic_heart);
                break;
            case 2:
                btn.setText("Haha");
                react_iv.setImageResource(R.drawable.ic_happy);
                break;
            case 3:
                btn.setText("Angry");
                react_iv.setImageResource(R.drawable.ic_angry);
                break;
            case 4:
                btn.setText("Sad");
                react_iv.setImageResource(R.drawable.ic_sad);
                break;
        }
    }


    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.alarm_white)
                        .setContentTitle("Notifications Example")
                        .setContentText("This is a test notification");

        Intent notificationIntent = new Intent(this, Test.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }


    private void Notification(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("n","n",NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"n")
                .setContentTitle("Code Space")
                .setSmallIcon(R.drawable.alarm_black)
                .setAutoCancel(true)

                .setContentText("New Data is added!");

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());


    }





/*
    private void sampleCenterLeft() {

        ReactionPopup popup = new ReactionPopup(
                this,
                new ReactionsConfigBuilder(this)
                        .withReactions(new int[]{
                                R.drawable.ic_fb_like,
                                R.drawable.ic_fb_love,
                                R.drawable.ic_fb_laugh,
                                R.drawable.ic_fb_wow,
                                R.drawable.ic_fb_sad,
                                R.drawable.ic_fb_angry,
                        })
                        .withReactionTexts(position -> strings[position])
                        .build());

        findViewById(R.id.facebook_btn).setOnTouchListener(popup);
    }

    */
/*
    private void sampleTopLeft() {
        ReactionPopup popup = new ReactionPopup(
                this,
                new ReactionsConfigBuilder(this)
                        .withReactions(new int[]{
                                R.drawable.ic_fb_like,
                                R.drawable.ic_fb_love,
                                R.drawable.ic_fb_laugh,
                        })
                        .withPopupAlpha(20)
                        .withReactionTexts(position -> strings[position])
                        .withTextBackground(new ColorDrawable(Color.TRANSPARENT))
                        .withTextColor(Color.BLACK)
                        .withTextHorizontalPadding(0)
                        .withTextVerticalPadding(0)
                        .withTextSize(getResources().getDimension(R.dimen.reactions_text_size))
                        .build(),
                position -> true);

        findViewById(R.id.top_btn).setOnTouchListener(popup);
    }

    private void sampleBottomLeft() {
        int margin = getResources().getDimensionPixelSize(R.dimen.crypto_item_margin);

        ReactionPopup popup = new ReactionPopup(this, new ReactionsConfigBuilder(this)
                .withReactions(new int[]{
                        R.drawable.ic_crypto_btc,
                        R.drawable.ic_crypto_eth,
                        R.drawable.ic_crypto_ltc,
                        R.drawable.ic_crypto_dash,
                        R.drawable.ic_crypto_xrp,
                        R.drawable.ic_crypto_xmr,
                        R.drawable.ic_crypto_doge,
                        R.drawable.ic_crypto_steem,
                        R.drawable.ic_crypto_kmd,
                        R.drawable.ic_crypto_zec
                })
                .withReactionTexts(R.array.crypto_symbols)
                .withPopupColor(Color.LTGRAY)
                .withReactionSize(getResources().getDimensionPixelSize(R.dimen.crypto_item_size))
                .withHorizontalMargin(margin)
                .withVerticalMargin(margin / 2)
                .withTextBackground(new ColorDrawable(Color.TRANSPARENT))
                .withTextColor(Color.BLACK)
                .withTextSize(getResources().getDimension(R.dimen.reactions_text_size) * 1.5f)
                .build());

        popup.setReactionSelectedListener((position) -> {
            Log.i("Reactions", "Selection position=" + position);
            // Close selector if not invalid item (testing purpose)
            return position != 3;
        });

        findViewById(R.id.crypto_bottom_left).setOnTouchListener(popup);
    }
*/



}