package com.dnamedical.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.VectorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.OpenableColumns;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.dnamedical.R;

/**
 * Created by t.gupta on 24-10-2017.
 */

public class ImageUtils {
    private static final String TAG = "ImageUtils";
    public static void setTintedDrawable(Context context, int drawableId, ImageView imageView, @ColorRes int color) {
        if (context == null) {
            return;
        }

        if (imageView == null) {
            return;
        }

        Drawable          drawable  = ContextCompat.getDrawable(context, drawableId);
        @ColorInt Integer tintColor = ContextCompat.getColor(context, color);
        drawable = ImageUtils.getTintedDrawable(drawable, tintColor);
        if (drawable != null)
            imageView.setImageDrawable(drawable);
    }
    public static Drawable getTintedDrawable(Drawable drawable, @ColorInt int tintColor) {
        if (drawable == null) {
            return null;
        }
        drawable = DrawableCompat.wrap(drawable);
        drawable = drawable.mutate();
        DrawableCompat.setTint(drawable, tintColor);
        return drawable;
    }

}
