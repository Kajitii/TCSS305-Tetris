/*
 * TCSS305 Assignment 6: Tetris
 * 5/30/2013 Aaron Chen
 */

package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.Controls;

/**
 * The window that will allow the player to review the controls and reassign them.
 * 
 * @author Kajiti
 * @version 0.6.1
 */
public class ControlSettingsGUI extends JDialog {

  /** The serial version UID. */
  private static final long serialVersionUID = 2279878744705425260L;
  
  /** The title of the <code>JFrame</code>. */
  private static final String TITLE = "Control Settings";
  
  /** The number of columns in the grid layout per player. */
  private static final int NUM_COLUMNS_PER_PLAYER = 2;
  
  /** The horizontal gap between the grid layout's cells. */
  private static final int HORIZONTAL_GAP = 15;
  
  /** The vertical gap between the grid layout's cells. */
  private static final int VERTICAL_GAP = 3;
  
  /** The margins of the window. */
  private static final int MARGINS = 20;
  
  /** The amount of padding to be used for components. */
  private static final int PADDING = 6;
  
  /** The string to be displayed when the user first opens the window. */
  private static final String OPENING_INSTRUCTIONS =
      "Review your controls.  Select a control to be reassigned.";
  
  /** The string to be displayed when the user selects a control. */
  private static final String PRESS_INSTRUCTIONS =
      "Press the key you want to bind the control to.";

  /** The string to be displayed when the user attempts to reassign a
   *  control that is invalid. */
  private static final String ERROR_1_INSTRUCTIONS =
      "Failed to assign the control.  Please try again.";

  /** The string to be displayed when the user attempts to exit the dialog
   *  window without setting all controls to a valid value. */
  private static final String ERROR_2_INSTRUCTIONS =
      "Invalid control assignment detected, requires fixing.";
  
  /** The string to be displayed when the user successfully reassigns a control. */
  private static final String SUCCESS_INSTRUCTIONS =
      "Successfully assigned the control.";

  /** The label that displays instructions to the user. */
  private final JLabel my_instructions_label;

  
  /**
   * Constructs a new <code>ControlSettingsGUI</code> window.
   * 
   * @param the_frame the frame from which the dialog is displayed.
   * @param the_controls the controls used to play Tetris, depending on the number of
   *                     players on the local machine.
   * @throws IllegalArgumentException if <code>the_controls</code> is <code>null</code>
   *                                  or empty.
   */
  public ControlSettingsGUI(final Frame the_frame, final List<Controls> the_controls) {
    super(the_frame, TITLE, true);
    if (the_controls == null || the_controls.isEmpty()) {
      throw new IllegalArgumentException("must instantiate window with"
                                         + "at least one set of controls.");
    }
    my_instructions_label = new JLabel(OPENING_INSTRUCTIONS);
    init(the_controls);
  }
  
  /**
   * Initializes the window and its components.
   * 
   * @param the_controls the controls for which the GUI components will represent.
   */
  private void init(final List<Controls> the_controls) {
    setFocusable(true);
    addFocusListener(new FocusAdapter() {
      /** Called upon when the window gains focus.  Sets the instructions text.
       *  @param the_event not used. */
      @Override
      public void focusGained(final FocusEvent the_event) {
        my_instructions_label.setText(OPENING_INSTRUCTIONS);
      }
    });
    
    final Box box = Box.createVerticalBox();
    my_instructions_label.setAlignmentX(Component.CENTER_ALIGNMENT);
    box.add(Box.createRigidArea(new Dimension(0, MARGINS)));
    box.add(my_instructions_label);

    box.add(Box.createRigidArea(new Dimension(0, PADDING)));
    final JPanel player_panel = new JPanel(new GridLayout(1, the_controls.size()));
    for (int i = 0; i < the_controls.size(); i++) {
      final JLabel label = new JLabel("Player " + (i + 1));
      label.setHorizontalAlignment(SwingConstants.CENTER);
      player_panel.add(label);
    }
    box.add(player_panel);

    box.add(Box.createRigidArea(new Dimension(0, PADDING)));
    final Box sub_box = Box.createHorizontalBox();
    sub_box.add(Box.createRigidArea(new Dimension(MARGINS, 0)));
    final GridLayout grid = new GridLayout(1, the_controls.size());
    grid.setHgap(HORIZONTAL_GAP);
    grid.setVgap(VERTICAL_GAP);
    final JPanel controls_panel = new JPanel(grid);
    for (int i = 0; i < the_controls.size(); i++) {
      controls_panel.add(new ControlSettingsPanel(the_controls.get(i)));
    }
    sub_box.add(controls_panel);
    sub_box.add(Box.createRigidArea(new Dimension(MARGINS, 0)));
    box.add(sub_box);

    box.add(Box.createRigidArea(new Dimension(0, PADDING)));
    final JButton button = new JButton("OK");
    button.setAlignmentX(Component.CENTER_ALIGNMENT);
    button.addActionListener(new ActionListener() {
      /** Called when the user clicks on the button.
       *  @param the_event not used.*/
      @Override
      public void actionPerformed(final ActionEvent the_event) {
        if (Controls.allValidControls()) {
          dispose();
        } else {
          my_instructions_label.setText(ERROR_2_INSTRUCTIONS);
        }
      }
    });
    box.add(button);
    box.add(Box.createRigidArea(new Dimension(0, MARGINS)));
    
    add(box);
    
    pack();
    setSize(getWidth() + MARGINS, getHeight() + MARGINS);
    setResizable(false);
    setLocationByPlatform(true);
    setVisible(true);
  }
  
