package com.eficksan.mq4m1.photo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.eficksan.mq4m1.R;
import com.yalantis.ucrop.UCrop;

import static com.eficksan.mq4m1.commands.CommandFactory.TAKE_PHOTO_AND_THIRD_LIB_CROP_REQUEST_CODE;

/**
 * Created by Aleksei Ivshin
 * on 19.10.2016.
 */

public class ThirdLibTakePhotoCommand extends TakePhotoCommand {

    @Override
    protected boolean isCanHandleResult(int requestCode) {
        return TAKE_PHOTO_AND_THIRD_LIB_CROP_REQUEST_CODE == requestCode || UCrop.REQUEST_CROP == requestCode;
    }

    @Override
    protected int getDefaultCommandRequestCode() {
        return TAKE_PHOTO_AND_THIRD_LIB_CROP_REQUEST_CODE;
    }

    @Override
    protected void handleCommandResult(Activity activityContext, int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO_AND_THIRD_LIB_CROP_REQUEST_CODE:
                    UCrop.of(data.getData(), getDestFile(data.getData()))
                            .withAspectRatio(1, 1)
                            .start(activityContext);
                    break;
                case UCrop.REQUEST_CROP:
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

}
