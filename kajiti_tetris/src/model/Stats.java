/*
 * TCSS305 Assignment 6: Tetris
 * 6/6/2013 Aaron Chen
 */

package model;

import java.util.Observable;
import java.util.Observer;

/**
 * The stats for the Tetris game.
 * 
 * @author Kajiti
 * @version 0.6.1
 */
public class Stats extends Observable implements Observer {
  
  /** The default level when starting a new game. */
  private static final int DEFAULT_LEVEL = 1;
  
  /** The initial number of lines needed to advance to level 2. */
  private static final int INITIAL_LINES = 10;
  
  /** Mathematical constant for calculating level progression. */
  private static final int LINES_PER_LEVEL_INCREMENT = 2;
  
  /** The base score value of a single line clear. */
  private static final int SCORE_PER_LINE = 100;
  
  /** The text to be displayed in stats. */
  private static final String[] STATS_STRING = {
    "Score",
    "Lines",
    "Level",
  };

  /** The index for the score in the current game. */
  private static final int SCORE_INDEX = 0;
  
  /** The index for the number of lines cleared in the current game. */
  private static final int LINES_INDEX = 1;

  /** The index for the difficulty level of the current game. */
  private static final int LEVEL_INDEX = 2;
  
  /** The statistics for the current game. */
  private final int[] my_stats;
  
  /** The lines required to advance to the next level. */
  private int my_lines_to_next_level;

  
  /**
   * Constructs a new <code>Stats</code> object.
   * 
   * @param the_level the level which to start the game at.
   */
  public Stats(final int the_level) {
    super();
    my_stats = new int[STATS_STRING.length];
    reset(the_level);
  }
  
  /**
   * The default constructor for <code>Stats</code>.
   */
  public Stats() {
    this(DEFAULT_LEVEL);
  }
  
  /**
   * Resets the statistics.
   * 
   * @param the_level the level which to start the game at.
   */
  public final void reset(final int the_level) {
    for (int i = 0; i < my_stats.length; i++) {
      my_stats[i] = 0;
    }
    my_stats[LEVEL_INDEX] = the_level;
    my_lines_to_next_level = INITIAL_LINES + the_level * LINES_PER_LEVEL_INCREMENT;
    setChanged();
    notifyObservers();
  }
  
  /**
   * Returns the labels for the statistics.
   * 
   * @return the labels for the statistics.
   */
  public final String[] getStatsLabels() {
    return STATS_STRING.clone();
  }
  
  /**
   * Returns the statistics.
   * 
   * @return the statistics.
   */
  public final int[] getStats() {
    return my_stats.clone();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void update(final Observable the_o, final Object the_arg) {
    //lines cleared reported from Board
    if (the_arg instanceof Integer) {
      final int lines = (Integer) the_arg;
      my_stats[LINES_INDEX] += lines;
      my_stats[SCORE_INDEX] += lines * lines * SCORE_PER_LINE;
      setChanged();
      if (my_stats[LINES_INDEX] >= my_lines_to_next_level) {
        my_lines_to_next_level += ++my_stats[LEVEL_INDEX] * LINES_PER_LEVEL_INCREMENT;
        notifyObservers(my_stats[LEVEL_INDEX]);
      }
      notifyObservers();
    }
  }

}
