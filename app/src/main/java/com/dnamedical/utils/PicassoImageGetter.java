//package com.dnamedical.utils;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.text.Html;
//import android.widget.TextView;
//
//import com.dnamedical.DNAApplication;
//import com.dnamedical.R;
//import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Target;
//
//public class PicassoImageGetter implements Html.ImageGetter {
//
//    private TextView textView = null;
//    Context context;
//
//
//
//    public PicassoImageGetter(TextView target,Context context) {
//        textView = target;
//        this.context = context;
//
//    }
//
//    @Override
//    public Drawable getDrawable(String source) {
//        BitmapDrawablePlaceHolder drawable = new BitmapDrawablePlaceHolder();
//        Picasso.with(context)
//                .load(source)
//                .placeholder(R.drawable.logo_with_title)
//                .into(drawable);
//        return drawable;
//    }
//
//    private class BitmapDrawablePlaceHolder extends BitmapDrawable implements Target {
//
//        protected Drawable drawable;
//
//        @Override
//        public void draw(final Canvas canvas) {
//            if (drawable != null) {
//                drawable.draw(canvas);
//            }
//        }
//
//        public void setDrawable(Drawable drawable) {
//            this.drawable = drawable;
//            int width = drawable.getIntrinsicWidth();
//            int height = drawable.getIntrinsicHeight();
//            drawable.setBounds(0, 0, width, height);
//            setBounds(0, 0, width, height);
//            if (textView != null) {
//                textView.setText(textView.getText());
//            }
//        }
//
//        @Override
//        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//            setDrawable(new BitmapDrawable(context.getResources(), bitmap));
//        }
//
//        @Override
//        public void onBitmapFailed(Drawable errorDrawable) {
//        }
//
//        @Override
//        public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//        }
//
//    }
//}