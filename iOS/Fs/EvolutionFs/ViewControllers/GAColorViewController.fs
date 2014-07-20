namespace EvolutionFs
open System
open System.Linq
open System.Drawing
open System.Collections.Generic

open MonoTouch.UIKit
open MonoTouch.Foundation
open GAColor

type GAColorViewController() as this=
    inherit UIViewController()

    let viewWidth = 32
    let viewHeight = 32

    let mutable population = 0

    let fitnessLabel = 
        new UILabel(new RectangleF(10.f, float32(64 + 10 * viewHeight + 10) , 140.f, 30.f))

    let populationLabel = 
        new UILabel(new RectangleF(10.f, fitnessLabel.Frame.Y + fitnessLabel.Frame.Height, 140.f, 30.f))

    let targetLabel = 
        new UILabel()

    let mutable views = 
        Array.init 100 (fun x -> new UIView(this.View.Bounds))

    //this is definitely not functional programming. but it's funny how you can still do this
    let Frames = 
        let rectangles = new List<RectangleF>()
        for i = 0 to 9 do
            for j = 0 to 9 do
                rectangles.Add(new RectangleF(new PointF( float32(viewWidth * j), float32(viewHeight * i + 64)), new SizeF( float32(viewWidth), float32(viewHeight)))) 
            done
        done
        rectangles

    let refreshColors (fromPopulation : Citizen[]) () = 
        views
        |> Array.iteri (fun i x -> x.BackgroundColor <- (fst fromPopulation.[i]).ToUIColor())

        targetLabel.BackgroundColor <- (!GA_TARGET).ToUIColor()
    
    let continuationToken = ref true

    let workingPop = ref seedPopulation

    let StartAlgorithm = 
        EventHandler(fun _ _ -> this.NavigationItem.RightBarButtonItem.Enabled <- false
                                this.InvokeOnMainThread( fun() -> async {
                                while !continuationToken do
                                    workingPop := (childGeneration !workingPop)
                                    let fitness = getFullFitness !workingPop
                                    if(fitness < 25) then
                                        GA_TARGET := (FromBin(createBinString 24))
                                        population <- population + 1
                                        populationLabel.Text <- "Population: " + (population).ToString()
                                        do! Async.Sleep(2000)
                                    UIView.Animate(float(1.0f),
                                                   float(0.0f),
                                                   UIViewAnimationOptions.TransitionNone,
                                                   new NSAction( refreshColors !workingPop ),
                                                   fun() -> ())
                                    do! Async.Sleep(1100)
                                    fitnessLabel.Text <- "Fitness: " + (fitness).ToString()} |> Async.StartImmediate))

    do
        this.Title <- "Evolution of Color"
        this.View.Frame <- UIScreen.MainScreen.Bounds
        this.View.BackgroundColor <- UIColor.White
        this.View.AutoresizingMask <- UIViewAutoresizing.FlexibleWidth
        this.NavigationItem.SetRightBarButtonItem(new UIBarButtonItem("Start", UIBarButtonItemStyle.Plain, StartAlgorithm), true)

        //set views
        views 
        |> Array.iteri (fun i x ->  x.BackgroundColor <- (fst GAColor.seedPopulation.[i]).ToUIColor()
                                    x.Frame <- Frames.[i])

        this.View.AddSubviews views

        //set labels
        fitnessLabel.Text <- String.Format("Fitness: 0");

        targetLabel.Text <- "Target"
        targetLabel.TextAlignment <- UITextAlignment.Center
        targetLabel.Frame <- new RectangleF(new PointF(fitnessLabel.Frame.X + fitnessLabel.Frame.Width + 10.f, fitnessLabel.Frame.Y), new SizeF(100.f, 50.f))
        targetLabel.BackgroundColor <- (!GA_TARGET).ToUIColor()

        populationLabel.Text <- "Popluation: 0"

        this.View.AddSubviews(fitnessLabel, targetLabel, populationLabel)

    override this.ViewDidDisappear(animated) =
        base.ViewDidDisappear(animated)
        this.NavigationItem.RightBarButtonItem.Enabled <- true
        continuationToken := false