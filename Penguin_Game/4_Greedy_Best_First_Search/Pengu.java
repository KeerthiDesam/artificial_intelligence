import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Comparator;

public class Pengu {
    //Initializing the direction vectors
    static int rowVector[] = {-1, 0, 1, 0, -1, 1, -1, 1};
    static int colVector[] = {0, 1, 0, -1, 1, -1, -1, 1};

    static class Block {
        int rows, cols;

        public Block(int rows, int cols) {
            this.rows = rows;
            this.cols = cols;
        }

        @Override
        public String toString() {
            return "{row:" + rows + ",col:" + cols + "} ";
        }
    }
    static class Path{
        String path;
        int score = 0;

        public Path(String path) {
            this.path = path;
        }

        public int getScore() {
            return score;
        }

        @Override
        public String toString() {
            return path;
        }
    }

    public static void main(String[] args) {
        System.out.print("\nStart point of puzzle\n");

        //Check whether user has passed correct number of arguments or not, if not exit the loop
        if (args.length != 2) {
            System.out.println("ERROR- Please pass 2 arguments.The input arguments passed number is incorrect.");
            System.exit(0);
        }
        String input_Name = args[0];
        File inFile = checkInFileExist(input_Name);
        String output_Name = args[1];

        String box[][] = getTheBoardState(inFile);
        if (box == null) {
                System.out.println("ERROR-Can't read INPUT file");
                System.exit(0);
        }


       Block penguPresentPosition = getPenguPresentPosition(box);
        if (penguPresentPosition != null) {
            System.out.println("Penguin start position:[" + penguPresentPosition.rows + "," + penguPresentPosition.cols + "]");
            BFS(box, penguPresentPosition.rows, penguPresentPosition.cols, output_Name);
        } else {
            System.out.println("ERROR: Penguin not found");
            System.exit(0);
        }
    }

    //BFS traversal function starts here
    static void BFS(String initialBox[][], int rows, int cols, String output_Name) {
        int score = 0;
        int finalGoal = 20;
        StringBuilder penguinMoves = new StringBuilder();
        Block penguinStartPosition = new Block(rows, cols);

        // Stores indices of the matrix cells
        PriorityQueue<Path> queue = new PriorityQueue<>(new PriorityQueueComparator());
        String[][] box = copyNewPath(initialBox);

        do {
            addAdjacentsToStack(null,penguinStartPosition, initialBox, queue);

            // Iterate until the queue is not empty
            while (!queue.isEmpty()) {
                Path path = queue.peek();
                queue.remove(path);

                String[] pathMovement = path.path.split(",");
                Block presentBlock = null;

                for (String move : pathMovement) {
                   if (!isDestination(score, finalGoal)) {
                        penguinMoves = penguinMoves.append(move);

                        Block directionBlock = getPenguinDirection(Integer.parseInt(move));

                        //Moves made at the start position of pengu
                        if (presentBlock == null) {
                            box[penguinStartPosition.rows][penguinStartPosition.cols] = " ";
                            presentBlock = new Block(penguinStartPosition.rows + directionBlock.rows, penguinStartPosition.cols + directionBlock.cols);
                        } else {
                            presentBlock = new Block(presentBlock .rows + directionBlock.rows, presentBlock .cols + directionBlock.cols);
                        }

                       boolean slide = true;
                        while (slide) {
				  // Checking all the conditions for penguin to travel
                            switch (box[presentBlock.rows][presentBlock.cols]) {
                                case "U":
                                case "S":
                                    slide = false;
                                    box[presentBlock.rows][presentBlock.cols] = "X";
                                    break;
                                case "*":
                                    score += 1;
                                    box[presentBlock.rows][presentBlock.cols] = " ";
                                    break;
                                case " ":
                                    break;
                                case "0":
                                    slide = false;
                                    break;
                                case "#":
                                    slide = false;
                                    break;
                                default:
                                    box[presentBlock.rows][presentBlock.cols] = " ";
                                    slide = false;
                                    break;
                            }

                            if (slide) {
                                if (isVisitedValid(presentBlock.rows + directionBlock.rows, presentBlock.cols + directionBlock.cols, box)) {
                                    presentBlock= new Block(presentBlock.rows + directionBlock.rows, presentBlock.cols + directionBlock.cols);
                                } else {
                                    slide = false;
                                }
                            }
                        }
                    }
                }


               if (!isDestination(score, finalGoal)) {

                    if (box[presentBlock.rows][presentBlock.cols] != "X") {
                        addAdjacentsToStack(path, presentBlock, box, queue); //adding adjacents to the stack
                    }
                     //Reset the grid after visiting everypath and reset the score to 0 
                    box = copyNewPath(initialBox);
                    score = 0;
                    penguinMoves.setLength(0);

                } else if (isDestination(score, finalGoal)) {
                    if (box[presentBlock.rows][presentBlock.cols] != "X") {
                        box[presentBlock.rows][presentBlock.cols] = "P";
                    }
                    break;
                }
            }
        } while (!isDestination(score, finalGoal));

        handlingOutputFile(output_Name, penguinMoves.toString(), score, box);
    }

