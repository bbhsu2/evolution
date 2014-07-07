using MonoTouch.UIKit;
using System.Drawing;

namespace Evolution.iOS
{
	public class RoundedButton : UIButton
	{
		float selectionButtonWidth = 280.0f;
		float selectionButtonHeight = 50.0f;

		public RoundedButton(RectangleF bounds, string Title)
		{
			Frame = bounds;
			//Frame = new RectangleF(
			//        bounds.Width / 2 - selectionButtonWidth / 2,
			//        bounds.Height / 2 - selectionButtonHeight / 2,
			//        selectionButtonWidth,
			//        selectionButtonHeight);
			Layer.BorderWidth = 1;
			Layer.CornerRadius = 4;
			Layer.BorderColor = UIColor.Black.CGColor;
			
			SetTitle(Title, UIControlState.Normal);
			SetTitleColor(UIColor.Black, UIControlState.Normal);
		}
	}
}