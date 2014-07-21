package fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.allgoodpeopleus.evolution.EvolutionModel;
import com.allgoodpeopleus.evolution.MainActivity;
import com.allgoodpeopleus.evolutionp.R;


public class InformationFragment extends ListFragment {
	ListView listview;
	TextView textview;
	View header;
	ArrayAdapter<String> listAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		inflater.inflate(R.layout.fragment_information, container, false);
		listAdapter = new ArrayAdapter<String>(inflater.getContext(),
				android.R.layout.simple_list_item_1, new String[] {
						"See the Demo!", "Read More!" });
		header = inflater.inflate(R.layout.information_headerview, null);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		TextView tv = (TextView) header.findViewById(R.id.information_header_textview);
		tv.setText(EvolutionModel.Information);
		
		getListView().addHeaderView(header, null, false);
		setListAdapter(listAdapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		switch (position) {
		case 1:
			MainActivity.Shared.ShowGAColor();
			break;
		case 2:
			MainActivity.Shared.showBlogPost();
			break;
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		setListAdapter(null);
	}
}
