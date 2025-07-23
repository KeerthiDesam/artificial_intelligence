/**
 * 
 */
package games.chess.gamePlay;

/**
 * @author Chandrashekar Akkenapally
 *
 */
public class ChessBoardValuation {
	//The below are the chessboard valuation tables for each chess piece type
	static int[][] pawnTable= {
			{0,  0,  0,  0,  0,  0,  0,  0},
		    {50, 50, 50, 50, 50, 50, 50, 50},
		    {10, 10, 20, 30, 30, 20, 10, 10},
		     {5,  5, 10, 27, 27, 10,  5,  5},
		     {0,  0,  0, 25, 25,  0,  0,  0},
		     {5, -5,-10,  0,  0,-10, -5,  5},
		     {5, 10, 10,-25,-25, 10, 10,  5},
		     {0,  0,  0,  0,  0,  0,  0,  0}
	};
	
	static int[][] knightTable= {
			{-50,-40,-30,-30,-30,-30,-40,-50},
		    {-40,-20,  0,  0,  0,  0,-20,-40},
		    {-30,  0, 10, 15, 15, 10,  0,-30},
		    {-30,  5, 15, 20, 20, 15,  5,-30},
		    {-30,  0, 15, 20, 20, 15,  0,-30},
		    {-30,  5, 10, 15, 15, 10,  5,-30},
		    {-40,-20,  0,  5,  5,  0,-20,-40},
		    {-50,-40,-20,-30,-30,-20,-40,-50}
	};
	
	static int[][] bishopTable= {
			{-20,-10,-10,-10,-10,-10,-10,-20},
		    {-10,  0,  0,  0,  0,  0,  0,-10},
		    {-10,  0,  5, 10, 10,  5,  0,-10},
		    {-10,  5,  5, 10, 10,  5,  5,-10},
		    {-10,  0, 10, 10, 10, 10,  0,-10},
		    {-10, 10, 10, 10, 10, 10, 10,-10},
		    {-10,  5,  0,  0,  0,  0,  5,-10},
		    {-20,-10,-40,-10,-10,-40,-10,-20}
	};
	
	static int[][] rookTable= {
			{32, 42, 32, 51, 63, 9, 31, 43},
			{27, 32, 58, 62, 80, 67, 26, 44},
			{-5, 19, 26, 36, 17, 45, 61, 16},
			{-24, -11, 7, 26, 24, 35, -8, -20},
			{-36, -26, -12, -1, 9, -7, 6, -23},
			{-45, -25, -16, -17, 3, 0, -5, -33},
			{-44, -16, -20, -9, -1, 11, -6, -71},
			{-19, -13, 1, 17, 16, 7, -37, -26}
	};
	
	static int[][] queenTable= {
			{-28, 0, 29, 12, 59, 44, 43, 45},
			{-24, -39, -5, 1, -16, 57, 28, 54},
			{-13, -17, 7, 8, 29, 56, 47, 57},
			{-27, -27, -16, -16, -1, 17, -2, 1},
			{-9, -26, -9, -10, -2, -4, 3, -3},
			{-14, 2, -11, -2, -5, 2, 14, 5},
			{-35, -8, 11, 2, 8, 15, -3, 1},
			{-1, -18, -9, 10, -15, -25, -31, -50}	
	};
	
	static int[][] kingTable= {
			  {-30, -40, -40, -50, -50, -40, -40, -30},
			  {-30, -40, -40, -50, -50, -40, -40, -30},
			  {-30, -40, -40, -50, -50, -40, -40, -30},
			  {-30, -40, -40, -50, -50, -40, -40, -30},
			  {-20, -30, -30, -40, -40, -30, -30, -20},
			  {-10, -20, -20, -20, -20, -20, -20, -10}, 
			  {20,  20,   0,   0,   0,   0,  20,  20},
			  {20,  30,  10,   0,   0,  10,  30,  20}
	};
	
	static int[][] kingTableEndGame= {
			 {-50,-40,-30,-20,-20,-30,-40,-50},
			 {-30,-20,-10,  0,  0,-10,-20,-30},
			 {-30,-10, 20, 30, 30, 20,-10,-30},
			 {-30,-10, 30, 40, 40, 30,-10,-30},
			 {-30,-10, 30, 40, 40, 30,-10,-30},
			 {-30,-10, 20, 30, 30, 20,-10,-30},
			 {-30,-30,  0,  0,  0,  0,-30,-30},
			 {-50,-30,-30,-30,-30,-30,-30,-50}
	};
	
	public int HeuristicFunction(ChessBoard chessboard) {//The Heuristic Function helps to calculate the Heuristic value of the chessboard.
		//In chessboard each piece is having a value. The sum of the values of all pieces is the heuristic value. 
      	int HeuristicValue1= 0;
    	int HeuristicValue2= 0;
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				if((chessboard.sideToMove=='w')&&(Character.isUpperCase(chessboard.gameBoard[i][j]))) {
					HeuristicValue1=HeuristicValue1+getChessPieceValue(chessboard.gameBoard[i][j])*getChessPiecePositionValue((chessboard.gameBoard[i][j]),i,j);
				}
				else if((chessboard.sideToMove=='b')&&(Character.isLowerCase(chessboard.gameBoard[i][j]))) {
					HeuristicValue2=HeuristicValue2+getChessPieceValue(chessboard.gameBoard[i][j])*getChessPiecePositionValue((chessboard.gameBoard[i][j]),i,j);
				}
			}
		}
		
		HeuristicValue1=HeuristicValue1+chessboard.whitePawanPromotionCount*80+chessboard.blackKingCheckCount*1000+chessboard.whiteKillBlackCount*120;
		HeuristicValue2=HeuristicValue2+chessboard.blackPawanPromotionCount*80+chessboard.whiteKingCheckCount*1000+chessboard.blackKillWhiteCount*120;
		if(chessboard.sideToMove=='b'){
			if(chessboard.executedMove.contains("k")) {//If the move is of kings then I'm trying to give the least value so that king moves wont be picked at initial stages
				HeuristicValue2=HeuristicValue2-1000;
			}
			return HeuristicValue2-HeuristicValue1;
		}
		if(chessboard.executedMove.contains("K")) {//If the move is of kings then I'm trying to give the least value so that king moves wont be picked at initial stages
			HeuristicValue1=HeuristicValue1-1000;
		}
		return HeuristicValue1-HeuristicValue2;
		
	}
	
	public int getChessPiecePositionValue(char ch,int i, int j) {//This method helps to use the a corresponding chessboard valuation table for each chess piece type.
		if(Character.toUpperCase(ch)=='K') {
			if(((ch=='K')&&(i!=0)&&(j!=4))||((ch=='k')&&(i!=7)&&(j!=4))) {//If king position is not according to initial game state then I will using end game type chessboard for king.
				return kingTableEndGame[i][j];
			}
			return kingTable[i][j];
		}
		else if(Character.toUpperCase(ch)=='Q') {
			return queenTable[i][j];
		}
		else if(Character.toUpperCase(ch)=='R') {
			return rookTable[i][j];
		}
		else if(Character.toUpperCase(ch)=='B') {
			return bishopTable[i][j];
		}
		else if(Character.toUpperCase(ch)=='N') {
			return knightTable[i][j];
		}
		else if(Character.toUpperCase(ch)=='P') {
			return pawnTable[i][j];
		}
		return 0; 
	}
	
	public int getChessPieceValue(char ch) {//The method gives value of each chess piece type. 
		String s="pnbrqk";
		int val[]= {1,3,4,5,9,10};
		int n=s.indexOf(String.valueOf(Character.toLowerCase(ch)));
		return val[n];
	}

}
