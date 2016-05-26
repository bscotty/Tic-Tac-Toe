# Tic-Tac-Toe

##Latest Changes:

Refactored code to set up an instruction/message system, and implemented precautions to work around the Life Cycle. Ultimately, the base level code and what is getting done is not all that different, but there are a few notable additions. 

##Changed Files: 

**MainActivity.java**
* Additional Life Cycle precautions have been added to ***onCreate*** and ***onSaveInstanceState***
* A number of other changes have also been made, to facilitate working around the life cycle. 
* Several “placeholder” methods or other seemingly unnecessary abstractions have been made to allow for future implementation of an event handling system. These methods are marked with TODOs.

**activity_main.xml**
* A “Winner’s” TextView has been added, as part of phasing out the use of Toast notifications.
* A few variables have been renamed to facilitate easier comprehension.
