/*Copyright 2014 All Good People LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

using System;
using MonoTouch.UIKit;
using System.Drawing;
using MonoTouch.Foundation;

namespace Evolution.iOS
{
	public class BlogPostViewController : UIViewController
	{
		string url = "http://www.letsthinkabout.us/post/genetics-and-evolution-of-color-in-c-xamarin-ios";
		UIWebView webView;
		LoadingOverlay loadingOverlay;

		public BlogPostViewController()
		{
			Title = "Evolution of Color";

			try
			{
				View.AddSubview(webView = new UIWebView(new RectangleF(
					0,
					0,
					View.Bounds.Width,
					View.Bounds.Height
				)));

				loadingOverlay = new LoadingOverlay(View.Bounds);
				View.AddSubview(loadingOverlay);

				webView.LoadRequest(new NSUrlRequest(new NSUrl(url)));
				webView.ScalesPageToFit = true;
			}
			catch
			{
				UILabel whoops = new UILabel
				{
					Text = "Whoops something went wrong",
					Font = UIFont.FromName("HelveticaNeue", 22),
					Frame = new RectangleF(
						30,
						125,
						125,
						40),
				};
				View.AddSubview(whoops);
			}

			loadingOverlay.Hide();
		}

		public override void ViewWillLayoutSubviews()
		{
			base.ViewWillLayoutSubviews();
			if (AppDelegate.Shared.IsAdSupported)
			{
				AppDelegate.Shared.adBanner.Hidden = false;
				//View.AddSubview (AppDelegate.Shared.adBanner);
				if (webView != null)
					webView.Frame = new RectangleF (
						0,
						50,
						View.Bounds.Width,
						View.Bounds.Height - 50);
			}
			else
			{
				//AppDelegate.Shared.adBanner.RemoveFromSuperview ();
				AppDelegate.Shared.adBanner.Hidden = true;
			}
		}
	}
}

