//
// Genetic Algorithm
// Client
// Juan Recinos
//

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GAClient extends JFrame {
    
  private Population population;
    
  private Container contents;
  private JLabel enterName, enterGenerations, enterPopSize, chromosomeSize;
  private JTextField nameField, generationField, popSizeField, chromosomeSizeField;
  private JTextArea solutionText;
  private JPanel topArea, labelArea, labelArea2, buttonArea;
  private JScrollPane scroll;
  private JButton runAlgorithm, exit;
  
  // Fitness fitness = factory.chooseFitness( enterName );
  
  public GAClient( ) {
    super( "Genetic Algorithm" );
    
    contents = getContentPane( );
    contents.setLayout( new BorderLayout( ) ); 
    
    enterName = new JLabel( "Enter Genetic Algorithm Test: " );
    enterGenerations = new JLabel( "Enter Number of Generations: " );
    enterPopSize = new JLabel( "Enter Population Size: " );
    
    nameField = new JTextField( 20 );
    generationField = new JTextField( 5 );
    popSizeField = new JTextField( 5 );
    
    labelArea = new JPanel();
    labelArea.setLayout( new FlowLayout( ) );
    labelArea.add( enterName );
    labelArea.add( nameField );
    labelArea.add( enterGenerations );
    labelArea.add( generationField );
    labelArea.add( enterPopSize );
    labelArea.add( popSizeField );
    
    runAlgorithm = new JButton( "Run Genetic Algorithm" );
    exit = new JButton( "Exit" );
    
    buttonArea = new JPanel();
    buttonArea.setLayout( new FlowLayout( ) );
    buttonArea.add( runAlgorithm );
    buttonArea.add( exit );
    
    chromosomeSize = new JLabel( "Enter chromosome size: " );
    chromosomeSizeField = new JTextField( 5 );
    
    labelArea2 = new JPanel();
    labelArea2.setLayout( new FlowLayout( ) );
    labelArea2.add( chromosomeSize );
    labelArea2.add( chromosomeSizeField );
    
    topArea = new JPanel();
    topArea.setLayout( new BorderLayout( ) );
    topArea.add( labelArea, BorderLayout.NORTH );
    topArea.add( labelArea2, BorderLayout.CENTER );
    topArea.add( buttonArea, BorderLayout.SOUTH );
    
    solutionText = new JTextArea( 10, 10 );
    solutionText.setEditable( false );
    solutionText.setLineWrap( false );
    scroll = new JScrollPane( solutionText );
    
    contents.add( topArea, BorderLayout.NORTH );
    contents.add( scroll, BorderLayout.CENTER );
    
    RunHandler rh = new RunHandler();
    runAlgorithm.addActionListener( rh );
    ExitHandler eh = new ExitHandler();
    exit.addActionListener( eh );
    
    setSize( 1024, 800 );
    
    setVisible( true );
    
  }
  
  private class RunHandler implements ActionListener {
    public void actionPerformed( ActionEvent e ) {
      population = new Population( nameField.getText(), Integer.parseInt( generationField.getText() ),
                                  Integer.parseInt( popSizeField.getText() ), Integer.parseInt( chromosomeSizeField.getText() ));
      solutionText.append( "Outcome for: " + nameField.getText() + "; Fitness Threshhold is " + population.returnFitnessThreshhold() + "\n" );
      solutionText.append( population.returnStats() );
      solutionText.append( "\n" );
      while ( !population.resolved() && !population.generationLimitReached() ) {
        population.mate( true, false, true );
        solutionText.append( population.returnStats() );
        solutionText.append( "\n" );
      }
      solutionText.append( population.returnStats() );
      solutionText.append( "\n" );
      solutionText.append( population.returnSolution() );
      solutionText.append( "\n" );
    }
  }
  
  private class ExitHandler implements ActionListener {
    public void actionPerformed( ActionEvent e ) {
      System.exit( 0 );
    }
  }
  
  /** main */
  public static void main( String [] args )
  {
    GAClient gui = new GAClient( );
    gui.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
  }
}
