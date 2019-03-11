package edu.com.medicalapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import edu.com.medicalapp.R;

public class QbankRatingActivity extends AppCompatActivity {


    private TextView textViewToomuch,textViewToolittle,textViewToohard,textViewTooEasy
                             ,textViewNotNeet,textViewNeedmore,
                            textViewExplanations,textViewlacksConcept,
                            textViewPoorly,textViewOthers,textViewAddFeedback;

    private Button buttonSubmit;
    StringBuilder result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qbank_rating);
        textViewAddFeedback=findViewById(R.id.add_feedback);
        textViewExplanations=findViewById(R.id.explanation_not_clear);
        textViewlacksConcept=findViewById(R.id.lacks_concepts);
        textViewNeedmore=findViewById(R.id.need_more_images);
        textViewOthers=findViewById(R.id.other);
        textViewNotNeet=findViewById(R.id.no_neet_pattern);
        textViewTooEasy=findViewById(R.id.too_easy);
        textViewToohard=findViewById(R.id.too_hard);
        textViewToomuch=findViewById(R.id.too_much_content);
        textViewToolittle=findViewById(R.id.too_little_content);



    }
}
