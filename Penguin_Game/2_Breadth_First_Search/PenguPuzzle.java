

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Stack;
import java.util.Random;
import java.util.Collections;
import java.util.ArrayList;

public class PenguPuzzle {
    // Direction vectors
    static int dRow[] = {-1, 0, 1, 0, -1, 1, -1, 1};
    static int dCol[] = {0, 1, 0, -1, 1, -1, -1, 1};

    static class Cell {
        int row, column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public String toString() {
            return "{row:" + row + ",col:" + column + "} ";
        }
    }

    static class Path {
        String path;

        public Path(String path) {
            this.path = path;
        }

        @Override
        public String toString() {
            return path;
        }
    }

    // Driver Code
    public static void main(String[] args) {
        System.out.print("\n-----  PUZZLE3 STARTED --> \n");

        // Validate if User passed correct no of arguments, if NOT then EXIT
        if (args.length != 2) {
            System.out.println("ERROR: Input arguments count is INVALID. Please pass two arguments.");
            System.exit(0);
        }

        String inputFileName = args[0];
        String outputFileName = args[1];
        File inputFile = validateInputFileExist(inputFileName);

        // Given input matrix
        String grid[][] = getBoardState(inputFile);
        if (grid == null) {
            System.out.println("ERROR: Error reading INPUT file");
            System.exit(0);
        }


        Cell penguCurrentPosition = getPenguCurrentPosition(grid);
        if (penguCurrentPosition != null) {
            System.out.println("PENGU STARTING POSITION:[" + penguCurrentPosition.row + "," + penguCurrentPosition.column + "]");
            DFS(grid, penguCurrentPosition.row, penguCurrentPosition.column, outputFileName);
        } else {
            System.out.println("ERROR: PENGU NOT FOUND ON BOARD");
            System.exit(0);
        }
    }

    // Function to perform the DFS traversal
    static void DFS(String initialGrid[][], int row, int col, String outputFileName) {
        int score = 0;
        int endGoal = 16;
        StringBuilder penguMoves = new StringBuilder();

        Cell penguInitialPosition = new Cell(row, col);

        // Stores indices of the matrix cells
        Stack<Path> stack = new Stack<>();
        String[][] grid = copyInitialGridForNewPath(initialGrid);
        int depth = 0;

        do {
            depth = depth +1;
            System.out.print("DEPTH -->" + depth + "\n");

            // Start BFS with PENGU starting position
            findAdjacentsAndAddToStack(null, penguInitialPosition, initialGrid, stack);

            // Iterate while the stack is not empty
            while (!stack.isEmpty()) {

                //System.out.print("\nSTACK -->\n");
               // stack.forEach(System.out::println);

                Path path = stack.pop();
               // System.out.print("\nNow Processing from Queue --> " + path.path + "\n");

                String[] pathMoves = path.path.split(",");
                Cell currentCell = null;

                for (String move : pathMoves) {

                    if (!isGoalMet(score, endGoal)) {

                        penguMoves = penguMoves.append(move);
                     //   System.out.print("Pengu Move direction--> " + move + "\n");

                        Cell directionCell = getPenguDirectionByMove(Integer.parseInt(move));

                        // First move during each path PENGU starts at INITIAL Position
                        if (currentCell == null) {
                            grid[penguInitialPosition.row][penguInitialPosition.column] = " ";
                            currentCell = new Cell(penguInitialPosition.row + directionCell.row, penguInitialPosition.column + directionCell.column);
                        } else {
                            currentCell = new Cell(currentCell.row + directionCell.row, currentCell.column + directionCell.column);
                        }

                        boolean canPenguSlide = true;
                        while (canPenguSlide) {
                          //  System.out.print("\nROW:" + currentCell.row + " Column:" + currentCell.column + " Item:" + grid[currentCell.row][currentCell.column] + " ");

                            switch (grid[currentCell.row][currentCell.column]) {
                                case "U":
                                case "S":
                                    canPenguSlide = false;
                                   // System.out.print("OOPS Hazard found. SKIP PATH!!\n");
                                    grid[currentCell.row][currentCell.column] = "X";
                                    break;
                                case "*":
                                    score = score + 1;
                                    grid[currentCell.row][currentCell.column] = " ";
                                    break;
                                case " ":
                                    break;
                                case "0":
                                    canPenguSlide = false;
                                    if (isGoalMet(score, endGoal)) {
                                        grid[currentCell.row][currentCell.column] = "P";
                                    }
                                    break;
                                case "#":
                                    canPenguSlide = false;
                                    break;
                                default:
                                    grid[currentCell.row][currentCell.column] = " ";
                                    canPenguSlide = false;
                                    break;
                            }

                            if (canPenguSlide) {
                                if (isValid(currentCell.row + directionCell.row, currentCell.column + directionCell.column, grid)) {
                                    currentCell = new Cell(currentCell.row + directionCell.row, currentCell.column + directionCell.column);
                                } else {
                                    canPenguSlide = false;
                                }
                            }
                        }
                    }
                }

                // AFTER EVERY PATH FIGURE OUT IF ADJACENT"S NEEDS TO BE ADDED TO STACK
                if (!isGoalMet(score, endGoal)) {

                    if (grid[currentCell.row][currentCell.column] != "X") {
                        if (pathMoves.length < depth) {
                            // Add the adjacent Paths to Stack
                            findAdjacentsAndAddToStack(path, currentCell, grid, stack);
                        } else {
                           // System.out.print("SKIP Adjacent's of current PATH because it reached current MAX DEPTH!!\n");
                        }
                    } else {
                       // System.out.print("SKIP Adjacent's of current PATH! because PENGU DIED!\n");
                    }

                    // reset the grid and after every Path visit if the end goal is not met
                    grid = copyInitialGridForNewPath(initialGrid);

                    // Reset the score as it is exploring new Path
                    score = 0;

                    // Clear moves as we have to track new Path
                    penguMoves.setLength(0);

                } else if (isGoalMet(score, endGoal)) {
                    break; // EXIT DFS
                }
            }
        } while (!isGoalMet(score, endGoal));

        handleOutputFile(outputFileName, penguMoves.toString(), score, grid);
    }

