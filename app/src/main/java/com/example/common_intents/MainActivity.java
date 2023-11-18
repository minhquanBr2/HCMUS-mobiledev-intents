package com.example.common_intents;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 100;
    private int gridColumnCount = 3;
    private int gridRowCount = 4;
    private int idOffset = 65000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Create layout*/
        LinearLayout gridLayout = findViewById(R.id.grid);
        String[] btnName = {"CALL", "WALLPAPER", "PLAY VIDEO", "SET ALARM", "ADD CONTACT", "WIFI SETTING", "EMAIL", "CAPTURE PHOTO", "CAPTURE VIDEO", "WEB SEARCH", "ADD CALENDAR EVENT", "PLAY MUSIC"};
        LayoutSetup.createButtonGridLayout(this, gridLayout, gridRowCount, gridColumnCount, idOffset, btnName);

        // Get button
        Button btn[][] = new Button[gridRowCount][gridColumnCount];
        for (int i = 0; i < gridRowCount; i++){
            for (int j = 0; j < gridColumnCount; j++){
                btn[i][j] = findViewById(idOffset + (i * gridColumnCount + j));
            }
        }

        // QUÂN
        btn[0][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText telEditText = findViewById(R.id.editText1);
                String tel = telEditText.getText().toString();
                IntentCaller.callPhoneNumber(MainActivity.this, tel); }
        });
        btn[0][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { IntentCaller.setWallpaper(MainActivity.this); }
        });
        btn[0][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { IntentCaller.playVideo(MainActivity.this); }
        });

        // TÂN
        btn[1][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentCaller.createAlarm(MainActivity.this);
            }
        });
        btn[1][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentCaller.insertContact(MainActivity.this);
            }
        });
        btn[1][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { IntentCaller.openWifiSettings(MainActivity.this); }
        });

        // KHANG
        btn[2][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { IntentCaller.capturePhoto(MainActivity.this); }
        });
        btn[2][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { IntentCaller.captureVideo(MainActivity.this); }
        });

        // HOÀNG
        btn[3][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText urlEditText = findViewById(R.id.editText1);
                String url = urlEditText.getText().toString();
                IntentCaller.openWebPage(MainActivity.this, url); }
        });
        btn[3][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { IntentCaller.viewCalendar(MainActivity.this); }
        });
        btn[3][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { IntentCaller.playMusic(MainActivity.this); }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK){
            Log.e("Activity", "Intent return not fine");
            return;
        }
        if (requestCode == IntentCaller.REQUEST_SELECT_VIDEO && data != null) {
            Uri uri = data.getData();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "video/mp4");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//DO NOT FORGET THIS EVER
            startActivity(intent);
        }
        else if (requestCode == IntentCaller.REQUEST_SELECT_MUSIC&& data != null) {
            Uri selectedMusicUri = (data != null) ? data.getData() : null;
            if (selectedMusicUri == null) {
                return;
            }
            Log.d("DEBUG", selectedMusicUri.toString());
            Intent viewMusicIntent = new Intent(Intent.ACTION_VIEW);
            viewMusicIntent.setDataAndType(selectedMusicUri, "audio/mp3");
            viewMusicIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//DO NOT FORGET THIS EVER
//                if (viewMusicIntent.resolveActivity(getPackageManager()) != null) {
            try {
                startActivity(viewMusicIntent);
            } catch (ActivityNotFoundException e) {
                Log.d("DEBUG", "Activity not found");
            }
        } else if (requestCode == IntentCaller.REQUEST_IMAGE_CAPTURE) {
            Uri selectedImageUri = IntentCaller.photoUri;

            if (selectedImageUri == null) {
                Log.e("Camera: ", " Image empty");
                return;
            }
            Log.e("Camera: ", "Success");
            IntentCaller.composeEmail(this, new String[]{"hoduykhanghocmai@gmail.com"}, "CAMERA IMAGE", selectedImageUri);
        } else if (requestCode == IntentCaller.REQUEST_VIDEO_CAPTURE) {
            Uri selectedVideoUri = IntentCaller.videoUri;

            if (selectedVideoUri == null) {
                Log.e("Camera: ", " Image empty");
                return;
            }
            Log.e("Camera: ", "Success");
            IntentCaller.composeEmail(this, new String[]{"hoduykhanghocmai@gmail.com"}, "CAMERA VIDEO", selectedVideoUri);
        }
    }
}