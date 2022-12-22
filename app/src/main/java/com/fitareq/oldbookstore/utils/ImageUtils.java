package com.fitareq.oldbookstore.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.fitareq.oldbookstore.ui.registration.RegistrationActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;

public class ImageUtils {

    public static File compressImageQualityIntoHalf(Uri imageUri, Activity activity, String fileName) throws Exception{
                Bitmap bitmap;
                ContentResolver contentResolver = activity.getContentResolver();
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri);
                } else {
                    ImageDecoder.Source source = ImageDecoder.createSource(contentResolver, imageUri);
                    bitmap = ImageDecoder.decodeBitmap(source);
                }
                File file = new File(activity.getCacheDir(), fileName+".jpg");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                return file;
    }
}
