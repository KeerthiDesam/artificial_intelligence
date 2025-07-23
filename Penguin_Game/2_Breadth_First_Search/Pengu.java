import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Collections;

public class Pengu {
    // Initializing the direction vectors
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

    static class PathDirection {
        String pathdirection;

        public PathDirection(String pathdirection) {
            this.pathdirection = pathdirection;
        }

        @Override
        public String toString() {
            return pathdirection;
        }
    }

    public static void main(String[] args) {
        System.out.print("\nStart point of puzzle\n");

        // Check whether user has passed correct number of arguments or not, if not exit the loop
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

        BFS(box, 1, 1, output_Name);
    }

    // BFS traversal function starts here
    static void BFS(String initialBox[][], int rows, int cols, String output_Name) {
        int score = 0;
        int finalGoal = 8;
        StringBuilder penguinMoves = new StringBuilder();

        Block penguinStartPosition = new Block(rows, cols);

        Queue<PathDirection> q = new LinkedList<>();

        addingAdjacentsToTheQueue(null, penguinStartPosition , initialBox, q);

        String[][] box = newPath(initialBox);

        while (!q.isEmpty()) {

            PathDirection pathdirection = q.peek();
            q.remove();
            String[] pathMoves = pathdirection.pathdirection.split(",");

            Block presentBlock = null;

            for (String move : pathMoves) {

                if (score < finalGoal) {

                    penguinMoves = penguinMoves.append(move);
                    Block directionBlock = getPenguDirection(Integer.parseInt(move));

                    // The initial pengu movement
                    if (presentBlock == null) {
                        box[penguinStartPosition.rows][penguinStartPosition.cols] = " ";
                        presentBlock = new Block(penguinStartPosition.rows + directionBlock.rows, penguinStartPosition.cols + directionBlock.cols);
                    } else {
                        presentBlock = new Block(presentBlock.rows + directionBlock.rows, presentBlock.cols + directionBlock.cols);
                    }

                    boolean slide = true;
                    while (slide ) {
                        switch (box[presentBlock.rows][presentBlock.cols]) {
                            case "U":
                            case "S":
                                slide = false;
                                box[presentBlock.rows][presentBlock.cols] = "X" ;
                                break;
                            case "*":
                                score += 1;
                                if (score == finalGoal) {
                                    box[presentBlock.rows][presentBlock.cols] = "P";
                                    slide = false;
                                }else{
                                    box[presentBlock.rows][presentBlock.cols] = " ";
                                }
                                break;
                            case " ":
                                break;
                            case "0":
                            case "#":
                                slide = false;
                                break;
                            default:
                                box[presentBlock.rows][presentBlock.cols] = " ";
                                slide = false;
                                break;
                        }

                        if (slide) {
                            if (isVisitedValid(presentBlock.rows + directionBlock.rows,presentBlock.cols + directionBlock.cols, box)){
                                presentBlock= new Block(presentBlock.rows + directionBlock.rows, presentBlock.cols + directionBlock.cols);
                            } else{
                                slide = false;
                            }
                        }
                    }
                }
            }

            if (score < finalGoal) {
                // Adding adjacents to Queue and reset the grid after visited the path until you meet the goal
                addingAdjacentsToTheQueue(pathdirection, presentBlock, box, q);
                box = newPath(initialBox);

                // Reset the score and clear the moves as it is exploring new paths
                score = 0;
		    penguinMoves.setLength(0);
            }
        }

        handlingOutputFile(output_Name, penguinMoves.toString(), score, box);
    }

