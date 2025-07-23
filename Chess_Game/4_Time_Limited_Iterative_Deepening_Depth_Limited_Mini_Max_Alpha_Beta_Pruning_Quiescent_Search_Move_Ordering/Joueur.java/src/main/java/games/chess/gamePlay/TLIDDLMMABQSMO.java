/**
 * 
 */
package games.chess.gamePlay;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Chandrashekar Akkenapally
 *
 */
public class TLIDDLMMABQSMO {
	
//	public static int TOTAL_TIME_AVAILABLE_IN_MILLISECS = 15 * 60 * 1000;
//	public static int TOTAL_MOVES = 40;
//	public static int TURN_TIME_MSECS = TOTAL_TIME_AVAILABLE_IN_MILLISECS / TOTAL_MOVES;
	
	public int minPlayer(ChessBoard chessboard,int depth, int alpha, int beta) {//The minPlayer side logic is being implemented in this function. This function takes the input chessboard,alpha, depth
		// and beta , and generates all the valid moves and its executed boards. Each move and its executed chessboard is send to max player function by flipping the side to play.
		//If the depth is zero it returns the heuristic value of that chessboard. 
		ArrayList<MoveBoard> moveAndBoards=new ArrayList<MoveBoard>();
		if(depth==0) { 
			return quiescentSearch(chessboard,alpha,beta);//Executing the QuiescentSearch
		}
		int value=Integer.MAX_VALUE;
		moveAndBoards=chessboard.generateMoveAndItsCorrespondingBoards(chessboard);
		Collections.sort(moveAndBoards, MoveBoard.BoardComparator);//We are implementing the Move Order by sorting the list of moves and its corresponding boards. 
		//The Logic of comparasion of moves is present in the comparator function of MoveBoard class.
		for(int i=0;i<moveAndBoards.size();i++) {
			ChessBoard tempChessBoard=new ChessBoard();
			tempChessBoard=moveAndBoards.get(i).chessboard.copyChessBoardObject();
			tempChessBoard.changePlayerSide();
			value=Math.min(value,maxPlayer(tempChessBoard, depth-1,alpha,beta));
			if(value<=alpha) {
				return value;
			}
			beta=Math.min(beta, value);
		}
		return value;
	}
	
	public int maxPlayer(ChessBoard chessboard,int depth,int alpha, int beta) {
		//The maxPlayer side logic is being implemented in this function. This function takes the input chessboard, alpha, beta
	    // and depth , and generates all the valid moves and its executed boards. Each move and its executed chessboard is send to min player function by flipping the side to play.
		//If the depth is zero it returns the heuristic value of that chessboard. 
		ArrayList<MoveBoard> moveAndBoards=new ArrayList<MoveBoard>();
		if(depth==0) {
			return quiescentSearch(chessboard,alpha,beta);//Executing the QuiescentSearch
		}
		int value=Integer.MIN_VALUE;
		moveAndBoards=chessboard.generateMoveAndItsCorrespondingBoards(chessboard);
		Collections.sort(moveAndBoards, MoveBoard.BoardComparator);//We are implementing the Move Order by sorting the list of moves and its corresponding boards. 
		//The Logic of comparasion of moves is present in the comparator function of MoveBoard class.
		for(int i=0;i<moveAndBoards.size();i++) {
			ChessBoard tempChessBoard=new ChessBoard();
			tempChessBoard=moveAndBoards.get(i).chessboard.copyChessBoardObject();
			tempChessBoard.changePlayerSide();
			value=Math.min(value,minPlayer(tempChessBoard, depth-1,alpha,beta));
			if(value>=beta) {
				return value;
			}
			alpha=Math.max(alpha, value);
		}
		return value;
	}
	
