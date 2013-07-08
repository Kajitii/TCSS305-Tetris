/*
 * TCSS305 Assignment 6: Tetris
 * 5/24/2013 Aaron Chen
 */

package view;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.JPanel;

import model.Controls;

/**
 * A single instance of a Tetris graphical user interface.
 * 
 * @author Kajiti
 * @version 0.6.1
 */
public class TetrisGameGUI extends JPanel {

  /** The string representing all other conditions, including a single player game loss. */
  public static final String RESULT_NONE = null;
  
  /** The string representing the loss status for a multiplayer match. */
  public static final String RESULT_LOSE = "YOU LOSE!";
  
  /** The string representing the win status for a multiplayer match. */
  public static final String RESULT_WIN = "YOU WIN!";
  
  /** The string representing the draw status for a multiplayer match. */
  public static final String RESULT_DRAW = "DRAW!";

  /** The serial version UID. */
  private static final long serialVersionUID = -2828059938703055988L;
  
  /** The default starting level. */
  private static final int DEFAULT_LEVEL = 1;
  
  /** The default starting board width. */
  private static final int DEFAULT_BOARD_WIDTH = 10;
  
  /** The default starting board height. */
  private static final int DEFAULT_BOARD_HEIGHT = 20;
  
  /** The gap between the Tetris main board and the side panels. */
  private static final int HGAP = 10;
  
  /** The graphical component that represents the Tetris board. */
  private final TetrisBoardGUI my_board;
  
  /** The graphical component that represents the preview piece display. */
  private final TetrisPreviewGUI my_preview;
  
  /** The graphical component that displays statistics about the current game. */
  private final TetrisStatsGUI my_stats_board;
  
  /** The width of the board when starting a new game. */
  private int my_starting_board_width;

  /** The height of the board when starting a new game. */
  private int my_starting_board_height;
  
  /** The difficulty level to begin at when starting a new game. */
  private int my_starting_level;
  
  
  /**
   * Constructs a new <code>TetrisGameGUI</code>.
   * 
   * @param the_o the top level observer that is responsible for controlling the state of
   *              the multiplayer match.
   * @param the_controls the controls this particular Tetris board will use.
   */
  public TetrisGameGUI(final Observer the_o, final Controls the_controls) {
    super();
    my_preview = new TetrisPreviewGUI();
    my_stats_board = new TetrisStatsGUI();
    my_board = new TetrisBoardGUI(my_preview, my_stats_board.getStats(), the_controls);
    my_board.addObserver(the_o);
    my_starting_level = DEFAULT_LEVEL;
    my_starting_board_width = DEFAULT_BOARD_WIDTH;
    my_starting_board_height = DEFAULT_BOARD_HEIGHT;
    init();
  }
  
  /**
   * Initializes the window and all its components.
   */
  private void init() {
    final GridLayout grid = new GridLayout();
    grid.setHgap(HGAP);
    setLayout(grid);
    final Box box = Box.createVerticalBox();
    add(my_board);
    my_preview.setAlignmentY(Component.CENTER_ALIGNMENT);
    my_stats_board.setAlignmentY(Component.CENTER_ALIGNMENT);
    box.add(my_preview);
    box.add(my_stats_board);
    
    add(box);
    
  }
  
  /**
   * Starts a game of Tetris.
   */
  public void start() {
    my_stats_board.getStats().reset(my_starting_level);
    my_board.start(my_starting_board_width, my_starting_board_height);
  }
  
  /**
   * Ends the current game of Tetris.
   * 
   * @param the_result the result as determined by the match rules.
   */
  public void endGame(final String the_result) {
    my_board.endGame(the_result);
  }
  
  /**
   * Returns the gameover status of the board.
   * 
   * @return true if the game is over; false otherwise.
   */
  public boolean gameIsOver() {
    return my_board.gameIsOver();
  }
  
  /**
   * Pauses or unpauses the game.
   * 
   * @param the_b true if the game should be paused; false otherwise.
   */
  public void setPause(final boolean the_b) {
    my_board.setPause(the_b);
  }
  
  /**
   * Sends garbage blocks to this board.  Used for multiplayer.
   * 
   * @param the_lines the number of lines of garbage blocks to be sent.
   */
  public void sendGarbage(final int the_lines) {
    my_board.sendGarbage(the_lines);
  }
  
  /**
   * Sets the <code>Board</code>'s dimensions when starting a new game.
   * 
   * @param the_width the width of the <code>Board</code>, in blocks.
   * @param the_height the height of the <code>Board</code>, in blocks.
   */
  public void setStartingBoardDimensions(final int the_width, final int the_height) {
    my_starting_board_width = the_width;
    my_starting_board_height = the_height;
  }
  
  /**
   * Sets the difficulty level when starting a new game.
   * 
   * @param the_level the difficulty level to start at when starting a new game.
   * @throws IllegalArgumentException if <code>the_level</code> is less than 1.
   */
  public void setStartingLevel(final int the_level) {
    if (the_level < 1) {
      throw new IllegalArgumentException("cannot have a starting level below 1.");
    }
    my_starting_level = the_level;
  }
  
  /**
   * Sets the scale of all the GUI elements.
   * 
   * @param the_scale the scale value.
   */
  public void setScale(final double the_scale) {
    my_board.setScale(the_scale);
    my_preview.setScale(the_scale);
    my_stats_board.setScale(the_scale);
  }
  
  /**
   * Handles key input received from the parent component.
   * 
   * @param the_event the event to be passed.
   */
  public void handleKeyEvent(final KeyEvent the_event) {
    my_board.handleKeyEvent(the_event);
  }
  
}
