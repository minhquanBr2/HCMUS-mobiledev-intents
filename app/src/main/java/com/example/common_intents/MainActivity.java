package com.example.common_intents;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private int gridColumnCount = 3;
    private int gridRowCount = 4;
    private int idOffset = 65000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Create layout*/
        LinearLayout gridLayout = findViewById(R.id.grid);
        String[] btnName = {"CALL", "WALLPAPER", "VIEW VIDEO", "SET ALARM", "ADD CONTACT", "WIFI SETTING", "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED"};
        LayoutSetup.createButtonGridLayout(this, gridLayout, gridRowCount, gridColumnCount, idOffset, btnName);

        // Get button
        Button btn1 = findViewById(idOffset + 1);
        Button btn2 = findViewById(idOffset + 2);
        Button btn3 = findViewById(idOffset + 3);
        Button btn4 = findViewById(idOffset + 4);
        Button btn5 = findViewById(idOffset + 5);
        Button btn6 = findViewById(idOffset + 6);
        Button btn7 = findViewById(idOffset + 7);
        Button btn8 = findViewById(idOffset + 8);
        Button btn9 = findViewById(idOffset + 9);
        Button btn10 = findViewById(idOffset + 10);
        Button btn11 = findViewById(idOffset + 11);
        Button btn12 = findViewById(idOffset + 12);


        // Set button action
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentCaller.createAlarm(MainActivity.this);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentCaller.insertContact(MainActivity.this);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentCaller.openWifiSettings(MainActivity.this);
            }
        });
    }
}