/*
 * TCSS305 Assignment 6: Tetris
 * 5/24/2013 Aaron Chen
 */

package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

import java.util.Observable;
import java.util.Observer;

import model.Stats;

/**
 * The graphical user interface for the statistics.  
 * 
 * @author Kajiti
 * @version 0.6.1
 */
public class TetrisStatsGUI extends AbstractScalableGUI implements Observer {

  /** The serial version UID. */
  private static final long serialVersionUID = -9154045033859776802L;

  /** The default width of the stats panel. */
  private static final int WIDTH = 120;
  
  /** The default height of the stats panel. */
  private static final int HEIGHT = 200;
  
  /** The default background color for UI windows (not necessarily Containers). */
  private static final Color BG_COLOR = new Color(0x00, 0x00, 0x00, 0xFF);
  
  /** The default font color for the statistics panel. */
  private static final Color FONT_COLOR = Color.WHITE;
  
  /** The default starting level. */
  private static final int DEFAULT_STARTING_LEVEL = 1;
  
  /** The font to be used for text. */
  private static final Font FONT = new Font(Font.SERIF, Font.PLAIN, 12);
  
  /** The object which holds all the statistics about this game. */
  private final Stats my_stats;
  
  /** The level which to start at. */
  private int my_starting_level;
  
  
  /**
   * Constructs a new <code>TetrisStatsGUI</code>.
   */
  public TetrisStatsGUI() {
    super(WIDTH, HEIGHT);
    my_starting_level = DEFAULT_STARTING_LEVEL;
    my_stats = new Stats(my_starting_level);
    my_stats.addObserver(this);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void paintComponent(final Graphics the_graphics) {
    final Graphics2D g2d = (Graphics2D) the_graphics;
    g2d.setColor(BG_COLOR);
    g2d.fillRect(0, 0, getWidth(), getHeight());
    g2d.setFont(FONT);

    g2d.setColor(FONT_COLOR);
    final String[] stats_labels = my_stats.getStatsLabels();
    final int[] stats = my_stats.getStats();
    int row = 0;
    for (int i = 0; i < stats_labels.length; i++) {
      final String text = stats_labels[i] + ": " + stats[i];
      final FontRenderContext frc = g2d.getFontRenderContext();
      final GlyphVector gv = FONT.createGlyphVector(frc, text);
      final Rectangle2D rect = gv.getVisualBounds();
      final int margin = (int) rect.getHeight();
      row += margin * 2;
      g2d.drawString(text, margin, row); 
    }
  }
  
  /**
   * Sets the starting level.
   * 
   * @param the_n the starting level.
   */
  public void setStartingLevel(final int the_n) {
    my_starting_level = the_n;
  }
  
  /**
   * Returns the statistics object.
   * 
   * @return the statistics object.
   */
  public Stats getStats() {
    return my_stats;
  }
  
  /**
   * Registers an observer to the Tetris <code>Stats</code> object.
   * 
   * @param the_o the object observing the <code>Stats</code>.
   */
  public void addObserver(final Observer the_o) {
    my_stats.addObserver(the_o);
  }

  /**
   * {@inheritDoc}
   * <p>
   * If Stats notifies the GUI, it repaints.
   */
  @Override
  public void update(final Observable the_obs, final Object the_arg) {
    repaint();
  }
  
}
