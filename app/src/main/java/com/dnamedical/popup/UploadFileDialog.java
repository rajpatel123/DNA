package com.dnamedical.popup;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.dnamedical.R;


/**
 * Created by LENOVO-PC on 02-06-2019.
 */

public class UploadFileDialog extends Dialog {

    private TextView btncamera, btnGallery,btnDocument;
    private ImageView ivCancel;
    private Context context;


    public UploadFileDialog(Context context, UploadFileDialog.onUploadFileDialog onUploadFileDialog) {
        super(context);
        // TODO Auto-generated constructor stub
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_upload);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
        setCancelable(false);
        this.context = context;
        this.onUploadFileDialog = onUploadFileDialog;

    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        super.show();
        findView();
    }

    private void findView() {
        btncamera = (TextView) findViewById(R.id.btncamera);
        btnGallery = (TextView) findViewById(R.id.btnGallery);
        ivCancel = (ImageView) findViewById(R.id.ivCancel);

        btnDocument = (TextView) findViewById(R.id.btnDocument);
        btncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onUploadFileDialog.onUploadFile("camera");
                UploadFileDialog.this.dismiss();
            }
        });
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onUploadFileDialog.onUploadFile("Gallery");
                UploadFileDialog.this.dismiss();
            }
        });
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UploadFileDialog.this.dismiss();
            }
        });
        btnDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onUploadFileDialog.onUploadFile("Document");
                UploadFileDialog.this.dismiss();
            }
        });

    }

    public UploadFileDialog.onUploadFileDialog onUploadFileDialog = null;

    public interface onUploadFileDialog {
        public void onUploadFile(String type);
    }
}

