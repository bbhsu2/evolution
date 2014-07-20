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


using Evolution.Shared;
using MonoTouch.Foundation;
using MonoTouch.UIKit;
using System;

namespace Evolution.iOS
{
	[Register ("AppDelegate")]
	public partial class AppDelegate : UIApplicationDelegate
	{
		public static AppDelegate Shared;
        public bool IsAdSupported = true;
        public string CustomAdString;
		public Model model;
        public EvoAd adBanner;

		UINavigationController navigation;
        GAColorViewController gaColorVC;
        ColorPickerViewController colorPickerVC;
		UIWindow window;

		public override bool FinishedLaunching(UIApplication app, NSDictionary options)
		{
			Shared = this;
			model = new Model();
			window = new UIWindow(UIScreen.MainScreen.Bounds);
			UIApplication.SharedApplication.SetStatusBarStyle(UIStatusBarStyle.LightContent, true);

			window.RootViewController = navigation = new UINavigationController(new HomeViewController(ShowInfoViewController));
			navigation.NavigationBar.TintColor = UIColor.White;
			navigation.NavigationBar.BarTintColor = Color.EvoGreen;
			navigation.NavigationBar.SetTitleTextAttributes (new UITextAttributes() {TextColor = UIColor.White} );
			window.MakeKeyAndVisible();

			SetColorPickerVC();
            gaColorVC = new GAColorViewController();

			return true;
		}

        UIBarButtonItem doneBtn;
        private void SetColorPickerVC()
        {
            colorPickerVC = new ColorPickerViewController();
            colorPickerVC.ColorPicked += SetTargetColor;
            colorPickerVC.Title = "Pick a color!";

            doneBtn = new UIBarButtonItem(UIBarButtonSystemItem.Done);
            doneBtn.Clicked += doneBtn_Clicked;
            colorPickerVC.NavigationItem.SetRightBarButtonItem(doneBtn, false);
        }

        public void ShowInfoViewController()
        {
			var infoVC = new InformationViewController (Model.Information, ShowGAColorViewController, ShowBlogPost);
			navigation.PushViewController(infoVC, true);
        }

        public void ShowGAColorViewController()
        {
            navigation.PushViewController(gaColorVC, true);
        }

		public void ShowBlogPost()
		{
			navigation.PushViewController(new BlogPostViewController(), true);
		}

        public void ShowColorPicker(object sender, EventArgs e)
        {
            navigation.PushViewController(colorPickerVC, true);
        }

        void doneBtn_Clicked(object sender, EventArgs e)
        {
            SetTargetColor();
            navigation.PopViewControllerAnimated(true);
        }

        public void AddAdBanner()
        {
            adBanner = new EvoAd();
            adBanner.OnCustomAdTapped += CustomAdClicked;

            window.RootViewController.Add(adBanner);
        }

        public void CustomAdClicked(UITapGestureRecognizer tap)
        {
            if (CustomAdString != null)
                UIApplication.SharedApplication.OpenUrl(new NSUrl(CustomAdString));
            else
                UIApplication.SharedApplication.OpenUrl(new NSUrl("https://itunes.apple.com/us/app/normal-lab-values++/id790880885?mt=8&uo=4"));
        }

        public void SetTargetColor()
        {
			Model.GA_TARGET = Color.FromUIColor(colorPickerVC.SelectedColor);
        }
	}
}

