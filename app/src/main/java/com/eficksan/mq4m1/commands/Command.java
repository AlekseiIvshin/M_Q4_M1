package com.eficksan.mq4m1.commands;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

/**
 * Created by Aleksei Ivshin
 * on 13.10.2016.
 */

public abstract class Command {

    public abstract void execute(Activity activityContext);

    public void handleResult(Activity activityContext, int requestCode, int resultCode, Intent data) {
        if (isCanHandleResult(requestCode)) {
            handleCommandResult(activityContext, requestCode, resultCode, data);
        }
    }

    protected abstract void handleCommandResult(Activity activityContext, int requestCode, int resultCode, Intent data);

    protected abstract int getDefaultCommandRequestCode();

    protected boolean isCanHandleResult(int requestCode) {
        return requestCode == getDefaultCommandRequestCode();
    }

    public static boolean isPermissionsGranted(Activity activityContext, String[] requiredPermissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String permission : requiredPermissions) {
            if (ContextCompat.checkSelfPermission(activityContext, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
