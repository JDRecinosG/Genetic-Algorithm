//
// Genetic Algorithm
// Fitness Factory
// Juan Recinos
//

import java.util.*;

public class FitnessFactory {
 
  public FitnessFactory() {
  }
  
  public Fitness chooseFitness( String fit, int chromSize ) {
    if ( fit.equals( "Eight Queens" ) ) {
      return new FitnessEightQueens( chromSize );
    }
    else
      throw new IllegalArgumentException( );
  }
  
}