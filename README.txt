To use these files:
-There are two PuzzleNode constructors. The comments above these constructors explain how they are to be used. Note that the constructors contain a parameter which will randomize the puzzle
during initialization. To prevent this, use a value of zero for this parameter. The state from which it randomizes is the goal state (noted in code comments).
PuzzleNode example = new PuzzleNode(8, 5) is exactly the same as:
PuzzleNode example = new PuzzleNode(8, 0)
example.randomize(5)
-Searches are done through the static depthFirstSolver and breadthFirstSolver methods inside the PuzzleSolver class. To solve a puzzle, use the call
PuzzleSolver.depthFirstSearch(rootPuzzleNode) or PuzzleSolver.breadthFirstSearch(rootPuzzleNode). The parameter is the puzzleNode which you want to find the solution to. 
-All tests are done in the PuzzleTester.java class. There are 6 tests. For many of the tests, running them will result in a call to System.exit. For this reason, the class
comes with one test enabled by default (test 3, breadth first). To use the other tests, simply remove the slashes from the test block (Ctrl+/ in eclipse is a great thing), and comment
out the prior enabled test. Make sure that only one test is enabled at a time(although test 5 won't cause termination). PuzzleTester.java contains a description of each of the tests. 
*************************************
Other:
-I have found that depth first search has problems when it isn't limited by depth. This is why the constant DEPTH_BOUND is included in the solver class. I have used a maximum
search depth of 5 to keep solutions small and prevent an overflow of the stack, but you can adjust this variable as you please.
-My sixth test randomizes a 4x4 puzzle 35 times, which, when solving, can be RAM intensive. On my machine, it ran fine, but I don't know what you have. The reason the random count is set so
high is to demonstrate how a BFS will handle higher counts while DFS won't. Also, this test shows that my puzzleNode can handle different grid sizes. If, at minimum, BFS doesn't run properly,
you can lower the random count. DFS will run first, so if DFS does find a path (the point of this test is that it doesn't), BFS will not run. Keep this in mind when running the test.
-Lastly, I catch "OutOfMemoryError" to prevent both BFS and DFS from crashing when eating up a ton of RAM. The depth counter for DFS is also used as
a way to prevent crashes from excessive memory use.
**************************************
Included Files: (in bms113_P4_EECS233_FIXED.zip)
-PuzzleSolver.java - contains the DFS and BFS methods to solve the n puzzles.
-PuzzleNode.java - contains the puzzle itself. It contains the puzzle's grid (called puzzleState, an int[][]), as well as all 
information related to the puzzle other than the solver
-PuzzleTester.java - the only executable class in the project. It contains 6 tests to validate my methods, structures, and algorithms. 
-README.txt


