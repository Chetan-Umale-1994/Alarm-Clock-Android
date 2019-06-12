package com.example.abc.analog_clock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class MyBroadcastReciever extends BroadcastReceiver {

    MediaPlayer mMediaPlayer;
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Its time!!!!",Toast.LENGTH_SHORT).show();

        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        mMediaPlayer = MediaPlayer.create(context,R.raw.alarm_classic);



            mMediaPlayer.start();

            vibrator.vibrate(3000);






    }
}
