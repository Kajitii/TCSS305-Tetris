/*
 * TCSS305 Assignment 6: Tetris
 * 5/24/2013 Aaron Chen
 */

package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Timer;

import model.AbstractPiece;
import model.Block;
import model.Board;
import model.Controls;
import model.MutablePiece;
import model.Stats;

/**
 * The graphical user interface for the actual Tetris board.  Also responsible
 * for handling user input and updating the game's state.
 * 
 * @author Kajiti
 * @version 0.6.1
 */
public class TetrisBoardGUI extends AbstractScalableGUI implements Observer {

  /** The serial version UID. */
  private static final long serialVersionUID = 1978071181777117465L;
  
  /** The string to show at the end of a single player game. */
  private static final String RESULT_NONE = null;
  
  /** The default width of the board image in pixels. */
  private static final int WIDTH = 200;
  
  /** The default height of the board image in pixels. */
  private static final int HEIGHT = 400;
  
  /** The length of the side of an individual block in pixels. */
  private static final int LENGTH = 20;
  
  /** A scale value that determines the "miter" width of the blocks. */
  private static final double SUB_BLOCK = 0.2;
  
  /** The default background color for UI windows (not necessarily Containers). */
  private static final Color BG_COLOR = new Color(0x00, 0x00, 0x00, 0xFF);
  
  /** The default delay between steps in milliseconds. */
  private static final int DELAY = 1000;
  
  /** The factor by which the delay goes down as the difficulty level increases. */
  private static final double DELAY_DECREASE_FACTOR = 0.8;
  
  /** The font to be used for text. */
  private static final Font FONT = new Font(Font.SERIF, Font.BOLD, 28);
  
  /** The default font color for the statistics panel. */
  private static final Color FONT_COLOR = Color.WHITE;
  
  /** The <code>Timer</code> object that controls how fast the game runs. */
  private final Timer my_timer;
  
  /** The board that handles the Tetris gameplay. */
  private final Board my_board;
  
  /** The controls by which the player can play this game. */
  private final Controls my_controls;
  
  /** Determines whether the game is paused or not. */
  private boolean my_is_paused;
  
  /** The result of the game. */
  private String my_result;
  