    //Adding adjacents to the queue
    static void addAdjacentsToStack(Path path, Block penguinPosition, String box[][], PriorityQueue<Path> queue) {
       List<Integer> randomAdjacents = generatingAdjacents();
        for (int i = 0; i < 8; i++) {
            int randomSelection = randomAdjacents.get(i).intValue();

            int adjacentX = penguinPosition.rows + rowVector[randomSelection];
            int adjacentY = penguinPosition.cols + colVector[randomSelection];

            if (isVisitedValid(adjacentX, adjacentY, box)) {
                int penguinDirection = getPenguMove(rowVector[randomSelection], colVector[randomSelection]);
                Path newPath = (path != null) ? new Path(path.path + "," + String.valueOf(penguinDirection)) : new Path(String.valueOf(penguinDirection));
                int scoreAchived = getScore(adjacentX, adjacentY, penguinDirection, box);
                if (scoreAchived >= 0) {
                    newPath.score = ((path != null) ? path.score : 0 ) + scoreAchived;
                    queue.add(newPath);
                } 
            }
        }
    }

    //If the path is valid return positive otherwise negative
    static int getScore(int penguinRow, int penguinColumn, int penguinDirection, String box[][]) {
        int score = 0;
        Block directionBlock = getPenguinDirection(penguinDirection);
        Block presentBlock = new Block(penguinRow, penguinColumn);
        boolean slide = true;

        while (slide) {
            switch (box[presentBlock.rows][presentBlock.cols]) {
                case "U":
                case "S":
                case "#":
                    return -1;
                case "*":
                    score += 1;
                    break;
                case " ":
                    break;
                case "0":
                    return score;
                default:
                    return score;
            }

            if (isVisitedValid(presentBlock.rows + directionBlock.rows, presentBlock.cols + directionBlock.cols, box)) {
                presentBlock = new Block(presentBlock.rows + directionBlock.rows, presentBlock.cols + directionBlock.cols);
            } else {
                return score;
            }
        }
        return score;
    }

    //Checking whether we have visited the block or not
    static boolean isVisitedValid(int r, int c, String box[][]) {
        if (r < 0 || c < 0 ||
                r >= box.length || c >= box[0].length) {
            return false;
        }
        if (box[r][c].equalsIgnoreCase("#")) {
            return false;
        }
        return true;
    }

    static int getPenguMove(int adjacnetRow, int adjacentColumn) { 
        //North, East, South, West, NorthEast, SouthWest, NorthWest, SouthEast
        if (adjacnetRow== -1 && adjacentColumn== 0) {
            return 8; 
        } else if (adjacnetRow== 0 && adjacentColumn== 1) {
            return 6; 
        } else if (adjacnetRow== 1 && adjacentColumn== 0) {
            return 2; 
        } else if (adjacnetRow== 0 && adjacentColumn== -1) {
            return 4; 
        } else if (adjacnetRow== -1 && adjacentColumn== 1) {
            return 9; 
        } else if (adjacnetRow== 1 && adjacentColumn== -1) {
            return 1; 
        } else if (adjacnetRow== -1 && adjacentColumn== -1) {
            return 7; 
        } else if (adjacnetRow== 1 && adjacentColumn== 1) {
            return 3; 
        } else {
            return 5;
        }
    }

