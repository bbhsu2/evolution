package fragments;

import com.allgoodpeopleus.evolutionp.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class WebViewFragment extends Fragment {
	WebView wv;
	String url;
	
	public WebViewFragment(String url){
		this.url = url;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_webview, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		wv = (WebView)getView().findViewById(R.id.webview);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.loadUrl(url);
	}
}
