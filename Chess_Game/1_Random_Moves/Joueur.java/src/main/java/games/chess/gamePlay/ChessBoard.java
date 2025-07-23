package games.chess.gamePlay;

/**
 * 
 */

import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

/**
 * @author Chandrashekar Akkenapally
 *
 */
class MoveBoard{
	public ChessBoard chessboard=new ChessBoard();
	public String move="";
}


public class ChessBoard {
	char[][] gameBoard = new char[8][8]; //This is a character array used to store the gameboard.
	String fenString = ""; //This is used to store the fenString
	char sideToMove; //This is used to store the which player side we have to execute the move b or w
	String castlingAbility = "";//This variable is used to store castlingAbility of black and white along with their respective side.
	String enPassantTargetSquare = "";//This variable is used to store the enPassant Target Square.
	int halfMoveCounter = 0; //This variable is used to store halfMoveCounter.
	int fullMoveCounter = 0;  //This variable is used to store fullMoveCounter.
	String move = ""; //This variable is used to store intermediate move path.
	char touchPiece; //This variable is used to store the chess piece on which we are performing operations. 
	int[][] position = new int[1][2]; //This array is used to store position of the above chess piece on which we are performing operations.
	int[][] kingPosition = new int[1][2]; //This array is used to store kings position.
	ArrayList<String> blackValidMoves = new ArrayList<String>(); //This array of strings is used to store all the valid moves of black chess pieces.
	ArrayList<String> whiteValidMoves = new ArrayList<String>(); //This array is used to store all the valid moves of white chess pieces.
	static int[][] directions = { { 1, -1 }, { 1, 0 }, { 1, 1 }, { 0, -1 }, { -1, -1 }, { -1, 0 }, { -1, 1 },
			{ 0, 1 } };//The directions is a static array which stores all the possible directions of move in a chessboard.
	static char[] boardColumn = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };//This character array is used to represent actual move or standard notation of chess piece move.
	// ArrayList<String> kingCheckPath = new ArrayList<String>();

	//The readFenString methods helps to read the input fenString from the server.
	public void readFenString(String FenString) {
		
		this.fenString = FenString;
		String[] temp = this.fenString.split(" ");
		this.sideToMove = temp[1].charAt(0);
		this.castlingAbility = temp[2];
		this.enPassantTargetSquare = temp[3];
		this.halfMoveCounter = Integer.parseInt(temp[4]);
		this.fullMoveCounter = Integer.parseInt(temp[5]);
		this.generateChessBoard(temp[0]);

	}
    
	//The generateChessBoard method helps to place all the chess pieces in the gameboard character array.
	public void generateChessBoard(String board) {
		String[] tempboard = board.split("/");

		for (int i = 0, k = tempboard.length - 1; i < tempboard.length && k >= 0; i++, k--) {
			int j = 0, m = 0;
			while (j < tempboard[k].length()) {
				if (Character.isAlphabetic(tempboard[k].charAt(j)) == true) {
					this.gameBoard[i][m] = tempboard[k].charAt(j);
					j++;
					m++;
				} else {
					m = m + Integer.parseInt(String.valueOf(tempboard[k].charAt(j)));
					j++;
				}
			}
		}
	}

	//The copyChessBoardObject is a method which helps to copy the parameters of current ChessBoard Object to a new ChessBoard Object.
	public ChessBoard copyChessBoardObject() {
		ChessBoard chessboard = new ChessBoard();
		chessboard.fenString = this.fenString;
		chessboard.sideToMove = this.sideToMove;
		chessboard.castlingAbility = this.castlingAbility;
		chessboard.enPassantTargetSquare = this.enPassantTargetSquare;
		chessboard.halfMoveCounter = this.halfMoveCounter;
		chessboard.fullMoveCounter = this.fullMoveCounter;
		chessboard.blackValidMoves.clear();
		chessboard.whiteValidMoves.clear();
		// chessboard.blackValidMoves.addAll(this.blackValidMoves);
		// chessboard.whiteValidMoves.addAll(this.whiteValidMoves);
		// chessboard.kingCheckPath.clear();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				chessboard.gameBoard[i][j] = this.gameBoard[i][j];
			}
		}
		return chessboard;
	}

	//This method helps generateValidMoves for all the chess pieces.
	public void generateValidMoves() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				this.touchPiece = this.gameBoard[i][j];//We are storing the current chess piece in which we are generating valid moves.
				this.position[0][0] = i;//We are storing row position of the current touchPiece.
				this.position[0][1] = j;//We are storing column position of the current touchPiece.
				if (Character.isUpperCase(this.gameBoard[i][j])) {//If the selected touchPiece is uppercase then we are storing all its valid moves
					this.whiteValidMoves.addAll(generateChessPieceMoves(this.gameBoard[i][j]));// in all white pieces valid moves.
				}
				if (Character.isLowerCase(this.gameBoard[i][j])) {//If the selected touchPiece is lowercase then we are storing all its valid moves
					this.blackValidMoves.addAll(generateChessPieceMoves(this.gameBoard[i][j]));// in all black pieces valid moves.
				}
				if ((this.gameBoard[i][j] == 'k' && this.sideToMove == 'b')//If the current touchPiece is either k or K we are storing its position
						|| (this.gameBoard[i][j] == 'K' && this.sideToMove == 'w')) {//based on current side to move.
					this.kingPosition[0][0] = i;
					this.kingPosition[0][1] = j;
				}
			}
		}
		//System.out.println(String.format("This enPassantTargetSquare %s and %b",this.enPassantTargetSquare,(!this.enPassantTargetSquare.equals("-")) ));
		if ((this.sideToMove == 'w')&&(!this.enPassantTargetSquare.equals("-"))) {
			this.whiteValidMoves.addAll(checkEnpassantMoves());//We are adding all the white Enpassant moves to white valid moves.
		}
		if((this.sideToMove == 'w')&&(!this.castlingAbility.equals("-"))){
			this.whiteValidMoves.addAll(checkCastling());//We are adding all the white castling moves to white valid moves.
		}
		if ((this.sideToMove == 'b')&&(!this.enPassantTargetSquare.equals("-"))) {
			this.blackValidMoves.addAll(checkEnpassantMoves());//We are adding all the black Enpassant moves to black valid moves.
		}
		if((this.sideToMove == 'b')&&(!this.castlingAbility.equals("-"))){
			this.blackValidMoves.addAll(checkCastling());//We are adding all the black castling moves to black valid moves.
		}
	}

	public ArrayList<String> generateChessPieceMoves(char piece) {//The generateChessPieceMoves method helps to generate all the piece valid moves for the current
		ArrayList<String> pieceValidMoves = new ArrayList<String>();//selected chess piece.
		if (Character.toUpperCase(piece) == 'P') { 
			pieceValidMoves.addAll(generatePawnMoves());//All the generated valid moves for Pawn is added to the list
		}
		if (Character.toUpperCase(piece) == 'N') {
			pieceValidMoves.addAll(generateKnightMoves());//All the generated valid moves for Knight is added to the list
		}
		if (Character.toUpperCase(piece) == 'B') {
			pieceValidMoves.addAll(generateBishopMoves());//All the generated valid moves for Bishop is added to the list
		}
		if (Character.toUpperCase(piece) == 'R') {
			pieceValidMoves.addAll(generateRookMoves());//All the generated valid moves for Rook is added to the list
		}
		if (Character.toUpperCase(piece) == 'Q') {
			pieceValidMoves.addAll(generateQueenMoves());//All the generated valid moves for Queen is added to the list
		}
		if (Character.toUpperCase(piece) == 'K') {
			pieceValidMoves.addAll(generateKingMoves());//All the generated valid moves for King is added to the list
		}
		return pieceValidMoves;
	}

	public ArrayList<String> generateKingMoves() {//Generating all the valid moves for king.
		ArrayList<String> kingValidMoves = new ArrayList<String>();
		int row = 0, col = 0;
		for (int i = 0; i < directions.length; i++) {//Here we are checking step of all possible directions of move for king
			row = this.position[0][0] + directions[i][0];
			col = this.position[0][1] + directions[i][1];
			if (row >= 0 && row < 8 && col >= 0 && col < 8) {//Checking the boundary conditions
				if (checkCondition(row, col) == 0 || checkCondition(row, col) == 1) {//In the checkCondition method we are check whether the corresponding move is valid or not
					this.move = "0_" + String.valueOf(this.touchPiece) + "_" + Integer.toString(this.position[0][0])//The normal moves are stored in the format 0_P_34_44 where 0 indicates normal move and 34 indicates the source and 44 indicates the destination 
							+ Integer.toString(this.position[0][1]) + "_" + Integer.toString(row)
							+ Integer.toString(col);
					kingValidMoves.add(this.move);//all every valid move to kingValidMoves array list.
				}
			}
		}
		return kingValidMoves;
	}

	public ArrayList<String> checkCastling() {//Checking and Generating all possible castling moves
		ArrayList<String> kingValidMoves = new ArrayList<String>();//Array list to store the valid castling moves of king
		String[][] castling = { { "bk", "74", "76", "77", "75" }, { "bq", "74", "72", "70", "73" },//This String array stores all possible combinations of castling, kings source and destination location and corresponding rook's source and destination location 
				{ "wK", "04", "06", "07", "05" }, { "wQ", "04", "02", "00", "03" } }; //For example in first row we have bk which indicates black side move and king side castling followed by in the next column its source and its destination and in the third column(starting with 0) we have
		//the king side rooks move from source to destination. 
		String kingPosition = "" + String.valueOf(this.kingPosition[0][0]) + String.valueOf(this.kingPosition[0][1]);
		if ((!(this.castlingAbility.contains("-"))) && (!verifyKingCheckStatus(kingPosition))) {//We are checking whether there is castling ability is true or false and also whether king is in check in current position.
			for (int i = 0; i < this.castlingAbility.length(); i++) {
				String s = String.valueOf(this.sideToMove) + String.valueOf(castlingAbility.charAt(i));//concatinating the sidetomove with the all the characters in castlingAbility string.
				for (int j = 0; j < castling.length; j++) {
					if (s.equals(castling[j][0])) {//Comparing the concatinated string with the first row first col of the castling string array.
						this.move = "";//Clearing the move path.
					    int temp=(Integer.parseInt(String.valueOf(castling[j][2].charAt(1)))-Integer.parseInt(String.valueOf(castling[j][1].charAt(1))))/2;//The below three steps are to fine intermediate step when king moves as part of castling.
					    temp=Integer.parseInt(String.valueOf(castling[j][1].charAt(1)))+temp;//We will then check the king check condition on this intermediate step also. 
					    String itpath=""+castling[j][1].charAt(0)+String.valueOf(temp);
						if (j < 2 && (!verifyKingCheckStatus(castling[j][2]))
								&& (checkCastlingPath(castling[j][1], castling[j][3]))&&(!verifyKingCheckStatus(String.valueOf(itpath)))) {//Here we are checking for blacks whether the black king is in check or not after the castling is done and whether the corresponding castling path is empty or not
							this.move = "1_k_" + castling[j][1] + "_" + castling[j][2] + "_r_" + castling[j][3] + "_"
									+ castling[j][4];
						}
						if (j > 1 && (!verifyKingCheckStatus(castling[j][2]))
								&& (checkCastlingPath(castling[j][1], castling[j][3]))&&(!verifyKingCheckStatus(String.valueOf(itpath)))) {//Here we are checking for white whether the white king is in check or not after the castling is done and whether the corresponding castling path is empty or not
							this.move = "1_K_" + castling[j][1] + "_" + castling[j][2] + "_R_" + castling[j][3] + "_"
									+ castling[j][4];
						}
						if (this.move != "") {
							kingValidMoves.add(this.move);
						}
					}
				}
			}
		}
		return kingValidMoves;
	}
	
	public boolean checkCastlingPath(String start, String end) {//Here we are checking whether castling path is empty or not
		boolean path = false;
		int from = Integer.parseInt(start);//Here we are converting the king start location from string format to Integer format
		int to = Integer.parseInt(end);//Here we are converting the king's destination location from string format to Integer format
		if (from > to) {//Here we are swapping the numbers source location and destination location  so that from will be smaller and to will be greater
			int temp = from;
			from = to;
			to = temp;
		}
		int count = 0;
		for (int i = from + 1; i < to; i++) {//from fromvalue to tovalue we are checking whether all the intermediate paths are empty or not
			if (this.gameBoard[i / 10][i % 10] == '\0') { 
				count++;//We are incrementing the count that is the number of empty spaces between the from and to location
			}
		}
		if (count == (to - from - 1)) {//If the count is equals the number of empty spaces then we are returning true.
			path = true;
		}
		return path;
	}

	public boolean verifyKingCheckStatus(String location) {//Here we are checking whether the king is in check status or not
		ArrayList<String> oppositeTeamValidMoves = new ArrayList<String>();
		if (this.sideToMove == 'w') {//If the side to move is white then we are copying all the valid moves of black into oppositeTeamValidMoves
			oppositeTeamValidMoves.addAll(this.blackValidMoves);
		}
		if (this.sideToMove == 'b') {//If the side to move is black then we are copying all the valid moves of white into oppositeTeamValidMoves
			oppositeTeamValidMoves.addAll(this.whiteValidMoves);
		}

		for (int i = 0; i < oppositeTeamValidMoves.size(); i++) {//Here we are checking all the opponent valid moves and each opponent valid move has source and destination
			String tempmove=""+oppositeTeamValidMoves.get(i).charAt(7)+oppositeTeamValidMoves.get(i).charAt(8);//We are comparing its destination location with the kings present location
			if (location.equals(tempmove)) {//If both are equal then we are returning true as check status. 
				return true;
			}
		}
		return false;
	}

	public ArrayList<String> generateQueenMoves() {//Generating all the valid moves for Queen.
		ArrayList<String> queenValidMoves = new ArrayList<String>();
		for (int i = 0; i < directions.length; i++) {//Here we are checking step of all possible directions of move for queen
			int count = 0;
			for (int j = this.position[0][0], k = this.position[0][1]; j < 8 && j >= 0 && k < 8
					&& k >= 0; j += (directions[i][0]), k += (directions[i][1])) {//Here we are picking each direction from directions array and travelling in the direction to boundary of board until 
				if (count > 0) {// there is chess piece in between
					if (checkCondition(j, k) == 0 || checkCondition(j, k) == 1) {//We are checking the path being travelled, the checkCondition method return 0 if there is opponents piece in the path
						this.move = "0_" + String.valueOf(this.touchPiece) + "_" + Integer.toString(this.position[0][0])//it return 1 if there is an empty path
								+ Integer.toString(this.position[0][1]) + "_" + Integer.toString(j)
								+ Integer.toString(k);
						queenValidMoves.add(this.move);
					}
					if (checkCondition(j, k) == 0 || checkCondition(j, k) == -1) {//We are stopping when we reaching opponents chess piece in the path or same teams chess piece in the path. 
						j = 9;
						k = 9;
					}
				}
				count++;
			}
		}
		return queenValidMoves;//Returning queen moves
	}

	public ArrayList<String> generateRookMoves() {//Generating all the valid moves for Rook.
		ArrayList<String> rookValidMoves = new ArrayList<String>();
		for (int i = 1; i < directions.length; i += 2) {//Here we are checking step of all possible directions of move for Rook such as north, south,east and west 
			int count = 0;
			for (int j = this.position[0][0], k = this.position[0][1]; j < 8 && j >= 0 && k < 8
					&& k >= 0; j += (directions[i][0]), k += (directions[i][1])) {//Here we are picking each direction from directions array and travelling in the direction to boundary of board until 
				if (count > 0) {
					if (checkCondition(j, k) == 0 || checkCondition(j, k) == 1) {//We are checking the path being travelled, the checkCondition method return 0 if there is opponents piece in the path
						this.move = "0_" + String.valueOf(this.touchPiece) + "_" + Integer.toString(this.position[0][0])//it return 1 if there is an empty path
								+ Integer.toString(this.position[0][1]) + "_" + Integer.toString(j)
								+ Integer.toString(k);
						rookValidMoves.add(this.move);
					}
					if (checkCondition(j, k) == 0 || checkCondition(j, k) == -1) {//We are stopping when we reaching opponents chess piece in the path or same teams chess piece in the path. 
						j = 9;
						k = 9;
					}
				}
				count++;
			}
		}
		return rookValidMoves;//Returning rook moves
	}

	public ArrayList<String> generateBishopMoves() {//Generating all the valid moves for Bishop.
		ArrayList<String> bishopValidMoves = new ArrayList<String>();
		for (int i = 0; i < directions.length; i += 2) {//Here we are checking step of all possible directions of move for Bishop such as northeast, southwest,southeast and northwest 
			int count = 0;
			for (int j = this.position[0][0], k = this.position[0][1]; j < 8 && j >= 0 && k < 8
					&& k >= 0; j += (directions[i][0]), k += (directions[i][1])) {//Here we are picking each direction from directions array and travelling in the direction to boundary of board until 
				if (count > 0) {
					if (checkCondition(j, k) == 0 || checkCondition(j, k) == 1) {//We are checking the path being travelled, the checkCondition method return 0 if there is opponents piece in the path
						this.move = "0_" + String.valueOf(this.touchPiece) + "_" + Integer.toString(this.position[0][0])//it return 1 if there is an empty path
								+ Integer.toString(this.position[0][1]) + "_" + Integer.toString(j)
								+ Integer.toString(k);
						bishopValidMoves.add(this.move);
					}
					if (checkCondition(j, k) == 0 || checkCondition(j, k) == -1) {//We are stopping when we reaching opponents chess piece in the path or same teams chess piece in the path.
						j = 9;
						k = 9;
					}
				}
				count++;
			}
		}
		return bishopValidMoves;//Returning all bishop valid moves
	}

	public ArrayList<String> generateKnightMoves() {//Generating all the valid moves for Knight.
		ArrayList<String> knightValidMoves = new ArrayList<String>();
		int direction[][] = { { 1, -2 }, { 2, -1 }, { 2, 1 }, { 1, 2 }, { -1, -2 }, { -2, -1 }, { -2, 1 }, { -1, 2 } };//These are the directions in which Knight can move
		int row = 0, col = 0;
		for (int i = 0; i < direction.length; i++) {//Here we are picking each direction from directions array and travelling in that direction
			row = this.position[0][0] + direction[i][0];
			col = this.position[0][1] + direction[i][1];
			if ((row >= 0 && row < 8) && (col >= 0 && col < 8)) {//Checking boundary conditions
				if (checkCondition(row, col) == 0 || checkCondition(row, col) == 1) {//We are checking the path being travelled, the checkCondition method return 0 if there is opponents piece in the path
					this.move = "0_" + String.valueOf(this.touchPiece) + "_" + Integer.toString(this.position[0][0])//it return 1 if there is an empty path
							+ Integer.toString(this.position[0][1]) + "_" + Integer.toString(row)
							+ Integer.toString(col);
					knightValidMoves.add(this.move);
				}
			}
		}
		return knightValidMoves;//Returning all the Knight valid moves.
	}

	public ArrayList<String> generatePawnMoves() {//Generating all the valid moves for Pawn
		ArrayList<String> pawnValidMoves = new ArrayList<String>();
		int[][] pmoves = { { 2, -2, 0 }, { 1, -1, 0 }, { 1, -1, -1 }, { 1, -1, 1 } };//These are the valid moves for pawn first column is valid row increment/decrement values for black similarly
		//The second column is blacks and the last column is the column incremental value for both whites and blacks.
		int j = 0, row = 0, col = 0, prow = 0;//The row ,col values are used to stored the incremented/decremented values of current position. The prow is used to check the previous row and corresponding column chess piece while taking two steps forward.
		if((this.touchPiece=='P'&&this.position[0][0]==1)||(this.touchPiece=='p'&&this.position[0][0]==6)) {//If the white or black pawn is present at 1 or 6 row respectively then the pawn can take two steps so we will start from 0th row of pawn valid moves(pmoves)
			j=0;
		}
		else {//Else if the pawn is in different location other than its starting board position then it will increment only 1 forward, so we are starting from 1st row of pawn valid moves(pmoves)
			j=1;
		}
		for (int k = j; k < pmoves.length; k++) {
			this.move = "";
			col = this.position[0][1] + pmoves[k][2]; //We are incrementing or decrementing the column value
			if (Character.isUpperCase(this.touchPiece)) { //If the touchpiece is P then we are incrementing/decrementing the row using 0th column values of pmoves
				row = this.position[0][0] + pmoves[k][0];
				if (k == 0) {//If k=0 that is when the touchpiece is P has chance to move two steps so we are storing row value of one step forward
					prow = this.position[0][0] + pmoves[1][0];
				}
			}
			if (Character.isLowerCase(this.touchPiece)) {//If the touchpiece is p then we are incrementing/decrementing the row using 1st column values of pmoves
				row = this.position[0][0] + pmoves[k][1];
				if (k == 0) {//If k=0 that is when the touchpiece is P has chance to move two steps so we are storing row value of one step forward
					prow = this.position[0][0] + pmoves[1][1];
				}
			}
			
			if (row >= 0 && row < 8 && col >= 0 && col < 8) {//Checking the boundary conditions.
				if (((k == 1) && (this.gameBoard[row][col] == '\0'))//If k=1 then we are checking the next forward step for the pawn to be null
						|| ((k == 0) && (this.gameBoard[row][col] == '\0') && (this.gameBoard[prow][col] == '\0')) ) {//If k=0 then we are checking the next twp forward steps for the pawn to be null
					if (((Character.isUpperCase(this.touchPiece)) && (row == 7))//If the white pawn is at row 7 it means it is promoted
							|| ((Character.isLowerCase(this.touchPiece)) && (row == 0))) {//If the black pawn is at row 0 it means it is promoted
						String newPiece = pawnPromotion();//Calling the pawnPromotion function which randomly select the promoted chess piece
						this.move = "2_" + newPiece + "_" + Integer.toString(this.position[0][0])//The promotion move is represented uniquely as move type 2
								+ Integer.toString(this.position[0][1]) + "_" + Integer.toString(row)
								+ Integer.toString(col) ;
					} else {//Else the normal straight move is returned. 
						this.move = "0_" + String.valueOf(this.touchPiece) + "_" + Integer.toString(this.position[0][0])
								+ Integer.toString(this.position[0][1]) + "_" + Integer.toString(row)
								+ Integer.toString(col);
					}
				}
				if ( ((k > 1)
						&& (Character.isUpperCase(this.touchPiece) && Character.isLowerCase(this.gameBoard[row][col])))//Checking whether the diagonal element is black or not for the white pawn
						|| ((k > 1)&&(Character.isLowerCase(this.touchPiece)//Checking whether the diagonal element is white or not for the black pawn
								&& Character.isUpperCase(this.gameBoard[row][col])))) {
					if (((Character.isUpperCase(this.touchPiece)) && (row == 7))//After checking the kill of the black opponent diagonally checking whether end step is promotion or not
							|| ((Character.isLowerCase(this.touchPiece)) && (row == 0))) {//After checking the kill of the white opponent diagonally checking whether end step is promotion or not
						String newPiece = pawnPromotion();
						this.move = "2_" + newPiece + "_" + Integer.toString(this.position[0][0])
								+ Integer.toString(this.position[0][1]) + "_" + Integer.toString(row)
								+ Integer.toString(col);
					} else {//Else the normal diagonal move after killing opponent is returned. 
						this.move = "0_" + String.valueOf(this.touchPiece) + "_" + Integer.toString(this.position[0][0])
								+ Integer.toString(this.position[0][1]) + "_" + Integer.toString(row)
								+ Integer.toString(col);
					}
				}
			}
			if (this.move != "") {
				pawnValidMoves.add(this.move);
			}
		}
		
		return pawnValidMoves;//All the pawn valid moves are returned.
	}

	public ArrayList<String> checkEnpassantMoves() {//Checking for EnpassantMoves
		ArrayList<String> pawanValidMoves = new ArrayList<String>();
		int row = 0, col = 0, j = 0, max = 0, opprow = 0;//The row and col variables are used to store the position of enpassant square and
		//opprow is used to store the position of pawn which will be captured as part of enpassant move
		int[][] enpassantdirection = { { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } };//The first two rows are the positions to be checked for black pawn when white pawn has created enpassant square
		//The last two rows are the positions to be checked for white pawn when black pawn has created enpassant square
		for (int i = 0; i < boardColumn.length; i++) {//Convert the chess board row column representation to array row column values
			if (this.enPassantTargetSquare.charAt(0) == boardColumn[i]) {
				col = i;
			}
		}
		row = Integer.parseInt(String.valueOf(this.enPassantTargetSquare.charAt(1))) - 1;
		if (this.sideToMove == 'w') {//If the side to move is white then we have check the first two rows as mentioned above
			j = 0;
			max = 2;
			opprow = row - 1;
		}
		if (this.sideToMove == 'b') {//If the side to move is black then we have check the first two rows as mentioned above
			j = 2;
			max = 4;
			opprow = row + 1;
		}
		while (j < max) {
			this.move = "";
			int nrow = row + enpassantdirection[j][0];//Calculating the row value to be checked with respect to enpassant square position
			int ncol = col + enpassantdirection[j][1];//Calculating the col value to be checked with respect to enpassant square position
			if(nrow>=0&&nrow<8&&ncol>=0&&ncol<8&&opprow>=0&&opprow<8) {//Checking the boundary conditions
			if ((this.sideToMove == 'w' && this.gameBoard[nrow][ncol] == 'P')
					|| (this.sideToMove == 'b' && this.gameBoard[nrow][ncol] == 'p')) {
				this.move = "3_" + String.valueOf(this.gameBoard[nrow][ncol]) + "_" //Here I am denoting enpassant move type as type 3 move it will contain information about
						+ Integer.toString((nrow * 10 + ncol)) + "_" + Integer.toString((row * 10 + col)) + "_"
						+ Integer.toString((opprow * 10 + col));
			}
		     }
			if (this.move != "") {
				pawanValidMoves.add(this.move);
			}
			j++;
		}
		return pawanValidMoves; //All the valid pawn enpassant moves are returned.
	}

	public String pawnPromotion() {//The pawnPromotion method returns randomly promoted chess piece for the pawn which reached promotion state
		char Pieces[] = { 'q', 'r', 'b', 'n' };//When a pawn is promoted it will select the chess pieces from this list
		Random rand = new Random();
		int n = rand.nextInt(4);
		String newPiece = "";
		if (n < 0 || n >= Pieces.length) {
			newPiece = pawnPromotion();
		} else {
			if(Character.isUpperCase(this.touchPiece)) {
				newPiece = String.valueOf(Character.toUpperCase(Pieces[n]));
			}
			else {
			newPiece = String.valueOf(Pieces[n]);
			}
		}
		//System.out.println(String.format("The promoted pawn is %s", newPiece));
		return newPiece;//It will return the randomly selected chess piece
	}

	public int checkCondition(int j, int k) {//The checkCondition method takes row and column as input and it will check whether it is empty square or if any chess piece is present
		int validpathornot = 0;
		if (Character.isUpperCase(this.gameBoard[j][k]) && Character.isLowerCase(this.touchPiece)) {//If the current touchpiece is black then the piece present at that row and column in gameboard has to white
			validpathornot = 0;  //Then only it will consider as valid square to be occupied
		} else if (Character.isLowerCase(this.gameBoard[j][k]) && Character.isUpperCase(this.touchPiece)) {//If the current touchpiece is white then the piece present at that row and column in gameboard has to black
			validpathornot = 0;//Then only it will consider as valid square to be occupied
		} else if (this.gameBoard[j][k] == '\0') {//If the current touchpiece is white or black irrespective of that if it is an empty square in specified row and col location of gameboard.
			validpathornot = 1;//then it is considered a valid square to be occupied
		} else {//If the square is neither of the above conditions then it is an invalid square to be occupied.
			validpathornot = -1;
		}
		return validpathornot;//The type of valid square  is returned. 
	}

	public String executeMove(String move) {//This executeMove function takes input move as a String and executes the move
		String[] movepath = move.split("_"); //In all types of moves the common part is movetype_chesspiece_fromposition_toposition, hence we are splitting the string. 
		this.touchPiece = move.charAt(2);
		int from = Integer.parseInt(movepath[2]);
		int to = Integer.parseInt(movepath[3]);
		this.gameBoard[(from / 10)][(from % 10)] = '\0'; //Replacing the from position with null character.
		this.gameBoard[(to / 10)][(to % 10)] = this.touchPiece; //Replacing the to position with the chess piece
		
		if(movepath[0].equals("1")) {//It is Castling move so we will take the rook start position and end position and we will try to move the rook accordingly
			int rfrom = Integer.parseInt(movepath[5]);
			int rto = Integer.parseInt(movepath[6]);
			this.gameBoard[rfrom / 10][rfrom % 10] = '\0';
			this.gameBoard[rto / 10][rto % 10] = movepath[4].charAt(0);
		}
		else if(movepath[0].equals("3")) {//It is a enpassant move so after the pawn has occupied the enpassant square the opponent pawn which has made the enpassant move will be removed from board.
			int killpawn = Integer.parseInt(movepath[4]);
			this.gameBoard[(killpawn / 10)][(killpawn % 10)] = '\0';
		}
		move=rawMoveToStandardMove(move);
		return move; 
	}

	public void displayChessBoard() {//The displayChessBoard function helps to print different information present in fenString after categorising. Inaddition to that it will print fenString and current ChessBoard
		System.out.println("Printing Chess Board Object Parameters");
		System.out.println(String.format(
				"The FenString is %s \nThe sideToMove is %c \nThe castlingAbility is %s \nThe en Passant Taget Square is %s \nThe half move counter is %d \nThe full move counter is %d",
				this.fenString, this.sideToMove, this.castlingAbility, this.enPassantTargetSquare, this.halfMoveCounter,
				this.fullMoveCounter));
		System.out.println("Printing the Board");
		for (int i = 0; i < this.gameBoard.length; i++) {
			for (int j = 0; j < this.gameBoard[0].length; j++) {
				if (String.valueOf(this.gameBoard[i][j]).equals("\0")) {
					System.out.printf("*");
				} else {
					System.out.printf("%c", this.gameBoard[i][j]);
				}
			}
			System.out.println();
		}
	}

	public String rawMoveToStandardMove(String move) {//This function is used to convert moves from raw format to standard notation. For example a move from P_21_31 is convert to b3b4
		String[] movepath = move.split("_");//In all types of moves the common part is movetype_chesspiece_fromposition_toposition, hence we are splitting the string. 
		this.touchPiece = move.charAt(2);
		int from = Integer.parseInt(movepath[2]);
		int to = Integer.parseInt(movepath[3]);
		move = String.valueOf(boardColumn[from % 10]) + String.valueOf((from / 10) + 1) //Generating the Algebraic standard notation of move as for example d4d5 
		+ String.valueOf(boardColumn[to % 10]) + String.valueOf((to / 10) + 1);
		if(movepath[0].equals("2"))  {//It is a promotion move so the standard move is attached with the promoted piece
			move=move+this.touchPiece;
		}
		return move;
	}
	
	public String selectRandomMove() {//The selectRandomMove helps to select only the moves which help to remove or nullify the existing check. If there is no existing check it will return all validmoves. 
		//From these filtered moves we are selecting a randomMove to be executed. 
		ArrayList<String> validMoves = new ArrayList<String>();
		ArrayList<String> pseudoLegalMoves;
		if (this.sideToMove == 'w') {//We are copying all the valid moves of the side which we are playing
			pseudoLegalMoves = whiteValidMoves;
		} else {
			pseudoLegalMoves = blackValidMoves;
		}
		for (String eachMove : pseudoLegalMoves) {
			ChessBoard chessboard = new ChessBoard();//Creating a temporary chessBoard.
			chessboard = this.copyChessBoardObject();//Copying the current chessboard to the temporary chessboard object
			chessboard.executeMove(eachMove);//Executing each valid move.
			chessboard.generateValidMoves();//After executing the move, generating the valid moves of opponent team.
			String m = String.valueOf(chessboard.kingPosition[0][0]) + String.valueOf(chessboard.kingPosition[0][1]);//concatinating the Kingsposition into a String
			if (!chessboard.verifyKingCheckStatus(m)) {//Verifying the king check status. If the king is not in check then the move is being added to final valid moves list. 
				validMoves.add(eachMove);
			}
		}
		Random r = new Random();//Generating a random number.
		int low = 0;
		int high = validMoves.size();
		int result = r.nextInt(high - low) + low;
		return validMoves.get(result);//returning the random move generated from the filtered list. 
	}

}
