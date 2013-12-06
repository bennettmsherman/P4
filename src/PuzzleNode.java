/**
 * @author Ben Sherman, bms113
 * Case Western Reserve University
 * EECS 233 - Data Structures
 * This class is a PuzzleNode object. A PuzzleNode has a state (the puzzle board),
 * an ArrayList of children (other PuzzleNodes which are the results of the parent
 * PuzzleNode moving its blank), and a parent PuzzleNode, from which the node came from
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


public class PuzzleNode {
	
	private int[][] puzzleState; //PuzzleState/board in int[][] form
	private int blankRow; 	//Row where the blank is stored
	private int blankCol;	//Col where the blank is stored
	private int rcLength; //R and C will be equal length, this variable was added to prevent confusion
	ArrayList<PuzzleNode> childrenPuzzles = new ArrayList<PuzzleNode>();	//The children puzzles of this PuzzleNode. Each child represents another way 
																			//movement of the blank. It isn't private for convenience
	private PuzzleNode parentP;	//Parent PuzzleNode of this puzzleNode
	private static Random randomGen = new Random();	
	private boolean visited = false;	//Bool to show whether or not this node has been visited in a search
	private String movementType = "";	//String which states what movement was done to create this puzzleNode
	
	
	/**
	 * PuzzleNode constructor to create a new PuzzleNode from a specified state, and with a specified parent
	 * @param entState	- int[][] which will be the starting state of the new puzzle
	 * @param entParent - the parent of this puzzleNode. When using this constructor, use the value null for this parameter (unless you
	 * want to test specifying the parent). This parameter is not null for the solving methods, as well in the child puzzleNodes which
	 * stem from a given puzzleNode. 
	 * @param entRandomSteps - number of random steps to do on the entered state/puzzle
	 */
	public PuzzleNode(int[][] entState, PuzzleNode entParent, int entRandomSteps)
	{
		rcLength = entState.length; //both r and c should be the same	
		try
		{
			puzzleState = arrayClone(entState);
		}
		catch(IndexOutOfBoundsException arrayError)	//will catch if there is an index out of bounds error. This means the rows and cols weren't equal, and will cause termination
		{
			System.err.println("Entered array error. Rows and cols aren't equal length.\nProgram Terminating");
			System.exit(0);
		}
		parentP = entParent; //set the parent
		findAndUpdateBlankPosition(); //find the blank, update the instances
		randomize(entRandomSteps);	//randomize
	}
	
	/**
	 * PuzzleNode constructor to create a default PuzzleNode with a given size, and with specified amount of random steps.
	 * The default puzzle is the goal state for that grid. EX for a 3x3:
	 *  0 1 2
	 *  3 4 5
	 *  6 7 8
	 * If entPuzzleSize is not (PerfectSquare - 1), it will form a grid of the next fitting size. So, grid configs will be
	 * 2x2, 3x3, 4x4 etc. "8" will create an 8 puzzle with grid size 3x3. "9" will create a 4x4 grid. 
	 * @param entPuzzleSize - an int to determing the grid size. See explanation above.
	 * @param entRandomSteps - number of random movements of the blank on the default puzzle. 
	 */
	public PuzzleNode(int entPuzzleSize, int entRandomSteps)
	{
		parentP = null;
		blankRow = 0; //initialize the row and col location of blank to 0,0
		blankCol = 0;
		rcLength = (int) (Math.sqrt(entPuzzleSize) + 1); //get size of row and col for grid
		puzzleState = new int[rcLength][rcLength];	//initialize the puzzleState array to proper size
		populateDefaultState();	//fill the array with the default config
		randomize(entRandomSteps);	//randomize
	}
	
	
	/**
	 * Finds the blank space (represented by an "0"), and updates the 
	 * location instances with this position. Program will terminate 
	 * if the entered grid has no blanks
	 */
	public void findAndUpdateBlankPosition()
	{
		boolean updatedBlank = false; //bool to confirm if blank exists/has been moved
		for(int row = 0; row < rcLength; row++)	//traverses entire array
		{
			for(int col = 0; col < rcLength; col++)
			{
				if(puzzleState[row][col] == 0)	//if blank found, update the location instances
				{
					blankRow = row;
					blankCol = col;
					updatedBlank = true;
				}
			}
		}
		if(!updatedBlank)	//if blank wasn't updated, it means there is no blank in the array. Print error and terminate
		{
			System.err.println("THE GRID IS INVALID. TERMINATING");
			System.exit(0);
		}
	}
	
	
	/**
	 * This method creates a cloned version of the array passed in as a param.
	 * @param entArr - the array to clone
	 * @return - a copy of the entered array
	 */
	public int[][] arrayClone(int[][] entArr)
	{
		int[][] temp = new int[entArr.length][entArr[0].length]; //temp array to store values from array to clone
		for(int row = 0; row < entArr.length; row++)
		{
			for(int col = 0; col < entArr[0].length; col++)
			{
				temp[row][col] = entArr[row][col];	//updates temp array
			}
		}
		return temp;
	}
	
	/**
	 * Prints the current state/config of the puzzle to the console.
	 * Prints "|" between numbers for clarity
	 */
	public void printPuzzleState()
	{
		for(int row = 0; row < rcLength; row++) //Row
		{
			for(int col = 0; col < rcLength; col++) //Col
				System.out.print("|" + puzzleState[row][col] + "|"); // "|num|"
			System.out.println();
		}
	}
	
	/**
	 * Fills the puzzleState array with the default puzzle values,
	 * which is the goal state. For a 3x3, default state is:
	 * 0 1 2
	 * 3 4 5
	 * 6 7 8
	 */
	public void populateDefaultState()
	{
		int currentNum = 0;	//will be value inserted into puzzleState array
		for(int row = 0; row < rcLength; row++)	//traverses both dimensions and fills them starting with 0 (blank)
		{
			for(int col = 0; col < rcLength; col++)
			{
				puzzleState[row][col] = currentNum;
				currentNum++;
			}
		}
	}
	
	/**
	 * Gets the goal state for the puzzle of this size. 
	 * @return goal state for this size puzzle in int[][] form
	 */
	public int[][] getGoalState()
	{
		int currentNum = 0; //will be the value inserted into the goalState array
		int[][] goalState = new int[rcLength][rcLength];	//goalstate int[][]
		for(int row = 0; row < rcLength; row++) //traverses both dimensions and fills them starting with 0 (blank)
		{
			for(int col = 0; col < rcLength; col++)
			{
				goalState[row][col] = currentNum;
				currentNum++;
			}
		}
		return goalState;
	}
	
	/**
	 * Used to find the positions where the blank can move to. The possivblePos boolean array
	 * contains 4 indicies. 0 represents left, 1 represents right, 2 represents up, 3 represents down.
	 * A true value means the the blank can move in this direction, and false means the blank cannot move in that direction
	 * @return an array of positions where the blank can move
	 */
	public boolean[] getMovableBlankPositions()
	{
		boolean[] possiblePos = {true, true, true, true}; //left,right,up,down
		
		//left check
		if(blankCol-1 < 0)
			possiblePos[0] = false;
		
		//right check
		if(blankCol + 1 >= rcLength)
			possiblePos[1] = false;
		
		//up check
		if(blankRow - 1 < 0)
			possiblePos[2] = false;
		
		//down check
		if(blankRow + 1 >= rcLength)
			possiblePos[3] = false;

		return possiblePos;
	}

	/**
	 * Method to move the blank, and swap it with the number in the position it is moving to.
	 * Position to move to is an integer, where 0 = left, 1 = right, 2 = up, 3 = down
	 * Calls the getMovableBlankPositions() method to see if the entered position can be moved to. 
	 * Also saves the direction the blank was moved. 
	 * @param whichDirectionToMove where 0 = left, 1 = right, 2 = up, 3 = down
	 * @return a boolean that says if the move was successful
	 */
	public boolean moveBlank(int whichDirectionToMove)
	{
		//0 = left, 1 = right, 2 = up, 3 = down
		boolean[] placesToMove = getMovableBlankPositions();
		
		if(whichDirectionToMove > 3) //catches the issue when the blank move position is invalid
		{
			System.err.println("Can't move blank to this position. Value must be between 0 and 3");
			return false;
		}
		
		else if(placesToMove[whichDirectionToMove] == false) //if the blank can't be moved to the entered position, return false
			return false;
		
		switch(whichDirectionToMove)	//switch to make the movements from int values
		{
			case 0:	//left
			{
				int temp = puzzleState[blankRow][blankCol - 1];	//these actions swap the blank, and update the row and col when necessary
				puzzleState[blankRow][blankCol - 1] = 0;
				puzzleState[blankRow][blankCol] = temp;
				blankCol--;
				movementType = "move blank left";
				break;
			}
			case 1: //right
			{
				int temp = puzzleState[blankRow][blankCol + 1];
				puzzleState[blankRow][blankCol + 1] = 0;
				puzzleState[blankRow][blankCol] = temp;
				blankCol++;
				movementType = "move blank right";
				break;
			}
			case 2: //up
			{
				int temp = puzzleState[blankRow - 1][blankCol];
				puzzleState[blankRow - 1][blankCol] = 0;
				puzzleState[blankRow][blankCol] = temp;
				blankRow--;
				movementType = "move blank up";
				break;
			}
			case 3: //down
			{
				int temp = puzzleState[blankRow + 1][blankCol];
				puzzleState[blankRow + 1][blankCol] = 0;
				puzzleState[blankRow][blankCol] = temp;
				blankRow++;
				movementType = "move blank down";
				break;
			}	
		}		
		return true;	
	}
	
	/**
	 * Randomizes the puzzle. This method moves the blank to random positions
	 * a given amount of times
	 * @param entRandomSteps - the amount of times to move the blank
	 */
	public void randomize(int entRandomSteps)
	{
		int randomStepsMade = 0;
		while(randomStepsMade < entRandomSteps)	//Will run until entRandomSteps moves have been made
		{
			if(moveBlank(randomGen.nextInt(4)) == true) //generate random int from 0-3 (values that represent movements). If moveBlank returns false, 
			{											//the amount of moved states will not be updated
				randomStepsMade++;
				if(randomStepsMade == entRandomSteps)
				{
					System.out.println("Randomizations Completed: " + randomStepsMade);
					printPuzzleState();
				}
			}
		}
	}
	
	
	/**
	 * This method will find all the positions where the blank can move to, and will
	 * create a new puzzleNode for each movement. It will not randomize the new puzzleNode,
	 * and will set its parent to the calling PuzzleNode. The new puzzleNodes will be stored
	 * in an arrayList of puzzleNodes
	 */
	public void generateChildren()
	{
		for(int i = 0; i < 4; i++) //moveBlank works with vals 0-3 (All the possible movements)
		{
			PuzzleNode temp = new PuzzleNode(puzzleState, this, 0);	//create a new PuzzleNode with the same state as the parent, as well as set the parent
			if(temp.moveBlank(i) == true)
				childrenPuzzles.add(temp);	//add the new PuzzleState to the childrenPuzzles list
		}
	}
	
	/**
	 * @return the int[][] puzzleState array. The puzzle in int[][] form.
	 */
	public int[][] getPuzzleStateArray()
	{
		return puzzleState;
	}
	
	/**
	 * Prints the path to the final state of the puzzle, from the pre-solved state.
	 * It will show the puzzle at each step, as well as the movement done at each step.
	 * Uses a stack for efficiency and convenience. 
	 */
	public void printPathToGoal()
	{
		Stack<PuzzleNode> pathToRoot = new Stack<PuzzleNode>(); //stack to store nodes
		PuzzleNode trav = this;
		while(trav != null)
		{
			pathToRoot.add(trav);	//Traverses from the bottom up, so stack will have the goalState at the bottom, start state at the top
			trav = trav.parentP;
		}
		
		System.out.println("\nPATH FROM INITIAL STATE TO GOAL STATE:");
		int moveCounter = 1; 	//counts the number of moves from initial to goal state
		
		while(!pathToRoot.isEmpty())	//pops the stack (movements will be in order from start state to goal state)
		{
			System.out.println("Operation at this step: " + pathToRoot.peek().movementType);
			System.out.println("Move: " + moveCounter++);
			pathToRoot.pop().printPuzzleState();
			System.out.println();
		}
		
		System.out.println("End Path Print");
	}
	
	/**
	 * Sets the visited instance to true, use for BFS and DFS
	 */
	public void setPuzzleNodeVisited()
	{
		visited = true;
	}
	
	/**
	 * @return - visited, whether or not this node has been visited already
	 */
	public boolean isVisited()
	{
		return visited;
	}
	
	/**
	 * Uses a stringbuilder to convert the puzzleboard into a string. Appends the numbers in the board
	 * from left to right, top to bottom. 
	 * @return the puzzle board in string form. Used with a hash table in BFS and DFS
	 */
	public String getPuzzleStateAsString()
	{
		StringBuilder sBuilder = new StringBuilder();
		for(int row = 0; row < rcLength; row++)
		{
			for(int col = 0; col < rcLength; col++)
			{
				sBuilder.append(puzzleState[row][col]); 	//add the value to the string builder
			}
		}
		return sBuilder.toString();
	}
}
