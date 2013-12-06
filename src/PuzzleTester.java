/**
 * @author Ben Sherman, bms113
 * Case Western Reserve University
 * EECS 233 - Data Structures
 * This class is used to test the BFS and DFS methods
 */
public class PuzzleTester {

	public static void main(String[] args) {

		/*            										 TESTS
		 * Test 1.) Try to build a PuzzleNode from an array with no blanks, it will terminate the program
		 * Test 2.) Try to build a PuzzleNode from an array that isn't "square." Cols and Rows will have different vals. Will terminate program
		 * Test 3.) Solve a default puzzle with BFS that has been randomized 20 times. Will terminate program
		 * Test 4.) Solve a default puzzle with DFS that has been randomized 5 times. Will terminate program
		 * Test 5.) Try to solve an unsolvable puzzle with DFS and BFS.
		 * Test 6.) Try to solve a 15 puzzle (4x4) with DFS and BFS. DFS will likely not work because of its design, BFS will. This will terminate the
		 * program
		 */
		
//		//THIS TEST WILL CAUSE PROGRAM TERMINATION
//		//TEST ONE - try to build a puzzleNode from an array that contains no blanks
//		System.out.println("1.)***TEST ONE. CREATE 3x3 PUZZLE FROM ARRAY WITH NO BLANKS");
//		int[][] testOneArray = new int[][] {{1,2,3}, {4,5,6}, {7,8,9}};
//		PuzzleNode testOnePuzzle = new PuzzleNode(testOneArray,null, 0);
//		testOnePuzzle.printPuzzleState();
//		//END TEST ONE
		
//		//THIS TEST WILL CAUSE PROGRAM TO TERMINATE
//		//TEST TWO - try to build a puzzleNode from an array that isn't "square"
//		System.out.println("2.)***TEST TWO. ATTEMPT TO CREATE 3x3 FROM ARRAY. ARRAY IS NOT SQUARE, WILL FAIL");
//		int[][] testTwoArray = new int[][] {{1,2,3}, {4,5,6}, {7,8,9, 10, 100, 100, 100}};
//		PuzzleNode testTwoPuzzle = new PuzzleNode(testTwoArray,null, 0);
//		testTwoPuzzle.printPuzzleState();
//		//END TEST TWO
		
		//THIS TEST WILL CAUSE PROGRAM TERMINATION
		//TEST THREE - solve a default puzzle that has been randomized 20 times with BFS
		System.out.println("3.)***TEST THREE. SOLVE 20 TIMES RANDOMIZED PUZZLE WITH BFS");
		PuzzleNode testThreePuzzle = new PuzzleNode(8, 0);
		testThreePuzzle.randomize(20);
		PuzzleSolver.breadthFirstSolver(testThreePuzzle);
		//END TEST 3
		
//		//THIS TEST WILL CAUSE PROGRAM TERMINATION
//		//TEST FOUR - solve a default puzzle that has been randomized 5 times with DFS
//		System.out.println("4.)***TEST FOUR. SOLVE 5 TIMES RANDOMIZED PUZZLE WITH DFS");
//		PuzzleNode testFourPuzzle = new PuzzleNode(8, 5);
//		PuzzleSolver.depthFirstSolver(testFourPuzzle);
//		//END TEST 4
		
//		//TEST FIVE - attempt to solve an unsolvable puzzle with DFS and BFS. Initial puzzle was created from a specified array, and not randomized.
//		System.out.println("5.)***TEST FIVE. ATTEMPT TO SOLVE UNSOLVABLE PUZZLE WITH DFS AND BFS");
//		int[][] testFiveArray = new int[][]{{1,2,3}, {4,5,6}, {8,7,0}};
//		PuzzleNode testFivePuzzle = new PuzzleNode(testFiveArray, null, 0);
//		PuzzleSolver.depthFirstSolver(testFivePuzzle);
//		PuzzleSolver.breadthFirstSolver(testFivePuzzle);
//		//END TEST 5
		
//		//THIS TEST WILL CAUSE PROGRAM TO TERMINATE
//		//TEST SIX - solve a 15 puzzle, 35 times randomized, with DFS and BFS. BFS will be successful, DFS will *VERY LIKELY* be unsuccessful because of depth limitations. 
//		//this test will show a major difference between DFS and BFS for large puzzles and very randomized puzzles. Although there is a chance DFS will work, it is EXTREMELY unlikely.
//		//If DFS does work, run the test again. My tests had DFS work once or twice for several dozen runs. Also, depending on the amount of RAM on your machine,
//		//this test may stall(for me, the CPU utilization spiked up and the test got very slow). In this case, rerun and it should solve. 
//		System.out.println("6.)***TEST SIX. ATTEMPT TO SOLVE 15 PUZZLE (35 TIMES RANDOMIZED) WITH DFS AND BFS. DFS WILL LIKELY FAIL");
//		PuzzleNode testSixPuzzle = new PuzzleNode(15, 35);
//		PuzzleSolver.depthFirstSolver(testSixPuzzle);
//		PuzzleSolver.breadthFirstSolver(testSixPuzzle);
//		//END TEST 6
		
	}
}
