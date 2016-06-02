package com.example.bryan.tick_tack_toe;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

/**
 * A simple Tic-Tac-Toe game. The Main Activity creates the board, and handles all button presses.
 * In addition, it evaluates the board state and determines if the game has been won, and by whom.
 * @author Bryan Scott -- bryan@pajato.com
 */
public class MainActivity extends AppCompatActivity {

    // The Match History
    private ArrayList<String> mInstructions;
    private static final String mKEY_INSTRUCTIONS = "match_history";

    // Keeps track of the mTurn user. True = Player 1, False = Player 2.
    private boolean mTurn;
    private static final String mKEY_TURN = "current_turn";

    // Player Turn Strings
    private String mPLAYER1;
    private String mPLAYER2;

    // Individual Game Fragments
    private TTTFragment ticTacToe;

    @Override public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the member variables.
        mPLAYER1 = getString(R.string.player_1);
        mPLAYER2 = getString(R.string.player_2);
        mInstructions = new ArrayList<>();
        mTurn = true;

        // Setup the Tic-Tac-Toe fragment and inflate its layout.
        ticTacToe = new TTTFragment();
        ticTacToe.setArguments(getIntent().getExtras());
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, ticTacToe)
                .commit();

        // If we saved any Instructions, we need to recreate the board state.
        if(savedInstanceState != null) {
            mTurn = savedInstanceState.getBoolean(mKEY_TURN);
            mInstructions = savedInstanceState.getStringArrayList(mKEY_INSTRUCTIONS);
            for(int i = 0; i < mInstructions.size(); i++) {
                sendMessage(mInstructions.get(i));
            }
            // If there is an odd number of Instructions, then the turn indicator will be set to
            // the wrong value. We can handle this here.
            if(mInstructions.size() % 2 != 0) {
                mTurn = !mTurn;
            }
            ticTacToe.checkNotFinished();
        }

    }

    @Override public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        // Store the Instructions and Turn for the save state.
        outState.putStringArrayList(mKEY_INSTRUCTIONS, mInstructions);
        outState.putBoolean(mKEY_TURN, mTurn);
    }

    /**
     * Sends a message alerting the event handling system that the new game button was clicked.
     *
     * @param view the new game button.
     */
    public void onNewGame(final View view) {
        // Create the message.
        String msg = getTurn() + "\n";
        msg += view.getTag().toString();

        // Empty the instructions list, as a new game has begun.
        mInstructions.clear();
        mInstructions.add(msg);

        // Output New Game Message
        String newTurn = (getTurn().equals(getString(R.string.player_1)) ?
                "Player 1 (" + getString(R.string.xValue) + ")" :
                "Player 2 (" + getString(R.string.oValue) + ")") + "'s Turn";
        Snackbar start = Snackbar.make(findViewById(R.id.fragment_container), "New Game! " + newTurn, Snackbar.LENGTH_SHORT);
        start.show();

        //TODO: replace this with an implemented event handling system.
        sendMessage(msg);
    }

    /**
     * Sends a message alerting the event handling system that there was a tile clicked, and
     * swaps the mTurn to the opposite player.
     *
     * @param view the tile clicked
     */
    public void tileOnClick(final View view) {
        String msg = getTurn() + "\n";
        msg = msg + view.getTag().toString();

        // Keep track of mInstructions for recreating the board.
        mInstructions.add(msg);

        //TODO: replace this with an implemented event handling system.
        sendMessage(msg);

        mTurn = !mTurn;
    }


    /**
     * A placeholder method for a message handler / event coordinator to be implemented at a later time.
     *
     * @param msg the message to transmit to the message handler.
     */
    private void sendMessage(final String msg) {
        //TODO: replace this with an implemented event handling system.
        // This will be a switch for each of the individual game fragment handlers.
        ticTacToe.messageHandler(msg);
    }

    /**
     * Gets the current mTurn and returns a string reflecting the player's
     * name who is currently playing.
     *
     * @return player 1 or player 2, depending on the mTurn.
     */
    private String getTurn() {
        return mTurn ? mPLAYER1 : mPLAYER2;
    }

}

