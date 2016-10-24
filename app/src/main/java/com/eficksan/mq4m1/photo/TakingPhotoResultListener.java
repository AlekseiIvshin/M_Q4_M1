package com.eficksan.mq4m1.photo;

/**
 * Created by Aleksei Ivshin
 * on 16.10.2016.
 */

public interface TakingPhotoResultListener {

    void onSuccess(String videoUrl);

    void onFail(Throwable exception);

    void onFail(String message);
}
