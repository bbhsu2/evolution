package fragments;

import java.util.List;

import android.app.ListFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.allgoodpeopleus.evolution.MainActivity;
import com.allgoodpeopleus.evolutionp.R;
import com.flaviofaria.kenburnsview.KenBurnsView;

public class HomeFragment extends ListFragment {
	View header;
	ArrayAdapter<String> arrayAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		inflater.inflate(R.layout.fragment_home, container);
		arrayAdapter = new ArrayAdapter<String>(inflater.getContext(),
				android.R.layout.simple_list_item_1,
				new String[] { "Evolution of Color" });
		header = inflater.inflate(R.layout.logo_image, null);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		getListView().addHeaderView(header, null, false);
		setListAdapter(arrayAdapter);
		StretchImage();
	}

	void StretchImage() {
		KenBurnsView kbimageView = (KenBurnsView) getView().findViewById(
				R.id.kbimage);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.splash_square512);

		int imageWidth = bitmap.getWidth();
		int imageHeight = bitmap.getHeight();

		DisplayMetrics displaymetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(displaymetrics);
		int newWidth = displaymetrics.widthPixels;

		float scaleFactor = (float) newWidth / (float) imageWidth;
		int newHeight = (int) (imageHeight * scaleFactor);

		bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
		kbimageView.setImageBitmap(bitmap);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		switch (position) {
		case 1:
			MainActivity.Shared.showInformation();
			break;
		}
	}
	
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setListAdapter(null);
    }

	class HomeListViewAdapter extends BaseAdapter {
		Context context;
		public List<String> Options;

		public HomeListViewAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			if (this.Options == null)
				return 0;
			else
				return Options.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return Options.get(position).hashCode();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return null;
		}

	}
}
