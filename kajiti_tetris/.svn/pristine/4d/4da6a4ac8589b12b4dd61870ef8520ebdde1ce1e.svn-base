/*
 * TCSS305 Assignment 6: Tetris
 * 5/24/2013 Aaron Chen
 */

package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import model.Board;
import model.Controls;

/**
 * The top level container for the Tetris GUI.  May contain multiple TetrisGameGUIs, and
 * possibly other elements not specific to any instance of a game. 
 * 
 * @author Kajiti
 * @version 0.6.1
 */
public class TetrisGUI extends JFrame implements Observer {
  
  /** The serial version UID. */
  private static final long serialVersionUID = 5857467584409853121L;

  /** The random number generator used for choosing attack victims. */
  private static final Random RANDOM = new Random();
  
  /** The title <code>String</code> for the "About..." dialog. */
  private static final String ABOUT_TITLE = "About...";
  
  /** The "About..." message that the dialog shows. */
  private static final String ABOUT_MESSAGE =
      "TCSS 305, Spring 2013\n"
      + "Programming by Alan Fowler, Aaron Chen\n"
      + "Insert the rest of the credits here!";
  
  /** The title <code>String</code> for the "How to Play" dialog. */
  private static final String HOW_TO_PLAY_TITLE = "How to Play";
  
  /** The "How to Play" message that the dialog shows. */
  private static final String HOW_TO_PLAY_MESSAGE = 
      "Tetriminoes are falling down!  Move and rotate the tetriminoes to\n"
      + "form rows.  Forming rows clears the board, and scores points.\n"
      + "But be careful, as the game goes on, the tetriminoes come more often\n"
      + "and fall faster.  Can you keep up, score high, and be the best Tetris\n"
      + "player of all time?\n\n"
      + "Clearing a line scores 100 points.  Clearing multiple lines score more points,\n"
      + "with upwards of 1600 for clearing four lines, otherwise known as a Tetris!\n\n"
      + "As you clear lines, you progress in levels of difficulty.  To score as much as\n"
      + "possible, you should aim to score as many Tetrises as possible.";
  
  /** The warning message that displays when attempting a three player local match. */
  private static final String THREE_PLAYER_WARNING_MESSAGE = 
      "Three is a crowd!  And your keyboard might not be able to handle it!\n\n"
      + "Proceed anyways?";
  
  /** The text for the menu pause function. */
  private static final String PAUSE_STRING = "Pause Game";
  
  /** The text for the menu unpause function. */
  private static final String UNPAUSE_STRING = "Unpause Game";
  
  /** The number of players in a single player game. */
  private static final int SINGLE_PLAYER = 1;
  
  /** The number of players in a versus player game. */
  private static final int VERSUS_PLAYER = 2;
  
  /** The number of players in a versus player game. */
  private static final int THREE_PLAYER = 3;
  
  /** The key listener for responding to player input. */
  private final KeyListener my_key_listener;
  
  /** The focus listener for responding to player input. */
  private final FocusListener my_focus_listener;

  /** The controls used to play the games. */
  private final List<Controls> my_controls;
  
  /** The game instances. */
  private final List<TetrisGameGUI> my_games;
  
  /** The action listener that shows the control settings window. */
  private final ControlSettingsActionListener my_controls_al;
  
  //private BufferedImage my_bg_image;
  
  /** The default resolution of a game of Tetris. */
  private Dimension my_normal_size;
  
  /** The boolean indicating the user wants to pause the game. */
  private boolean my_manual_pause;
  
  /** The boolean indicating whether a match is active or not. */
  private boolean my_match_is_active;
  

  /**
   * Constructs a new <code>TetrisGUI</code>.
   */
  public TetrisGUI() {
    super("TCSS 305 PowerPaint, Spring 2013");
    my_controls = new ArrayList<Controls>(2);
    my_games = new ArrayList<TetrisGameGUI>(2);
    my_key_listener = new TetrisKeyListener();
    my_focus_listener = new TetrisFocusListener();
    my_controls_al = new ControlSettingsActionListener();
    init();
  }
  
  /**
   * Initializes the window and its components.
   */
  private void init() {
    setFocusable(true);
    setLayout(new GridLayout());
    
    setNumberOfGames(1);
    
    addComponentListener(new WindowSizeListener());
    
    setJMenuBar(createJMenuBar());
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationByPlatform(true);
    setSize();
    setVisible(true);
  }
  
  /**
   * Called to set the normal size of the window, and ensure accurate scaling.
   */
  private void setSize() {
    pack();
    my_normal_size = getSize();
  }
  
