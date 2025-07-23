/**
 * 
 */
package games.chess.gamePlay;
import games.chess.gamePlay.*;
import java.util.Random;
/**
 * @author MYPC
 *
 */
public class Main {
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ChessBoard chessboard=new ChessBoard();
		chessboard.readFenString("q7/4P1r1/Qpb3R1/P1Bk4/3pn1Pp/3P1B2/7P/R4KN1 b - - 6 52");
		String m="";
		chessboard.displayChessBoard();
		chessboard.generateValidMoves();
		for(int i=0;i<chessboard.blackValidMoves.size();i++) {
			System.out.println(chessboard.blackValidMoves.get(i));
		}
		//m=chessboard.selectRandomMove();
		//m=chessboard.executeMove(m);
		System.out.println(String.format("The executed move is %s", m));
    	chessboard.displayChessBoard();

	}

}
