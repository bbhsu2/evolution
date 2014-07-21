package fragments;

import views.EvolutionView;
import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allgoodpeopleus.evolution.MainActivity;
import com.allgoodpeopleus.evolution.EvolutionModel;
import com.allgoodpeopleus.evolutionp.R;

import evolution.shared.Color;
import evolution.shared.Utils;

public class EvolutionFragment extends Fragment implements Runnable {
	LinearLayout fl;
	Handler mHandler = new Handler();
	EvolutionView ev;
	TextView generation;
	TextView population;
	View targetColorView;
	Button startButton;
	int generationCount = 1;
	int populationCount = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_evolution, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// add evolutionView to the top frame
		fl = (LinearLayout) getView().findViewById(R.id.top_evolution_layout);
		ev = new EvolutionView(getActivity());
		fl.addView(ev);

		// set the start button
		startButton = (Button) getView().findViewById(R.id.evolution_start_button);
		startButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startButton.setEnabled(false);
				run();
			}
		});
		
		// set the picker button
		final Button colorPickerButton = (Button) getView().findViewById(
				R.id.evolution_colorpicker_button);
		colorPickerButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//open color picker
				colorpicker();
			}
		});
		
		//set the pop and gen labels
		generation = (TextView)getView().findViewById(R.id.evolution_generation_textview);
		population = (TextView)getView().findViewById(R.id.evolution_population_textview);
		targetColorView = getView().findViewById(R.id.evolution_target_view);
		setTargetBackgroundColor();
	}
	
    public void colorpicker() {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(), android.graphics.Color.parseColor("#"+EvolutionModel.GA_TARGET.getHexval()), new OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog){
            }
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
            	StopRunning();
            	EvolutionModel.GA_TARGET = Color.FromHex(color);
            	setTargetBackgroundColor();
            	startButton.setEnabled(true);
            }
        });
        dialog.show();
    }

	int k = 0;
	@Override
	public void run() {
		MainActivity.evolutionModel.Tick();
		ev.invalidate();
		generation.setText(String.format("Generation: %d", ++generationCount));
		k++;
		if (MainActivity.evolutionModel.Is99PercentFit() || k > 30) {
			k = 0;
			population.setText(String.format("Population: %d", ++populationCount));
			EvolutionModel.GA_TARGET = Utils.GetRandomColor();
			setTargetBackgroundColor();
			Toast.makeText(getActivity(), "99% fitness achieved! New Target!", Toast.LENGTH_LONG).show();
		}
		mHandler.postDelayed(this, 750);
	}
	
	void setTargetBackgroundColor(){
		targetColorView.setBackgroundColor(evolution.shared.Color.GetAndroidColor(EvolutionModel.GA_TARGET));
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		StopRunning();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		StopRunning();
	}
	
	void StopRunning(){
		mHandler.removeCallbacks(this);
		startButton.setEnabled(true);
	}
}
