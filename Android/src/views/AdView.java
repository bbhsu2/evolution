package views;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.allgoodpeopleus.evolutionp.R;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;
import com.mopub.mobileads.MoPubView.BannerAdListener;

public class AdView extends MoPubView implements BannerAdListener{

	ImageView homeBrewAd;
	Context context;
	
	public AdView(Context context) {
		super(context);
	}
	
	public AdView(Context context, AttributeSet attrs){
		super(context, attrs);
		this.context = context;
		this.setAdUnitId("");
		this.loadAd();
		this.setBannerAdListener(this);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
	
	@Override
	public void onBannerClicked(MoPubView arg0) {
	}

	@Override
	public void onBannerCollapsed(MoPubView arg0) {
	}

	@Override
	public void onBannerExpanded(MoPubView arg0) {
	}

	@Override
	public void onBannerFailed(MoPubView arg0, MoPubErrorCode arg1) {
		homeBrewAd = new ImageView(context);
		homeBrewAd.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.nlv_adbanner));
		homeBrewAd.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("market://details?id=com.allgoodpeopleus.NormalLabValues"));
				v.getContext().startActivity(intent);
			}
		});
		addView(homeBrewAd);
	}

	@Override
	public void onBannerLoaded(MoPubView arg0) {
		if (homeBrewAd != null){
			homeBrewAd.setVisibility(View.INVISIBLE);
			homeBrewAd.setImageBitmap(null);
			homeBrewAd.setOnClickListener(null);
		}
	}
}
