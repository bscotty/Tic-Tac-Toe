# Tic-Tac-Toe

##Latest Changes:

Testing Implemented for the current code.

#New Files

## UiOperationsTest.java 
* Ensures that the new game button properly clears the board of all placed pieces
* Ensures that the players alternate turns
* Ensures that, on a new game, the person who ought to go next should.
* Ensure that at the start of a new game, the winner’s view is invisible
* Ensure that players cannot overwrite where their opponent has gotten with their own piece.

## WinConditionsTest.java

* Ensures that a tie will be handled properly as a tie.
* Ensures that empty tiles cannot be added after the game has ended
* Ensures that each of the 16 possible win scenarios actually end the game.

Refactored code to set up an instruction/message system, and implemented precautions to work around the Life Cycle. Ultimately, the base level code and what is getting done is not all that different, but there are a few notable additions. 


# Previous Changes:

Initial Commit

##Changed Files: 

**MainActivity.java**
* Additional Life Cycle precautions have been added to ***onCreate*** and ***onSaveInstanceState***
* A number of other changes have also been made, to facilitate working around the life cycle. 
* Several “placeholder” methods or other seemingly unnecessary abstractions have been made to allow for future implementation of an event handling system. These methods are marked with TODOs.

**activity_main.xml**
* A “Winner’s” TextView has been added, as part of phasing out the use of Toast notifications.
* A few variables have been renamed to facilitate easier comprehension.