  /**
   * Creates the <code>JMenuBar</code> for this window.
   * 
   * @return the <code>JMenuBar</code> containing functions for the game.
   */
  private JMenuBar createJMenuBar() {
    final JMenuBar jmb = new JMenuBar();
    jmb.add(createGameJMenu());
    jmb.add(createOptionsJMenu());
    jmb.add(createHelpJMenu());
    return jmb;
  }
  
  /**
   * Creates the "Game" <code>JMenu</code>, which contains basic functions such as starting the
   * game or exiting. 
   * 
   * @return the "Game" <code>JMenu</code>.
   */
  private JMenu createGameJMenu() {
    final JMenu game_jm = new JMenu("Game");
    
    final JMenuItem pause_game_jmi = new JMenuItem(PAUSE_STRING);
    final ActionListener pause_game_al = new ActionListener() {
      /** Called when the menu item has been clicked.  Toggles the manual pause status.
       *  @param the_event not used.*/
      @Override
      public void actionPerformed(final ActionEvent the_event) {
        my_manual_pause ^= true;
        setPause(my_manual_pause);
        if (my_manual_pause) {
          pause_game_jmi.setText(UNPAUSE_STRING);
        } else {
          pause_game_jmi.setText(PAUSE_STRING);
        }
      }
    };
    pause_game_jmi.addActionListener(pause_game_al);
    
    final JMenu new_game_jm = createNewGameSubJMenu(pause_game_al);
    
    final JMenuItem end_game_jmi = new JMenuItem("End Game");
    end_game_jmi.addActionListener(new ActionListener() {
      /** Called when the menu item "End Game" has been clicked.
       *  @param the_event not used.*/
      @Override
      public void actionPerformed(final ActionEvent the_event) {
        if (my_match_is_active) {
          endMatch(getNumPlayersRemaining());
          if (my_manual_pause) {
            pause_game_al.actionPerformed(the_event);
          }
        }
      }
    });
    
    final JMenuItem exit_jmi = new JMenuItem("Exit");
    exit_jmi.addActionListener(new ActionListener() {
      /** Called when the menu item "Exit" has been clicked.
       *  @param the_event not used.*/
      @Override
      public void actionPerformed(final ActionEvent the_event) {
        dispose();
      }
    });
    
    game_jm.add(new_game_jm);
    game_jm.add(pause_game_jmi);
    game_jm.add(end_game_jmi);
    game_jm.addSeparator();
    game_jm.add(exit_jmi);
    
    return game_jm;
  }
  
  /**
   * Creates the "New Game..." sub-<code>JMenu</code>, which contains options on how what
   * type of game should be played.
   * 
   * @param the_al the <code>ActionListener</code> for the manual pause function.
   * @return the "New Game..." sub-<code>JMenu</code>.
   */
  private JMenu createNewGameSubJMenu(final ActionListener the_al) {
    final JMenu new_game_jm = new JMenu("New Game...");
    
    final JMenuItem single_player_jmi = new JMenuItem("1-Player Game");
    single_player_jmi.addActionListener(new ActionListener() {
      /** Called when the menu item "1-Player Game" has been clicked.
       *  @param the_event not used.*/
      @Override
      public void actionPerformed(final ActionEvent the_event) {
        setNumberOfGames(SINGLE_PLAYER);
        if (my_manual_pause) {
          the_al.actionPerformed(the_event);
        }
        startMatch();
      }
    });
    
    final JMenuItem versus_player_jmi = new JMenuItem("Versus Game");
    versus_player_jmi.addActionListener(new ActionListener() {
      /** Called when the menu item "Versus Game" has been clicked.
       *  @param the_event not used.*/
      @Override
      public void actionPerformed(final ActionEvent the_event) {
        setNumberOfGames(VERSUS_PLAYER);
        if (my_manual_pause) {
          the_al.actionPerformed(the_event);
        }
        startMatch();
      }
    });
    
    final JMenuItem three_player_jmi = new JMenuItem("Three's a Crowd Game");
    three_player_jmi.addActionListener(new ActionListener() {
      /** Called when the menu item "Three's a Crowd Game" has been clicked.
       *  @param the_event not used.*/
      @Override
      public void actionPerformed(final ActionEvent the_event) {
        final int n = JOptionPane.showConfirmDialog(TetrisGUI.this,
                                                    THREE_PLAYER_WARNING_MESSAGE,
                                                    "Warning",
                                                    JOptionPane.YES_NO_OPTION,
                                                    JOptionPane.WARNING_MESSAGE);
        if (n == JOptionPane.YES_OPTION) {
          setNumberOfGames(THREE_PLAYER);
          if (my_manual_pause) {
            the_al.actionPerformed(the_event);
          }
          startMatch();
        }
      }
    });
    
    new_game_jm.add(single_player_jmi);
    new_game_jm.add(versus_player_jmi);
    new_game_jm.add(three_player_jmi);
    
    return new_game_jm;
  }
  
