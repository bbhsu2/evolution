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
using System.Collections.Generic;

namespace Evolution.Shared
{
    public class Model
    {
        public static readonly int GA_POPSIZE = 100;
        public static readonly double GA_ELITRATE = 0.10;
        public static readonly double GA_MUTATIONRATE = 0.25;
        public static readonly Random rnd = new Random();
        public static Color GA_TARGET = Color.Purple;

        public static string Information = "This is a genetic algorithm that changes color.  By using principles of genetics, we evolve a population to conform to a target based on genetic mutation and random mating. For more info, click Read More. Else, see the demo in action!";

        public List<Color> ParentColors { get; set; }
        public List<Color> ChildColors { get; set; }
        public int Generation { get; set; }

        public Model()
        {
            InitializeAll();
        }

        public void InitializeAll()
        {
            this.Generation = 0;
            SeedPopulation();
        }

        private void SeedPopulation()
        {
            ParentColors = new List<Color>();
            ChildColors = new List<Color>();
            for (int i = 0; i < GA_POPSIZE; i++)
            {
                ParentColors.Add(Utils.GetRandomColor());
                ChildColors.Add(Utils.GetRandomColor());
            }
        }

        public void Tick()
        {
			int bourgeoisie = (int)(GA_POPSIZE * GA_ELITRATE);
            for (int i = 0; i < bourgeoisie; i++)
                ChildColors[i] = ParentColors[i];

            for (int i = bourgeoisie; i < GA_POPSIZE; i++)
            {
                int mom = rnd.Next(0, GA_POPSIZE / 2);
                int dad = rnd.Next(0, GA_POPSIZE / 2);
                int spos = rnd.Next(0, GA_TARGET.binVal.Length);

                ChildColors[i] = Color.FromBin(ParentColors[mom].binVal.Substring(0, spos) + ParentColors[dad].binVal.Substring(spos, ParentColors[dad].binVal.Length - spos));
                if (rnd.NextDouble() < GA_MUTATIONRATE)
                {
                    int pos = rnd.Next(0, GA_TARGET.binVal.Length - 1);
                    ChildColors[i] = Color.FromBin(ChildColors[i].binVal.Substring(0, pos) + (rnd.NextDouble() > 0.5 ? '0' : '1') + ChildColors[i].binVal.Substring(pos + 1, 24 - pos - 1));
                }
            }

            Swap();
            SortByFitness(ParentColors);
        }

        private void Swap()
        {
            List<Color> temp = new List<Color>();
            foreach (Color c in ParentColors)
                temp.Add(c);

            for (int i = 0; i < ChildColors.Count; i++)
                ParentColors[i] = ChildColors[i];

            for (int i = 0; i < temp.Count; i++)
                ChildColors[i] = temp[i];
        }

        public void SortByFitness(List<Color> ListToSort)
        {
			ListToSort.Sort((bourgeoisie, proletariat) => bourgeoisie.fitness.CompareTo(proletariat.fitness));
        }
    }
}