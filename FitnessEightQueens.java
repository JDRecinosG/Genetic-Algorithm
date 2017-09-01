//
// Genetic Algorithm
// Eight Queens Fitness Tester
// Juan Recinos
//

import java.util.*;

public class FitnessEightQueens implements Fitness {
  
  private int chromSize;
  private int fitnessThresh;
  
  public FitnessEightQueens( int chromSize ) {
    this.chromSize = chromSize;
    this.fitnessThresh = 0;
    for ( int i = chromSize - 1; i > 0; i-- ) {
      this.fitnessThresh += chromSize;
    }    
    this.fitnessThresh /= 2;
  }
  
  public int returnChromosomeSize() {
    return chromSize;
  }
  public int returnFitnessThreshhold() {
    return fitnessThresh;
  }
  public int returnFitness( ArrayList<Object> chromosome ) {    
    
    int returnFit = fitnessThresh;
    for ( int i = 0; i < chromSize - 1; i++ ) {
      for ( int j = i + 1; j < chromSize; j++ ) {
        int ival = ( Integer.parseInt( chromosome.get( i ).toString() ) );
        int jval = ( Integer.parseInt( chromosome.get( j ).toString() ) );
        if ( ival == jval )          
          returnFit--;        
        else if ( ival == ( jval - ( j - i ) ) || jval == ( ival - ( j - i ) ) )
          returnFit--;        
      }            
    }
    return returnFit;
  }
  
  public String returnSolution( ArrayList<Object> chromosome ) {    
    String returnString = "";
    
    
    for ( int i = 0; i < chromSize; i++ ) {
      for ( int j = 0; j < chromSize; j++ ) {
        int ival = ( Integer.parseInt( chromosome.get( i ).toString() ));
        if ( ival == j )
          returnString+= "[Q]";
        else
          returnString+= "[_]";
      }
      returnString+= "\n";
    }
    
    return returnString;
  }
  
  public ArrayList<Object> newChromosome() {
    ArrayList<Object> chromosome = new ArrayList<Object>();
    Random rand = new Random();
    for ( int i = 0; i < chromSize; i++ ) {
      chromosome.add( rand.nextInt( chromSize ) );
    }
    return chromosome;
  }
  
  public ArrayList<Chromosome> mate( Chromosome parent1, Chromosome parent2 ) {
    Random rand = new Random();
    int splice = rand.nextInt( chromSize - 1 );
    
    ArrayList<Object> c1 = parent1.returnChromosome();
    ArrayList<Object> c2 = parent2.returnChromosome();
    ArrayList<Object> child1 = new ArrayList<Object>();
    ArrayList<Object> child2 = new ArrayList<Object>();
    
    for ( int i = 0; i < c1.size(); i++ ) {
      if ( i > splice ) {
        child1.add( c2.get( i ) );
        child2.add( c1.get( i ) );        
      }                     
      else {
        child1.add( c1.get( i ) );
        child2.add( c2.get( i ) );        
      }
    }
    
    int mutate = rand.nextInt( 1000 );
    if ( mutate == 999 ) {
      int mutateIndex = rand.nextInt( chromSize ) ;
      child1.set( mutateIndex, rand.nextInt( chromSize ) );
      child2.set( mutateIndex, rand.nextInt( chromSize ) );      
    }
    ArrayList<Chromosome> returnArray = new ArrayList<Chromosome>();
    returnArray.add( new Chromosome( child1, this ) );
    returnArray.add( new Chromosome( child2, this ) );
    return returnArray;
  }
  
}