  /**
   * Creates the "Options" <code>JMenu</code>, which contains functions for changing game
   * settings, controls, and sound.
   * 
   * @return the "Options" <code>JMenu</code>.
   */
  private JMenu createOptionsJMenu() {
    final JMenu options_jm = new JMenu("Options");
    
    final JMenuItem customize_controls_jmi = new JMenuItem("Customize Controls");
    customize_controls_jmi.addActionListener(my_controls_al);
    
    options_jm.add(customize_controls_jmi);
    
    return options_jm;
  }
  
  /**
   * Creates the "Help" <code>JMenu</code>, which contains basic functions such as starting the
   * game or exiting. 
   * 
   * @return the "Help" <code>JMenu</code>.
   */
  private JMenu createHelpJMenu() {
    final JMenu help_jm = new JMenu("Help");
    
    final JMenuItem how_to_play_jmi = new JMenuItem(HOW_TO_PLAY_TITLE);
    how_to_play_jmi.addActionListener(new ActionListener() {
      /** Called when the menu item "How to Play" has been clicked.
       *  @param the_event not used.*/
      @Override
      public void actionPerformed(final ActionEvent the_event) {
        JOptionPane.showMessageDialog(TetrisGUI.this, HOW_TO_PLAY_MESSAGE,
                                      HOW_TO_PLAY_TITLE, JOptionPane.INFORMATION_MESSAGE);
      }
    });

    final JMenuItem about_jmi = new JMenuItem(ABOUT_TITLE);
    about_jmi.addActionListener(new ActionListener() {
      /** Called when the menu item "About..." has been clicked.
       *  @param the_event not used.*/
      @Override
      public void actionPerformed(final ActionEvent the_event) {
        JOptionPane.showMessageDialog(TetrisGUI.this, ABOUT_MESSAGE,
                                      ABOUT_TITLE, JOptionPane.INFORMATION_MESSAGE);
      }
    });

    help_jm.add(how_to_play_jmi);
    help_jm.addSeparator();
    help_jm.add(about_jmi);
    
    return help_jm;
  }
  
