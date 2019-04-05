package com.dnamedical.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.dnamedical.R;

public class DNASuscribeActivity extends AppCompatActivity {

    @BindView(R.id.first)
    TextView textViewOne;

    @BindView(R.id.second)
    TextView textViewSecond;

    @BindView(R.id.third)
    TextView textViewThird;

    @BindView(R.id.fourth)
    TextView textViewFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dnasuscribe);
        ButterKnife.bind(this);


        SpannableString spannableString = new SpannableString(getString(R.string.rupay));
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewOne.setText(spannableString);

        SpannableString spannableString1 = new SpannableString(getString(R.string.rupay));
        spannableString1.setSpan(new UnderlineSpan(), 0, spannableString1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewSecond.setText(spannableString1);

        SpannableString spannableString2 = new SpannableString(getString(R.string.rupay));
        spannableString2.setSpan(new UnderlineSpan(), 0, spannableString2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewThird.setText(spannableString2);

        SpannableString spannableString3 = new SpannableString(getString(R.string.rupay));
        spannableString3.setSpan(new UnderlineSpan(), 0, spannableString3.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewFour.setText(spannableString3);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

}
