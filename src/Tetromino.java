import java.util.Random;

public class Tetromino {
   private char type; 
   private Tile[][] tileMatrix; 
   private Point bottomLeftCell = new Point();
   private Tile[][] minBoundedTileMatrix;
   private Point minBoundedTileMatrixPosition = new Point();

   public static int widthG, heightG; 
   private boolean canBeRotated;

   public Tetromino(char type) {
      this.type = type;
      
      Point[] occupiedTiles = new Point[4]; 
      int n = 0;
      if (type == 'I') {
         n = 4; 
         occupiedTiles[0] = new Point(1, 0); 
         occupiedTiles[1] = new Point(1, 1);
         occupiedTiles[2] = new Point(1, 2);
         occupiedTiles[3] = new Point(1, 3);
      } else if (type == 'O') {
         n = 2; 
         occupiedTiles[0] = new Point(0, 0); 
         occupiedTiles[1] = new Point(1, 0);
         occupiedTiles[2] = new Point(0, 1);
         occupiedTiles[3] = new Point(1, 1);
      } else if (type == 'Z') {
         n = 3; 
         occupiedTiles[0] = new Point(0, 1);
         occupiedTiles[1] = new Point(1, 1);
         occupiedTiles[2] = new Point(1, 2);
         occupiedTiles[3] = new Point(2, 2);
         
      } else if (type == 'S') {
         n = 3;
         occupiedTiles[0] = new Point(1, 0);
         occupiedTiles[1] = new Point(2, 0);
         occupiedTiles[2] = new Point(0, 1);
         occupiedTiles[3] = new Point(1, 1);
      } else if (type == 'L') {
         n = 3;
         occupiedTiles[0] = new Point(0, 0);
         occupiedTiles[1] = new Point(0, 1);
         occupiedTiles[2] = new Point(1, 2);
         occupiedTiles[3] = new Point(0, 2);
      } else if (type == 'J') {
         n = 3;
         occupiedTiles[0] = new Point(1, 0);
         occupiedTiles[1] = new Point(1, 1);
         occupiedTiles[2] = new Point(0, 2);
         occupiedTiles[3] = new Point(1, 2);
         
      } else if (type == 'T') {
         n = 3;
         occupiedTiles[0] = new Point(1, 0);
         occupiedTiles[1] = new Point(0, 1);
         occupiedTiles[2] = new Point(1, 1);
         occupiedTiles[3] = new Point(2, 1);
      }

         
      tileMatrix = new Tile[n][n];
      for (Point tilePosition : occupiedTiles) { 
         int colIndex = tilePosition.getX(), rowIndex = tilePosition.getY();
         tileMatrix[rowIndex][colIndex] = new Tile();
      }
      bottomLeftCell.setY(heightG - 1);
      Random random = new Random();
      bottomLeftCell.setX(random.nextInt(widthG - n + 1));
   }

   public Point getCellPosition(int row, int col) {
      int n = tileMatrix.length; 
      Point position = new Point();
      position.setX(bottomLeftCell.getX() + col);
      position.setY(bottomLeftCell.getY() + (n - 1) - row);
      return position;
   }

   public void createMinBoundedTileMatrix() {
      int n = tileMatrix.length;       int minRow = n - 1, maxRow = 0, minCol = n - 1, maxCol = 0;
      for (int row = 0; row < n; row++) {
         for (int col = 0; col < n; col++) {
            if (tileMatrix[row][col] != null) {
               if (row < minRow)
                  minRow = row;
               if (row > maxRow)
                  maxRow = row;
               if (col < minCol)
                  minCol = col;
               if (col > maxCol)
                  maxCol = col;
            }
         }
      }
      minBoundedTileMatrix = new Tile[maxRow - minRow + 1][maxCol - minCol + 1];
      for (int row = minRow; row <= maxRow; row++) {
         for (int col = minCol; col <= maxCol; col++) {
            if (tileMatrix[row][col] != null) {
               int rowIndex = row - minRow, colIndex = col - minCol;
               minBoundedTileMatrix[rowIndex][colIndex] = tileMatrix[row][col];
            }
         }
      }
      int blcX = bottomLeftCell.getX(), blcY = bottomLeftCell.getY();
      minBoundedTileMatrixPosition.setX(blcX + minCol);
      minBoundedTileMatrixPosition.setY(blcY + (n - 1) - maxRow);
   }

