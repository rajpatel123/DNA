package edu.com.medicalapp.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.com.medicalapp.R;
import edu.com.medicalapp.utils.Constants;
import edu.com.medicalapp.utils.DnaPrefs;

public class DNAProfileActivity extends AppCompatActivity {

@BindView(R.id.profile_image)
    CircleImageView circleImageView;
   @BindView(R.id.profile_name)
   TextView tvName;

   @BindView(R.id.profile_email)
    TextView tvEmail;

   @BindView(R.id.profile_logout)
   TextView textViewProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dnaprofile);
        ButterKnife.bind(this);

        setprofiledata();
        textViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userlogout();
            }
        });

    }

    private void userlogout() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and titl
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView= inflater.inflate(R.layout.profile_alertdialog, null);
        dialogBuilder.setView(dialogView);

        final AlertDialog dialog = dialogBuilder.create();
        Button btn_Cancel = dialogView.findViewById(R.id.btn_cancel);
        TextView text_logout=dialogView.findViewById(R.id.text_logout);
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             dialog.dismiss();

            }
        });

        text_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DnaPrefs.putBoolean(DNAProfileActivity.this, Constants.LoginCheck,false);
                startActivity(new Intent(DNAProfileActivity.this,FirstloginActivity.class));
                finish();
            }
        });

        dialog.show();

    }

    private void setprofiledata() {
        Intent intent=getIntent();

            String name=DnaPrefs.getString(getApplicationContext(),"NAME");
            String image=DnaPrefs.getString(getApplicationContext(),"URL");
            String email=DnaPrefs.getString(getApplicationContext(),"EMAIL");

            tvName.setText(name);
            tvEmail.setText(email);
            if(!TextUtils.isEmpty(image))
            {

                Picasso.with(this)
                        .load(image)
                        .error(R.drawable.dnalogo)
                        .into(circleImageView);
            }
            else
            {

                Picasso.with(this)
                        .load(R.drawable.dnalogo)
                        .error(R.drawable.dnalogo)
                        .into(circleImageView);
            }


    }


    @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.profile_main_drawer, menu);
            return true;
        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.profile_edit:
                    //your code here
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
    }

