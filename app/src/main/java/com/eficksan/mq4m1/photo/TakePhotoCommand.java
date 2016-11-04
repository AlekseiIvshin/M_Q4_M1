package com.eficksan.mq4m1.photo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.eficksan.mq4m1.R;
import com.eficksan.mq4m1.commands.Command;
import com.eficksan.mq4m1.crop.CropActivity;

import java.io.File;

import static com.eficksan.mq4m1.commands.CommandFactory.CROP_PHOTO_REQUEST_CODE;
import static com.eficksan.mq4m1.commands.CommandFactory.TAKE_PHOTO_AND_CUSTOM_CROP_REQUEST_CODE;

/**
 * Created by Aleksei Ivshin
 * on 19.10.2016.
 */

public class TakePhotoCommand extends Command {
    private static final String TAG = TakePhotoCommand.class.getSimpleName();

    @Override
    public void execute(Activity activityContext) {
        if (ContextCompat.checkSelfPermission(activityContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(activityContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permissions needed");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activityContext.requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, getDefaultCommandRequestCode());
            }
            return;
        }
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Log.v(TAG, "execute");

        Intent chooser = Intent.createChooser(takeVideoIntent, activityContext.getString(R.string.choose_take_a_video));
        if (chooser.resolveActivity(activityContext.getPackageManager()) != null) {
            activityContext.startActivity(chooser);
        }
    }

    @Override
    protected void handleCommandResult(Activity activityContext, int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO_AND_CUSTOM_CROP_REQUEST_CODE:
                    Intent cropIntent = CropActivity.cropImage(activityContext, data.getData(), getDestFile(data.getData()));
                    activityContext.startActivityForResult(cropIntent, CROP_PHOTO_REQUEST_CODE);
                    break;
                case CROP_PHOTO_REQUEST_CODE:
                    Uri photoUri = data.getData();
                    Toast.makeText(
                            activityContext,
                            activityContext.getString(R.string.taking_photo_result, photoUri.toString()),
                            Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        }
    }

    @Override
    protected boolean isCanHandleResult(int requestCode) {
        return TAKE_PHOTO_AND_CUSTOM_CROP_REQUEST_CODE == requestCode || CROP_PHOTO_REQUEST_CODE == requestCode;
    }

    @Override
    protected int getDefaultCommandRequestCode() {
        return TAKE_PHOTO_AND_CUSTOM_CROP_REQUEST_CODE;
    }

    protected Uri getDestFile(Uri sourceFile) {
        File srcFile = new File(sourceFile.toString());
        File destFile = new File(srcFile.getParent(), "cropped_" + System.currentTimeMillis() + "_" + srcFile.getName());
        return Uri.fromFile(destFile);
    }
}
