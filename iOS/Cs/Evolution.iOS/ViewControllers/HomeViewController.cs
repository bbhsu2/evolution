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
using System.Collections.Generic;
using System.Drawing;

namespace Evolution.iOS
{
    public class HomeViewController : UITableViewController
    {
        public event Action GoToSettingsVC = delegate { };

        JBKenBurnsView imageView;
        HomeTableSource source;
        RectangleF bounds;
        int CellHeight = 55;

        public HomeViewController(Action GoToSettingsVC)
        {
            Title = "Evolution++";
            NavigationItem.BackBarButtonItem = new UIBarButtonItem("", UIBarButtonItemStyle.Plain, handler: null);

            this.GoToSettingsVC = GoToSettingsVC;

            LoadTable();
        }

        private void LoadTable()
        {
            TableView.RowHeight = CellHeight;
            TableView.Source = source = new HomeTableSource();
            source.GoToActions = new Action[1] { this.GoToSettingsVC };
            source.Items = new List<string>() { "Evolution of Color" };
            TableView.ReloadData();
        }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            View.Frame = this.bounds = UIScreen.MainScreen.Bounds;
            View.BackgroundColor = UIColor.White;
            View.AutoresizingMask = UIViewAutoresizing.FlexibleWidth | UIViewAutoresizing.FlexibleHeight;

            imageView = new JBKenBurnsView
            {
                Frame = new RectangleF(0, -20, 320, bounds.Height - (2 * CellHeight) - 75),
                Images = new List<UIImage> {
					UIImage.FromFile("Assets/splash_square.png"),
					UIImage.FromFile("Assets/splash_square.png"),
				},
                UserInteractionEnabled = false,
            };
            TableView.TableHeaderView = new UIView(new RectangleF(0, 0, bounds.Width, imageView.Frame.Bottom));
            View.AddSubview(imageView);
        }

        public override void ViewWillAppear(bool animated)
        {
            base.ViewWillAppear(animated);
			if (AppDelegate.Shared.adBanner != null)
			{
				//AppDelegate.Shared.adBanner.RemoveFromSuperview ();
				AppDelegate.Shared.adBanner.Hidden = true;
			}
            imageView.Animate();
        }
    }
}