package edu.com.medicalapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.com.medicalapp.Models.Detail;
import edu.com.medicalapp.R;

public class TruitonListFragment extends Fragment {
	int fragNum;
	Detail question;
	private TextView questionTxt;
	LinearLayout answerList;

	public static TruitonListFragment init(Detail question, int position) {
		TruitonListFragment truitonList = new TruitonListFragment();

		// Supply val input as an argument.
		Bundle args = new Bundle();
		args.putInt("val", position);
		args.putParcelable("question", question);
		truitonList.setArguments(args);

		return truitonList;
	}

	/**
	 * Retrieving this instance's number from its arguments.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fragNum = getArguments() != null ? getArguments().getInt("val") : 1;
		question = getArguments() != null ? getArguments().getParcelable("question") : null;

	}

	/**
	 * The Fragment's UI is a simple text view showing its instance number and
	 * an associated list.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layoutView = inflater.inflate(R.layout.fragment_pager_list,
				container, false);
		answerList = layoutView.findViewById(R.id.answerList);
		questionTxt = layoutView.findViewById(R.id.questionTxt);
		questionTxt.setText("Q" + (fragNum + 1) + ". " + question.getQuestion());


		for (int i = 0; i < 4; i++) {

			switch (i) {
				case 0:
					View answerView = inflater.inflate(R.layout.item_answer,
							container, false);

					TextView answer1 = answerView.findViewById(R.id.answer);
					CardView cardView1 = answerView.findViewById(R.id.cardView);
					answer1.setText(question.getAnswer1());
					answerList.addView(answerView);
					answer1.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							cardView1.setCardBackgroundColor(getContext().getResources().getColor(R.color.test_fragment_card_bacckground));

						}
					});

					break;
				case 1:
					View answerView1 = inflater.inflate(R.layout.item_answer,
							container, false);

					TextView answer2 = answerView1.findViewById(R.id.answer);
					CardView cardView2 = answerView1.findViewById(R.id.cardView);
					answer2.setText(question.getAnswer2());
					answerList.addView(answerView1);

					answer2.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							cardView2.setCardBackgroundColor(getContext().getResources().getColor(R.color.test_fragment_card_bacckground));

						}
					});
					break;
				case 2:
					View answerView2 = inflater.inflate(R.layout.item_answer,
							container, false);

					TextView answer3 = answerView2.findViewById(R.id.answer);
					CardView cardView3 = answerView2.findViewById(R.id.cardView);
					answer3.setText(question.getAnswer3());
					answerList.addView(answerView2);

					answer3.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							cardView3.setCardBackgroundColor(getContext().getResources().getColor(R.color.test_fragment_card_bacckground));

						}
					});
					break;
				case 3:
					View answerView4 = inflater.inflate(R.layout.item_answer,
							container, false);

					TextView answer4 = answerView4.findViewById(R.id.answer);
					CardView cardView4 = answerView4.findViewById(R.id.cardView);
					answer4.setText(question.getAnswer4());
					answerList.addView(answerView4);

					answer4.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
                       cardView4.setCardBackgroundColor(getContext().getResources().getColor(R.color.test_fragment_card_bacckground));
						}
					});
					break;
			}
		}


		return layoutView;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}


}