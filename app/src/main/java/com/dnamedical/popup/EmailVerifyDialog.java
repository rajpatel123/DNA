package com.dnamedical.popup;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dnamedical.R;

import uk.co.senab.photoview.PhotoViewAttacher;

public class EmailVerifyDialog extends Dialog {


    private ImageView ivCancel;
    private Context context;
    String url;
    EditText etemail;
    Button btnsubmit;

    public EmailVerifyDialog(Context context, EmailVerifyDialog.onEmailVerifyDialog onEmailVerifyDialog) {
        super(context);
        // TODO Auto-generated constructor stub
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_email_verify);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
        setCancelable(false);
        this.context = context;
        this.url = url;
        this.onEmailVerifyDialog = onEmailVerifyDialog;

    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        super.show();
        findView();
    }

    private void findView() {

        etemail = (EditText) findViewById(R.id.etemail);
        btnsubmit = (Button) findViewById(R.id.btnsubmit);
        ivCancel= (ImageView) findViewById(R.id.ivCancel);

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EmailVerifyDialog.this.dismiss();
            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(etemail.getText().toString().trim().length()==0) {
                Toast.makeText(context,"Enter email id",Toast.LENGTH_SHORT).show();
            }
            else {

                onEmailVerifyDialog.emailverfy(etemail.getText().toString());
                dismiss();
            }


            }
        });

    }

    public EmailVerifyDialog.onEmailVerifyDialog onEmailVerifyDialog = null;

    public interface onEmailVerifyDialog {
        public void emailverfy(String type);
    }
}
