package com.eficksan.mq4m1.video;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.eficksan.mq4m1.R;
import com.eficksan.mq4m1.video.v16.VideoFragmentV16;
import com.eficksan.mq4m1.video.v21.VideoFragmentV21;

public class VideoRecordingActivity extends AppCompatActivity implements VideoRecordingResultListener {

    private static final String TAG = VideoRecordingActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, getFragment())
                    .commit();

        }
    }

    private Fragment getFragment() {
        String output = generateVideoPath();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return VideoFragmentV16.newInstance(output);
        } else {
            return VideoFragmentV21.newInstance(output);
        }
    }

    @Override
    public void onVideoRecorded(String videoUrl) {
        Log.v(TAG, "Video saved: " + videoUrl);
        Intent intent = new Intent();
        intent.setData(Uri.parse(videoUrl));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onRecordingFailed(Throwable exception) {
        Log.e(TAG, exception.getMessage(), exception);
        finishWithFail();
    }

    @Override
    public void onRecordingFailed(String message) {
        Log.e(TAG, message);
        finishWithFail();
    }

    private void finishWithFail() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private String generateVideoPath() {
            Intent args = getIntent();
            String directory;
                directory = args.getStringExtra(MediaStore.EXTRA_OUTPUT);
            if (directory==null){
                directory = getDefaultResultPath();
            }
            return directory + "/" + System.currentTimeMillis() + ".mp4";
    }

    private String getDefaultResultPath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath();
    }

}
