package edu.com.medicalapp.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import edu.com.medicalapp.Models.Detail;
import edu.com.medicalapp.R;

public class TruitonListFragment extends ListFragment {
	int fragNum;
	Detail question;
	RecyclerView recyclerView;

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
		View tv = layoutView.findViewById(R.id.text);
		recyclerView = layoutView.findViewById(R.id.list);
		((TextView) tv).setText("Truiton Fragment #" + fragNum);

		return layoutView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Log.i("Truiton FragmentList", "Item clicked: " + id);
	}
}