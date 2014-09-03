package fragments;

import views.GameOfLifeView;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allgoodpeopleus.evolution.GameOfLifeModel;
import com.allgoodpeopleus.evolution.MainActivity;
import com.allgoodpeopleus.evolutionp.R;

public class GameOfLifeFragment extends Fragment implements Runnable {

	LinearLayout fl;
	Handler mHandler = new Handler();
	GameOfLifeView gv;
	GameOfLifeModel model = MainActivity.gameOfLifeModel;
	TextView generationTextView;
	TextView cellCountTextView;
	View targetColorView;
	Button startButton;
	Button patternButton;
	int generationCount = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_gameoflife, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// add GameOfLifeView to the top frame
		fl = (LinearLayout) getView().findViewById(R.id.top_gameoflife_layout);
		gv = new GameOfLifeView(getActivity());
		fl.addView(gv);

		// set buttons
		setStartButton();
		setPatternButton();

		// set the pop and gen labels
		generationTextView = (TextView) getView().findViewById(R.id.gameoflife_generation_textview);
		cellCountTextView = (TextView) getView().findViewById(R.id.gameoflife_cells_textview);
	}

	private void setPatternButton() {
		StopRunning();

		final CharSequence[] patterns = { "Glider", "Infinite", "Random", "Starships" };

		final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Pick a pattern").setItems(patterns, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
					case 0:
						MainActivity.gameOfLifeModel.setGlider();
						gv.invalidate();
						break;
					case 1:
						MainActivity.gameOfLifeModel.setInfinite();
						gv.invalidate();
						break;
					case 2:
						MainActivity.gameOfLifeModel.setRandom();
						gv.invalidate();
						break;
					case 3:
						MainActivity.gameOfLifeModel.setStarShip();
						gv.invalidate();
					default:
						break;
				}
			}
		});

		patternButton = (Button) getView().findViewById(R.id.gameoflife_patternpicker_button);
		patternButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				builder.create().show();
			}
		});
	}

	private void setStartButton() {
		startButton = (Button) getView().findViewById(R.id.gameoflife_start_button);
		startButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startButton.setEnabled(false);
				run();
			}
		});
	}

	@Override
	public void run() {
		model.tick();
		gv.invalidate();
		generationTextView.setText(String.format("Generation: %d", ++generationCount));
		cellCountTextView.setText(String.format("Cells alive: %d", model.getNumAlive()));
		mHandler.postDelayed(this, 200);
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

	void StopRunning() {
		mHandler.removeCallbacks(this);
		startButton.setEnabled(true);
		generationCount = 1;
		model.initializeAll();
	}
}
