package fragments;

import android.app.ListFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.allgoodpeopleus.evolution.MainActivity;
import com.allgoodpeopleus.evolutionp.R;
import com.flaviofaria.kenburnsview.KenBurnsView;

public class HomeFragment extends ListFragment {
	KenBurnsView header;
	ArrayAdapter<String> arrayAdapter;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home, null, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		//set the header stuff
		header = new KenBurnsView(getActivity());
		getListView().addHeaderView(header, null, false);
		header.setImageBitmap(StretchedImage());
		
		//set the list adapter
		setListAdapter(arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new String[] {"Conway's Game of Life", "Evolution of Color" /*, "Boids"*/})); 
	}
	
	private Bitmap StretchedImage() {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.splash_square512);

		int imageWidth = bitmap.getWidth();
		int imageHeight = bitmap.getHeight();

		DisplayMetrics displaymetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int newWidth = displaymetrics.widthPixels;

		float scaleFactor = (float) newWidth / (float) imageWidth;
		int newHeight = (int) (imageHeight * scaleFactor);

		return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		switch (position) {
			case 1:
				Runnable[] gameOfLifeNavigations = { 
						new Runnable(){
							public void run() { MainActivity.Shared.ShowGameOfLife(); };
						},
						new Runnable(){
							public void run() { MainActivity.Shared.showBlogPost(MainActivity.gameOfLifeModel.BlogUrl); };
						}
					};
					
				MainActivity.Shared.showInformation(MainActivity.gameOfLifeModel.Information, gameOfLifeNavigations);
				
				break;
				
			case 2:
				Runnable[] evolutionNavigations = { 
						new Runnable(){
							public void run() { MainActivity.Shared.ShowGAColor(); };
						},
						new Runnable(){
							public void run() { MainActivity.Shared.showBlogPost(MainActivity.evolutionModel.BlogUrl); };
						}
					};
					
				MainActivity.Shared.showInformation(MainActivity.evolutionModel.Information, evolutionNavigations);
				
				break;
				
			case 3:
				Runnable[] boidsNavigations = { 
						new Runnable(){
							public void run() { MainActivity.Shared.ShowBoids(); };
						},
						new Runnable(){
							public void run() { MainActivity.Shared.showBlogPost(MainActivity.evolutionModel.BlogUrl); };
						}
					};
				MainActivity.Shared.ShowBoids();	
				//MainActivity.Shared.showInformation(MainActivity.evolutionModel.Information, boidsNavigations);
				break;
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		setListAdapter(null);
	}
}
