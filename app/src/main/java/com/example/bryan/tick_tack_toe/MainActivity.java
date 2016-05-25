package com.example.bryan.tick_tack_toe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A simple Tic-Tac-Toe game. The Main Activity creates the board, and handles all button presses.
 * In addition, it evaluates the board state and determines if the game has been won, and by whom.
 * @author Bryan Scott -- bryan@pajato.com
 */
public class MainActivity extends AppCompatActivity {

    // Keeps track of the turn user
    private boolean turn;
    private final String KEY_TURN = "turn";
    // Keeps track of the turn number
    private int turnCount;
    // Keeps track if the game has ended
    private boolean isNotFinished;

    // Player Piece Strings
    private String xMsg = getString(R.string.xValue);
    private String oMsg = getString(R.string.oValue);

    // The board state
    private View board;
    private int[][] boardValues;

    // The Match History
    private ArrayList<String> instructions;
    private final String KEY_INSTRUCTIONS = "match_history";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the match history, turn, and board variables.
        instructions = new ArrayList<>();
        turn = true;
        turnCount = 9;
        board = findViewById(R.id.board);
        boardValues = new int[3][3];
        isNotFinished = true;

        // If we saved instructions, we need to recreate the board state.
        if(savedInstanceState != null) {
            turn = savedInstanceState.getBoolean(KEY_TURN);
            instructions = savedInstanceState.getStringArrayList(KEY_INSTRUCTIONS);
            for(int i = 0; i < instructions.size(); i++) {
                messageHandler(instructions.get(i));
            }
        }
        evaluateBoard();

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Saves the instructions for when the activity is called to save the instance state.
        outState.putStringArrayList(KEY_INSTRUCTIONS, instructions);
        outState.putBoolean(KEY_TURN, turn);
    }

    /**
     * Sends a message alerting the event handling system that there was a tile clicked.
     *
     * @param view the tile clicked
     */
    public void tileOnClick(View view) {
        String msg = getTurn() + "\n";
        msg = msg + view.getTag().toString();

        //TODO: replace this with an implemented event handling system.
        sendMessage(msg);
    }

    /**
     * Sends a message alerting the event handling system that the new game button was clicked.
     *
     * @param view the new game button.
     */
    public void onNewGame(View view) {
        String msg = getTurn() + "\n";
        msg = msg + view.getTag().toString();

        //TODO: replace this with an implemented event handling system.
        sendMessage(msg);
    }

    /**
     * A placeholder method for a message handler / event coordinator to be implemented at a later time.
     * @param msg the message to transmit to the message handler.
     */
    private void sendMessage(String msg) {
        // Keep track of instructions for recreating the board.
        instructions.add(msg);

        //TODO: replace this with an implemented event handling system.
        messageHandler(msg);
    }

    /**
     * Translates the messages sent by the event system and handles the individual clicks
     * based on messages sent.
     *
     * @param msg the message to be handled.
     */
    private void messageHandler(String msg) {
        //TODO: Modify this when an implemented event handling system is implemented.
        Scanner input = new Scanner(msg);
        String player = input.nextLine();
        String buttonTag = input.nextLine();
        input.close();

        // Call appropriate methods for each button.
        if(buttonTag.equals("NewGame")) {
            handleNewGame(player, buttonTag);
        } else {
            handleTileClick(player, buttonTag);
        }

    }

    /**
     * Empties the instructions, board tiles, and outputs a new game message.
     *
     * @param player the player who initiated the click.
     * @param buttonTag the tag of the button clicked.
     */
    private void handleNewGame(String player, String buttonTag) {
        // Empty the instructions list, as a new game has begun.
        instructions.clear();
        instructions.add(player + "\n" + buttonTag);

        // Reset initial variables.
        turnCount = 9;
        isNotFinished = true;
        TextView Winner = (TextView) findViewById(R.id.Winner);
        Winner.setVisibility(View.INVISIBLE);

        // Set values for each tile to empty.
        ((Button) board.findViewWithTag("button00")).setText(getString(R.string.spaceValue));
        ((Button) board.findViewWithTag("button01")).setText(getString(R.string.spaceValue));
        ((Button) board.findViewWithTag("button02")).setText(getString(R.string.spaceValue));
        ((Button) board.findViewWithTag("button10")).setText(getString(R.string.spaceValue));
        ((Button) board.findViewWithTag("button11")).setText(getString(R.string.spaceValue));
        ((Button) board.findViewWithTag("button12")).setText(getString(R.string.spaceValue));
        ((Button) board.findViewWithTag("button20")).setText(getString(R.string.spaceValue));
        ((Button) board.findViewWithTag("button21")).setText(getString(R.string.spaceValue));
        ((Button) board.findViewWithTag("button22")).setText(getString(R.string.spaceValue));

        // Output New Game Message
        String newTurn = player + "'s Turn";
        Toast start = Toast.makeText(getApplicationContext(), "New Game! " + newTurn, Toast.LENGTH_SHORT);
        start.show();
    }

    /**
     * Assigns a value to a button without text, either X or O, depending on the player whose turn it is.
     *
     * @param player the player who initiated the click.
     * @param buttonTag the tag of the button clicked.
     */
    private void handleTileClick(String player, String buttonTag) {
        // Only changes the value if the current value is empty.
        Button b = (Button) board.findViewWithTag(buttonTag);
        if (b.getText().toString().equals(getString(R.string.spaceValue)) && isNotFinished) {
            b.setText(player);
            turnCount--;

            // Check if the game is still going after the most recent turn. If it is, move to the next turn.
            isNotFinished = checkNotFinished();
            if(isNotFinished) {
                turn = !turn;
                TextView turnNumber = (TextView) findViewById(R.id.turnNumber);
                turnNumber.setText(getTurn());
            }
        }
    }


    /**
     * Returns a string enumerating the player, depending on the current turn.
     *
     * @return R.string.xValue or R.string.oValue, depending on whose turn it is.
     */
    private String getTurn() {
        if(turn) {
            return xMsg;
        } else {
            return oMsg;
        }
    }

    /**
     * Evaluates the current state of the individual tiles of the board and stores them.
     */
    private void evaluateBoard() {
        // Go through all the buttons.
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                Button currTile = (Button) board.findViewWithTag("button" + Integer.toString(i) + Integer.toString(j));
                String tileValue = currTile.getText().toString();

                // Assign each possible state for each tile as a value. The only possible values
                // of each row (that indicate and endgame state) are 3 in a row of X or O. There
                // is no way to get a value of 3 without getting 3 Xs, and there's no way to get a
                // value of 6 without getting 3 Os.

                // If there's an X in this button, store a 1
                if(tileValue.equals(xMsg)) {
                    boardValues[i][j] = 1;
                    // If there's an O in this button, store a 2
                } else if (tileValue.equals(oMsg)) {
                    boardValues[i][j] = 2;
                    // Otherwise, there's a space. -5 was chosen arbitrarily to keep row values unique.
                } else {
                    boardValues[i][j] = -5;
                }
            }
        }
    }

    /**
     * Evaluates the state of the board, determining if there are three in a row of either X or O.
     *
     * @return false if a player has won or if the full number of turns has occurred. true otherwise.
     */
    private boolean checkNotFinished() {
        // First, we need to check on the buttons' states.
        evaluateBoard();

        // Evaluate all possible lines of 3.
        int topRow = boardValues[0][0] + boardValues[0][1] + boardValues[0][2];
        int midRow = boardValues[1][0] + boardValues[1][1] + boardValues[1][2];
        int botRow = boardValues[2][0] + boardValues[2][1] + boardValues[2][2];

        int startCol = boardValues[0][0] + boardValues[1][0] + boardValues[2][0];
        int centerCol = boardValues[0][1] + boardValues[1][1] + boardValues[2][1];
        int rightCol = boardValues[0][2] + boardValues[1][2] + boardValues[2][2];

        int leftDiag = boardValues[0][0] + boardValues[1][1] + boardValues[2][2];
        int rightDiag = boardValues[2][0] + boardValues[1][1] + boardValues [0][2];

        // If any lines of 3 are equal to 3, X wins.
        boolean xWins = (topRow == 3 || midRow == 3 || botRow == 3 || startCol == 3
                || centerCol == 3 || rightCol == 3 || leftDiag == 3 || rightDiag == 3);

        // If any lines of 3 are equal to 6, O wins.
        boolean oWins = (topRow == 6 || midRow == 6 || botRow == 6 || startCol == 6
                || centerCol == 6 || rightCol == 6 || leftDiag == 6 || rightDiag == 6);

        // Handle the win conditions.
        Toast winMsg;
        TextView Winner = (TextView) findViewById(R.id.Winner);
        Winner.setVisibility(View.VISIBLE);
        if(xWins) {
            // Reveal Winning Messages
            winMsg = Toast.makeText(getApplicationContext(), "Game over. " + xMsg + " wins!", Toast.LENGTH_SHORT);
            winMsg.show();
            Winner.setText(R.string.xWins);
        } else if (oWins) {
            // Reveal Winning Messages
            winMsg = Toast.makeText(getApplicationContext(), "Game over. " + oMsg + " wins!", Toast.LENGTH_SHORT);
            winMsg.show();
            Winner.setText(R.string.oWins);

        // If no one has one, and the turn timer has run out, end the game.
        } else if(turnCount == 0) {
            // Reveal Tie Messages
            Winner.setText(R.string.tie);
            Toast noWin = Toast.makeText(getApplicationContext(), "Tie!", Toast.LENGTH_SHORT);
            noWin.show();
            return false;
        }

        // If none of the conditions are met, the game has not yet ended, and we can continue.
        return true;
    }

}

