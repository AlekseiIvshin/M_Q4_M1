package com.eficksan.mq4m1.video;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.eficksan.mq4m1.R;
import com.eficksan.mq4m1.commands.Command;
import com.eficksan.mq4m1.commands.CommandFactory;

import static com.eficksan.mq4m1.commands.CommandFactory.TAKE_PHOTO_AND_CUSTOM_CROP_REQUEST_CODE;
import static com.eficksan.mq4m1.commands.CommandFactory.TAKE_VIDEO_REQUEST_CODE;

/**
 * Created by Aleksei Ivshin
 * on 16.10.2016.
 */

public class VideoCapturingCommand extends Command {

    @Override
    public void execute(Activity activityContext) {

        if (ContextCompat.checkSelfPermission(activityContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(activityContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activityContext.requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, TAKE_VIDEO_REQUEST_CODE);
            }
            return;
        }
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        Intent chooser = Intent.createChooser(
                takeVideoIntent,
                activityContext.getString(R.string.choose_take_a_video));
        if (chooser.resolveActivity(activityContext.getPackageManager()) != null) {
            activityContext.startActivityForResult(chooser, getDefaultCommandRequestCode());
        }
    }

    @Override
    protected void handleCommandResult(Activity activityContext, int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri videoUri = data.getData();
            Toast.makeText(
                    activityContext,
                    activityContext.getString(R.string.video_capturing_result, videoUri.toString()),
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    protected int getDefaultCommandRequestCode() {
        return CommandFactory.TAKE_VIDEO_REQUEST_CODE;
    }
}
