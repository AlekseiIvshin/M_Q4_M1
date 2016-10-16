package com.eficksan.mq4m1.video.v21;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Size;

/**
 * Created by Aleksei Ivshin
 * on 16.10.2016.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class CompareSizesByArea implements java.util.Comparator<Size> {
    @Override
    public int compare(Size lhs, Size rhs) {
        // We cast here to ensure the multiplications won't overflow
        return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                (long) rhs.getWidth() * rhs.getHeight());
    }
}
