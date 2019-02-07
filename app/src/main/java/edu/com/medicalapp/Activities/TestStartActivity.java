package edu.com.medicalapp.Activities;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import edu.com.medicalapp.Adapters.TestAdapter;
import edu.com.medicalapp.R;

public class TestStartActivity extends AppCompatActivity implements TestAdapter.OnCategoryClick {

    @BindView(R.id.test_topic)
    TextView testTopic;

    @BindView(R.id.btn_Start)
    Button btnStart;


    @BindView(R.id.test_image)
    ImageView testImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_start);

        testImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startTest();

            }
        });

    }

    private void startTest() {


            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            // ...Irrelevant code for customizing the buttons and titl
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView= inflater.inflate(R.layout.start_test_alertdialog, null);
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
                    //here we start new activity whatever u want
                    //startActivity(new Intent(this,FirstloginActivity.class));
                    finish();
                }
            });

            dialog.show();

        }




    @Override
    public void onCateClick(String id) {

    }
}
