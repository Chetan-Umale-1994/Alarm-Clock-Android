package com.example.abc.analog_clock;



import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends Activity  {

    OurView v;
    private Button color_button;
    private Button alarm_button;
    Intent intent = null;
    int hour,minute;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        v = new OurView(this);



        setContentView(R.layout.activity_main);
        LinearLayout linearLayout = findViewById(R.id.main_layout);
        linearLayout.addView(v);

        color_button = findViewById(R.id.color_button);
        alarm_button = findViewById(R.id.alarm_button);



        color_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), ColorsActivity.class);
                startActivity(intent);

            }
        });







    }

    public void setAlarm(View view)
    {
        Calendar cal2 = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                hour = i;
                minute = i1;
                alarm_ring();

            }
        },cal2.get(Calendar.HOUR_OF_DAY),cal2.get(Calendar.MINUTE),false);

        timePickerDialog.show();

    }

    public void alarm_ring()
    {
        Intent intent1 = new Intent(this,MyBroadcastReciever.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(),23433,intent1,0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar cal_alarm = Calendar.getInstance();
        cal_alarm.set(Calendar.HOUR_OF_DAY,hour);
        cal_alarm.set(Calendar.MINUTE,minute);
        cal_alarm.set(Calendar.SECOND,0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,cal_alarm.getTimeInMillis(),pendingIntent);
        Toast.makeText(this,"Alarm is set!",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        v.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        v.resume();
    }

    public class OurView extends SurfaceView implements Runnable
    {
        Thread t = null;
        SurfaceHolder holder;
        boolean isItOK = false;
        float x,y;
        int hour,min,sec;


        public OurView(Context context) {
            super(context);
            holder = getHolder();
        }

        @Override
        public void run() {
            while (isItOK == true)
            {
                //performs canvas drawing
                if(!holder.getSurface().isValid())
                {
                    continue;
                }

                Canvas c = holder.lockCanvas();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();

                if(pref.getInt("background_color", -1)== -1)
                {
                    c.drawColor(getResources().getColor(R.color.black));
                    editor.putInt("hour_color", R.color.blue);
                    editor.putInt("minute_color", R.color.red);
                    editor.putInt("second_color", R.color.yellow);
                    editor.putInt("mark_color", R.color.green);
                    editor.putInt("background_color", R.color.black);
                    editor.commit();
                }

                else
                {
                    c.drawColor(getResources().getColor(pref.getInt("background_color", -1)));
                }



                Paint p1 = new Paint();
                Paint p2 = new Paint();
                Paint p3 = new Paint();
                Paint p4 = new Paint();
                Paint p5 = new Paint();

                p1.setStrokeWidth(5);
                p2.setStrokeWidth(5);
                p3.setStrokeWidth(5);
                p4.setStrokeWidth(5);
                p5.setTextSize(40);


               if(pref.getInt("mark_color", -1)== -1 || pref.getInt("hour_color", -1)== -1
                       ||pref.getInt("minute_color", -1)== -1
                        || pref.getInt("second_color", -1)== -1)
               {
                   p1.setColor(getResources().getColor(R.color.green));
                   p2.setColor(getResources().getColor(R.color.blue));
                   p3.setColor(getResources().getColor(R.color.yellow));
                   p4.setColor(getResources().getColor(R.color.red));
                   p5.setColor(getResources().getColor(R.color.green));
                   editor.putInt("hour_color", R.color.blue);
                   editor.putInt("minute_color", R.color.red);
                   editor.putInt("second_color", R.color.yellow);
                   editor.putInt("mark_color", R.color.green);
                   editor.putInt("background_color", R.color.black);
                   editor.commit();

               }
               else
               {
                   p1.setColor(getResources().getColor(pref.getInt("mark_color", -1)));
                   p2.setColor(getResources().getColor(pref.getInt("hour_color", -1)));
                   p3.setColor(getResources().getColor(pref.getInt("minute_color", -1)));
                   p4.setColor(getResources().getColor(pref.getInt("second_color", -1)));
                   p5.setColor(getResources().getColor(pref.getInt("mark_color", -1)));
               }



                Regular_Polygon secMarks  = new Regular_Polygon(60, 350, getWidth()/2, getHeight()/2, c,p5);
                Regular_Polygon hourHand  = new Regular_Polygon(60, 350-120, getWidth()/2, getHeight()/2, c, p2);
                Regular_Polygon minHand   = new Regular_Polygon(60, 350-90, getWidth()/2, getHeight()/2, c, p3);
                Regular_Polygon secHand   = new Regular_Polygon(60, 350-60, getWidth()/2, getHeight()/2, c, p4);
                Regular_Polygon body  = new Regular_Polygon(60, 370, getWidth()/2, getHeight()/2, c,p1);

                Regular_Polygon numbers  = new Regular_Polygon(12, 350-40, getWidth()/2, getHeight()/2, c,p5);

                secMarks.drawPoints();
                body.drawRegPoly();

                for(int i=1;i<=12;i++)
                {
                    c.drawText(Integer.toString(i),numbers.getX((i+9)%12),numbers.getY((i+9)%12),p5);
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {e.printStackTrace();}

                Calendar calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR);
                min = calendar.get(Calendar.MINUTE);
                sec = calendar.get(Calendar.SECOND);

                secHand.drawRadius(sec+45);
                minHand.drawRadius(min+45);
                hourHand.drawRadius((hour*5)+(min/12)+45);




                holder.unlockCanvasAndPost(c);


            }

        }

        public void pause()
        {
            isItOK = false;
            while(true)
            {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
            t = null;
        }

        public void resume()
        {
            isItOK = true;
            t = new Thread(this);
            t.start();
        }
    }

}
