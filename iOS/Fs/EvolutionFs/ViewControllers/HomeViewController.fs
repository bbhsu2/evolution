namespace EvolutionFs

open System
open System.Linq
open System.Drawing
open System.Collections.Generic

open MonoTouch.UIKit
open MonoTouch.Foundation
open GAColor

[<AllowNullLiteral>]
type HomeCell() as this =
    inherit UITableViewCell()

    do this.SelectionStyle <- UITableViewCellSelectionStyle.None
       this.Accessory <- UITableViewCellAccessory.DisclosureIndicator
       this.TextLabel.TextColor <- GAColor.EvoGreen.ToUIColor()
    
    member this.Text 
        with get() = this.TextLabel.Text
        and set(value) = this.TextLabel.Text <- value
    
    static member val Key = "c" with get

type HomeListViewSource(optionSelected:string->unit) =
    inherit UITableViewSource()

    member val Items = [||] with get,set

    override this.RowsInSection (tableview, section) =
        if this.Items = [||] then 1 else this.Items.Length

    override this.RowSelected (tableView, indexPath) =
        if not (this.Items = [||]) then
            optionSelected (this.Items.[indexPath.Row])

    override this.GetCell (tableView, indexPath) =
        let cell = match tableView.DequeueReusableCell(HomeCell.Key) with
            | :? HomeCell as cell when not (cell = null) -> cell
            | _ -> new HomeCell()

        cell.Text <- this.Items.[indexPath.Row]
        cell :> UITableViewCell


type HomeViewController() as this =
    inherit UITableViewController()

    let imageView = new JBKenBurnsView()
    let cellHeight = 55.f
    let source = new HomeListViewSource(fun x-> this.OptionSelected(x))

    let LoadTable = 
        this.TableView.RowHeight <- cellHeight
        this.TableView.Source <- source :> UITableViewSource
        source.Items <- [|"Evolution of Color"|]
        this.TableView.ReloadData()
    
    do
        this.Title <- "Evolution and Artificial Life"
        this.NavigationItem.BackBarButtonItem <- new UIBarButtonItem ("", UIBarButtonItemStyle.Plain, handler=null)
        this.View.Frame <- UIScreen.MainScreen.Bounds
        this.View.BackgroundColor <- UIColor.White
        this.View.AutoresizingMask <- UIViewAutoresizing.FlexibleWidth

        imageView.Frame <- new RectangleF(0.f, -20.f, 320.f, this.View.Bounds.Height - (2.f * cellHeight) - 75.f)
        imageView.Images <- new List<UIImage>()
        imageView.Images.Add(UIImage.FromFile("Assets/splash_square.png"))
        imageView.Images.Add(UIImage.FromFile("Assets/splash_square.png"))
        imageView.UserInteractionEnabled <- false

        this.TableView.TableHeaderView <- new UIView(new RectangleF(0.f, 0.f, this.View.Bounds.Width, imageView.Frame.Bottom))
        this.View.AddSubview(imageView)
        LoadTable 

    member val ImageWidth = UIScreen.MainScreen.Bounds.Width * UIScreen.MainScreen.Scale with get
    member val OptionSelected = (fun (x:string)->()) with get,set

    override this.ViewWillAppear animated =
        base.ViewWillAppear (animated)
        imageView.Animate()
