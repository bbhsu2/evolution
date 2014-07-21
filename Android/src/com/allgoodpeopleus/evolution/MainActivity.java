package com.allgoodpeopleus.evolution;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.allgoodpeopleus.evolutionp.R;

import fragments.EvolutionFragment;
import fragments.HomeFragment;
import fragments.InformationFragment;
import fragments.WebViewFragment;

public class MainActivity extends Activity {
	public static MainActivity Shared;
	int baseFragment;
	public static EvolutionModel evolutionModel = new EvolutionModel();
	FragmentManager fragmentManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Shared = this;
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		fragmentManager = getFragmentManager();

		if(fragmentManager.getBackStackEntryCount() ==0 ){
			HomeFragment homeFragment = new HomeFragment();
			baseFragment = homeFragment.getId();
			SwitchScreens(homeFragment, true, true);
		}
	}
	
	void GetAnimationsForFragment(Fragment fragment, List<Integer> anims) {
		anims.set(0, R.anim.enter);
		anims.set(1, R.anim.exit);
		
		switch(fragment.getClass().getName()){
		case "HomeFragment":
			break;
		case "EvolutionFragment":
			break;
		}
	}
	
	public int SwitchScreens(Fragment fragment, Boolean animated, Boolean isRoot){
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		
		if(animated){
			transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.detail_in, R.anim.detail_out);
		}
		transaction.replace(R.id.contentArea, fragment);
		if(!isRoot)
			transaction.addToBackStack(null);
		
		return transaction.commit();
	}
	
	public void showInformation(){
		InformationFragment iFragment = new InformationFragment();
		SwitchScreens(iFragment, true, false);
	}
	
	public void ShowGAColor(){
		EvolutionFragment evFragment = new EvolutionFragment();
		baseFragment = evFragment.getId();
		SwitchScreens(evFragment, true, false);
	}
	
	public void showBlogPost(){
		WebViewFragment wvFragment = new WebViewFragment();
		SwitchScreens(wvFragment, true, false);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.enter, R.anim.exit);
	}
}
