/*
 * TCSS305 Assignment 3: easy street
 * 4/18/2013 Aaron Chen
 */

package view;

import javax.swing.SwingUtilities;

/**
 * Tetris main for running Tetris games!
 * 
 * @author Kajiti
 * @version 0.6.1
 */
public final class TetrisMain {

  /**
   * Private constructor to prevent instantiation.
   */
  private TetrisMain() {
    throw new IllegalArgumentException();
  }
  
  /**
   * Runs the Tetris programs.
   * 
   * @param the_args command line arguments, not used.
   */
  public static void main(final String[] the_args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new TetrisGUI();
      }
    });
  }

}
