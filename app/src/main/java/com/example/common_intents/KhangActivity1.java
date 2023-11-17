package com.example.common_intents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KhangActivity1 extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 2;
    static final int REQUEST_VIDEO_CAPTURE = 3;
    static Uri photoUri = null;
    static Uri videoUri = null;
    static final String FOLDER_NAME = "IntentContent";
    private int file_id = 34234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khang1);

        Button imageButton = this.findViewById(R.id.imageButton);
        Button videoButton = this.findViewById(R.id.videoButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturePhoto();
            }
        });
        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureVideo();
            }
        });

    }


    private File createImageFileInAppDir(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File imagePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(imagePath, "image_" + timeStamp + ".jpg");
    }
    public void capturePhoto() {
        File photoFile = createImageFileInAppDir();
        if (photoFile == null){
            return;
        }

        photoUri  = FileProvider.getUriForFile(
                this,
                "com.example.android.provider",
                photoFile
        );
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags( Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private File createVideoFileInAppDir(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File imagePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(imagePath, "video_" + timeStamp + ".mp4");
    }
    public void captureVideo() {
        File photoFile = createVideoFileInAppDir();
        if (photoFile == null){
            return;
        }

        videoUri  = FileProvider.getUriForFile(
                this,
                "com.example.android.provider",
                photoFile
        );
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags( Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);
        }
    }
    public void composeEmail(String[] address, String subject, Uri attachment) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
//        emailIntent.setType("application/image");
        emailIntent.setData(Uri.parse("mailto:")); // Only email apps handle this.
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, address);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,subject);
        emailIntent.putExtra(Intent.EXTRA_STREAM, attachment);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "From My App");

        startActivity(emailIntent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                Uri selectedImageUri = photoUri;

                if (selectedImageUri == null) {
                    Log.e("Camera: ", " Image empty");
                    return;
                }
                Log.e("Camera: ", "Success");
                composeEmail(new String[]{"hoduykhanghocmai@gmail.com"}, "CAMERA IMAGE", selectedImageUri);
                break;
            case REQUEST_VIDEO_CAPTURE:
                Uri selectedVideoUri = videoUri;

                if (selectedVideoUri == null) {
                    Log.e("Camera: ", " Image empty");
                    return;
                }
                Log.e("Camera: ", "Success");
                composeEmail(new String[]{"hoduykhanghocmai@gmail.com"}, "CAMERA IMAGE", selectedVideoUri);
                break;
        }
    }
}