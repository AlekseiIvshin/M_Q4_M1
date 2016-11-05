package com.eficksan.mq4m1.crop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.eficksan.mq4m1.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

public class CropActivity extends AppCompatActivity {

    private static final String TAG = CropActivity.class.getSimpleName();

    private static final String KEY_SOURCE = "KEY_SOURCE";
    private static final String KEY_DEST = "KEY_DEST";

    public static Intent cropImage(Context context, Uri source, Uri dest) {
        Intent intent = new Intent(context, CropActivity.class);
        intent.putExtra(KEY_SOURCE, source);
        intent.putExtra(KEY_DEST, dest);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        Uri sourceFile = getIntent().getParcelableExtra(KEY_SOURCE);
        Uri destFile = getIntent().getParcelableExtra(KEY_DEST);

        if (sourceFile == null || destFile == null) {
            throw new IllegalArgumentException("No output file!");
        }

        new CroppingTask().execute(sourceFile, destFile);
    }

    public class CroppingTask extends AsyncTask<Uri, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Uri... params) {
            Uri sourceFile = params[0];
            Uri destFile = params[1];
            Log.v(TAG, String.format("Src: '%s', dest: '%s'", sourceFile, destFile));
            Bitmap bitmap = CropUtils.cromImageCenteredSquare(sourceFile.toString());
            File file = new File(URI.create(destFile.toString()));
            FileOutputStream os = null;
            try {
                os = new FileOutputStream(file);

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            if (isSuccess) {
                Log.v(TAG, "Image is cropped and saved: " + isSuccess);
                Uri dest = getIntent().getParcelableExtra(KEY_DEST);
                Intent intent = new Intent();
                intent.setData(dest);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                setResult(RESULT_CANCELED);
                finish();
            }
        }
    }
}
