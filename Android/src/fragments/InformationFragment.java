package fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.allgoodpeopleus.evolutionp.R;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;
import com.mopub.mobileads.MoPubView.BannerAdListener;

public class InformationFragment extends ListFragment {
	
	ListView listview;
	TextView textview;
	ArrayAdapter<String> listAdapter;
	String simulationInformation;
	Runnable[] navigations;

	public InformationFragment(String simulationInformation, Runnable[] navigations) {
		this.simulationInformation = simulationInformation;
		this.navigations = navigations;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_information, null, true);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		TextView tv = (TextView) view.findViewById(R.id.fragment_information_textview);
		tv.setText(simulationInformation);
		
//		TextView tv = (TextView) header.findViewById(R.id.information_header_textview);
//		tv.setText(simulationInformation);
//		
//		mopubView = (MoPubView) header.findViewById(R.id.adview);
//		mopubView.setAdUnitId("2639080df6d64c93ab30e388091850e6"); // 2bb070d185634290badca47d46e536f2 // 2639080df6d64c93ab30e388091850e6
//		mopubView.loadAd();
//
//		getListView().addHeaderView(header, null, false);
		listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new String[] { "See the Demo!", "Read More!" });
		setListAdapter(listAdapter );
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		switch (position) {
			case 0:
				navigations[0].run(); //show demo
				break;
			case 1:
				navigations[1].run(); //blog post
				break;
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		setListAdapter(null);
	}
}
