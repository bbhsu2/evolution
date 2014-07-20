module GAColor

open System
open System.Collections.Generic
open System.Linq
open MonoTouch.UIKit

let rnd = Random()

let getRandom = 
    rnd.Next(48,50)

let convertToString = 
    Array.map(sprintf "%i") >> String.concat ""

let setBinValue (value : String) = 
    let len = value.Length
    match len with
    | 24 -> value
    | _  -> let buffer : int array = Array.zeroCreate (24-len)
            convertToString buffer + value

//setup the types that make up the individual Citizens
type Color(R, G, B, hexVal, binVal) = 
    member this.R = R
    member this.G = G
    member this.B = B
    member this.hexVal = hexVal
    member this.binVal = binVal
    member this.ToUIColor () =
        UIColor.FromRGB (float32 this.R, float32 this.G, float32 this.B)

type Fitness = int

type Citizen = Color * Fitness

//setup ways to create Color part of Citizen
let FromHex (hex:int) : Color =
    let at offset = double ((hex >>> offset) &&& 0xFF)
    let str (value : int) = value.ToString("X2")
    let bin (value : string) = setBinValue (Convert.ToString(Convert.ToInt32(value, 16), 2))
    let r = (int)(at 16)
    let g = (int)(at 8)
    let b = (int)(at 0)
    let rgbString = str r + str g + str b
    let binString = bin rgbString
    new Color(R = (float)r /255.0,
        G = (float)g /255.0,
        B = (float)b /255.0,
        hexVal = rgbString,
        binVal = binString)
 
let FromBin bin : Color= 
    FromHex (Int32.Parse(Convert.ToInt32(bin, 2).ToString("X2"), System.Globalization.NumberStyles.AllowHexSpecifier))
 
//let Purple =    FromHex 0xB455B6
//let Blue =      FromHex 0x3498DB
//let DarkBlue =  FromHex 0x2C3E50
//let Green =     FromHex 0x77D065
//let Gray =      FromHex 0x738182
//let LightGray = FromHex 0xB4BCBC
let EvoGreen =  FromHex 0x44B542

let GA_TARGET = ref EvoGreen
let GA_POPSIZE = 100
let GA_ELITISM = 14
let GA_MUTATIONRATE = 0.25

let difference (x:char)(y:char) = 
    abs((int)x - (int)y)
 
let calculateFitness (target: Color) (citizen: Color) = 
    Array.map2(fun x y -> difference x y) (target.binVal.ToCharArray()) (citizen.binVal.ToCharArray())
    |> Array.sum
 
let getFitness = 
    calculateFitness !GA_TARGET
 
let createBinString size =
    let newBinValue = Array.init<char> size (fun _ -> (char)(rnd.Next(48,50))) //'0' and '1' are 48, 49
    System.String.Concat(newBinValue)
 
let createBinStrings numberOfCitizens = 
    let binStrings = Array.init<string> numberOfCitizens (fun _ -> createBinString 24)
    binStrings

let seedPopulation : Citizen[] = 
    let citizens = new List<Citizen>()
    let binStrings = createBinStrings GA_POPSIZE
    for i = 0 to (GA_POPSIZE - 1) do
        let citoyenLouisCapet = FromBin(binStrings.[i])
        citizens.Add( (citoyenLouisCapet), getFitness citoyenLouisCapet)
    citizens.ToArray() |> Array.sortBy( fun (_, f) -> f) //this is not functional, but I can't get random to work without doing this

let getBourgeoisie (population : Citizen[]) = 
    population.[..GA_ELITISM]

let mutate (mom : Color) (dad : Color) = 
    let index = rnd.Next((!GA_TARGET).binVal.Length)
    let sb = new System.Text.StringBuilder()
    sb.Append(mom.binVal.[..(index-1)]) |> ignore 
    sb.Append(createBinString 1) |> ignore
    sb.Append(dad.binVal.[index+1..]) |> ignore
    sb.ToString()
 
let matePopulation (population : Citizen[]) = 
    let bourgeoisieNumber = (getBourgeoisie population).Length
    let mom = fst population.[rnd.Next(0, GA_POPSIZE / 2 )]
    let dad = fst population.[rnd.Next(0, GA_POPSIZE / 2 )]
    let index = rnd.Next((!GA_TARGET).binVal.Length)
  
    let newBin = (Array.append (mom.binVal.[..index].ToCharArray()) (dad.binVal.[index+1..].ToCharArray()))
    let chromosome = ref (FromBin(mutate mom dad))

    if not (rnd.NextDouble() < GA_MUTATIONRATE) then
        chromosome := FromBin(String.Concat(newBin))
    
    let fitness = calculateFitness (!GA_TARGET) (!chromosome)

    (!chromosome, fitness) 

let childGeneration population = 
    let bourgeoisie = (getBourgeoisie population)
    let proletariatSize = float32(GA_ELITISM) / 0.15f //150 - GA_ELITISM  //population.Length - GA_ELITISM
    Array.init<Citizen> (int(proletariatSize)) (fun _ -> matePopulation population)
    |> Array.append <| bourgeoisie
    |> Array.sortBy (fun (_, f) -> f)

let getFullFitness (population : Citizen[]) = 
    ResizeArray (population |> Seq.map snd) |> Seq.sum