package com.allgoodpeopleus.evolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import evolution.shared.Color;
import evolution.shared.Utils;

public class EvolutionModel {
    public static final int GA_POPSIZE = 400;
    public static final double GA_ELITRATE = 0.10;
    public static final double GA_MUTATIONRATE = 0.25;
    public static final Random rnd = new Random();
    public static Color GA_TARGET = Color.Purple;
    public static final String Information = "This is a genetic algorithm that evolves color based on population.  By randomly mating and mutating colors, your device figures out the target, to converge on a solution!";

    public List<Color> ParentColors;
    public List<Color> ChildColors;
    public int Generation;
    public List<Double> FitnessHistory;
    public int CachedTotalFitness;

    public EvolutionModel(){
        InitializeAll();
    }

    public void InitializeAll(){
        this.Generation = 0;
        this.FitnessHistory = new ArrayList<Double>();
        SeedPopulation();
    }

    private void SeedPopulation(){
        ParentColors = new ArrayList<Color>();
        ChildColors = new ArrayList<Color>();
        for (int i = 0; i < GA_POPSIZE; i++) {
            ParentColors.add(Utils.GetRandomColor());
            ChildColors.add(Utils.GetRandomColor());
        }
    }

    public int TotalFitness() {
        int totalFitness = 0;
        for (int i = 0; i < ParentColors.size(); i++)
            totalFitness += Utils.GetFitness(ParentColors.get(i), Color.Purple);
        return (CachedTotalFitness = totalFitness);
    }

    public Boolean Is99PercentFit(){
        final int maxFitness = 24 * GA_POPSIZE;
        int allFitness = 0;
        for(Color c : ParentColors){
            allFitness += c.getFitness();
        }
        return allFitness <= 0.01 * maxFitness ? true : false;
    }

    public void Tick()
    {
        int esize = (int)(GA_POPSIZE * GA_ELITRATE);
        for (int i = 0; i < esize; i++)
            ChildColors.set(i, ParentColors.get(i));

        for (int i = esize; i < GA_POPSIZE; i++) {
            int i1 = rnd.nextInt(GA_POPSIZE / 2);
            int i2 = rnd.nextInt(GA_POPSIZE / 2);
            int spos = rnd.nextInt(GA_TARGET.getBinVal().length());

            ChildColors.set(i, Color.FromBin(ParentColors.get(i1).getBinVal().substring(0, spos) + ParentColors.get(i2).getBinVal().substring(spos, GA_TARGET.getBinVal().length())));//  ParentColors.get(i2).binVal.length() - spos)));
            if (rnd.nextDouble() < GA_MUTATIONRATE) {
                int pos = rnd.nextInt(GA_TARGET.getBinVal().length() - 1);
                ChildColors.set(i, Color.FromBin(ChildColors.get(i).getBinVal().substring(0, pos) + (rnd.nextDouble() > 0.5 ? '0' : '1') + ChildColors.get(i).getBinVal().substring(pos + 1, GA_TARGET.getBinVal().length())));
            }
        }

        Swap();
        SortByFitness(ParentColors);
    }

    private void Swap() {
        List<Color> temp = new ArrayList<Color>();
        for (Color c : ParentColors)
            temp.add(c);

        for (int i = 0; i < ChildColors.size(); i++)
            ParentColors.set(i, ChildColors.get(i));

        for (int i = 0; i < temp.size(); i++)
            ChildColors.set(i, temp.get(i));
    }

    Comparator<Color> colorComparator = new Comparator<Color>(){
        @Override
        public int compare(Color lhs, Color rhs) {
            return Integer.compare(lhs.getFitness(), rhs.getFitness());
        }};
    public void SortByFitness(List<Color> ListToSort) {
        Collections.sort(ListToSort, colorComparator);
    }


    public List<Color> getParentColors() {
        return ParentColors;
    }

    public void setParentColors(List<Color> parentColors) {
        ParentColors = parentColors;
    }

    public List<Color> getChildColors() {
        return ChildColors;
    }

    public void setChildColors(List<Color> childColors) {
        ChildColors = childColors;
    }

    public int getGeneration() {
        return Generation;
    }

    public void setGeneration(int generation) {
        Generation = generation;
    }

    public List<Double> getFitnessHistory() {
        return FitnessHistory;
    }

    public void setFitnessHistory(List<Double> fitnessHistory) {
        FitnessHistory = fitnessHistory;
    }

    public int getCachedTotalFitness() {
        return CachedTotalFitness;
    }

    public void setCachedTotalFitness(int cachedTotalFitness) {
        CachedTotalFitness = cachedTotalFitness;
    }
}
