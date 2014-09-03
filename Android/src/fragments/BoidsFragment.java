package fragments;

import views.BoidsView;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.allgoodpeopleus.evolutionp.R;

public class BoidsFragment extends Fragment implements Runnable {
	
	LinearLayout fl;
	BoidsView bv;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_boids, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		fl = (LinearLayout) getView().findViewById(R.id.top_boids_layout);
		bv = new BoidsView(getActivity());
		fl.addView(bv);
	}

	@Override
	public void run() {
	}
}
