package com.eficksan.mq4m1.video;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.eficksan.mq4m1.commands.Command;
import com.eficksan.mq4m1.commands.CommandFactory;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Aleksei Ivshin
 * on 16.10.2016.
 */

public class VideoCustomCapturingCommand extends Command {
    @Override
    public void execute(Activity activityContext) {
        activityContext.startActivityForResult(
                VideoRecordingActivity.buildLauncherIntent(activityContext),
                getCommandRequestCode());
    }

    @Override
    protected void handleCommandResult(Activity activityContext, int requestCode, int resultCode, Intent data) {
        if (RESULT_OK == resultCode) {
            String videoUrl = VideoRecordingActivity.takeResult(data);
            Toast.makeText(activityContext, "Video saved: " + videoUrl,
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activityContext, "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected int getCommandRequestCode() {
        return CommandFactory.TAKE_PHOTO_CUSTOM_REQUEST_CODE;
    }
}