    static Block getPenguinDirection(int move) {
        if (move == 8) {
            return new Block(-1, 0);
        } else if (move == 6) {
            return new Block(0, 1);
        } else if (move == 2) {
            return new Block(1, 0);
        } else if (move == 4) {
            return new Block(0, -1);
        } else if (move == 9) {
            return new Block(-1, 1);
        } else if (move == 1) {
            return new Block(1, -1);
        } else if (move == 7) {
            return new Block(-1, -1);
        } else if (move == 3) {
            return new Block(1, 1);
        } else {
            return new Block(0, 0); 
        }
    }

   static List<Integer> generatingAdjacents() {
        Random random = new Random();
        List<Integer> position = new ArrayList<>();
        for (int i = 0; i < 8; ++i) {
            position.add(i);
        }
        Collections.shuffle(position, random);
        return position;
    }

    public static String[][] getTheBoardState(File inFile) {
        //Checking if the input file has valid data
        int rows = 0;
        int columns = 0;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(inFile));
            String line = reader.readLine();
            if (line == null) {
                System.out.println("ERROR: No correct information");
                return null;
            }

            //Check whether rows and columns are provided
            String[] rowToken = line.trim().split("\\s+");
            if (rowToken.length != 2) {
                System.out.println("ERROR: Give valid rows and columns");
                return null;
            }

            rows = Integer.parseInt(rowToken[0]);
            columns = Integer.parseInt(rowToken[1]);
            System.out.println("\nNo of rows:" + rowToken[0] + "\nNo of columns:" + rowToken[1]);
            
		//Setting up the board
            String[][] board = new String[rows][columns];
            line = reader.readLine();
            int rowNum = 0;
            while ((line != null && line.length() > 0)) {
                String[] row_Tokens = line.trim().split("");
                if (row_Tokens.length < columns) {
                    System.out.println("ERROR: Row:" + rowNum + " given data is incorrect");
                    return null;
                }
                for (int j = 0; j < row_Tokens.length; j++) {
                    board[rowNum][j] = row_Tokens[j];
                }
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

    //Checking whether the input file exists or not
    public static File checkInFileExist(String input_Name) {
        File inFile = new File(input_Name);
        boolean inFileExist = inFile.exists();
        if (!inFileExist) {
            System.out.println("ERROR: Cannot find input file");
            System.exit(0);
        }
        return inFile;
    }

    public static void handlingOutputFile(String output_Name, String moves, int score, String[][] board) {
        System.out.println("\n OUTPUT \n");
        try {
            File file = new File(output_Name);
            if (file.createNewFile()) {
                System.out.println("New OutputFile is created: " + file.getName());
            }

            FileWriter writer = new FileWriter(file);
            System.out.println("Moves made:" + moves);
            writer.write(moves + "\n");
            writer.write(String.valueOf(score) + "\n");
            System.out.println("Score:" + score + "\n");

            for (int r = 0; r < board.length; r++) {
                StringBuilder everyRow = new StringBuilder();
                for (int c = 0; c < board[r].length; c++) {
                    everyRow.append(board[r][c]);
                }
                writer.write(everyRow + "\n");
                System.out.println(everyRow);
            }
            writer.close();
            if (score > 3) {
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println("\nERROR: Couldn't write to the output file" + output_Name);
        }
    }

    public static String[][] copyNewPath(String[][] s) {
        if (s == null) {
            return null;
        }
        String[][] copyFile = new String[s.length][];
        for (int i = 0; i < s.length; i++) {
            copyFile[i] = s[i].clone();
        }
        return copyFile;
    }

     public static Block getPenguPresentPosition(String[][] board) {

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if ((board[row][col]).equalsIgnoreCase("P") || (board[row][col]).equalsIgnoreCase("0P")) {
                    return new Block(row, col);
                }
            }
        }
        return null;
    }


    public static boolean isDestination(int score, int finalGoal){
        return  (score >= finalGoal);
    }
    public static class PriorityQueueComparator implements Comparator<Path> {
        public int compare(Path path1, Path path2){
             if (path1.score < path2.score) {
                 return 1;
             } else if (path1.score > path2.score) {
                 return -1;
             } else {
                if ((path1 != null) && (path2 != null)) {
                    int path1Move = Integer.parseInt(path1.path.substring(path1.path.length() - 1));
                    int path2Move = Integer.parseInt(path2.path.substring(path2.path.length() - 1));
                    if (path1Move > path2Move){
                        return 1;
                    }else{
                        return -1;
                    }
                }

                return 0;
             }
        }
    }
}