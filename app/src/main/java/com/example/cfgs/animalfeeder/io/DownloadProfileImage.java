package com.example.cfgs.animalfeeder.io;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;



/**
 * Created by ArribasD on 4/23/2018.
 */

public class DownloadProfileImage extends AsyncTask<String, Void, Boolean> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... url) {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try {
            URLConnection conn = new URL(url[0]).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bm = BitmapFactory.decodeStream(bis);
            //Upload the image
            FirebaseStorage storage  = FirebaseStorage.getInstance("gs://animalfeeder-cae79.appspot.com/");

            StorageReference uploadImg = storage.getReference("profileImage");

            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bout);
            UploadTask uploadTask = uploadImg.child(FirebaseAuth.getInstance().getCurrentUser().getUid() + ".jpg").putBytes(bout.toByteArray());

        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            if (bis != null) {
                try {
                    bis.close();
                }catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            if (is != null) {
                try {
                    is.close();
                }catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean b) {
        super.onPostExecute(b);
    }
}
