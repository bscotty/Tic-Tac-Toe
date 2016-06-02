# Tic-Tac-Toe

# Latest Changes (June 2, 2016)

Refactoring the previous **MainActivity.java** into two specific pieces: a game engine (**MainActivity.java**) and specific board fragment (**TTTFragment.java**)

## Changed Files

**MainActivity.java**
* Now functions as the game engine, keeping track of the current turn, and the turns already taken by the players.
* Keeps ***onCreate***, ***sendMessage***, and the onClick methods which are described in the XML (NOTE: these onClick methods are now described in **ttt_layout.xml**)
* Snackbar notifications are now being used instead of Toast notifications
* Member variables renamed to adhere to convention

**activity_main.xml**
* The meat of the layout is removed from **activity_main.xml** and into **ttt_layout.xml**
* Only a fragment container remains, which will be used to store the current game fragment.

**AndroidManifest.xml**
* Changed so that whenever the orientation changes, instead of destroying and recreating the activity, we only cause a configChanges event

## New Files

**TTTFragment.java**
* A ***Fragment*** class that interprets the message commands that are enumerated by the game engine in **MainActivity.java**
* Minor refactoring of previous methods that were stored in the old **MainActivity.java**
* Snackbar notifications are now being used instead of Toast notifications
* Member variables renamed to adhere to convention

**ttt_layout.xml**
* Essentially identical to the previous **activity_main.xml** layout file
* Winner's Textview now aligns itself to the bottom of the board as opposed to the bottom of the screen, to avoid overlapping with the new Snackbox notifications

**/app/build.gradle**
* Included the new dependency for the Snackbox notifications.

**build.gradle**
* Included some new shortcuts.


# Previous Changes

##(May 31, 2016):

Testing Implemented for the current code.

### New Files

**UiOperationsTest.java **
* Ensures that the new game button properly clears the board of all placed pieces
* Ensures that the players alternate turns
* Ensures that, on a new game, the person who ought to go next should.
* Ensure that at the start of a new game, the winner’s view is invisible
* Ensure that players cannot overwrite where their opponent has gotten with their own piece.

**WinConditionsTest.java**
* Ensures that a tie will be handled properly as a tie.
* Ensures that empty tiles cannot be added after the game has ended
* Ensures that each of the 16 possible win scenarios actually end the game.

## (May 26, 2016):

Refactored code to set up an instruction/message system, and implemented precautions to work around the Life Cycle. Ultimately, the base level code and what is getting done is not all that different, but there are a few notable additions. 

### Changed Files:

**MainActivity.java**
* Additional Life Cycle precautions have been added to ***onCreate*** and ***onSaveInstanceState***
* A number of other changes have also been made, to facilitate working around the life cycle. 
* Several “placeholder” methods or other seemingly unnecessary abstractions have been made to allow for future implementation of an event handling system. These methods are marked with TODOs.

**activity_main.xml**
* A “Winner’s” TextView has been added, as part of phasing out the use of Toast notifications.
* A few variables have been renamed to facilitate easier comprehension.
