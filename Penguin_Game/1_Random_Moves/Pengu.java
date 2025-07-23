import java.lang.*;
import java.util.*;
import java.io.*;

public class Pengu {

    public static void main(String[] args) {
        // Check whether user has passed correct number of arguments or not, if not exit the loop
        if (args.length != 2) {
            System.out.println("ERROR- Please pass 2 arguments.The input arguments passed number is incorrect.");
            System.exit(0);
        }

        String input_Name = args[0];
        File inFile = checkInFileExist(input_Name);

        String[][] board = getTheBoardState(inFile);
        if (board == null) {
            System.out.println("ERROR: Can't read INPUT file");
            System.exit(0);
        }
        String penguPresentPosition = getPenguPresentPosition(board);
        if (penguPresentPosition == "PENGUNOTFOUND") {
            System.out.println("ERROR: Pengu not found in the Input file.");
            System.exit(0);
        }
        System.out.println("\nPuzzle Start point");
        String output_Name = args[1];
        play(board,output_Name);
        System.out.println("\nPuzzle End point");
    }
    /*
        Checking the existence of input file
        if NOT present then EXIT
   */
    public static File checkInFileExist(String input_Name) {
        File inFile = new File(input_Name);
        boolean inFileExist = inFile.exists();
        if (!inFileExist) {
            System.out.println("ERROR: Cannot find input file");
            System.exit(0);
        }
        return inFile;
    }

    public static String[][] getTheBoardState(File inFile) {
        //Checking if the input file has valid data
        int row,column= 0;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(inFile));
            String line = reader.readLine();
            if (line == null) {
                System.out.println("ERROR: No correct information");
                return null;
            }
            // Check whether rows and columns are provided
            String[] rowToken = line.trim().split("\\s+");
            if (rowToken.length != 2) {
                System.out.println("ERROR: Give valid rows and columns");
                return null;
            }
            row = Integer.parseInt(rowToken[0]);
            column = Integer.parseInt(rowToken[1]);
            System.out.println("\nNO_OF_ROWS_PRESENT:" + row + "\nNO_OF_COLUMNS_PRESENT:" + column);

            //setting board initialsize
            String[][] board = new String[row][column];
            line = reader.readLine();
            int rowNum = 0;
            while ((line != null && line.length() > 0)) {
                String[] row_Tokens = line.trim().split("");
                // Checking if every row is specified with correct columns
                if (row_Tokens.length < column) {
                    System.out.println("ERROR: Row:" + rowNum + " given data is incorrect");
                    return null;
                }
                for (int j = 0; j < row_Tokens.length; j++) {
                    board[rowNum][j] = row_Tokens[j];
                }
                // reading the next line
                line = reader.readLine();
                rowNum += 1;
            }
            return board;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static void play(String[][] board, String output_Name) {
        int[] movesPossible = new int[]{7, 8, 9, 4, 6, 1, 2, 3};
        StringBuilder pegnuinMoves = new StringBuilder();
        int finalMovesMade = 1,score=0;
        //int score = 0;
        // breaks here if anything happens 
        while (finalMovesMade <= 6) {
            String penguPresentPosition = getPenguPresentPosition(board);
            System.out.println("\nPenguin Moves:" + finalMovesMade + " Penguin's present position:" + penguPresentPosition);

            String[] penguinPositionArray = penguPresentPosition.split(",");
            int presentRow = Integer.parseInt(penguinPositionArray[0]);
            int presentCol = Integer.parseInt(penguinPositionArray[1]);

            int nextRandomMove = movesPossible[new Random().nextInt(movesPossible.length)];
            System.out.println("\nPenguin's next random move made:" + nextRandomMove);

            if (canPenguinMove(board, presentRow, presentCol, nextRandomMove)) {
                pegnuinMoves.append(nextRandomMove);
                score = nextMove(board, score, presentRow, presentCol, nextRandomMove);

                if (getPenguPresentPosition(board) == "PENGUNOTFOUND") break;

                finalMovesMade +=  1;
            }
        }
        lastMove(board); //Handling the snow cell in the last move
        System.out.println("\n MOVES: " + pegnuinMoves);
        System.out.println("\n SCORE: " + score);
        System.out.println("\n Board Final Position\n");
        printingBoard(board);
        handlingOutputFile(output_Name, pegnuinMoves.toString(), score, board);
    }
    public static String getPenguPresentPosition(String[][] board) {
        for (int r = 0; r < board.length; r++) { //r-reperesents row and c-represents column
            for (int c = 0; c < board[r].length; c++) {
                if ((board[r][c]).equalsIgnoreCase("P") || (board[r][c]).equalsIgnoreCase("0P")) {
                    return r + "," + c; // We need to return
                }
            }
        }
        return "PENGUNOTFOUND";
    }

