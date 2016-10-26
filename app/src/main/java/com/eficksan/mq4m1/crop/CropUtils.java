package com.eficksan.mq4m1.crop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Aleksei Ivshin
 * on 26.10.2016.
 */

public class CropUtils {

    public static Bitmap cromImageCenteredSquare(String srcFile) {
        Bitmap source = BitmapFactory.decodeFile(srcFile);
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();
        int size = Math.min(sourceHeight, sourceWidth);

        int horizontalOffset = (sourceWidth - size) / 2;
        int verticalOffset = (sourceHeight - size) / 2;

        return cropImageSquare(source, verticalOffset, horizontalOffset, size);
    }

    public static Bitmap cropImageSquare(Bitmap srcBitMap, int top, int left, int size) {
        return cropImage(srcBitMap, top, left, size, size);
    }

    public static Bitmap cropImage(String srcFile, int top, int left, int width, int height) {
        Bitmap srcBitMap = BitmapFactory.decodeFile(srcFile);
        return cropImage(srcBitMap, top, left, width, height);
    }

    public static Bitmap cropImage(Bitmap srcBitMap, int top, int left, int width, int height) {
        return Bitmap.createBitmap(srcBitMap, top, left, width, height);
    }
}