    // Explore adjacent's for a cell and PUSH to Stack
    static void findAdjacentsAndAddToStack(Path path, Cell penguPosition, String grid[][], Stack<Path> stack) {
      //  System.out.println("\nDFS Exploring adjecent's FOR:" + penguPosition.row + "," + penguPosition.column);

        // Add the adjacent cells to Queue
        List<Integer> randomAdj = generateRandomAdjacents();
        for (int i = 0; i < 8; i++) {
            int randomSelection = randomAdj.get(i).intValue();

            int adjx = penguPosition.row + dRow[randomSelection];
            int adjy = penguPosition.column + dCol[randomSelection];

            if (isValid(adjx, adjy, grid)) {
                int penguDirection = getPenguMove(dRow[randomSelection], dCol[randomSelection]);
                Path newPath = (path != null) ? new Path(path.path + "," + String.valueOf(penguDirection)) : new Path(String.valueOf(penguDirection));
                stack.push(newPath);
            }
        }
    }

    // Function to check if a cell is be visited or not
    static boolean isValid(int row, int col, String grid[][]) {
        // If cell lies out of bounds
        if (row < 0 || col < 0 ||
                row >= grid.length || col >= grid[0].length) {
            return false;
        }
        // if cell is a wall cell
        if (grid[row][col].equalsIgnoreCase("#")) {
            return false;
        }
      //  System.out.println("BFS Adjacent Cell:" + row + "," + col);
        return true;
    }

    static int getPenguMove(int adjRow, int adjColumn) {
        if (adjRow == -1 && adjColumn == 0) {
            return 8; //N
        } else if (adjRow == 0 && adjColumn == 1) {
            return 6; //E
        } else if (adjRow == 1 && adjColumn == 0) {
            return 2; //S
        } else if (adjRow == 0 && adjColumn == -1) {
            return 4; //W
        } else if (adjRow == -1 && adjColumn == 1) {
            return 9; //NE
        } else if (adjRow == 1 && adjColumn == -1) {
            return 1; //SW
        } else if (adjRow == -1 && adjColumn == -1) {
            return 7; //NW
        } else if (adjRow == 1 && adjColumn == 1) {
            return 3; //SE
        } else {
            return 5; // NOT MOVED
        }
    }

    static Cell getPenguDirectionByMove(int move) {
        if (move == 8) {
            return new Cell(-1, 0);
        } else if (move == 6) {
            return new Cell(0, 1);
        } else if (move == 2) {
            return new Cell(1, 0);
        } else if (move == 4) {
            return new Cell(0, -1);
        } else if (move == 9) {
            return new Cell(-1, 1);
        } else if (move == 1) {
            return new Cell(1, -1);
        } else if (move == 7) {
            return new Cell(-1, -1);
        } else if (move == 3) {
            return new Cell(1, 1);
        } else {
            return new Cell(0, 0);  // NOT MOVED
        }
    }