  /**
   * Sets the number of games that will be active at once, primarily for local multiplayer.
   * 
   * @param the_n the number of players.
   */
  private void setNumberOfGames(final int the_n) {
    if (the_n != my_games.size()) {
      if (the_n > my_games.size()) {
        while (my_games.size() < the_n) {
          final Controls controls = new Controls();
          final TetrisGameGUI game = new TetrisGameGUI(this, controls);
          my_controls.add(controls);
          my_games.add(game);
          add(game);
        }
      } else if (the_n < my_games.size()) {
        while (my_games.size() > the_n) {
          my_controls.remove(my_controls.size() - 1).clear();
          remove(my_games.remove(my_games.size() - 1));
        }
      }
      setSize();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void update(final Observable the_obs, final Object the_obj) {
    if (the_obj instanceof Integer && my_games.size() > 1) { //lines cleared
      attack((Board) the_obs, (int) the_obj);
    }
    if (the_obj instanceof Boolean) { //gameover boolean
      final boolean b = (boolean) the_obj;
      final int n = getNumPlayersRemaining();
      if (b && my_games.size() > 1 && n == 1) {
        endMatch(n);
        enableFocus(!b);
      }
      if (my_games.size() == 1) {
        enableFocus(!b);
      }
    }
  }
  
  /**
   * Attack a random player by sending garbage blocks onto his board.
   * 
   * @param the_board the board that just cleared lines, and is attacking.
   * @param the_n the number of lines cleared, which dictates the severity of the attack.
   */
  private void attack(final Board the_board, final int the_n) {
    int m = RANDOM.nextInt(my_games.size());
    while (m == the_board.getID() || my_games.get(m).gameIsOver()) {
      m = RANDOM.nextInt(my_games.size());
    }
    my_games.get(m).sendGarbage(the_n);
  }
  
  /**
   * Starts the match.  Can either be single player or multiplayer.
   */
  private void startMatch() {
    my_match_is_active = true;
    my_controls_al.actionPerformed(null);
    enableFocus(true);
    for (TetrisGameGUI game : my_games) {
      game.start();
    }
  }
  
  /**
   * Ends the match.  If it is a single player match, notify the game board that it has "lost".
   * Otherwise, notifies the game boards that the match is over and hands out the results.
   * 
   * @param the_num_players the number of players remaining.
   */
  private void endMatch(final int the_num_players) {
    my_match_is_active = false;
    String favored_result = TetrisGameGUI.RESULT_NONE;
    if (the_num_players == 1 && my_games.size() != 1) {
      favored_result = TetrisGameGUI.RESULT_WIN;
    } else if (the_num_players > 1) {
      favored_result = TetrisGameGUI.RESULT_DRAW;
    }
    for (TetrisGameGUI game : my_games) {
      if (game.gameIsOver()) {
        game.endGame(TetrisGameGUI.RESULT_LOSE);
      } else {
        game.endGame(favored_result);
      }
    }
    setPause(false);
    enableFocus(false);
  }
  
  /**
   * Returns the number of players remaining in the current match.
   * 
   * @return the number of players remaining in the current match.
   */
  private int getNumPlayersRemaining() {
    int players_remaining = my_games.size();
    for (TetrisGameGUI game : my_games) {
      if (game.gameIsOver()) {
        players_remaining--;
      }
    }
    return players_remaining;
  }
  
  /**
   * Pauses or unpauses the game.
   * 
   * @param the_pause true if the game should be paused; false otherwise.
   */
  private void setPause(final boolean the_pause) {
    if (!my_manual_pause || the_pause) {
      for (TetrisGameGUI game : my_games) {
        game.setPause(the_pause);
        enablePlay(!the_pause);
      }
    }
  }
  
  /**
   * Enables or disables the key listener.
   * 
   * @param the_b true if play should be enabled; false otherwise.
   */
  private void enablePlay(final boolean the_b) {
    removeKeyListener(my_key_listener);
    if (the_b) {
      addKeyListener(my_key_listener);
    }
  }

  /**
   * Enables or disables the key and focus listeners.
   * 
   * @param the_b true if focus should be enabled; false otherwise.
   */
  private void enableFocus(final boolean the_b) {
    enablePlay(the_b);
    removeFocusListener(my_focus_listener);
    if (the_b) {
      addFocusListener(my_focus_listener);
    }
  }
  
  
  /**
   * A component listener class that notifies Tetris GUI that the size of the window changed.
   * This scales all the components so that they fit within the window, while still maintaining
   * the proper ratio. 
   * 
   * @author Kajiti
   * @version 0.6.1
   */
  private class WindowSizeListener extends ComponentAdapter {
    
    /**
     * {@inheritDoc}
     * 
     * Sets the scale factor based on the window's new size.
     */
    @Override
    public void componentResized(final ComponentEvent the_event) {
      double scale = 1.0;
      final double scale_y = getSize().getHeight() / my_normal_size.getHeight();
      final double scale_x = getSize().getWidth() / my_normal_size.getWidth();
      if (scale_y < scale_x) {
        scale = scale_y;
      } else {
        scale = scale_x;
      }
      for (TetrisGameGUI game : my_games) {
        game.setScale(scale);
      }
    }
  }
  
  /**
   * An action listener class that opens the control settings dialog window.
   * 
   * @author Kajiti
   * @version 0.6.1
   */
  private class ControlSettingsActionListener implements ActionListener {
    
    /**
     * Called when the corresponding button with this <code>ActionListener</code> has
     * been clicked.
     * 
     *  @param the_event not used.
     */
    @Override
    public void actionPerformed(final ActionEvent the_event) {
      new ControlSettingsGUI(TetrisGUI.this, my_controls);
      while (!Controls.allValidControls()) {
        new ControlSettingsGUI(TetrisGUI.this, my_controls);
      }
    }
  }
  
  /**
   * The key listener class for playing a game of Tetris.
   * 
   * @author Kajiti
   * @version 0.6.1
   */
  private final class TetrisKeyListener extends KeyAdapter {

    /** 
     * Called when a key is pressed.
     * 
     * @param the_event the event being passed.
     */
    @Override
    public void keyPressed(final KeyEvent the_event) {
      for (TetrisGameGUI game : my_games) {
        game.handleKeyEvent(the_event);
      }
    }
    
  }

  /**
   * The focus listener class for playing a game of Tetris.
   * 
   * @author Kajiti
   * @version 0.6.1
   */
  private final class TetrisFocusListener implements FocusListener {
    
    /** 
     * Called when the board gains focus.
     * @param the_event not used.
     */
    @Override
    public void focusGained(final FocusEvent the_event) {
      setPause(false);
    }

    /** 
     * Called when the board gains focus.
     * @param the_event not used.
     */
    @Override
    public void focusLost(final FocusEvent the_event) {
      setPause(true);
    }
  }
  
}
