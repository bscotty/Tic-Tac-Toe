package com.example.bryan.tick_tack_toe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Keeps track of the turn number. The max possible turn count is 9.
    private int turnCount;
    // Keeps track of whose turn it is. True = X, False = O.
    private boolean turn;
    // A marker for the game ending.
    private boolean isNotFinished;

    // Output Strings for notifications of whose turn it is.
    private String oTurnMsg = "O's Turn";
    private String xTurnMsg = "X's Turn";

    // The board view, used to keep track of the buttons.
    private View board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        board = findViewById(R.id.board);
        turn = true;
        turnCount = 9;
        isNotFinished = true;
    }

    /**
     * Handles the onclick functionality of the tile buttons. Sets value of the buttons based on the turn,
     * then changes the turn, and alerts the players to whose turn is left.
     *
     * @param v a Tic-Tac-Toe tile Button.
     */
    public void tileOnClick(View v) {
        View board = findViewById(R.id.board);
        Button b = (Button) board.findViewWithTag(v.getTag());

        // Only changes the value if the current value is empty.
        if (b.getText().toString().equals(getString(R.string.spaceValue)) && isNotFinished) {
            if (turn) {
                b.setText(getString(R.string.xValue));

            } else {
                b.setText(getString(R.string.oValue));
            }
            turnCount--;

            // Check if the game is still going. If it is, move to the next turn.
            isNotFinished = checkNotFinished();
            if(isNotFinished) {
                turn = !turn;
                String output = (turn) ? getText(R.string.xValue).toString() : getText(R.string.oValue).toString();
                ((TextView) findViewById(R.id.turnNumber)).setText(output);
            }
        }
    }

    /**
     * Handles the onClick functionality of the new game button by resetting all buttons to empty,
     * then outputs a New Game notification.
     *
     * @param view the New Game button.
     */
    public void onNewGame(View view) {
        turnCount = 9;
        isNotFinished = true;

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
        String newTurn = (turn) ? xTurnMsg : oTurnMsg;
        Toast start = Toast.makeText(getApplicationContext(), "New Game! " + newTurn, Toast.LENGTH_SHORT);
        start.show();
    }

    /**
     * Determines if the game has still not ended.
     *
     * @return false if one player has 3 in a row, or the turn counter has run out. True otherwise.
     */
    private boolean checkNotFinished() {
        // If the turn timer has run out, end the game.
        if(turnCount == 0) {
            Toast noWin = Toast.makeText(getApplicationContext(), "Tie!", Toast.LENGTH_SHORT);
            noWin.show();
            return false;
        }

        // Otherwise, we need to check on the buttons states. For convenience, store them in a 2D array.
        Button[][] buttons = new Button[3][3];
        buttons[0][0] = (Button) board.findViewWithTag("button00");
        buttons[0][1] = (Button) board.findViewWithTag("button01");
        buttons[0][2] = (Button) board.findViewWithTag("button02");
        buttons[1][0] = (Button) board.findViewWithTag("button10");
        buttons[1][1] = (Button) board.findViewWithTag("button11");
        buttons[1][2] = (Button) board.findViewWithTag("button12");
        buttons[2][0] = (Button) board.findViewWithTag("button20");
        buttons[2][1] = (Button) board.findViewWithTag("button21");
        buttons[2][2] = (Button) board.findViewWithTag("button22");

        // Keep track of the values of each of the Buttons.
        int[][] values = new int[3][3];

        // Go through all the buttons.
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                // If there's an X in this button, store a 1
                if(buttons[i][j].getText().toString().equals(getString(R.string.xValue))) {
                    values[i][j] = 1;
                // If there's an O in this button, store a 2
                } else if (buttons[i][j].getText().toString().equals(getString(R.string.oValue))) {
                    values[i][j] = 2;
                // Otherwise, there's a space. -5 was chosen arbitrarily to keep row values unique.
                } else {
                    values[i][j] = -5;
                }
            }
        }

        // The only possible values of each row that matter are 3 in a row of X or O.
        // There is no way to get a value of 3 without getting 3 Xs, and there's no way to get a 6 without getting 3 Os.
        // Add up each of the values.
        int topRow = values[0][0] + values[0][1] + values[0][2];
        int midRow = values[1][0] + values[1][1] + values[1][2];
        int botRow = values[2][0] + values[2][1] + values[2][2];

        int startCol = values[0][0] + values[1][0] + values[2][0];
        int centrCol = values[0][1] + values[1][1] + values[2][1];
        int rightCol = values[0][2] + values[1][2] + values[2][2];

        int leftDiag = values[0][0] + values[1][1] + values[2][2];
        int rightDiag = values[2][0] + values[1][1] + values [0][2];

        // If any condition is met, the game is over.
        boolean xWins = (topRow == 3 || midRow == 3 || botRow == 3 || startCol == 3 || centrCol == 3 || rightCol == 3 || leftDiag == 3 || rightDiag == 3);
        boolean oWins = (topRow == 6 || midRow == 6 || botRow == 6 || startCol == 6 || centrCol == 6 || rightCol == 6 || leftDiag == 6 || rightDiag == 6);

        // Generate output messages for the players.
        if(xWins) {
            // X wins
            Toast xWinner = Toast.makeText(getApplicationContext(), "Game over. X wins!", Toast.LENGTH_SHORT);
            xWinner.show();
            return false;
        } else if (oWins) {
            // O wins
            Toast oWinner = Toast.makeText(getApplicationContext(), "Game over. O wins!", Toast.LENGTH_SHORT);
            oWinner.show();
            return false;
        }
        // If none of the conditions are met, the game has not yet ended, and we can continue.

        return true;
    }

}