    static List<Integer> generateRandomAdjacents() {
        Random rand = new Random();
        List<Integer> positions = new ArrayList<>();
        for (int j = 0; j < 8; ++j) {
            positions.add(j);
        }
        Collections.shuffle(positions, rand);
        return positions;
    }

    public static String[][] getBoardState(File inputFile) {
        /*
            validate if INPUT file has valid INPUT data
         */
        int rows = 0;
        int columns = 0;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            String line = reader.readLine();
            if (line == null) {
                System.out.println("ERROR: Input file did not correctly specifiy NO_ROWS AND NO_COLUMNS");
                return null;
            }

            // Check IF NO_ROWS and NO_COLUMNS specified?
            String[] firstRowTokens = line.trim().split("\\s+");
            if (firstRowTokens.length != 2) {
                System.out.println("ERROR: Input file did not correctly specifiy NO_ROWS or NO_COLUMNS");
                return null;
            }

            // Read NO_ROWS and NO_COLUMNS
            rows = Integer.parseInt(firstRowTokens[0]);
            columns = Integer.parseInt(firstRowTokens[1]);
            System.out.println("\nNO_ROWS:" + firstRowTokens[0] + "\nNO_COLUMNS:" + firstRowTokens[1]);

            //board initial State size
            String[][] board = new String[rows][columns];

            // read next line
            line = reader.readLine();

            // Setup initial board data
            int rowNumber = 0;
            while ((line != null && line.length() > 0)) {
                String[] rowTokens = line.trim().split("");

                // Validate if each row has correct columns specified in first row to play puzzle
                if (rowTokens.length < columns) {
                    System.out.println("ERROR: ROW:" + rowNumber + " specified incorrect data");
                    return null;
                }

                for (int i = 0; i < rowTokens.length; i++) {
                    board[rowNumber][i] = rowTokens[i];
                }
                // read next line
                line = reader.readLine();
                rowNumber = rowNumber + 1;
            }
            return board;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the reader
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

    /*
        validate if INPUT file exist?
        if NOT then EXIT
   */
    public static File validateInputFileExist(String inputFileName) {
        File inputFile = new File(inputFileName);
        boolean inputFileExist = inputFile.exists();
        if (!inputFileExist) {
            System.out.println("ERROR: Input file NOT_FOUND in current directory. Please copy input file to directory where program is running.");
            System.exit(0);
        }
        return inputFile;
    }

    public static void handleOutputFile(String outputFileName, String moves, int score, String[][] board) {
        System.out.println("\n --------- OUTPUT -------\n");
        try {
            File file = new File(outputFileName);
            if (file.createNewFile()) {
                System.out.println("OutFile created: " + file.getName());
            }

            FileWriter myWriter = new FileWriter(file);
            System.out.println("Moves:" + moves);
            myWriter.write(moves + "\n");
            myWriter.write(String.valueOf(score) + "\n");
            System.out.println("Score:" + score + "\n");

            for (int row = 0; row < board.length; row++) {
                StringBuilder eachRow = new StringBuilder();
                for (int col = 0; col < board[row].length; col++) {
                    eachRow.append(board[row][col]);
                }
                myWriter.write(eachRow + "\n");
                System.out.println(eachRow);
            }
            myWriter.close();
            System.out.println("\nOutput Successfully wrote to the output file --> " + outputFileName);
            if (score > 3) {
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println("\nERROR: Error writing output to file: " + outputFileName);
        }
    }

    public static String[][] copyInitialGridForNewPath(String[][] src) {
        if (src == null) {
            return null;
        }
        String[][] copy = new String[src.length][];
        for (int i = 0; i < src.length; i++) {
            copy[i] = src[i].clone();
        }
        return copy;
    }

    public static Cell getPenguCurrentPosition(String[][] board) {

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if ((board[row][col]).equalsIgnoreCase("P") || (board[row][col]).equalsIgnoreCase("0P")) {
                    return new Cell(row, col);
                }
            }
        }
        return null;
    }

    public static boolean isGoalMet(int score, int endGoal){
        return  (score >= endGoal);
    }
}