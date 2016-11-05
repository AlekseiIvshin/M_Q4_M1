package com.eficksan.mq4m1.photo;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.eficksan.mq4m1.R;
import com.eficksan.mq4m1.photo.v16.TakePhotoV16Fragment;
import com.eficksan.mq4m1.photo.v21.TakePhotoV21Fragment;

public class TakingPhotoActivity extends AppCompatActivity implements TakingPhotoResultListener {

    private static final String TAG = TakingPhotoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_container);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, getFragment())
                    .commit();

        }
    }

    private Fragment getFragment() {
        String output = generatePhotoPath();
        Log.v(TAG, "Output: " + output);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return TakePhotoV16Fragment.newInstance(output);
        } else {
            return TakePhotoV21Fragment.newInstance(output);
        }
    }

    @Override
    public void onSuccess(String videoUrl) {
        Log.v(TAG, "Photo saved: " + videoUrl);
        Intent intent = new Intent();
        intent.setData(Uri.parse(videoUrl));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onFail(Throwable exception) {
        Log.e(TAG, exception.getMessage(), exception);
        finishWithFail();
    }

    @Override
    public void onFail(String message) {
        Log.e(TAG, message);
        finishWithFail();
    }

    private void finishWithFail() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private String generatePhotoPath() {
        Intent args = getIntent();
        String directory;
        directory = args.getStringExtra(MediaStore.EXTRA_OUTPUT);
        if (directory == null) {
            directory = getDefaultResultPath();
        }
        return directory + "/" + System.currentTimeMillis() + ".jpg";
    }

    private String getDefaultResultPath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    }

}
