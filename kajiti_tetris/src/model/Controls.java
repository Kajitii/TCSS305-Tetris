/*
 * TCSS305 Assignment 6: Tetris
 * 5/30/2013 Aaron Chen
 */

package model;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

/**
 * This class stores all of the controls.  Includes the capabilities to customize the
 * controls. 
 * 
 * @author kajiti
 * @version 0.6.1
 */
public final class Controls {
  
  /** The number of keys the keyboard listener should be listening to. */
  public static final int NUM_CONTROLS = 6;
  
  /** A value that does not map to a valid control value. */
  public static final int UNDEFINED_CONTROL = -1;
  
  /** A value that does not map to a valid control value. */
  public static final int UNASSIGNED_CONTROL = 0;

  /** The index for the control for moving the current piece left. */
  public static final int LEFT = 0;

  /** The index for the control for moving the current piece right. */
  public static final int RIGHT = 1;

  /** The index for the control for moving the current piece down. */
  public static final int DOWN = 2;

  /** The index for the control for hard dropping the current piece into place. */
  public static final int DROP = 3;

  /** The index for the control for rotating the current piece right. */
  public static final int CCW = 4;

  /** The index for the control for rotating the current piece left. */
  public static final int CW = 5;
  
  /** The list of all keycodes ever assigned to a control, regardless of source. */
  private static List<Integer> my_global_controls = new LinkedList<Integer>();
  
  /** The string descriptions for the controls. */
  private static final String[] CONTROL_LABELS = {
    "Move Left",
    "Move Right",
    "Move Down",
    "Hard Drop",
    "Rotate Counterclockwise",
    "Rotate Clockwise"
  };
  
  /** The keycodes for the controls. */
  private final int[] my_controls;
  
  /**
   * Constructs a new <code>Controls</code> object.
   * <p>
   * If a keycode being assigned through the constructor is already assigned by another
   * <code>Controls</code> object, the keycode changes to 0, an unknown keycode. 
   * 
   * @param the_move_left   the keycode for moving the piece left.
   * @param the_move_right  the keycode for moving the piece right.
   * @param the_move_down   the keycode for moving the piece down.
   * @param the_drop        the keycode for hard dropping the piece.
   * @param the_rotate_ccw  the keycode for rotating the piece counterclockwise.
   * @param the_rotate_cw   the keycode for rotating the piece clockwise.
   */
  public Controls(final int the_move_left, final int the_move_right, final int the_move_down,
                  final int the_drop, final int the_rotate_ccw, final int the_rotate_cw) {
    my_controls = new int[NUM_CONTROLS];
    forceSetControl(the_move_left, LEFT);
    forceSetControl(the_move_right, RIGHT);
    forceSetControl(the_move_down, DOWN);
    forceSetControl(the_drop, DROP);
    forceSetControl(the_rotate_ccw, CCW);
    forceSetControl(the_rotate_cw, CW);
  }
  
  /**
   * Constructs a new <code>Controls</code> object with the default player 1 controls.
   */
  public Controls() {
    this(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN,
         KeyEvent.VK_UP, KeyEvent.VK_Z, KeyEvent.VK_X);
  }
  
  /**
   * Returns the array of control keycodes.
   * 
   * @return the array of control keycodes.
   */
  public int[] getControls() {
    return my_controls.clone();
  }
  
  /**
   * Returns the array of control string descriptions.
   * 
   * @return the array of control string descriptions.
   */
  public String[] getControlLabels() {
    return CONTROL_LABELS.clone();
  }
  
  /**
   * Returns a value corresponding to a constant that represents a control.
   * 
   * @param the_event the key event passed.
   * @return the constant value corresponding to the control code, or
   *         <code>UNDEFINED_CONTROL</code> if the control keycode is not recognized.
   */
  public int determineControl(final KeyEvent the_event) {
    int result = UNDEFINED_CONTROL;
    final int keycode = the_event.getKeyCode();
    for (int i = 0; i < my_controls.length; i++) {
      if (keycode == my_controls[i]) {
        result = i;
        break;
      }
    }
    return result;
  }
  
  /**
   * Returns true if all controls are assigned to valid keycode values.
   * 
   * @return true if all controls are assigned to valid keycode values; false otherwise.
   */
  public static boolean allValidControls() {
    return !my_global_controls.contains(UNASSIGNED_CONTROL);
  }
  
  /**
   * Attempts to set the control specified to the keycode.
   * 
   * @param the_keycode the keycode to be set.
   * @param the_index the index which the control's keycode resides at.
   * @return true if the keycode was set successfully; false otherwise.
   */
  public boolean setControl(final int the_keycode, final int the_index) {
    final boolean is_set = isAssigned(the_keycode);
    if (!is_set) {
      assignControl(the_keycode, the_index);
    }
    return !is_set;
  }
  
  /**
   * Assigns the control specified to the keycode.  If the keycode is already being used,
   * then the control is assigned to <code>UNASSIGNED_CONTROL</code>.
   * 
   * @param the_keycode the keycode to be set.
   * @param the_index the index which the control's keycode resides at.
   */
  private void forceSetControl(final int the_keycode, final int the_index) {
    final boolean is_set = isAssigned(the_keycode);
    if (is_set) {
      my_global_controls.add(UNASSIGNED_CONTROL);
      my_controls[the_index] = UNASSIGNED_CONTROL;
    } else {
      assignControl(the_keycode, the_index);
    }
  }
  
  /**
   * Assigns the control specified to the keycode.
   * 
   * @param the_keycode the keycode to be set.
   * @param the_index the index which the control's keycode resides at.
   */
  private void assignControl(final int the_keycode, final int the_index) {
    my_global_controls.remove((Integer) my_controls[the_index]);
    my_controls[the_index] = the_keycode;
    my_global_controls.add(the_keycode);
  }
  
  /**
   * Detects if a keycode is already assigned to a control.
   * 
   * @param the_keycode the keycode to be checked.
   * @return true if a keycode is assigned to a control; false otherwise.
   */
  private boolean isAssigned(final int the_keycode) {
    return my_global_controls.contains(the_keycode);
  }
  
  /**
   * Clears this <code>Controls</code>' registered keys from the global list and resets
   * all controls.
   * <p>
   * This method should only be called when the <code>Controls</code> object is about to
   * be trashed. 
   */
  public void clear() {
    for (int i = 0; i < my_controls.length; i++) {
      my_global_controls.remove((Integer) my_controls[i]);
      my_controls[i] = 0;
    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("Controls: ");
    for (int i = 0; i < my_controls.length; i++) {
      sb.append('\n');
      sb.append(CONTROL_LABELS[i]);
      sb.append(": ");
      sb.append('(');
      sb.append(my_controls[i]);
      sb.append(')');
      sb.append(' ');
      sb.append(KeyEvent.getKeyText(my_controls[i]));
    }
    return sb.toString();
  }
  
}
