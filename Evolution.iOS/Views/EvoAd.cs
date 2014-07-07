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

using MonoTouch.iAd;
using MonoTouch.UIKit;
using System;
using System.Collections.Generic;

namespace Evolution.iOS
{
    public class EvoAd : ADBannerView
    {
        List<UIImageView> customAds;
        UITapGestureRecognizer tapRecognizer;

        public event Action<UITapGestureRecognizer> OnCustomAdTapped = delegate { };
        public override void LayoutSubviews()
        {
            base.LayoutSubviews();

            //set the bounds for the ad
            var bounds = UIScreen.MainScreen.Bounds;
            this.Frame = new System.Drawing.RectangleF(0, 64, bounds.Width, Frame.Height);


            this.AdLoaded += (object sender, EventArgs e) =>
            {
                if (AppDelegate.Shared.IsAdSupported)
                    this.Hidden = false;
                else
                    this.Hidden = true;
            };

            this.FailedToReceiveAd += (object sender, MonoTouch.iAd.AdErrorEventArgs e) =>
            {
                string[] adLinks = new string[] { "https://itunes.apple.com/us/app/normal-lab-values++/id790880885?mt=8&uo=4", "https://itunes.apple.com/us/app/roots-of-life/id806928953?ls=1&mt=8" };
                if (AppDelegate.Shared.IsAdSupported)
                {
                    if (customAds == null)
                    {
                        customAds = new List<UIImageView>();
                        tapRecognizer = new UITapGestureRecognizer(OnCustomAdTapped)
                        {
                            NumberOfTouchesRequired = 1,
                        };

                        customAds.Add(new UIImageView(UIImage.FromFile("Assets/nlv_adBanner.png")) { UserInteractionEnabled = true });
                        customAds.Add(new UIImageView(UIImage.FromFile("Assets/rol_adBanner.png")) { UserInteractionEnabled = true });
                        foreach(UIImageView v in customAds)
                            v.AddGestureRecognizer(tapRecognizer);
                    }
                    //this is a really bad implementation.  be sure to make a better version
                    Random rnd = new Random();
                    int choose = rnd.Next(0, 2);
                    AppDelegate.Shared.CustomAdString = adLinks[choose];
                    this.AddSubview(customAds[choose]);
                }
                else
                {
                    this.Hidden = true;
                }
            };
        }
    }
}