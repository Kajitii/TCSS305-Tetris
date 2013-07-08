/*
 * TCSS305 Assignment 6: Tetris
 * 5/25/2013 Aaron Chen
 */

package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
import java.util.Observer;

import model.AbstractPiece;
import model.Block;
import model.Board;
//import model.IPiece;
import model.MutablePiece;

/**
 * The drawing panel for the Tetris piece preview.
 * 
 * @author Kajiti
 * @version 0.6.1
 */
public class TetrisPreviewGUI extends AbstractScalableGUI implements Observer {

  /** The serial version UID. */
  private static final long serialVersionUID = -7959909502432196700L;

  /** The default width of the preview panel. */
  private static final int WIDTH = 120;
  
  /** The default height of the preview panel. */
  private static final int HEIGHT = 120;
  
  /** The length of the side of an individual block in pixels. */
  private static final int LENGTH = 20;
  
  /** A scale value that determines the "miter" width of the blocks. */
  private static final double SUB_BLOCK = 0.2;
  
  /** The offset value for drawing a piece correctly in the preview panel. */
  private static final int OFFSET = 4;
  
//  /** The default piece to be drawn if no valid piece can be found. */
//  private static final MutablePiece DEFAULT_PIECE = new IPiece(0, 0);
  
  /** The default background color for UI windows (not necessarily Containers). */
  private static final Color BG_COLOR = new Color(0x00, 0x00, 0x00, 0xFF);
  
  /** The piece that will be put into play next. */
  private MutablePiece my_piece;
  
  
  /**
   * Constructs a new <code>TetrisPreviewGUI</code> object.
   */
  public TetrisPreviewGUI() {
    super(WIDTH, HEIGHT);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void paintComponent(final Graphics the_graphics) {
    final Graphics2D g2d = (Graphics2D) the_graphics;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setColor(BG_COLOR);
    g2d.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
    if (my_piece != null) {
      drawPiece(g2d, my_piece, 1, OFFSET, my_panel_scale);
    }
  }
  
  /**
   * Draws the piece using the <code>Graphics2D</code> passed in.
   * 
   * @param the_g2d the graphics object for a drawing panel.
   * @param the_piece the piece to be drawn.
   * @param the_x the x coordinate to be rendered on the panel.
   * @param the_y the y coordinate to be rendered on the panel.
   * @param the_scale the scale which to render at.
   */
  public void drawPiece(final Graphics2D the_g2d,
                        final MutablePiece the_piece,
                        final int the_x,
                        final int the_y,
                        final double the_scale) {
    final int[][] formation = ((AbstractPiece) the_piece).getFormation();
    final Block block = ((AbstractPiece) the_piece).getBlock();
    for (int b = 0; b < formation.length; b++) {
      paintBlock(the_g2d, block.getColor(),
                 formation[b][1] + the_x,
                 -formation[b][0] + the_y,
                 the_scale);
    }
  }
  
  /**
   * Draws the block at the given coordinates.
   * 
   * @param the_g2d the graphics object to be used.
   * @param the_color the color of the block.
   * @param the_x the x-coordinates on the board.
   * @param the_y the y-coordinates on the board.
   * @param the_scale the scale which to render at.
   */
  public void paintBlock(final Graphics2D the_g2d,
                         final Color the_color,
                         final int the_x,
                         final int the_y,
                         final double the_scale) {
    final double mod_x = the_x * LENGTH;
    final double mod_y = the_y * LENGTH;
    
    //Defining the coordinates for the bright side of the block.
    final double[] bright_shape_x = {
      mod_x,
      mod_x + LENGTH,
      mod_x
    };
    final double[] bright_shape_y = {
      mod_y,
      mod_y,
      mod_y + LENGTH,
    };
    
    //Defining the coordinates for the dark side of the block.
    final double[] dark_shape_x = {
      mod_x + LENGTH,
      mod_x,
      mod_x + LENGTH,
    };
    final double[] dark_shape_y = {
      mod_y + LENGTH,
      mod_y + LENGTH,
      mod_y
    };
    
    //Applying the scale factor.
    for (int i = 0; i < bright_shape_x.length; i++) {
      bright_shape_x[i] *= the_scale;
      bright_shape_y[i] *= the_scale;
      dark_shape_x[i] *= the_scale;
      dark_shape_y[i] *= the_scale;
    }
    
    //Drawing the block!
    the_g2d.setStroke(new BasicStroke((float) the_scale,
                                      BasicStroke.CAP_SQUARE,
                                      BasicStroke.JOIN_MITER));
    //Drawing the bright and dark side of the block.
    final Path2D bright_shape = new Path2D.Double();
    bright_shape.moveTo(bright_shape_x[0], bright_shape_y[0]);
    final Path2D dark_shape = new Path2D.Double();
    dark_shape.moveTo(dark_shape_x[0], dark_shape_y[0]);
    for (int i = 1; i < bright_shape_x.length; i++) {
      bright_shape.lineTo(bright_shape_x[i], bright_shape_y[i]);
      dark_shape.lineTo(dark_shape_x[i], dark_shape_y[i]);
    }
    bright_shape.closePath();
    dark_shape.closePath();
    the_g2d.setColor(the_color.darker());
    the_g2d.fill(bright_shape);
    the_g2d.setColor(the_color.darker().darker().darker());
    the_g2d.fill(dark_shape);
    
    //Drawing the top left corner line.
    the_g2d.setColor(the_color);
    the_g2d.draw(new Line2D.Double(mod_x * the_scale,
                                   mod_y * the_scale,
                                   (mod_x + LENGTH * SUB_BLOCK) * the_scale,
                                   (mod_y + LENGTH * SUB_BLOCK) * the_scale));
    
    //Drawing the bottom right corner line.
    the_g2d.setColor(the_color.darker().darker().darker().darker());
    the_g2d.draw(new Line2D.Double((mod_x + LENGTH - 1) * the_scale,
                                   (mod_y + LENGTH - 1) * the_scale,
                                   (mod_x + LENGTH * (1 - SUB_BLOCK)) * the_scale,
                                   (mod_y + LENGTH * (1 - SUB_BLOCK)) * the_scale));
    
    //Drawing the center area of the block.
    the_g2d.setColor(the_color.darker().darker());
    the_g2d.fill(new Rectangle2D.Double((mod_x + LENGTH * SUB_BLOCK) * the_scale,
                                        (mod_y + LENGTH * SUB_BLOCK) * the_scale, 
                                        (LENGTH * (1 - 2 * SUB_BLOCK)) * the_scale,
                                        (LENGTH * (1 - 2 * SUB_BLOCK)) * the_scale));
    
    //Drawing the top right and bottom left corner lines.
    the_g2d.draw(new Line2D.Double(mod_x * the_scale,
                                   (mod_y + LENGTH - 1) * the_scale,
                                   (mod_x + LENGTH - 1) * the_scale,
                                   mod_y * the_scale));
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void update(final Observable the_o, final Object the_arg) {
    if (the_o.getClass().equals(Board.class)) {
      my_piece = ((Board) the_o).getNextPiece();
      repaint();
    } 
  }

}
