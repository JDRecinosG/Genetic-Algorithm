//
// Genetic Algorithm
// Population Class
// Juan Recinos
//

import java.util.*;

public class Population {
  
  private ArrayList<Chromosome> population;
  private Fitness fitness;
  private double avgFit;
  private int maxFit, maxGens, generationNum, totalFitness;
  private Chromosome solutionChromosome, maxChromosome;
  
  public Population( String fit, int numGens, int popSize, int chromSize ) {
    
    FitnessFactory factory = new FitnessFactory();
    fitness = factory.chooseFitness( fit, chromSize );
    
    
    
    population = new ArrayList<Chromosome>();
    maxGens = numGens;
    maxFit = 0;
    
    for ( int i = 0; i < popSize; i++ ) {
      population.add( new Chromosome( fitness ) );
      population.get( i ).calculateFitness();
    }
    
    calculateAverage();
    generationNum++;
  }
  
  public void mate( boolean dropLowest, boolean balancedProbability, boolean keep ) {
    
    // Preserve Size (make this better later)
    int size = population.size();
    
    // Make New List
    ArrayList<Chromosome> newList = new ArrayList<Chromosome>();
    
    // Drop Lowest
    if ( dropLowest ) {
      for ( int i = 0; i < size/10; i++ ) {
        int minFit = Integer.MAX_VALUE;
        int minIndex = 0;
        int counter = 0;
        
        for ( Chromosome chromosome : population ) {          
          if ( chromosome.getFitness() < minFit ) {
            minIndex = counter;
            minFit = chromosome.getFitness();            
          }
          counter++;
        }
        
        population.remove( minIndex );
      }       
    }
    
    // Keep Some
    if ( keep ) {
      ArrayList<Chromosome> keepList = new ArrayList<Chromosome>();
      for ( Chromosome chromosome : population ) {
        keepList.add( chromosome );
      }
      
      for ( int i = 0; i < size/10; i++ ) {
        int tempMax = 0;        
        int maxIndex = 0;
        int counter = 0;
        
        for ( Chromosome chromosome : keepList ) {          
          if ( chromosome.getFitness() > tempMax ) {
            maxIndex = counter;
            tempMax = chromosome.getFitness();            
          }
          counter++;
        }
        
        newList.add( keepList.remove( maxIndex ));
      }       
    }
    
    // Pure random.
    if ( !balancedProbability ) {
      Random rand = new Random();      
      ArrayList<Chromosome> tempList;
      
      for ( int i = 0; i < size/2; i++ ) {
        int rand1 = rand.nextInt( population.size() - 1 );
        int rand2 = rand.nextInt( population.size() - 1 );
        
        while ( rand1 == rand2 ) {
          rand2 = rand.nextInt( population.size() - 1 );
        }
        
        tempList = fitness.mate( population.get( rand1 ), population.get( rand2 ) );
        for ( Chromosome addChromo : tempList )
          if ( newList.size() < size )
          newList.add( addChromo );          
      }       
      
      if ( size != newList.size() )
        throw new IllegalArgumentException();
      
      calculateAverage();
      population = newList;
      generationNum++;
    }
    
    // Balanced Probability
    else {
      Random rand = new Random();
      
      ArrayList<Chromosome> tempList;
      
      for ( int i = 0; i < size/2; i++ ) {
        int rand1 = 0;
        int rand2 = 0;
        
        rand1 = rand.nextInt( totalFitness );
        int counter = 0;
        int stacker = 0;
        int check1 = 0;
        int check2 = 0;
        
        for ( Chromosome chromosome : population ) {
          if ( rand1 <= chromosome.getFitness() + stacker ) {
            check1 = counter;
            break;
          }
          counter++;
        }
        
        check2 = check1;
        
        while (rand1 == rand2) {
          rand2 = rand.nextInt( totalFitness );
          counter = 0;
          stacker = 0;
          
          for ( Chromosome chromosome : population ) {
            if ( rand2 <= chromosome.getFitness() + stacker ) {
              check2 = counter;
              break;
            }
            counter++;
          }
        }
        
        tempList = fitness.mate( population.get( check1 ), population.get( check2 ) );
        for ( Chromosome addChromo : tempList )
          if ( newList.size() < size )
          newList.add( addChromo );                 
      }
      
      if ( size != newList.size() )
        throw new IllegalArgumentException();
      
      calculateAverage();
      population = newList;
      generationNum++;
      
    }
  }
  
  
  public String returnStats() {
    String returnString = "Generation " + generationNum + ": Average Fitness of " + avgFit
      + "; Max Fitness of " + maxFit;
    return returnString;
  }  
  
  public void calculateAverage() {    
    totalFitness = 0;
    maxFit = 0;
    for ( Chromosome chromosome : population ) {
      totalFitness += chromosome.getFitness();
      if ( chromosome.getFitness() > maxFit ) {
        maxChromosome = chromosome;
        maxFit = chromosome.getFitness();
      }
    }
    avgFit = (double) totalFitness / population.size();
  }
  
  public String returnSolution() {
    if ( solutionChromosome != null ) {
      return solutionChromosome.returnSolution();
    }
    return "Fitness Threshhold not met. Best: \n" + maxChromosome.returnSolution();
  }
  
  public int returnFitnessThreshhold() {
    return fitness.returnFitnessThreshhold();
  }
  
  public boolean resolved() {
    calculateAverage();
    
    if ( solutionChromosome != null ) {
      return true;
    }
    
    if ( maxChromosome.getFitness() >= fitness.returnFitnessThreshhold() ) {
      solutionChromosome = maxChromosome;
      return true;
    }
    return false;
  }
  
  public boolean generationLimitReached() {
    if ( generationNum >= maxGens ) {
      return true;
    }
    return false;
  }
  
}                     