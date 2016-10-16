package com.eficksan.mq4m1.video;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.eficksan.mq4m1.R;
import com.eficksan.mq4m1.video.v21.VideoFragment;

public class VideoRecordingActivity extends AppCompatActivity implements VideoRecordingResultListener {

    private static final String TAG = VideoRecordingActivity.class.getSimpleName();
    private static final String EXTRA_VIDEO_URL = "EXTRA_VIDEO_URL";

    public static Intent buildLauncherIntent(Context context) {
        return new Intent(context, VideoRecordingActivity.class);
    }

    public static String takeResult(Intent result) {
        if (result != null) {
            return result.getStringExtra(EXTRA_VIDEO_URL);
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_recording);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, getFragment())
                    .commit();

        }
    }

    private Fragment getFragment() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.v(TAG, "Run fragment for sdk equal or greater than 21");
            return VideoFragment.newInstance(null);
        } else {
            throw new IllegalArgumentException("No implementation for older versions");
        }
    }

    @Override
    public void onVideoRecorded(String videoUrl) {
        Log.v(TAG, "Video saved: " + videoUrl);
        Intent intent = new Intent();
        intent.putExtra(EXTRA_VIDEO_URL, videoUrl);
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

}