  /**
   * A <code>JPanel</code> unit for displaying all the controls and allowing customization
   * of those controls.
   * 
   * @author Kajiti
   * @version 0.6.1
   */
  private class ControlSettingsPanel extends JPanel {
    
    /** The serial version UID. */
    private static final long serialVersionUID = 1961849497803157844L;
    
    /** The set of controls this panel is responsible for showing data about. */
    private final Controls my_controls;
    
    /**
     * Constructs a new <code>ControlSettingsPanel</code>.
     *
     * @param the_controls the set of controls which this panel displays.
     */
    public ControlSettingsPanel(final Controls the_controls) {
      super();
      my_controls = the_controls;
      init();
    }
    
    /**
     * Initializes all the components of the panel.
     */
    private void init() {
      final int[] ctrls = my_controls.getControls();
      final String[] ctrl_labels = my_controls.getControlLabels();

      final GridLayout grid = new GridLayout(ctrl_labels.length, NUM_COLUMNS_PER_PLAYER);
      grid.setVgap(VERTICAL_GAP);
      grid.setHgap(HORIZONTAL_GAP);
      setLayout(grid);
      
      for (int i = 0; i < ctrl_labels.length; i++) {
        final JLabel label = new JLabel(ctrl_labels[i]);
        add(label);
        final JTextField tf = new JTextField(KeyEvent.getKeyText(ctrls[i]));
        setUpTextField(tf, i);
        add(tf);
      }
    }
    
    /**
     * Sets up the text field so that it can be clicked on and can assign control keys.
     * 
     * @param the_tf the text field to be set up.
     * @param the_index the index of the control keycode.
     */
    private void setUpTextField(final JTextField the_tf, final int the_index) {
      the_tf.addFocusListener(new FocusListener() {
        /** Called when the textfield is selected.  Selects all the text.
         *  @param the_event not used. */
        @Override
        public void focusGained(final FocusEvent the_event) {
          the_tf.selectAll();
          my_instructions_label.setText(PRESS_INSTRUCTIONS);
        }
        /** Called when the textfield loses focus.  Deselects the text.
         *  @param the_event not used. */
        @Override
        public void focusLost(final FocusEvent the_event) {
          the_tf.select(0, 0);
        }
      });
      the_tf.addKeyListener(new KeyAdapter() {
        /** Called when a key is pressed.  Sets the control to the specified key. 
         *  @param the_event the key press event. */
        @Override
        public void keyPressed(final KeyEvent the_event) {
          if (my_controls.setControl(the_event.getKeyCode(), the_index)) {
            the_tf.setText(KeyEvent.getKeyText(the_event.getKeyCode()));
            my_instructions_label.setText(SUCCESS_INSTRUCTIONS);
          } else {
            my_instructions_label.setText(ERROR_1_INSTRUCTIONS);
          }
          the_tf.selectAll();
        }
      });
      the_tf.setEditable(false);
      the_tf.setFocusable(true);
    }
    
  }
  
}
