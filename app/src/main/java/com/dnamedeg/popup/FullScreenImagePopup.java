package com.dnamedeg.popup;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dnamedeg.R;

import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

public class FullScreenImagePopup extends Dialog {


    private ImageView ivImageZoom,ivCancel;
    private Context context;
    String url;

    public FullScreenImagePopup(String url,Context context, FullScreenImagePopup.onFullScreenImagePopup onFullScreenImagePopup) {
        super(context);
        // TODO Auto-generated constructor stub
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_imagezoom);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
        setCancelable(false);
        this.context = context;
        this.url = url;
        this.onFullScreenImagePopup = onFullScreenImagePopup;

    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        super.show();
        findView();
    }

    private void findView() {

        ivImageZoom = (ImageView) findViewById(R.id.ivImageZoom);
        ivCancel= (ImageView) findViewById(R.id.ivCancel);

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FullScreenImagePopup.this.dismiss();
            }
        });
        Glide.with(context)
                .load(url)
                .into(ivImageZoom);
        PhotoViewAttacher pAttacher;
        pAttacher = new PhotoViewAttacher(ivImageZoom);
        pAttacher.update();
    }

    public FullScreenImagePopup.onFullScreenImagePopup onFullScreenImagePopup = null;

    public interface onFullScreenImagePopup {
        public void onImage(String type);
    }
}

