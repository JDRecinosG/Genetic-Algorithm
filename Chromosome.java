//
// Genetic Algorithm
// Chromosome Class
// Juan Recinos
//

import java.util.*;

public class Chromosome {
 
  private ArrayList<Object> chromosome;
  private int fitnessValue;
  private Fitness fitness;
   
  public Chromosome( Fitness fitness ) {
    this.fitness = fitness;
    chromosome = fitness.newChromosome();
  }
  
  public Chromosome( ArrayList<Object> newChromosome, Fitness fitness ) {
    this.fitness = fitness;
    chromosome = newChromosome;
    calculateFitness();
  }
  
  public ArrayList<Object> returnChromosome() {
    return chromosome;
  }
  
  public void calculateFitness() {    
    fitnessValue = fitness.returnFitness( chromosome );
  }
  
  public int getFitness() { return fitnessValue; }
  
  public String returnSolution() {
    return fitness.returnSolution( chromosome );
  }
}