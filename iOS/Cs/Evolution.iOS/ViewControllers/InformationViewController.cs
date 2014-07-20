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

using MonoTouch.UIKit;
using System;
using System.Drawing;
using System.Collections.Generic;

namespace Evolution.iOS
{
    public class InformationViewController : UITableViewController
    {
		public event Action ShowDemo = delegate{};
		public event Action ShowBlogPost = delegate{};

        UITextView informationTextView;
		int CellHeight = 55;
		HomeTableSource source;
		string Information;
		RectangleF bounds;

		public InformationViewController(string Information, Action ShowDemo, Action ShowBlogPost)
        {
            Title = "About";
			this.ShowDemo = ShowDemo;
			this.ShowBlogPost = ShowBlogPost;
			this.Information = Information;

            View.Frame = UIScreen.MainScreen.Bounds;
            View.BackgroundColor = UIColor.White;
            View.AutoresizingMask = UIViewAutoresizing.FlexibleWidth | UIViewAutoresizing.FlexibleHeight;
            NavigationItem.BackBarButtonItem = new UIBarButtonItem("", UIBarButtonItemStyle.Plain, handler: null);

            informationTextView = new UITextView { 
                Text = Information,
                TextColor = UIColor.Black,
                Font = UIFont.FromName("HelveticaNeue", 20),
                TextAlignment = UITextAlignment.Center,
                Frame = new RectangleF(
                    20,
                    64,
                    View.Bounds.Width - 40,
                    230),
                Editable = false,
            };

			if(AppDelegate.Shared.IsAdSupported){
				AppDelegate.Shared.AddAdBanner ();
			}

			LoadTable ();
        }

		void LoadTable ()
		{
			TableView.RowHeight = CellHeight;
			TableView.Source = source = new HomeTableSource();
			source.GoToActions = new Action[2] { this.ShowDemo, this.ShowBlogPost };
			source.Items = new List<string>() { "See Demo!", "Read More!" };
			TableView.ReloadData();
		}

		public override void ViewDidLoad ()
		{
			base.ViewDidLoad ();
			View.Frame = this.bounds =  UIScreen.MainScreen.Bounds;

			informationTextView = new UITextView { 
				Text = Information,
				TextColor = UIColor.Black,
				Font = UIFont.FromName("HelveticaNeue", 18),
				TextAlignment = UITextAlignment.Center,
				Frame = new RectangleF(
					20,
					20,
					View.Bounds.Width - 40,
					230),
				Editable = false,
			};
			TableView.TableHeaderView = new UIView(new RectangleF(0, 0, bounds.Width, informationTextView.Frame.Bottom));
			View.AddSubview(informationTextView);
		}

		public override void ViewWillAppear (bool animated)
		{
			base.ViewWillAppear (animated);
			if (AppDelegate.Shared.IsAdSupported)
				AppDelegate.Shared.adBanner.Hidden = false;
		    
		}

		public override void ViewWillLayoutSubviews()
		{
			base.ViewWillLayoutSubviews();
			if (AppDelegate.Shared.IsAdSupported) //Shift table down if ad is present
			{
				this.TableView.Frame = new RectangleF(0, 50, View.Bounds.Width, View.Bounds.Height); //Shift table down if ad is present
				TableView.ContentInset = new UIEdgeInsets(64, 0, 50, 0); //set the table so all cells are reachable during scroll
			}
		}
    }
}