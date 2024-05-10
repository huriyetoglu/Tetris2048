import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class GameGrid {

   private int heightG, widthG; 
   private Tile[][] tileMatrix; 
   private Tetromino currentTetromino = null;
   private Tetromino nextTetromino = null;
   private boolean gameOver = false;
   private Color emptyCellColor; 
   private Color lineColor; 
   private Color boundaryColor; 
   private double lineThickness; 
   private double boxThickness; 
   private int score; 
   

   public GameGrid(int gridH, int gridW) {
      heightG = gridH;
      widthG = gridW;
      tileMatrix = new Tile[heightG][widthG];
      emptyCellColor = new Color(230,230,250);
      lineColor = new Color(0, 0, 0);
      boundaryColor = new Color(0, 100, 200);
      lineThickness = 0.002;
      boxThickness = 10 * lineThickness;
      score = 0;
     
   }

   public void setCurrentTetromino(Tetromino currentTetromino) {
      this.currentTetromino = currentTetromino;
   }

   public void setNextTetromino(Tetromino nextTetromino) {
      this.nextTetromino = nextTetromino;
   }

   public void display() {
      StdDraw.clear(emptyCellColor);
      drawGrid();
      drawSidebar();
      if (currentTetromino != null)
         currentTetromino.draw();
      drawBoundaries();
      StdDraw.show();
      StdDraw.pause(50);
   }

   public void drawSidebar() {
	   
      Color backgroundColor = new Color(42, 69, 99);
      Color buttonColor = new Color(139,0,139);
      Color textColor = new Color(31, 160, 239);
      double totalwidthG = widthG + widthG / 3.0;
      double sidebarCenterX = totalwidthG - (totalwidthG / 3.0) / 2.0;
      double sidebarCenterY = heightG - (heightG / 3.0) / 2.0;
      
      StdDraw.setPenColor(buttonColor);
      Font font = new Font("Verdana", Font.BOLD, 22);
      StdDraw.setFont(font);
      StdDraw.setPenColor(buttonColor);
      String textToDisplay = "SCORE:";
      StdDraw.text(sidebarCenterX, sidebarCenterY, textToDisplay);
      textToDisplay = String.valueOf(score);
      
      StdDraw.text(sidebarCenterX, sidebarCenterY - 1, textToDisplay);
      textToDisplay = "NEXT";
      StdDraw.text(sidebarCenterX, sidebarCenterY - 3, textToDisplay);
      textToDisplay = "TETROMINO";
      StdDraw.text(sidebarCenterX, sidebarCenterY - 4, textToDisplay);
      StdDraw.setPenColor(255, 222, 173);
      switch (nextTetromino.getType()) {
         case 'I':
        	 
            StdDraw.filledRectangle(sidebarCenterX, sidebarCenterY - 6, 0.4, 0.4);
            StdDraw.filledRectangle(sidebarCenterX, sidebarCenterY - 6.8, 0.4, 0.4);
            StdDraw.filledRectangle(sidebarCenterX, sidebarCenterY - 7.6, 0.4, 0.4);
            StdDraw.filledRectangle(sidebarCenterX, sidebarCenterY - 8.4, 0.4, 0.4);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.rectangle(sidebarCenterX, sidebarCenterY - 6, 0.4, 0.4);
            StdDraw.rectangle(sidebarCenterX, sidebarCenterY - 6.8, 0.4, 0.4);
            StdDraw.rectangle(sidebarCenterX, sidebarCenterY - 7.6, 0.4, 0.4);
            StdDraw.rectangle(sidebarCenterX, sidebarCenterY - 8.4, 0.4, 0.4);
            break;
            
         case 'O':
        	 
            StdDraw.filledRectangle(sidebarCenterX - 0.4, sidebarCenterY - 6, 0.4, 0.4);
            StdDraw.filledRectangle(sidebarCenterX - 0.4, sidebarCenterY - 6.8, 0.4, 0.4);
            StdDraw.filledRectangle(sidebarCenterX + 0.4, sidebarCenterY - 6, 0.4, 0.4);
            StdDraw.filledRectangle(sidebarCenterX + 0.4, sidebarCenterY - 6.8,0.4, 0.4);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.rectangle(sidebarCenterX - 0.4, sidebarCenterY - 6, 0.4, 0.4);
            StdDraw.rectangle(sidebarCenterX - 0.4, sidebarCenterY - 6.8, 0.4, 0.4);
            StdDraw.rectangle(sidebarCenterX + 0.4, sidebarCenterY - 6, 0.4, 0.4);
            StdDraw.rectangle(sidebarCenterX + 0.4, sidebarCenterY - 6.8, 0.4, 0.4);
            break;
         case 'Z':
            StdDraw.filledRectangle(sidebarCenterX, sidebarCenterY - 6, 0.4, 0.4);
            StdDraw.filledRectangle(sidebarCenterX, sidebarCenterY - 6.8, 0.4, 0.4);
            StdDraw.filledRectangle(sidebarCenterX - 0.8, sidebarCenterY - 6, 0.4, 0.4);
            StdDraw.filledRectangle(sidebarCenterX + 0.8, sidebarCenterY - 6.8, 0.4, 0.4);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.rectangle(sidebarCenterX, sidebarCenterY - 6, 0.4, 0.4);
            StdDraw.rectangle(sidebarCenterX, sidebarCenterY - 6.8, 0.4, 0.4);
            StdDraw.rectangle(sidebarCenterX - 0.8, sidebarCenterY - 6, 0.4, 0.4);
            StdDraw.rectangle(sidebarCenterX + 0.8, sidebarCenterY - 6.8, 0.4, 0.4);
            break;
         case 'S':
            StdDraw.filledRectangle(sidebarCenterX, sidebarCenterY - 6, 0.4, 0.4);
            StdDraw.filledRectangle(sidebarCenterX, sidebarCenterY - 6.8, 0.4, 0.4);
            StdDraw.filledRectangle(sidebarCenterX + 0.8, sidebarCenterY - 6, 0.4, 0.4);
            StdDraw.filledRectangle(sidebarCenterX - 0.8, sidebarCenterY - 6.8, 0.4, 0.4);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.rectangle(sidebarCenterX, sidebarCenterY - 6, 0.4, 0.4);
            StdDraw.rectangle(sidebarCenterX, sidebarCenterY - 6.8, 0.4, 0.4);
            StdDraw.rectangle(sidebarCenterX + 0.8, sidebarCenterY - 6, 0.4, 0.4);
            StdDraw.rectangle(sidebarCenterX - 0.8, sidebarCenterY - 6.8, 0.4, 0.4);
            break;
         case 'L':
            StdDraw.filledRectangle(sidebarCenterX - 0.4, sidebarCenterY - 6, 0.4, 0.4);
            StdDraw.filledRectangle(sidebarCenterX - 0.4, sidebarCenterY - 6.8, 0.4, 0.4);
            StdDraw.filledRectangle(sidebarCenterX - 0.4, sidebarCenterY - 7.6, 0.4, 0.4);
            StdDraw.filledRectangle(sidebarCenterX + 0.4, sidebarCenterY - 7.6, 0.4, 0.4);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.rectangle(sidebarCenterX - 0.4, sidebarCenterY - 6, 0.4, 0.4);
            StdDraw.rectangle(sidebarCenterX - 0.4, sidebarCenterY - 6.8, 0.4, 0.4);
            StdDraw.rectangle(sidebarCenterX - 0.4, sidebarCenterY - 7.6, 0.4, 0.4);
            StdDraw.rectangle(sidebarCenterX + 0.4, sidebarCenterY - 7.6, 0.4, 0.4);

            break;
         case 'J':
            StdDraw.filledRectangle(sidebarCenterX + 0.4, sidebarCenterY - 6, 0.4, 0.4);
            StdDraw.filledRectangle(sidebarCenterX + 0.4, sidebarCenterY - 6.8, 0.4, 0.4);
            StdDraw.filledRectangle(sidebarCenterX + 0.4, sidebarCenterY - 7.6, 0.4, 0.4);
            StdDraw.filledRectangle(sidebarCenterX - 0.4, sidebarCenterY - 7.6, 0.4, 0.4);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.rectangle(sidebarCenterX + 0.4, sidebarCenterY - 6, 0.4, 0.4);
            StdDraw.rectangle(sidebarCenterX + 0.4, sidebarCenterY - 6.8, 0.4, 0.4);
            StdDraw.rectangle(sidebarCenterX + 0.4, sidebarCenterY - 7.6, 0.4, 0.4);
            StdDraw.rectangle(sidebarCenterX - 0.4, sidebarCenterY - 7.6, 0.4, 0.4);

            break;
         case 'T':
            StdDraw.filledRectangle(sidebarCenterX, sidebarCenterY - 6, 0.4, 0.4);
            StdDraw.filledRectangle(sidebarCenterX, sidebarCenterY - 6.8, 0.4, 0.4);
            StdDraw.filledRectangle(sidebarCenterX + 0.8, sidebarCenterY - 6.8, 0.4, 0.4);
            StdDraw.filledRectangle(sidebarCenterX - 0.8, sidebarCenterY - 6.8, 0.4, 0.4);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.rectangle(sidebarCenterX, sidebarCenterY - 6, 0.4, 0.4);
            StdDraw.rectangle(sidebarCenterX, sidebarCenterY - 6.8, 0.4, 0.4);
            StdDraw.rectangle(sidebarCenterX + 0.8, sidebarCenterY - 6.8, 0.4, 0.4);
            StdDraw.rectangle(sidebarCenterX - 0.8, sidebarCenterY - 6.8, 0.4, 0.4);

            break;
      }
      
      
   }

   public void drawGrid() {
      
      for (int row = 0; row < heightG; row++)
         for (int col = 0; col < widthG; col++)
            if (tileMatrix[row][col] != null)
               tileMatrix[row][col].draw(new Point(col, row));
      StdDraw.setPenColor(lineColor);
      StdDraw.setPenRadius(lineThickness);
      double startX = -0.5, endX = widthG - 0.5;
      double startY = -0.5, endY = heightG - 0.5;
      for (double x = startX + 1; x < endX; x++) 
         StdDraw.line(x, startY, x, endY);
      for (double y = startY + 1; y < endY; y++) 
         StdDraw.line(startX, y, endX, y);
      StdDraw.setPenRadius(); 
   }

   public void drawBoundaries() {
      StdDraw.setPenColor(boundaryColor); 
      StdDraw.setPenRadius(boxThickness);
      double centerX = widthG / 2 - 0.5, centerY = heightG / 2 - 0.5;
      StdDraw.rectangle(centerX, centerY, widthG / 2, heightG / 2);
      StdDraw.setPenRadius(); 
   }

   public boolean isOccupied(int row, int col) {
      if (!isInside(row, col))
         return false;
      return tileMatrix[row][col] != null;
   }

   public boolean isInside(int row, int col) {
      if (row < 0 || row >= heightG)
         return false;
      if (col < 0 || col >= widthG)
         return false;
      return true;
   }

   
   public boolean updateGrid(Tile[][] tilesToLock, Point blcPosition) {
      currentTetromino = null;
      int nRows = tilesToLock.length, nCols = tilesToLock[0].length;
      for (int col = 0; col < nCols; col++) {
         for (int row = 0; row < nRows; row++) {
            // place each tile onto the game grid
            if (tilesToLock[row][col] != null) {
               // compute the position of the tile on the game grid
               Point pos = new Point();
               pos.setX(blcPosition.getX() + col);
               pos.setY(blcPosition.getY() + (nRows - 1) - row);
               if (isInside(pos.getY(), pos.getX()))
                  tileMatrix[pos.getY()][pos.getX()] = tilesToLock[row][col];
               else
                  gameOver = true;
            }
         }
      }
      mergeTiles();
      return gameOver;
   }

   public void mergeTiles() {
      boolean merged = true;
      try {
         while (merged) {
            merged = false;
            for (int w = 0; w < tileMatrix[0].length; w++) {
               for (int h = 0; h < tileMatrix.length; h++) {
                  if (tileMatrix[h][w] == null || tileMatrix[h+1][w] == null) {
                     continue;
                  }
                  if (tileMatrix[h][w].getNumber() == tileMatrix[h+1][w].getNumber()) {
                     score += tileMatrix[h][w].getNumber() * 2;
                     tileMatrix[h][w].setNumber(tileMatrix[h][w].getNumber() * 2);
                     tileMatrix[h+1][w] = null;
                     int above = 2;
                     while (tileMatrix[h+above][w] != null) {
                        tileMatrix[h+above-1][w] = tileMatrix[h+above][w];
                        tileMatrix[h+above][w] = null;
                        above++;
                     }
                     merged = true;
                     break;
                  }
               }
               if (merged) {
                  break;
               }
            }
            display();
         }
      } catch (Exception e) {
         System.out.println("Couldn't merge outside tiles!");
      }
      destroyFloatingTiles();
   }

   public void destroyFloatingTiles() {
      boolean[][] visited = new boolean[heightG][widthG];

      for (int col = 0; col < widthG; col++) {
         if (tileMatrix[0][col] != null && !visited[0][col]) {
            dfs(0, col, visited);
         }
      }

      for (int row = 1; row < heightG; row++) {
         for (int col = 0; col < widthG; col++) {
            if (tileMatrix[row][col] != null && !visited[row][col]) {
               score += tileMatrix[row][col].getNumber();
               tileMatrix[row][col] = null;
            }
         }
      }
   }

   private void dfs(int row, int col, boolean[][] visited) {
      if (row < 0 || row >= heightG || col < 0 || col >= widthG) {
         return;
      }

      if (visited[row][col] || tileMatrix[row][col] == null) {
         return;
      }

      visited[row][col] = true;

      dfs(row - 1, col, visited); // Up
      dfs(row + 1, col, visited); // Down
      dfs(row, col - 1, visited); // Left
      dfs(row, col + 1, visited); // Right
   }

   public void clearLines(){

      boolean isFilled;

      for(int r = heightG - 1; r >= 0; r--){

         isFilled = true;

         for(int c = 0; c < widthG; c++){
            if(tileMatrix[r][c] == null){
               isFilled = false;
               break;
            }
         }

         if(isFilled){
            clearLine(r);
            shiftDown(r);
         }
      }
   }

   public void clearLine(int r){
      int scoreToBeAdded = 0;
      for(int i = 0; i < widthG; i++){
         scoreToBeAdded += tileMatrix[r][i].getNumber();
         tileMatrix[r][i] = null;
      }

      score += scoreToBeAdded;
   }

   public void shiftDown(int r){
      for(int row = r; row < heightG - 1; row++){
         for(int col = 0; col < widthG; col++){
            tileMatrix[row][col] = tileMatrix[row + 1][col];
         }
      }
   }

   public int getLargestTileNumber() {
      int maxNum = 0;
      for (int i = 0; i < heightG; i++) {
         for (int j = 0; j < widthG; j++) {
            if (tileMatrix[i][j] != null && tileMatrix[i][j].getNumber() > maxNum) {
               maxNum = tileMatrix[i][j].getNumber();
            }
         }
      }
      return maxNum;
   }

}