package com.example.common_intents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import java.time.OffsetDateTime;

public class MainActivity extends AppCompatActivity {

    private int gridColumnCount = 3;
    private int gridRowCount = 4;
    private int idOffset = 65000;
    private static final int PICK_VIDEO_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // create layout
        LinearLayout gridLayout = findViewById(R.id.grid);
        for (int i = 0; i < gridRowCount; i++) {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1
            );
            row.setLayoutParams(rowParams);
            for (int j = 0; j < gridColumnCount; j++) {
                int id = idOffset + i * gridColumnCount + j;
                Button btn = new Button(this);
                LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        1
                );
                btnParams.gravity = Gravity.FILL;
                btn.setLayoutParams(btnParams);
                btn.setId(++id);
                btn.setText(String.valueOf(id - 65000).toString());
                row.addView(btn);
            }
            gridLayout.addView(row);
        }

        EditText editText1 = findViewById(R.id.editText1);
        EditText editText2 = findViewById(R.id.editText2);
        EditText editText3 = findViewById(R.id.editText3);

        Button btn1 = findViewById(idOffset + 1);
        btn1.setText("CALL");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialPhoneNumber(editText1.getText().toString());
            }
        });

        Button btn2 = findViewById(idOffset + 2);
        btn2.setText("WALLPAPER");
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWallpaper();
            }
        });

        Button btn3 = findViewById(idOffset + 3);
        btn3.setText("VIDEO");
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewVideo();
            }
        });

        Button btn4 = findViewById(idOffset + 4);
        btn4.setText("SET ALARM");
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlarm();
            }
        });

        Button btn5 = findViewById(idOffset+5);
        btn5.setText("ADD CONTACT");
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertContact();
            }
        });

        Button btn6 = findViewById(idOffset + 6);
        btn6.setText("WIFI SETTINGS");
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWifiSettings();
            }
        });
    }

    public void dialPhoneNumber(String phoneNumber){
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

    public void setWallpaper(){
        Intent intent = new Intent(Intent.ACTION_SET_WALLPAPER);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

    public void viewVideo(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"video/mp4"});
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, PICK_VIDEO_REQUEST_CODE);
        }
    }

    public void composeEmail(String[] addresses, String subject, Uri attachment) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_STREAM, attachment);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case PICK_VIDEO_REQUEST_CODE:
                Uri selectedVideoUri = (data != null) ? data.getData() : null;
                if (selectedVideoUri == null) {
                    return;
                }
                Intent viewVideoIntent = new Intent(Intent.ACTION_VIEW);
                viewVideoIntent.setDataAndType(selectedVideoUri, "video/mp4");
                if (viewVideoIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(viewVideoIntent);
                }
                break;
        }
    }


    // Intents cua Tan
    public void createAlarm() {
        String message = "Wake up time";
        int hour = 6;
        int minutes = 30;
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes);
//        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
//        }
    }

    public void insertContact() {
        String name = "Alex";
        String email = "AlexKhung222@gmail.com";
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
//        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
//        }
    }



    public void openWifiSettings() {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}