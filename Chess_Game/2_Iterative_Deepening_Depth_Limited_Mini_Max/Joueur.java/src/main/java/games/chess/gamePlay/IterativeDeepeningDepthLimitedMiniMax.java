/**
 * 
 */
package games.chess.gamePlay;

import java.util.ArrayList;

/**
 * @author Chandrashekar Akkenapally
 *
 */
public class IterativeDeepeningDepthLimitedMiniMax {
	
	public int minPlayer(ChessBoard chessboard,int depth) {//The minPlayer side logic is being implemented in this function. This function takes the input chessboard
		// and depth , and generates all the valid moves and its executed boards. Each move and its executed chessboard is send to max player function by flipping the side to play.
		//If the depth is zero it returns the heuristic value of that chessboard. 
		ArrayList<MoveBoard> moveAndBoards=new ArrayList<MoveBoard>();
		
		int returnvalue=0,value=0;
		if(depth==0) { 
			return chessboard.HeuristicFunction();
		}
		int bestMinValue=Integer.MAX_VALUE;
		moveAndBoards=chessboard.generateMoveAndItsCorrespondingBoards(chessboard);
		for(int i=0;i<moveAndBoards.size();i++) {
			ChessBoard tempChessBoard=new ChessBoard();
			tempChessBoard=moveAndBoards.get(i).chessboard.copyChessBoardObject();
			tempChessBoard.changePlayerSide();
			value=maxPlayer(tempChessBoard, depth-1);
			if(bestMinValue>value) {
				bestMinValue=value;
			}
		}
		return returnvalue;
	}
	
	public int maxPlayer(ChessBoard chessboard,int depth) {
		//The maxPlayer side logic is being implemented in this function. This function takes the input chessboard
	    // and depth , and generates all the valid moves and its executed boards. Each move and its executed chessboard is send to min player function by flipping the side to play.
		//If the depth is zero it returns the heuristic value of that chessboard. 
		ArrayList<MoveBoard> moveAndBoards=new ArrayList<MoveBoard>();
		
		int returnvalue=0,value=0;
		if(depth==0) {
			return chessboard.HeuristicFunction();
		}
		int bestMaxValue=Integer.MIN_VALUE;
		moveAndBoards=chessboard.generateMoveAndItsCorrespondingBoards(chessboard);
		for(int i=0;i<moveAndBoards.size();i++) {
			ChessBoard tempChessBoard=new ChessBoard();
			tempChessBoard=moveAndBoards.get(i).chessboard.copyChessBoardObject();
			tempChessBoard.changePlayerSide();
			value=minPlayer(tempChessBoard, depth-1);
			if(bestMaxValue<value) {
				bestMaxValue=value;
			}
		}
		return returnvalue;
	}
	
	public String maxChoice(ChessBoard chessboard, int depth) {
		//This function takes the input as chessboard and depth , and generates all the valid moves and its executed boards.
	    //  Each move and its executed chessboard is send to min player function by flipping the side to play.
		//With this it will calculate the best maximum heuristic value of  move and returns that best move.
		ArrayList<MoveBoard> moveAndBoards=new ArrayList<MoveBoard>();
		ChessBoard tempChessBoard=new ChessBoard();
		int bestMaxValue=Integer.MIN_VALUE;
		String bestMove="";
		String eachMove="";
		int value=0;
		moveAndBoards=chessboard.generateMoveAndItsCorrespondingBoards(chessboard);
		for(int i=0;i<moveAndBoards.size();i++) {
			eachMove=moveAndBoards.get(i).move;
			tempChessBoard=moveAndBoards.get(i).chessboard;
			tempChessBoard.changePlayerSide();
			value=minPlayer(tempChessBoard,depth-1);
			if(bestMaxValue<value) {
				bestMaxValue=value;
				bestMove=eachMove;
			}
		}
		return bestMove;
	}

	public String getBestMoveByIterativeDeepeningDepthLimitedMiniMax(String fenString) {
		//This functions gets the best move by iterative deepening depth limited mini max considering certain depth limit. 
		ChessBoard chessboard=new ChessBoard();
		int depth_limit= 3;
		int depth=1;
		String move="";
		String bestMove="";
    	chessboard.readFenString(fenString);
    	while(depth<(depth_limit+1)) {
    		move=maxChoice(chessboard,depth);
			if(!move.equals("")) {
    			bestMove=move;
    		}
    		depth++;
    	}
		return chessboard.rawMoveToStandardMove(bestMove);
	}
	
}
