namespace EvolutionFs

open System
open MonoTouch.UIKit
open MonoTouch.Foundation

[<Register ("AppDelegate")>]
type AppDelegate () =
    inherit UIApplicationDelegate ()

    let window = new UIWindow (UIScreen.MainScreen.Bounds)
    let mutable navigation:UINavigationController = null

    let showDemoDetail (demo : string) = 
        match (demo) with
        |"Evolution of Color" -> navigation.PushViewController(new GAColorViewController(), true)
        | _ -> ()

    override this.FinishedLaunching (app, options) =
        let attributes = new UITextAttributes()
        attributes.TextColor <- UIColor.White
        UIApplication.SharedApplication.SetStatusBarStyle(UIStatusBarStyle.LightContent, true)

        let hVC = new HomeViewController()
        hVC.OptionSelected <- fun demo -> showDemoDetail demo

        navigation <- new UINavigationController(hVC)
        navigation.NavigationBar.TintColor <- UIColor.White
        navigation.NavigationBar.BarTintColor <- GAColor.EvoGreen.ToUIColor()
        navigation.NavigationBar.SetTitleTextAttributes (attributes )

        window.RootViewController <- navigation
        window.MakeKeyAndVisible ()
        true

module Main = 
    [<EntryPoint>]
    let main args =
        MonoTouch.UIKit.UIApplication.Main (args, null, "AppDelegate")
        0
