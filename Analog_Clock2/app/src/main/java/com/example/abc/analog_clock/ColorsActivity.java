package com.example.abc.analog_clock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    private String [] colorNames = {"Black","White","Blue","Green","Yellow","Red"};
    private int [] colorNames1 = {R.color.black,R.color.white,R.color.blue,R.color.green,R.color.yellow,R.color.red};
    private ArrayAdapter<String> adapter = null;

    private Spinner spinner1 = null;
    private Spinner spinner2 = null;
    private Spinner spinner3 = null;
    private Spinner spinner4 = null;
    private Spinner spinner5 = null;
    private Button save_button = null;
    int hour_color,minute_color,second_color,mark_color,background_color;
    int hour_color1,minute_color1,second_color1,mark_color1,background_color1;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);

        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        spinner4 = findViewById(R.id.spinner4);
        spinner5 = findViewById(R.id.spinner5);

        save_button = findViewById(R.id.save_button);

        adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, colorNames);

        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);
        spinner4.setAdapter(adapter);
        spinner5.setAdapter(adapter);

        ArrayList<Integer> color_update = new ArrayList<Integer>();
        for(Integer x:colorNames1)
        {
            color_update.add(x);
        }

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);

        spinner1.setSelection(color_update.indexOf(pref.getInt("hour_color", -1)));
        spinner2.setSelection(color_update.indexOf(pref.getInt("minute_color", -1)));
        spinner3.setSelection(color_update.indexOf(pref.getInt("second_color", -1)));
        spinner4.setSelection(color_update.indexOf(pref.getInt("mark_color", -1)));
        spinner5.setSelection(color_update.indexOf(pref.getInt("background_color", -1)));

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                hour_color = i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                hour_color = 0;
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                minute_color = i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                minute_color = 0;
            }
        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                second_color = i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                second_color = 0;
            }
        });

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mark_color = i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mark_color = 0;
            }
        });

        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                background_color = i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                background_color = 0;
            }
        });





        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hour_color1 = colorNames1[hour_color];
                minute_color1 = colorNames1[minute_color];
                second_color1 = colorNames1[second_color];
                mark_color1 = colorNames1[mark_color];
                background_color1 = colorNames1[background_color];

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();

                editor.clear();
                editor.commit();

                editor.putInt("hour_color", hour_color1);
                editor.putInt("minute_color", minute_color1);
                editor.putInt("second_color", second_color1);
                editor.putInt("mark_color", mark_color1);
                editor.putInt("background_color", background_color1);


                editor.commit();

                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
