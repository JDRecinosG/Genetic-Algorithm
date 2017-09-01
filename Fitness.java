//
// Genetic Algorithm
// Abstract Fitness Tester
// Juan Recinos
//

import java.util.*;

public interface Fitness {

  public abstract int returnChromosomeSize();
  public abstract int returnFitnessThreshhold();
  public abstract int returnFitness( ArrayList<Object> chromosome );
  public abstract String returnSolution( ArrayList<Object> chromosome );
  public abstract ArrayList<Object> newChromosome();
  public abstract ArrayList<Chromosome> mate( Chromosome c1, Chromosome c2 );
  
}