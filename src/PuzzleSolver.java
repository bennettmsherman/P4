/**
 * @author Ben Sherman
 * Case Western Reserve University
 * EECS 233 - Data Structures
 * This class is used to solve N puzzles using BFS and DFS
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class PuzzleSolver {
	
	private final static int DEPTH_BOUND = 5;	//Maximum depth each PuzzleNode can travel. Used for DFS. Modify this to change the depth bounds
	
	/**
	 * This static method will find a solution to a puzzle using breadth first search. It will print the solution to the console
	 * @param rootPuzzle - the puzzleNpde to find the solution to
	 */
	public static void breadthFirstSolver(PuzzleNode rootPuzzle)
	{
		Queue<PuzzleNode> puzzleQueue = new LinkedList<PuzzleNode>();	//Create a queue to store PuzzleNodes whose children to be checked
		HashSet<String> visitedPuzzles = new HashSet<String>();	//HashSet which holds string versions of the visited puzzles, to make sure we don't cycle
		int[][] goalState = rootPuzzle.getGoalState();	//int[][] containing the goal state for this puzzle
		System.out.println("\n\n********STARTING BREADTH FIRST SEARCH********");
		puzzleQueue.add(rootPuzzle); //add the root state to the queue
		rootPuzzle.setPuzzleNodeVisited();	//set the root to visited
		
		try { //try block, to catch memory overusage
			while(!puzzleQueue.isEmpty())	//loop while the queue isn't empty
			{	
				PuzzleNode temp = puzzleQueue.poll();	//dequeue and store the element at the front of the queue
				temp.generateChildren();	//will generate the children for this temporary puzzlenode
				visitedPuzzles.add(temp.getPuzzleStateAsString());	//add this puzzle's state (in string form) to the hashset
				
				if(Arrays.deepEquals(temp.getPuzzleStateArray(), goalState))	//if the temporary puzzleNode is equal to the goal, we found a soln
				{
					temp.printPathToGoal();	//backtrack, then print the solution from the initial state to the goal
					System.out.println("BFS COMPLETE");
					System.exit(0);	//exit the program
				}
				
				for(PuzzleNode pn : temp.childrenPuzzles)	//for every child of the temp. puzzleNode...
				{	
					if(!pn.isVisited() && !visitedPuzzles.contains(pn.getPuzzleStateAsString()))	//if this hasn't been visited (not marked as well as not in the hashset)
					{
						pn.setPuzzleNodeVisited();	//mark this child as visited
						visitedPuzzles.add(pn.getPuzzleStateAsString());	//add this child's state to the hashset
						puzzleQueue.add(pn);	//add the child to the rear of the queue
					}
				}
			}
			
			if(puzzleQueue.isEmpty())	//if the queue is empty, it means no soln was found
				System.out.println("No solution found using BFS");
			
			System.out.println("BFS COMPLETE");
		}
		catch(OutOfMemoryError memErr)	//catches the case when the method uses all the system's memory, and terminates the program. 
		{
			System.err.println("Program Terminating, BFS has used all memory avaliable");
			System.exit(0);
		}
	}
	
	/**
	 * Method to initialize depth first solving
	 * @param rootPuzzle - the puzzle to find the solution to
	 */
	public static void depthFirstSolver(PuzzleNode rootPuzzle)
	{
		System.out.println("\n\n********STARTING DEPTH FIRST SEARCH********");
		HashSet<String> visitedPuzzles = new HashSet<String>();		//create HashSet of strings with the string representations of the puzzle states
		rootPuzzle.setPuzzleNodeVisited();	//mark the initial puzzle as visited
		visitedPuzzles.add(rootPuzzle.getPuzzleStateAsString());	//add the string form of the initial puzzle board to the hash set
		rootPuzzle.generateChildren();	//update the child list for the initial puzzleNode
		int currLevel = 0;	//used to track the depth with recursion
		int[][] goalState = rootPuzzle.getGoalState();	//get the goal state of the initial puzzle
		
		for(PuzzleNode pn : rootPuzzle.childrenPuzzles) //for each child of the initial puzzle...
		{
			try
			{	
				depthFirstHelper(pn, visitedPuzzles, currLevel, goalState);	//call the recursive helper for each child
			}
			catch(OutOfMemoryError memErr)	//catches the case when the method uses all the system's memory, and terminates the program. 
			{
				System.err.println("Program Terminating, BFS has used all memory avaliable");
				System.exit(0);
			}
		}
		
			//DFS will stop the program if it finds a solution. If we're here, it means no soln was found
			System.out.println("No solution found using DFS");
			System.out.println("DFS COMPLETE");
		
	}
	
	/**
	 * Helper method for DFS. This method will recursively call itself. Recursion for the particular PuzzleNod will stop when that state is 
	 * greater than or equal to the DEPTH_BOUND
	 * @param entPuzz - the puzzleNode which is being checked 
	 * @param entVisitedVals - the HashSet of visited puzzle states
	 * @param currLevel - the current depth of the search
	 * @param entGoalState - the goal state
	 */
	private static void depthFirstHelper(PuzzleNode entPuzz, HashSet<String> entVisitedVals, int currLevel, int[][] entGoalState)
	{
		if((currLevel <= DEPTH_BOUND) && !entVisitedVals.contains(entPuzz.getPuzzleStateAsString()) && !entPuzz.isVisited()) //if the current level is less than DEPTH_BOUND, hasn't been visited
		{																							//and this puzzle state isn't in the hash set (meaning is hasn't been checked)...
			entPuzz.setPuzzleNodeVisited();	//set this puzzleNode as visited	
			entVisitedVals.add(entPuzz.getPuzzleStateAsString());	//add this puzzleNode's state to the hash set
			entPuzz.generateChildren();	//update the child arrayList for this puzzleNode
			
			if(Arrays.deepEquals(entPuzz.getPuzzleStateArray(), entGoalState))	//if the current puzzleNode's state is the same as the goal state...
			{
				entPuzz.printPathToGoal();	//backtrack, then print the solution from the initial state to the goal
				System.out.println("DFS COMPLETE");
				System.exit(0); //terminate the program
			}
			
			for(PuzzleNode pn : entPuzz.childrenPuzzles) //for each child of this puzzleNode...
				depthFirstHelper(pn, entVisitedVals, currLevel++, entGoalState);	//recursively check for solutions in each child of this puzzleNode
		}
		else	//if this recursive call is greater than or equal to the DEPTH_BOUND or this puzzleNode's state has been visited, return
			return;

	}
}
