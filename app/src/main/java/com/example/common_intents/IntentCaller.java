package com.example.common_intents;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IntentCaller {
    static final int REQUEST_SELECT_VIDEO = 1;
    static final int REQUEST_SELECT_MUSIC = 2;
    static final int REQUEST_IMAGE_CAPTURE = 3;
    static final int REQUEST_VIDEO_CAPTURE = 4;
    static Uri photoUri = null;
    static Uri videoUri = null;

    // Phần của QUÂN
    public static void callPhoneNumber(Context context, String phoneNumber){
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(context.getPackageManager()) != null){
            context.startActivity(intent);
        }
    }

    public static void setWallpaper(Context context){
        Intent intent = new Intent(Intent.ACTION_SET_WALLPAPER);
        if (intent.resolveActivity(context.getPackageManager()) != null){
            context.startActivity(intent);
        }
    }

    public static void playVideo(Activity activity){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"video/mp4"});
        if (intent.resolveActivity(activity.getPackageManager()) != null){
            activity.startActivityForResult(intent, REQUEST_SELECT_VIDEO);
        }
    }

    public static void createAlarm(Context context) {
        String message = "Wake up time";
        int hour = 6;
        int minutes = 30;
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes);
//        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
//        }
    }

    // Phần của TÂN
    public static void insertContact(Context context) {
        String name = "Alex";
        String email = "AlexKhung222@gmail.com";
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
//        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
//        }
    }

    public static void openWifiSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
//        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
//        }
    }

    // Phần của KHANG
    public static void composeEmail(Context context, String[] addresses, String subject, Uri attachment) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_STREAM, attachment);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
    public static void capturePhoto(Activity activity) {
        File photoFile = createImageFileInAppDir(activity);
        if (photoFile == null){
            return;
        }

        photoUri  = FileProvider.getUriForFile(
                activity,
                "com.example.android.provider", // Modify as per your application's authority
                photoFile
        );
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public static void captureVideo(Activity activity) {
        File videoFile = createVideoFileInAppDir(activity);
        if (videoFile == null){
            return;
        }

        videoUri  = FileProvider.getUriForFile(
                activity,
                "com.example.android.provider", // Modify as per your application's authority
                videoFile
        );
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);
        }
    }

    private static File createImageFileInAppDir(Activity activity){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File imagePath = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(imagePath, "image_" + timeStamp + ".jpg");
    }

    private static File createVideoFileInAppDir(Activity activity){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File imagePath = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(imagePath, "video_" + timeStamp + ".mp4");
    }

    // Phần của HOÀNG
    public static void openWebPage(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, url);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.d("DEBUG", "Activity not found");
        }
    }

    // Function to view the calendar
    public static void viewCalendar(Context context) {
        Intent intent = new Intent(Intent.ACTION_INSERT,
                CalendarContract.Events.CONTENT_URI);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.d("DEBUG", "Activity not found");
        }
    }

    public static void playMusic(Activity activity) {
        Intent showMusicIntent = new Intent(Intent.ACTION_PICK);
        showMusicIntent.setType("audio/*");
        if (showMusicIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(showMusicIntent, REQUEST_SELECT_MUSIC);
        }
    }
}
