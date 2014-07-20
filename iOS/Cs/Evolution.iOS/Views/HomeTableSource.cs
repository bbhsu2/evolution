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
using System.Collections.Generic;
using MonoTouch.Foundation;

namespace Evolution.iOS
{
	class HomeTableSource : UITableViewSource
	{
		public Action[] GoToActions;

		public List<string> Items;

		public override UITableViewCell GetCell(UITableView tableView, NSIndexPath indexPath)
		{
			var cell = tableView.DequeueReusableCell(HomeCell.Key) as HomeCell ?? new HomeCell();
			cell.Text = Items[indexPath.Row];
			return cell;
		}

		public override int RowsInSection(UITableView tableview, int section)
		{
			return Items.Count;
		}

		public override void RowSelected(UITableView tableView, NSIndexPath indexPath)
		{
//			if (AppDelegate.Shared.IsAdSupported)
//				AppDelegate.Shared.AddAdBanner();
			GoToActions[indexPath.Row].Invoke();
			tableView.DeselectRow(indexPath, false);
		}
	}
}

