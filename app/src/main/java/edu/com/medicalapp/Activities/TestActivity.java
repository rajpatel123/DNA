package edu.com.medicalapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.com.medicalapp.Models.QustionDetails;
import edu.com.medicalapp.R;
import edu.com.medicalapp.Retrofit.RestClient;
import edu.com.medicalapp.fragment.TruitonListFragment;
import edu.com.medicalapp.utils.Utils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends FragmentActivity {
    MyAdapter mAdapter;
    ViewPager mPager;
    TextView quesionCounter;
    TextView timer;

    public Map<String, String> correctAnswerList = new HashMap<>();
    public Map<String, String> skippedQuestions = new HashMap<>();
    public Map<String, String> wrongAnswerList = new HashMap<>();
    CountDownTimer countDownTimer;
    private QustionDetails qustionDetails;

    private Button button,menuButton;
    static int currentPosition;
    boolean timeUp;
    private String testName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pager);



        menuButton=findViewById(R.id.nex1);
        menuButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                OpenMenuOption();

            }
        });
        quesionCounter = findViewById(R.id.counter);
        timer = findViewById(R.id.timer);
        String duration = getIntent().getStringExtra("duration");
        testName = getIntent().getStringExtra("testName");

        long testDuration = 0;
        if (!TextUtils.isEmpty(duration)) {
            switch (duration) {
                case "30m":
                    testDuration = 30;
                    break;
                case "45m":
                    testDuration = 45;
                    break;
                case "1h":
                    testDuration = 60;
                    break;
                case "2h":
                    testDuration = 120;
                    break;
                case "3h":
                    testDuration = 180;
                    break;
                case "3 hour":
                    testDuration = 180;
                    break;


            }
        }
        button = (Button) findViewById(R.id.next);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                quesionCounter.setText((currentPosition + 1) + " of " + qustionDetails.getDetail().size());
                mPager.setCurrentItem(currentPosition + 1);

            }

        });

        Button buttonSButton = findViewById(R.id.btnSubmit);
        buttonSButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog();
            }

        });


        countDownTimer = new CountDownTimer(testDuration * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                Log.e("TOTAL_TIME", "" + millisUntilFinished);
                timer.setText("" + new SimpleDateFormat("HH:mm:ss").format(new Date(millisUntilFinished)));
            }

            public void onFinish() {
                timer.setText("Time up!");
                timeUp = true;

            }
        };

        countDownTimer.start();
    }

    private void OpenMenuOption() {

        PopupMenu popupMenu=new PopupMenu(TestActivity.this,menuButton);
        popupMenu.getMenuInflater().inflate(R.menu.bottom_navigation,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });

    }




    @Override
    protected void onResume() {
        super.onResume();
        getTest();
    }

    public static class MyAdapter extends FragmentPagerAdapter {
        QustionDetails qustionDetails = null;
        TextView quesionCounter;

        public MyAdapter(FragmentManager fragmentManager, QustionDetails qustionDetails, TextView quesionCounter) {
            super(fragmentManager);
            this.qustionDetails = qustionDetails;
            this.quesionCounter = quesionCounter;
        }


        @Override
        public int getCount() {
            if (qustionDetails.getDetail() != null && qustionDetails.getDetail().size() > 0)
                return qustionDetails.getDetail().size();
            return 0;
        }

        @Override
        public Fragment getItem(int position) {
            quesionCounter.setText((position) + " of " + qustionDetails.getDetail().size());
            return TruitonListFragment.init(qustionDetails.getDetail().get(position), position);
        }
    }

    private void getTest() {
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            RestClient.getQuestion(getIntent().getStringExtra("id"), new Callback<QustionDetails>() {
                @Override
                public void onResponse(Call<QustionDetails> call, Response<QustionDetails> response) {
                    Utils.dismissProgressDialog();

                    if (response.code() == 200) {
                        qustionDetails = response.body();
                        mAdapter = new MyAdapter(getSupportFragmentManager(), qustionDetails, quesionCounter);
                        mPager = (ViewPager) findViewById(R.id.pager);
                        mPager.addOnPageChangeListener(pageChangeListener);
                        mPager.setAdapter(mAdapter);
                    }
                }

                @Override
                public void onFailure(Call<QustionDetails> call, Throwable t) {
                    Utils.dismissProgressDialog();
                }
            });
        } else {
            Utils.dismissProgressDialog();

            Toast.makeText(this, "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();
        }

    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int newPosition) {
            currentPosition = newPosition;
            quesionCounter.setText((newPosition + 1) + " of " + qustionDetails.getDetail().size());

        }

        @Override
        public void onPageScrolled(int newPosition, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {
        }
    };

    private void submitTest() {
        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);

            String user_id = "1";
            String test_id = getIntent().getStringExtra("id");
            String tquestion = "" + qustionDetails.getDetail().size();
            String canswer = "" + correctAnswerList.keySet().size();
            String wanswer = "" + wrongAnswerList.keySet().size();
            String sanswer = "" + (qustionDetails.getDetail().size() - (correctAnswerList.keySet().size() + wrongAnswerList.keySet().size()));


            RestClient.submitTest(user_id, test_id, tquestion, canswer, wanswer, sanswer, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Utils.dismissProgressDialog();

                    if (response.code() == 200) {
                        ResponseBody responseBody = response.body();
                        try {
                            String raw = responseBody.string();
                            try {
                                JSONObject jsonObject = new JSONObject(raw);
                                Toast.makeText(TestActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(TestActivity.this, ResultActivity.class);
                                intent.putExtra("average", jsonObject.getString("average"));
                                intent.putExtra("tquestion", tquestion);
                                intent.putExtra("canswer", canswer);
                                intent.putExtra("wanswer", wanswer);
                                intent.putExtra("sanswer", sanswer);
                                intent.putExtra("testName", testName);
                                startActivity(intent);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Utils.dismissProgressDialog();
                }
            });
        } else {
            Utils.dismissProgressDialog();

            Toast.makeText(this, "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();
        }

    }

    private void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Submit");
        builder.setMessage("Are you sure you want to submit test?");
        String positiveText = "OK";
        builder.setPositiveButton(positiveText, (dialog, which) -> {
            dialog.dismiss();
            if (countDownTimer != null)
                countDownTimer.cancel();
            submitTest();
        });
        String negativeText = "CANCEL";
        builder.setNegativeButton(negativeText, (dialog, which) -> {
            dialog.dismiss();
            finish();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}