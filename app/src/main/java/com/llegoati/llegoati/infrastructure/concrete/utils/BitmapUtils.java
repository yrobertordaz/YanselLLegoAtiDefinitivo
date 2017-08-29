package com.llegoati.llegoati.infrastructure.concrete.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Yansel on 1/6/2017.
 */

public class BitmapUtils {

    public static String resourceDrawableToBase64(Context context, int resourceDrawable) {
        Drawable drawable = context.getResources().getDrawable(resourceDrawable);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        byte[] buffer = out.toByteArray();
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }
}
