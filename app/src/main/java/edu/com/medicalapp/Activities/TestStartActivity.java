package edu.com.medicalapp.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.com.medicalapp.R;
import edu.com.medicalapp.utils.Constants;
import edu.com.medicalapp.utils.DnaPrefs;

public class TestStartActivity extends AppCompatActivity  {


    @BindView(R.id.test_topic)
    TextView testTopic;

    @BindView(R.id.test_information)
    TextView testInformation;

    @BindView(R.id.btn_Start)
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_start);
        ButterKnife.bind(this);


        if(getIntent()!=null)
        {
            String id = getIntent().getStringExtra("id");
            String duration = getIntent().getStringExtra("duration");
            String testName = getIntent().getStringExtra("testName");
            String testQuestion=getIntent().getStringExtra("testQuestion");
            testTopic.setText(testName);
            testInformation.setText("The test contains"+" "+testQuestion+" "+"questions from Psychiatry with a duration of"+" "+duration+""+".");
        }

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             StartTest();
            }
        });



    }

    private void StartTest(){

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and titl
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView= inflater.inflate(R.layout.start_test_alertdialog, null);
        dialogBuilder.setView(dialogView);

        final AlertDialog dialog = dialogBuilder.create();
        Button btn_yes = dialogView.findViewById(R.id.btn_done);
        TextView text_cancel=dialogView.findViewById(R.id.text_cancel);
        text_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestStartActivity.this, TestActivity.class);
                DnaPrefs.putBoolean(TestStartActivity.this,Constants.Resultsubmit,true);
                String id = getIntent().getStringExtra("id");
                String duration = getIntent().getStringExtra("duration");
                String testName = getIntent().getStringExtra("testName");
                intent.putExtra("id", id);
                intent.putExtra("duration", duration);
                intent.putExtra("testName", testName);
                startActivity(intent);
                finish();
                dialog.dismiss();
            }
        });

        dialog.show();

    }

//    private void startTest() {
//
//
//            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//            // ...Irrelevant code for customizing the buttons and titl
//            LayoutInflater inflater = this.getLayoutInflater();
//            View dialogView= inflater.inflate(R.layout.start_test_alertdialog, null);
//            dialogBuilder.setView(dialogView);
//
//            final AlertDialog dialog = dialogBuilder.create();
//            Button btn_Done = dialogView.findViewById(R.id.btn_done);
//            TextView text_Cancel=dialogView.findViewById(R.id.text_cancel);
//            text_Cancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//
//                }
//            });
//
//
//            btn_Done.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //here we start new activity whatever u want
//                    //startActivity(new Intent(this,FirstloginActivity.class));
//                    Intent intent = new Intent(TestStartActivity.this, TestActivity.class);
//                    String id = getIntent().getStringExtra("id");
//                    intent.putExtra("id", id);
//                    startActivity(intent);
//                    finish();
//                }
//            });
//            if (!dialog.isShowing())
//            dialog.show();
//
//        }




}