  /**
   * Constructs a new <code>TetrisBoardGUI</code>.
   * 
   * @param the_preview the preview that will observe the <code>Board</code>.
   * @param the_stats the statistics that will observe the <code></code>. 
   * @param the_controls the <code>Controls</code> to be assigned to this game instance.
   */
  public TetrisBoardGUI(final TetrisPreviewGUI the_preview,
                        final Stats the_stats,
                        final Controls the_controls) {
    super(WIDTH, HEIGHT);
    my_timer = new Timer(0, new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent the_event) {
        my_board.step();
      }
    });
    my_board = new Board();
    my_controls = the_controls;
    my_is_paused = true;
    init(the_preview, the_stats);
  }
  
  /**
   * Initializes the GUI components.
   * 
   * @param the_preview the preview that will observe the <code>Board</code>.
   * @param the_stats the statistics that will observe the <code></code>. 
   */
  private void init(final TetrisPreviewGUI the_preview, final Stats the_stats) {
    my_board.addObserver(this);
    my_board.addObserver(the_preview);
    my_board.addObserver(the_stats);
    the_stats.addObserver(this);
    setFocusable(true);
  }
  
  /**
   * Sets the <code>Timer</code> delay according to the level of difficulty.
   * 
   * @param the_level the difficulty level.
   */
  public final void setTimerDelay(final int the_level) {
    my_timer.stop();
    final int step_delay = (int) (DELAY * Math.pow(DELAY_DECREASE_FACTOR, the_level - 1));
    my_timer.setInitialDelay(step_delay);
    my_timer.setDelay(step_delay);
    my_timer.start();
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
    
    //Paint the board.
    if (!my_is_paused) {
      final List<Block[]> blocks =  my_board.getFrozenBlocks();
      int y = -1;
      for (Block[] block : blocks) {
        y++;
        for (int i = 0; i < block.length; i++) {
          paintBlock(g2d, block[i].getColor(),
                     i, my_board.getHeight() - y - 1, my_panel_scale);
        }
      }
      
      if (my_board.gameIsOver()) {
        //Paint text representing your outcome.
        g2d.setColor(FONT_COLOR);
        g2d.setFont(FONT);
        if (my_result != null) {
          final FontRenderContext frc = g2d.getFontRenderContext();
          final GlyphVector gv = FONT.createGlyphVector(frc, my_result);
          final Rectangle2D rect = gv.getVisualBounds();
          g2d.drawString(my_result,
                         (int) ((getWidth() - rect.getWidth()) / 2),
                         (int) ((getHeight() - rect.getHeight()) / 2));
        }
      } else {
        //Paint the current and ghost pieces.
        final MutablePiece ghost = my_board.getGhostPiece();
        drawPiece(g2d, ghost, ghost.getX(),
                  my_board.getHeight() - ghost.getY() - 1,
                  my_panel_scale);
        final MutablePiece piece = my_board.getCurrentPiece();
        drawPiece(g2d, piece, piece.getX(),
                  my_board.getHeight() - piece.getY() - 1,
                  my_panel_scale);
      }
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
   * Starts a game of Tetris.
   * 
   * @param the_width the width of the Tetris board, in blocks.
   * @param the_height the height of the Tetris board, in blocks.
   */
  public void start(final int the_width, final int the_height) {
    gameOver(); //voids the previous game, if any
    my_result = RESULT_NONE;
    my_board.newGame(the_width, the_height, null);
    setTimerDelay(1);
  }
  
  /**
   * Ends the current game of Tetris.
   * 
   * @param the_result the result as determined by the match rules.
   */
  public void endGame(final String the_result) {
    my_result = the_result;
    if (!my_board.gameIsOver()) {
      my_board.endGame();
      gameOver();
    }
  }
  
  /**
   * Called when the game is over.
   */
  private void gameOver() {
    my_timer.stop();
    my_is_paused = false; //so the gameover state shows correctly.
  }
  
  /**
   * Pauses or unpauses the game.
   * 
   * @param the_b true if the game should be paused; false otherwise.
   */
  public void setPause(final boolean the_b) {
    my_is_paused = the_b;
    if (!my_board.gameIsOver()) {
      if (the_b) {
        my_timer.stop();
      } else {
        if (!my_timer.isRunning()) {
          my_timer.start();
        }
      }
    }
    repaint();
  }
  
  /**
   * Sends garbage blocks to this board.  Used for multiplayer.
   * 
   * @param the_lines the number of lines of garbage blocks to be sent.
   */
  public void sendGarbage(final int the_lines) {
    my_board.addGarbage(the_lines);
  }
  
  /**
   * Registers an observer to the Tetris <code>Board</code> object.
   * 
   * @param the_o the object observing the <code>Board</code>.
   */
  public void addObserver(final Observer the_o) {
    my_board.addObserver(the_o);
  }
  
  /**
   * Returns the next piece to be fed onto the board.
   * 
   * @return the next piece.
   */
  public MutablePiece getNextPiece() {
    return my_board.getNextPiece();
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
   * {@inheritDoc}
   */
  @Override
  public void update(final Observable the_o, final Object the_arg) {
    if (the_o.getClass().equals(Board.class)) {
      if (my_board.gameIsOver()) {
        gameOver();
      }
      repaint();
    } else if (the_o.getClass().equals(Stats.class) && the_arg instanceof Integer) {
      setTimerDelay((int) the_arg);
    }
  }

  /**
   * Changes the state of the board depending on the keyboard input.
   * 
   * @param the_event the event which was passed. 
   */
  public void handleKeyEvent(final KeyEvent the_event) {
    if (my_board.gameIsOver()) {
      return;
    }
    final int control_code = my_controls.determineControl(the_event);
    switch(control_code) {
      case Controls.DOWN:
        my_timer.restart();
        my_board.moveDown();
        break;
      case Controls.LEFT:
        my_board.moveLeft();
        break;
      case Controls.RIGHT:
        my_board.moveRight();
        break;
      case Controls.DROP:
        my_timer.restart();
        my_board.hardDrop();
        break;
      case Controls.CCW:
        my_board.rotate(false);
        break;
      case Controls.CW:
        my_board.rotate(true);
        break;
      default:
        //do nothing
    }
  }
  
}