    static void addingAdjacentsToTheQueue(PathDirection pathdirection, Block penguinPosition, String box[][], Queue<PathDirection> q) {
        // Add the adjacent cells to Queue
        List<Integer> randomAdjacents = generatingAdjacents();
        for (int j = 0; j < 8; j++) {
            int randomSelection = randomAdjacents.get(j).intValue();

            int adjacentX = penguinPosition.rows + rowVector[randomSelection];
            int adjacentY = penguinPosition.cols + colVector[randomSelection];

            if (isVisitedValid(adjacentX, adjacentY, box)) {
                int penguinDirection = getPenguinMoves(rowVector[randomSelection], colVector[randomSelection]);
                PathDirection newPath = (pathdirection != null) ? new PathDirection(pathdirection.pathdirection + "," + String.valueOf(penguinDirection)) : new PathDirection (String.valueOf(penguinDirection));
                q.add(newPath);
            }
        }
    }

    // Checking the Block whether it is visited or not
    static boolean isVisitedValid(int row, int col, String box[][]) {
        if (row < 0 || col < 0 ||
                row >= box.length || col >= box[0].length) {
            return false;
        }
        if (box[row][col].equalsIgnoreCase("#")) {
            return false;
        }
        return true;
    }
    //Giving the directions in which the pengu should move (N,S,E,W,SE,SW,NE,NW)
    static int getPenguinMoves(int adjacentRow, int adjacentColumn) {
        if (adjacentRow== -1 && adjacentColumn == 0) {
            return 8; 
        } else if (adjacentRow== 0 && adjacentColumn == 1) {
            return 6; 
        } else if (adjacentRow== 1 && adjacentColumn == 0) {
            return 2; 
        } else if (adjacentRow== 0 && adjacentColumn == -1) {
            return 4; 
        } else if (adjacentRow== -1 && adjacentColumn == 1) {
            return 9; 
        } else if (adjacentRow== 1 && adjacentColumn == -1) {
            return 1; 
        } else if (adjacentRow== -1 && adjacentColumn == -1) {
            return 7; 
        } else if (adjacentRow== 1 && adjacentColumn == 1) {
            return 3; 
        } else {
            return 5; 
        }
    }

    static Block getPenguDirection(int moves) {
        if (moves == 8) {
            return new Block(-1, 0);
        } else if (moves == 6) {
            return new Block(0, 1);
        } else if (moves == 2) {
            return new Block(1, 0);
        } else if (moves == 4) {
            return new Block(0, -1);
        } else if (moves == 9) {
            return new Block(-1, 1);
        } else if (moves == 1) {
            return new Block(1, -1);
        } else if (moves == 7) {
            return new Block(-1, -1);
        } else if (moves == 3) {
            return new Block(1, 1);
        } else {
            return new Block(0, 0); 
        }
    }

    static List<Integer> generatingAdjacents() {
        Random random = new Random();
        List<Integer> position = new ArrayList<>();
        for (int j = 0; j < 8; ++j) {
            position.add(j);
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

            // Check whether rows and columns are provided
            String[] rowToken = line.trim().split("\\s+");
            if (rowToken.length != 2) {
                System.out.println("ERROR: Give valid rows and columns");
                return null;
            }

            rows = Integer.parseInt(rowToken[0]);
            columns = Integer.parseInt(rowToken[1]);
            System.out.println("\nNo of rows:" + rowToken[0] + "\nNo of columns:" + rowToken[1]);
            
		    //Initializing the board
            String[][] board = new String[rows][columns];
            line = reader.readLine();
            int rowNum = 0;
            while ((line != null && line.length() > 0)) {
                String[] row_Tokens = line.trim().split("");
                // Checking if every row is specified with correct columns
                if (row_Tokens.length < columns) {
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

    //Check whether the input file exists or not
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
        System.out.println("\n OUTPUT\n");
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
            System.out.println("\nOutput file:" + output_Name);
            if (score > 3) {
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println("\nERROR: Couldn't write to the output file" + output_Name);
        }

    }

    public static String[][] newPath(String[][] s) {
        if (s == null) {
            return null;
        }
        String[][] copyFile = new String[s.length][];
        for (int i = 0; i < s.length; i++) {
            copyFile[i] = s[i].clone();
        }
        return copyFile;
    }
}