	public String search (ChessBoard chessboard,int depth) {
		//The search method takes the input as chessboard and depth and generates all the valid moves and its executed boards.  
		//Each move, depth, alpha, beta and its executed chessboard is send to min player function by flipping the side to play.
		ArrayList<MoveBoard> moveAndBoards=new ArrayList<MoveBoard>();
		int bestValue=Integer.MIN_VALUE;
		int alpha=Integer.MIN_VALUE;
		int beta=Integer.MAX_VALUE;
		String bestMove="",eachMove="";
		int value=0;
		moveAndBoards=chessboard.generateMoveAndItsCorrespondingBoards(chessboard);
		Collections.sort(moveAndBoards, MoveBoard.BoardComparator);//We are implementing the Move Order by sorting the list of moves and its corresponding boards. 
		//The Logic of comparasion of moves is present in the comparator function of MoveBoard class.
		for(int i=0;i<moveAndBoards.size();i++) {
			ChessBoard tempChessBoard=new ChessBoard();
			eachMove=moveAndBoards.get(i).move;
			tempChessBoard=moveAndBoards.get(i).chessboard.copyChessBoardObject();
			tempChessBoard.changePlayerSide();
			value=minPlayer(tempChessBoard, depth,alpha,beta);
			if(value> bestValue) {
				bestValue=value;
				bestMove=eachMove;
			}
		}
		return bestMove;
	}
	
	public int quiescentSearch(ChessBoard chessboard,int alpha,int beta) {//The method QuiescentSearch implements the Quiescent Search algorithm
		ChessBoardValuation cbv=new ChessBoardValuation();
		ArrayList<MoveBoard> moveAndBoards=new ArrayList<MoveBoard>();
		int BoardValue=cbv.HeuristicFunction(chessboard);
		if(BoardValue>=beta) {
			return beta;
		}
		if(alpha<BoardValue) {
			alpha=BoardValue;
		}
		moveAndBoards=chessboard.generateCaptureMovesandItsCorrespondingBoards(chessboard);
		Collections.sort(moveAndBoards, MoveBoard.BoardComparator);//We are implementing the Move Order by sorting the list of moves and its corresponding boards. 
		//The Logic of comparasion of moves is present in the comparator function of MoveBoard class. 
		for(int i=0;i<moveAndBoards.size();i++) {
			ChessBoard tempChessBoard=new ChessBoard();
			tempChessBoard=moveAndBoards.get(i).chessboard;
			tempChessBoard.changePlayerSide();
			int score=-quiescentSearch(tempChessBoard,-beta,-alpha);		
			if(score>=beta) {
				return beta;
			}
			if(score>alpha) {
				alpha=score;
			}
		}
		return alpha;
	}
	
	public String getBestMoveByTimeLimitedIterativeDeepeningDepthLimitedMiniMaxAlphaBetaPruningQuiescentSearchMoveOrdering(String fenString, double TOTAL_TIME_AVAILABLE_IN_MILLISECS ) {
		String move="";
		ChessBoard chessboard=new ChessBoard();
		int TOTAL_MOVES = 40;
		double TURN_TIME_MSECS = TOTAL_TIME_AVAILABLE_IN_MILLISECS / TOTAL_MOVES;
		//System.out.println(TURN_TIME_MSECS);
		chessboard.readFenString(fenString);
		double t0 = System.currentTimeMillis();
		//System.out.println(System.currentTimeMillis());
		int currentDepth=1;
		double t1, t2, predictedTime;
		String bestMove="";
		move=search(chessboard,0);
		t1=System.currentTimeMillis();
		
		do {
			move=search(chessboard, currentDepth);
			if(!move.equals("")){
				bestMove=move;
			}
			t2 = System.currentTimeMillis();
			double timeRatio=(t2-t1)/(t1-t0);
			predictedTime=(t2-t1)*timeRatio;
			currentDepth++;
			System.out.println(String.format("depth is %d", currentDepth));
			t1=t2;
		}while((t2+predictedTime)<(t0+TURN_TIME_MSECS));
		//System.out.println((t2)-(t0+TURN_TIME_MSECS));
		return chessboard.rawMoveToStandardMove(bestMove);
	}

}
