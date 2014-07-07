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
using System.Drawing;
using System.Threading.Tasks;

namespace Evolution.iOS
{
    public class GAColorViewController : UIViewController
    {
        int viewWidth = 32;
        int viewHeight = 32;

        RoundedButton colorPickerButton;
        UIView targetColorView;
        UIView[] views;
        UILabel generationLabel, populationLabel, targetLabel;
		bool BreakExecution = false;

        public GAColorViewController()
        {
            Title = "Evolution of Color";

            View.Frame = UIScreen.MainScreen.Bounds;
            View.BackgroundColor = UIColor.White;
            View.AutoresizingMask = UIViewAutoresizing.FlexibleWidth | UIViewAutoresizing.FlexibleHeight;
			SetStartButton ();
            ColorPickerButton();
            SetTargetColorView();
            SetGenerationLabel();

            views = new UIView[100];
            for (int i = 0; i < 10; i++)
            {
                for (int j = 0; j < 10; j++)
                {
                    int index = (i * 10) + j;
                    views[index] = new UIView
                    {
                        BackgroundColor = AppDelegate.Shared.model.ParentColors[index],
                    };
                    views[index].Frame = new RectangleF(new PointF(viewWidth * j, viewHeight * i + 64), new SizeF(viewWidth, viewHeight));
                }
            }
            View.AddSubviews(views);
        }

        int generation = 0;
        int populationNumber = 0;
        void SetGenerationLabel()
        {
            generationLabel = new UILabel {
                Text = string.Format("Generation: 0"),
            };
            populationLabel = new UILabel {
                Text = string.Format("Population: 0"),
            };
            generationLabel.Frame = new RectangleF(colorPickerButton.Frame.X, colorPickerButton.Frame.Y + 40, 140, 30);
            populationLabel.Frame = new RectangleF(targetLabel.Frame.X, generationLabel.Frame.Y, 140, 30);
            View.AddSubviews(generationLabel, populationLabel);
        }

        void SetTargetColorView()
        {
            targetLabel = new UILabel { Text = "Target: " };
            var targetLabelSize = ((NSString)"Target:").StringSize(targetLabel.Font);
            targetLabel.Frame = new RectangleF(new PointF(colorPickerButton.Frame.X + colorPickerButton.Frame.Width + 15, colorPickerButton.Frame.Y), targetLabelSize);
            targetColorView = new UIView {
                BackgroundColor = Model.GA_TARGET,
            };
            targetColorView.Frame = new RectangleF(targetLabel.Frame.X +targetLabel.Frame.Width, targetLabel.Frame.Y -5, viewWidth, viewHeight);
             
            View.AddSubviews(targetLabel, targetColorView);
        }

        private void ColorPickerButton()
        {
            colorPickerButton = new RoundedButton(new RectangleF(
                    10,
                    64 + 10 * viewHeight + 10,
                    140,
                    30
                ), "Select Color");
            colorPickerButton.TouchUpInside += AppDelegate.Shared.ShowColorPicker;
            View.AddSubview(colorPickerButton);
        }

        void Start()
        {
            int i = 0;
            generationLabel.Text = string.Format("Generation: {0}", ++generation);
			targetColorView.BackgroundColor = Model.GA_TARGET; //this is broken.  can someone help?
            foreach (var view in views)
            {
                view.BackgroundColor = AppDelegate.Shared.model.ParentColors[i];
                i++;
            }
        }

        void StartAlgorithm(object sender, System.EventArgs e)
        {
            AppDelegate.Shared.model.InitializeAll();
			NavigationItem.RightBarButtonItem.Enabled = false;
			//NavigationItem.SetRightBarButtonItem(new UIBarButtonItem("Cancel", UIBarButtonItemStyle.Plain, (object s, System.EventArgs arg) => {BreakExecution = true; SetStartButton();}), true);
            InvokeOnMainThread(async () =>
            {
                int k = 0;
                while (true)
                {
					if(BreakExecution){
						break;
					}
                    k++;
                    if (k > 30 | Is99PercentFit)
                    {
                        await Task.Delay(2000);
                        Model.GA_TARGET = Utils.GetRandomColor();
                        populationLabel.Text = string.Format("Population: {0}", ++populationNumber);
                        k = 0;
                    }
                    AppDelegate.Shared.model.Tick();
                    UIView.Animate(1, 0, UIViewAnimationOptions.TransitionNone,
                        () => Start(),
                        () => { });
                    await Task.Delay(1100);
                }
            });
        }

        bool Is99PercentFit {
            get {
                int fit = 0;
                foreach (Color c in AppDelegate.Shared.model.ParentColors)
                    if (c.fitness < 2) fit++;

                return (fit / Model.GA_POPSIZE) > 0.01 ? true : false;
            }
        }

        public override void ViewWillAppear(bool animated)
        {
            base.ViewWillAppear(animated);
            if (AppDelegate.Shared.adBanner != null) {
                AppDelegate.Shared.adBanner.Hidden = true;
				//AppDelegate.Shared.adBanner.RemoveFromSuperview ();
            }
			BreakExecution = false;
			NavigationItem.RightBarButtonItem.Enabled = true;
        }

		void SetStartButton()
		{
			NavigationItem.SetRightBarButtonItem(new UIBarButtonItem("Start", UIBarButtonItemStyle.Plain, StartAlgorithm), true);
		}

		public override void ViewWillDisappear (bool animated)
		{
			base.ViewWillDisappear (animated);
			//stops the invoke on main thread
			BreakExecution = true;
		}
    }
}