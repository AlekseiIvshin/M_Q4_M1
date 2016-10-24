package com.eficksan.mq4m1.photo.v16;

import android.hardware.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.eficksan.mq4m1.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.OnClick;

public class TakePhotoV16Fragment extends Fragment {

    private static final String TAG = TakePhotoV16Fragment.class.getSimpleName();
    private String outputFile;
    private Camera camera;

    public TakePhotoV16Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TakePhotoV16Fragment.
     */
    public static TakePhotoV16Fragment newInstance(String photoPath) {
        TakePhotoV16Fragment fragment = new TakePhotoV16Fragment();
        Bundle args = new Bundle();
        args.putString(MediaStore.EXTRA_OUTPUT, photoPath);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            outputFile = getArguments().getString(MediaStore.EXTRA_OUTPUT);
        }
        if (TextUtils.isEmpty(outputFile)) {
            throw new IllegalArgumentException("Fragment is needed in output file name.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_take_photo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        camera = Camera.open();

        FrameLayout cameraPreviewContainer = (FrameLayout) view.findViewById(R.id.camera_preview);
        cameraPreviewContainer.addView(new CameraPreview(getActivity(), camera));
    }

    @OnClick(R.id.take_photo)
    public void takePhoto() {
        camera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

                File pictureFile = new File(outputFile);
                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                    fos.close();
                } catch (FileNotFoundException e) {
                    Log.d(TAG, "File not found: " + e.getMessage());
                } catch (IOException e) {
                    Log.d(TAG, "Error accessing file: " + e.getMessage());
                }
            }
        });
    }
}
