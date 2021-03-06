package com.example.bryan.tick_tack_toe;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Scanner;

public class TTTFragment extends Fragment {

    // Keeps track of the turn number
    private int mTurnCount;

    // Player Piece Strings
    private String mXValue;
    private String mOValue;

    // The mBoard view
    private View mBoard;

    public TTTFragment() {

    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        mXValue = getString(R.string.xValue);
        mOValue = getString(R.string.oValue);

        // Initialize the turn counter, and mBoard variables.
        mTurnCount = 9;
        mBoard = inflater.inflate(R.layout.ttt_layout, container, false);

        TextView turnDisplay = (TextView) mBoard.findViewById(R.id.turnDisplay);
        turnDisplay.setText(getTurn(true));

        return mBoard;
    }

    /**
     * Translates the messages sent by the event system and handles the individual clicks
     * based on messages sent.
     *
     * @param msg the message to be handled.
     */
    public void messageHandler(String msg) {
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
     * Empties the instructions, mBoard tiles, and outputs a new game message.
     *
     * @param player the player who initiated the click.
     * @param buttonTag the tag of the button clicked.
     */
    private void handleNewGame(String player, String buttonTag) {
        // Reset initial variables and the Winner / Turn views.
        mTurnCount = 9;
        TextView Winner = (TextView) super.getActivity().findViewById(R.id.Winner);
        Winner.setVisibility(View.INVISIBLE);

        TextView turnDisplay = (TextView) mBoard.findViewById(R.id.turnDisplay);
        turnDisplay.setText(getTurn(player.equals(getString(R.string.player_1))));

        // Set values for each tile to empty.
        ((Button) mBoard.findViewWithTag("button00")).setText(getString(R.string.spaceValue));
        ((Button) mBoard.findViewWithTag("button01")).setText(getString(R.string.spaceValue));
        ((Button) mBoard.findViewWithTag("button02")).setText(getString(R.string.spaceValue));
        ((Button) mBoard.findViewWithTag("button10")).setText(getString(R.string.spaceValue));
        ((Button) mBoard.findViewWithTag("button11")).setText(getString(R.string.spaceValue));
        ((Button) mBoard.findViewWithTag("button12")).setText(getString(R.string.spaceValue));
        ((Button) mBoard.findViewWithTag("button20")).setText(getString(R.string.spaceValue));
        ((Button) mBoard.findViewWithTag("button21")).setText(getString(R.string.spaceValue));
        ((Button) mBoard.findViewWithTag("button22")).setText(getString(R.string.spaceValue));

        checkNotFinished();
    }

    /**
     * Assigns a value to a button without text, either X or O,
     * depending on the player whose turn it is.
     *
     * @param player the player who initiated the click.
     * @param buttonTag the tag of the button clicked.
     */
    private void handleTileClick(String player, String buttonTag) {
        Button b = (Button) mBoard.findViewWithTag(buttonTag);
        // Only changes the value if the current value is empty and the game has not finished yet.
        if (b.getText().toString().equals(getString(R.string.spaceValue)) && checkNotFinished()) {
            mTurnCount--;

            b.setText(getTurn(player.equals(getString(R.string.player_1))));

            TextView turnNumber = (TextView) super.getActivity().findViewById(R.id.turnDisplay);
            turnNumber.setText(getTurn(player.equals(getString(R.string.player_2))));

            checkNotFinished();

        }
    }

    /**
     * Returns a string enumerating the player, depending on the current turn.
     *
     * @return R.string.xValue or R.string.oValue, depending on whose turn it is.
     */
    private String getTurn(boolean turnIndicator) {
        if(turnIndicator) {
            return mXValue;
        } else {
            return mOValue;
        }
    }

    /**
     * Evaluates the current state of the individual tiles of the mBoard and stores them.
     */
    private void evaluateBoard(int[][] boardValues) {
        // Go through all the buttons.
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                Button currTile = (Button) mBoard.findViewWithTag("button" + Integer.toString(i) + Integer.toString(j));
                String tileValue = currTile.getText().toString();

                // Assign each possible state for each tile as a value. The only possible values
                // of each row (that indicate and endgame state) are 3 in a row of X or O. There
                // is no way to get a value of 3 without getting 3 Xs, and there's no way to get a
                // value of 6 without getting 3 Os.

                // If there's an X in this button, store a 1
                if(tileValue.equals(mXValue)) {
                    boardValues[i][j] = 1;
                // If there's an O in this button, store a 2
                } else if (tileValue.equals(mOValue)) {
                    boardValues[i][j] = 2;
                // Otherwise, there's a space. -5 was chosen arbitrarily to keep row values unique.
                } else {
                    boardValues[i][j] = -5;
                }
            }
        }
    }

    /**
     * Evaluates the state of the mBoard, determining if there are three in a row of either X or O.
     *
     * @return false if a player has won or if the full number of turns has occurred, true otherwise.
     */
    public boolean checkNotFinished() {
        int[][] boardValues = new int[3][3];
        // First, we need to check on the buttons' states.
        evaluateBoard(boardValues);

        // Evaluate all possible lines of 3.
        int topRow = boardValues[0][0] + boardValues[0][1] + boardValues[0][2];
        int midRow = boardValues[1][0] + boardValues[1][1] + boardValues[1][2];
        int botRow = boardValues[2][0] + boardValues[2][1] + boardValues[2][2];

        int startCol = boardValues[0][0] + boardValues[1][0] + boardValues[2][0];
        int centerCol = boardValues[0][1] + boardValues[1][1] + boardValues[2][1];
        int endCol = boardValues[0][2] + boardValues[1][2] + boardValues[2][2];

        int leftDiag = boardValues[0][0] + boardValues[1][1] + boardValues[2][2];
        int rightDiag = boardValues[2][0] + boardValues[1][1] + boardValues [0][2];

        // If any lines of 3 are equal to 3, X wins.
        boolean xWins = (topRow == 3 || midRow == 3 || botRow == 3 || startCol == 3
                || centerCol == 3 || endCol == 3 || leftDiag == 3 || rightDiag == 3);

        // If any lines of 3 are equal to 6, O wins.
        boolean oWins = (topRow == 6 || midRow == 6 || botRow == 6 || startCol == 6
                || centerCol == 6 || endCol == 6 || leftDiag == 6 || rightDiag == 6);

        // Handle the win conditions.
        TextView Winner = (TextView) super.getActivity().findViewById(R.id.Winner);
        Winner.setText(getText(R.string.spaceValue));
        Winner.setVisibility(View.VISIBLE);
        Snackbar winMsg;

        // Reveal Winning Messages
        if(xWins) {
            winMsg = Snackbar.make(mBoard, "Player 1 (" + mXValue + ") Wins!", Snackbar.LENGTH_SHORT);
            winMsg.show();
            Winner.setText(R.string.xWins);
            return false;
        } else if (oWins) {
            winMsg = Snackbar.make(mBoard, "Player 2 (" + mOValue + ") Wins!", Snackbar.LENGTH_SHORT);
            winMsg.show();
            Winner.setText(R.string.oWins);
            return false;

        // If no one has won, and the turn timer has run out, end the game.
        } else if(mTurnCount == 0) {
            // Reveal Tie Messages
            winMsg = Snackbar.make(mBoard, "It's a Tie!", Snackbar.LENGTH_SHORT);
            winMsg.show();
            Winner.setText(R.string.tie);
            return false;
        }

        // If none of the conditions are met, the game has not yet ended, and we can continue it.
        return true;
    }

}
