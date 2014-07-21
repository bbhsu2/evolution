package evolution.shared;

import com.allgoodpeopleus.evolution.EvolutionModel;

public class Utils {

    public static Color GetRandomColor()
    {
        return Color.FromHex((int)(EvolutionModel.rnd.nextDouble() * 0xFFFFFF) << 0);
    }
    public static void Mutate(Color citizen)
    {
        char[] s = citizen.binVal.toCharArray();
        for (int i = 0; i < s.length; i++)
            if (EvolutionModel.rnd.nextDouble() <= EvolutionModel.GA_MUTATIONRATE)
                s[i] = s[i] == '0' ? '1' : '0';
    }

    public static Color Mate(Color mom, Color dad)
    {
        String momString = mom.getBinVal();
        String dadString = dad.getBinVal();
        int locus = EvolutionModel.rnd.nextInt(momString.length());
        String newChild = momString.substring(0, locus) + dadString.substring(locus, dadString.length() - locus);
        return Color.FromBin(newChild);
    }

    public static int GetFitness(Color citizen, Color target)
    {
        int alleleMatchCount = 0;
        for (int i = 0; i < citizen.binVal.length(); i++)
            alleleMatchCount += Math.abs((int)citizen.binVal.charAt(i) - (int)target.binVal.charAt(i));
        return alleleMatchCount;
    }
}
