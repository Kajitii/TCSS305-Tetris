/*
 * TCSS305 Assignment 6: Tetris
 * 6/7/2013 Aaron Chen
 */

package view;

import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * An abstract class that provides convenient scaling functionality.
 * 
 * @author Kajiti
 * @version 0.6.1
 */
public abstract class AbstractScalableGUI extends JPanel {

  /** The serial version UID. */
  private static final long serialVersionUID = 4273032032313395630L;
  
  /** The default scale value. */
  private static final double DEFAULT_SCALE = 1.0;
  
  /** The scale value for the graphical representation, in percent. */
  protected double my_panel_scale;

  /** The panel's original width. */
  private final int my_width;
  
  /** The panel's original height. */  
  private final int my_height;
  
  
  /**
   * Constructs a new <code>ScalableGUI</code>.  Sets the preferred size of the panel
   * as a convenience.
   * 
   * @param the_width the original width of the panel.
   * @param the_height the original height of the panel.
   */
  protected AbstractScalableGUI(final int the_width, final int the_height) {
    super();
    my_width = the_width;
    my_height = the_height;
    setScale(DEFAULT_SCALE);
    setPreferredSize(new Dimension(my_width, my_height));
  }
  
  /**
   * Resizes the Tetris board graphic.
   * 
   * @param the_scale the scale value.
   */
  public final void setScale(final double the_scale) {
    my_panel_scale = the_scale;
    setSize((int) (my_width * my_panel_scale), (int) (my_height * my_panel_scale));
  }
  
}
