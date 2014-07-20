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

namespace Evolution.Shared
{
    public static class Utils
    {
        public static Color GetRandomColor()
        {
            return Color.FromHex((int)(Model.rnd.NextDouble() * 0xFFFFFF) << 0);
        }

        public static void Mutate(Color citizen)
        {
            char[] s = ((string)citizen).ToCharArray();
            for (int i = 0; i < s.Length; i++)
                if (Model.rnd.NextDouble() <= Model.GA_MUTATIONRATE)
                    s[i] = Char.Equals(s[i], '0') ? '1' : '0';
        }

        public static Color Mate(Color mom, Color dad)
        {
            string momString = (string)mom;
            string dadString = (string)dad;
            int locus = Model.rnd.Next(1, momString.Length);
            string newChild = momString.Substring(0, locus) + dadString.Substring(locus, dadString.Length - locus);
            return Color.FromBin(newChild);
        }

        public static int GetFitness(Color citizen, Color target)
        {
            int alleleMatchCount = 0;
            for (int i = 0; i < citizen.binVal.Length; i++)
                alleleMatchCount += Math.Abs((int)citizen.binVal[i] - (int)target.binVal[i]);
            return alleleMatchCount;
        }
    }
}