    public static int nextMove(String[][] board, int score, int presentRow, int presentCol, int nextRandomMove) {
        int penguinNextRow = getNextRow(nextRandomMove, presentRow);
        int penguinNextCol = getNextColumn(nextRandomMove, presentCol);
        System.out.println("\nPenguin's Next Move Board Position:" + " Row:" + penguinNextRow + ", Column:" + penguinNextCol);
        System.out.println("\nBoard's current state at Penguin Next Move:" + board[penguinNextRow][penguinNextCol]);
        boolean slide = true; //initializing that penguin can slide as true
        while (slide) {
            if ((board[penguinNextRow][penguinNextCol]).equalsIgnoreCase("U") || (board[penguinNextRow][penguinNextCol]).equalsIgnoreCase("S")) {
                // We need to clear as penguin dies in the next position
                hanlde(board, presentRow, presentCol); //handling the next cell
                board[penguinNextRow][penguinNextCol] = "X";
                slide = false;
                System.out.println("\nPENGUIN_NOT_FOUND");
            } else if ((board[penguinNextRow][penguinNextCol]).equalsIgnoreCase("*")) {
                
                score += 1; //increase the score as pengu found a fish
                
                hanlde(board, presentRow, presentCol);
                board[penguinNextRow][penguinNextCol] = "P";
                slide = true;
                System.out.println("\nFish is found by the penguin");
            } else if ((board[penguinNextRow][penguinNextCol]).equalsIgnoreCase("#")) {
             
                System.out.println("\nPenguin has hit the wall");
                slide = false; //as penguin hit the wall it should not slide anymore
            } else if ((board[penguinNextRow][penguinNextCol]).equalsIgnoreCase("0")) {
      
                hanlde(board, presentRow, presentCol);
                board[penguinNextRow][penguinNextCol] = "0P";
                System.out.println("\nFound a snow cell");
                slide = false; //if snow cell is found it should stop sliding
            } else if((board[penguinNextRow][penguinNextCol]).equalsIgnoreCase(" ")) {
                
                hanlde(board, presentRow, presentCol);
                board[penguinNextRow][penguinNextCol] = "P";
                System.out.println("\nHitting the empty ice cell");
                slide = true;
            }
            if (slide){
              
                presentRow = penguinNextRow;
                presentCol = penguinNextCol;
                penguinNextRow = getNextRow(nextRandomMove, penguinNextRow);
                penguinNextCol = getNextColumn(nextRandomMove, penguinNextCol);
            }
        }
        return score;
    }
    public static int getNextRow(int nextRandomMove, int presentRow){
        switch (nextRandomMove) {
            case 7:
                return presentRow - 1;
            case 8:
                return presentRow - 1;
            case 9:
                return presentRow - 1;
            case 1:
                return presentRow + 1;
            case 2:
                return presentRow + 1;
            case 3:
                return presentRow + 1;

        }
        return presentRow;
    }
    public static int getNextColumn(int nextRandomMove, int presentCol){
        switch (nextRandomMove) {
            case 7:
                return presentCol- 1;
            case 4:
                return presentCol- 1;
            case 1:
                return presentCol- 1;
            case 6:
                return presentCol+ 1;
            case 9:
                return presentCol+ 1;
            case 3:
                return presentCol+ 1;

        }
        return presentCol;
    }
   public static boolean canPenguinMove(String[][] board, int presentRow, int presentCol, int nextRandomMove) {
        int penguinNextRow = getNextRow(nextRandomMove, presentRow);
        int penguinNextCol = getNextColumn(nextRandomMove, presentCol);

        if ((board[penguinNextRow][penguinNextCol]).equalsIgnoreCase("#")) {
           
            System.out.println("\nHit the wall");
            return false;
        }
        return true;
    }
    public static void hanlde(String[][] board, int presentRow, int presentCol){
        if ((board[presentRow][presentCol]).contains("0")){
            board[presentRow][presentCol] = "0";
        } else{
            board[presentRow][presentCol] = " ";
        }
    }
    public static void printingBoard(String[][] board){
         for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                System.out.print(board[r][c]);
            }
            System.out.println("");
        }
    }
    public static void lastMove(String[][] board){ //penguin last move at snow cell
         for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if (board[r][c] == "0P"){
                    board[r][c]="P";
                }
            }
        }
    }
    public static void handlingOutputFile(String output_Name, String moves, int score,String[][] board) {
        try {
            File file = new File(output_Name);
            if (file.createNewFile()) {
                System.out.println("New OutputFile is created: " + file.getName());
            } else {
                System.out.println("Output File is existed already");
            }
            FileWriter writer = new FileWriter(file);
            writer.write(moves+"\n");
            writer.write(String.valueOf(score)+"\n");
            for (int r = 0; r < board.length; r++) {
                StringBuilder everyRow = new StringBuilder();
                for (int c = 0; c < board[r].length; c++) {
                    everyRow.append(board[r][c]);
                }
                writer.write(everyRow+"\n");
            }
            writer.close();
            System.out.println("Successfully written to the output file.");
        } catch (Exception e){
            System.out.println("\nERROR: Cannot write to the output file " + output_Name);
        }
    }
}
