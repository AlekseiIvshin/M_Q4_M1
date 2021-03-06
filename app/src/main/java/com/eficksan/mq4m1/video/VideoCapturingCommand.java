package com.eficksan.mq4m1.video;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Toast;

import com.eficksan.mq4m1.R;
import com.eficksan.mq4m1.commands.Command;
import com.eficksan.mq4m1.commands.CommandFactory;

/**
 * Created by Aleksei Ivshin
 * on 16.10.2016.
 */

public class VideoCapturingCommand extends Command {

    private static final String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    public void execute(Activity activityContext) {
        if (!isPermissionsGranted(activityContext, PERMISSIONS) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activityContext.requestPermissions(PERMISSIONS, getDefaultCommandRequestCode());
            return;
        }
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        Intent chooser = Intent.createChooser(takeVideoIntent, activityContext.getString(R.string.choose_take_a_video));
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