   public Tile[][] getMinBoundedTileMatrix() {
      return minBoundedTileMatrix;
   }

   public Point getMinBoundedTileMatrixPosition() {
      return minBoundedTileMatrixPosition;
   }

   public void draw() {
      int n = tileMatrix.length; 
      for (int row = 0; row < n; row++) {
         for (int col = 0; col < n; col++) {
            if (tileMatrix[row][col] != null) {
               // get the position of the tile
               Point position = getCellPosition(row, col);
               if (position.getY() < heightG)
                  tileMatrix[row][col].draw(position);
            }         }      }   }

   public boolean move(String direction, GameGrid gameGrid) {
      if (!canBeMoved(direction, gameGrid))
         return false; 
      if (direction == "left")
         bottomLeftCell.setX(bottomLeftCell.getX() - 1);
      else if (direction == "right")
         bottomLeftCell.setX(bottomLeftCell.getX() + 1);
      else if (direction == "alldown") {
    	  while (canBeMoved( "down" , gameGrid)) {
    	     	 bottomLeftCell.setY(bottomLeftCell.getY() - 1 );
    	  }
      }
      else 
         bottomLeftCell.setY(bottomLeftCell.getY() - 1);
      return true; 
   }

   public void rotateClockwise() {
      int n = tileMatrix.length; 
      Tile[][] newTileMatrix = new Tile[n][n];
      for (int row = 0; row < n; row++) {
         for (int col = 0; col < n; col++) {
            newTileMatrix[row][col] = tileMatrix[n - 1 - col][row];
         }
      }
      tileMatrix = newTileMatrix;
   }

   public void rotateCounterclockwise() {
      int n = tileMatrix.length; 
      Tile[][] newTileMatrix = new Tile[n][n];
      for (int row = 0; row < n; row++) {
         for (int col = 0; col < n; col++) {
            newTileMatrix[row][col] = tileMatrix[col][n - 1 - row];
         }
      }
      tileMatrix = newTileMatrix;
   }



   public boolean canBeMoved(String dir, GameGrid gameGrid) {
      int n = tileMatrix.length; 
      if (dir == "left" || dir == "right") {
         for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
               if (dir == "left" && tileMatrix[row][col] != null) {
                  Point leftmost = getCellPosition(row, col);
                  if (leftmost.getY() >= heightG)
                     break;
                  if (leftmost.getX() == 0)
                     return false;
                  
                  if (gameGrid.isOccupied(leftmost.getY(), leftmost.getX() - 1))
                     return false;
                  break; 
               }
               else if (dir == "right" && tileMatrix[row][n - 1 - col] != null) {
                  Point rightmost = getCellPosition(row, n - 1 - col);
                  if (rightmost.getY() >= heightG)
                     break;
                  if (rightmost.getX() == widthG - 1)
                     return false;
                  if (gameGrid.isOccupied(rightmost.getY(), rightmost.getX() + 1))
                     return false;
                  break; 
               }
            }
         }
      }
      else {
         for (int col = 0; col < n; col++) {
            for (int row = n - 1; row > -1; row--) {
               if (tileMatrix[row][col] != null) {
                  Point bottommost = getCellPosition(row, col);
                  if (bottommost.getY() > heightG)
                     break;
                  if (bottommost.getY() == 0)
                     return false;
                  if (gameGrid.isOccupied(bottommost.getY() - 1, bottommost.getX()))
                     return false;
                  break; 
               }
            }
         }
      }
      return true; 
   }

   public char getType() {
      return type;
   }
}
