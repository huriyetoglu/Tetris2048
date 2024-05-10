import java.awt.Color; 
import java.awt.Font; 
import java.awt.event.KeyEvent; 
import java.util.Random;

public class Tetris2048 {
   static boolean isRestarted = false;
   public static void main(String[] args) throws InterruptedException {
      int gridH = 14, gridW = 14;
      int canvasH = 40 * gridH, canvasW = 40 * gridW + gridW/3 * 40;
      StdDraw.setCanvasSize(canvasW, canvasH);
      StdDraw.setXscale(-0.5, gridW + ((double) gridW / 3) - 0.5);
      StdDraw.setYscale(-0.5, gridH - 0.5);
      StdDraw.enableDoubleBuffering();

      Tetromino.heightG = gridH;
      Tetromino.widthG = gridW;

      GameGrid grid = new GameGrid(gridH, gridW);
      Tetromino currentTetromino = createTetromino();
      grid.setCurrentTetromino(currentTetromino);

      Tetromino nextTetromino = createTetromino();
      grid.setNextTetromino(nextTetromino);
      if(isRestarted == false){
         displayGameMenu(gridH, gridW);
      }
      int iterationCount = 0; 
      boolean isPaused = false;
      while (true) {
         if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
            currentTetromino.move("left", grid);
            StdDraw.pause(20);
         }
         else if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
            currentTetromino.move("right", grid);
            StdDraw.pause(20);
         }
         else if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {
            currentTetromino.move("down", grid);
            StdDraw.pause(20);
         }
         
         else if (StdDraw.isKeyPressed(KeyEvent.VK_S)) {
            currentTetromino.move("alldown", grid);
            StdDraw.pause(20);
         }

         else if (StdDraw.isKeyPressed(KeyEvent.VK_D)) {
            currentTetromino.rotateClockwise();
            StdDraw.pause(50);
         }
         else if (StdDraw.isKeyPressed(KeyEvent.VK_A)) {
            currentTetromino.rotateCounterclockwise();
            StdDraw.pause(50);
         }
         else if (StdDraw.isKeyPressed(KeyEvent.VK_ESCAPE)){
            StdDraw.pause(50);
            displayPauseMenu(gridH, gridW);
         }

         boolean success = true;
         if (iterationCount % 10 == 0)
            success = currentTetromino.move("down", grid);
         iterationCount++;

         if (!success) {
            currentTetromino.createMinBoundedTileMatrix();
            Tile[][] tiles = currentTetromino.getMinBoundedTileMatrix();
            Point pos = currentTetromino.getMinBoundedTileMatrixPosition();
            boolean gameOver = grid.updateGrid(tiles, pos);
            if (gameOver){
               displayGameOverMenu(gridH, gridW, false);
               break;
            }
            if (grid.getLargestTileNumber() >= 2048) {
               displayGameOverMenu(gridH, gridW, true);
            }
            currentTetromino = nextTetromino;
            grid.setCurrentTetromino(nextTetromino);
            nextTetromino = createTetromino();
            grid.setNextTetromino(nextTetromino);
            grid.clearLines();
         }

         grid.display();

      }

      System.out.println("Game over!");
   }

   public static Tetromino createTetromino() {
      char[] tetrominoTypes = { 'I', 'O', 'Z', 'S', 'J', 'L', 'T' };
      Random random = new Random();
      int randomIndex = random.nextInt(tetrominoTypes.length);
      char randomType = tetrominoTypes[randomIndex];
      return new Tetromino(randomType);
   }

   public static void displayGameMenu(int heightG, int widthG) {
      Color backgroundColor = new Color(42, 69, 99);
      Color buttonColor = new Color(255,99,71);
      Color textColor = new Color(72,61,139);
      StdDraw.clear(backgroundColor);
      String imgFile = "images/menu.png";
      widthG = widthG + widthG / 4;
      double imgCenterX = (widthG - 1) / 2.0, imgCenterY = heightG - 4;
      StdDraw.picture(imgCenterX, imgCenterY, imgFile);
      double buttonW = widthG - 1.5, buttonH = 2;
      double buttonX = imgCenterX, buttonY = 5;
      StdDraw.setPenColor(buttonColor);
      StdDraw.filledRectangle(buttonX, buttonY, buttonW / 2.5 , buttonH / 2.5 );
      Font font = new Font("Verdana", Font.BOLD, 25);
      StdDraw.setFont(font);
      StdDraw.setPenColor(textColor);
      String textToDisplay = "CLICK TO START GAME";
      StdDraw.text(buttonX, buttonY, textToDisplay);
      while (true) {
         StdDraw.show();
         StdDraw.pause(50);
         if (StdDraw.isMousePressed()) {
            double mouseX = StdDraw.mouseX(), mouseY = StdDraw.mouseY();
            if (mouseX > buttonX - buttonW / 2 && mouseX < buttonX + buttonW / 2)
               if (mouseY > buttonY - buttonH / 2 && mouseY < buttonY + buttonH / 2)
                  break; 
         }
      }
   }

   public static void displayPauseMenu(int heightG, int widthG) throws InterruptedException {
      Color backgroundColor = new Color(42, 69, 99);
      Color buttonColor = new Color(255,99,71);
      Color textColor = new Color(72,61,139);
      StdDraw.clear(backgroundColor);
      String imgFile = "images/pause.png";
      widthG = widthG + widthG / 4;
      double imgCenterX = (widthG - 1) / 2.0, imgCenterY = heightG - 5;
      StdDraw.picture(imgCenterX, imgCenterY + 2, imgFile);
      double buttonW = widthG - 1.5, buttonH = 2;
      double buttonX = imgCenterX, buttonY = 5;
      StdDraw.setPenColor(buttonColor);

      StdDraw.filledRectangle(buttonX, buttonY + 2.5, buttonW / 4, buttonH / 4);
      StdDraw.filledRectangle(buttonX, buttonY, buttonW / 4, buttonH / 4);
      StdDraw.filledRectangle(buttonX, buttonY - 2.5, buttonW / 4, buttonH / 4);

      Font font = new Font("Verdana", Font.BOLD, 25);
      StdDraw.setFont(font);
      StdDraw.setPenColor(textColor);

      String textToDisplay = "UNPAUSE GAME";
      StdDraw.text(buttonX, buttonY + 2.5, textToDisplay);

      textToDisplay = "RESTART GAME";
      StdDraw.text(buttonX, buttonY, textToDisplay);

      textToDisplay = "EXIT GAME";
      StdDraw.text(buttonX, buttonY - 2.5, textToDisplay);

      while(true){
         StdDraw.show();
         StdDraw.pause(50);

         if (StdDraw.isMousePressed()) {
            double mouseX = StdDraw.mouseX(), mouseY = StdDraw.mouseY();
            if (mouseX > buttonX - buttonW / 2 && mouseX < buttonX + buttonW / 2)
               if (mouseY > (buttonY + 2.5) - buttonH / 2 && mouseY < (buttonY + 2.5) + buttonH / 2)
                  break; 

               else if (mouseY > (buttonY) - buttonH / 2 && mouseY < (buttonY) + buttonH / 2){
                  restartGame();
               }

               else if(mouseY > (buttonY - 2.5) - buttonH / 2 && mouseY < (buttonY - 2.5) + buttonH / 2){
                  System.exit(0); 
               }
         }

         else if (StdDraw.isKeyPressed(KeyEvent.VK_ESCAPE)){
            StdDraw.pause(5);
            break;
         }
      }
   }
   public static void displayGameOverMenu(int heightG, int widthG, boolean win) throws InterruptedException {
      Color buttonColor = new Color(0, 0, 0);
      Color textColor = new Color(255, 255, 2);
      String imgFile = "images/lose.png";
      if (win) {
         imgFile = "images/win.png";
      }
      widthG = widthG + widthG / 4;
      double imgCenterX = (widthG - 1) / 2.0, imgCenterY = heightG - 7;
      StdDraw.picture(imgCenterX, imgCenterY + 2, imgFile);
      double buttonW = widthG - 1.5, buttonH = 2;
      double buttonX = imgCenterX, buttonY = 5;
      StdDraw.setPenColor(buttonColor);

      StdDraw.filledRectangle(buttonX, buttonY, buttonW / 4, buttonH / 4);
      StdDraw.filledRectangle(buttonX, buttonY - 2.5, buttonW / 4, buttonH / 4);

      Font font = new Font("Verdana", Font.BOLD, 25);
      StdDraw.setFont(font);
      StdDraw.setPenColor(textColor);

      String textToDisplay = "New Game";
      StdDraw.text(buttonX, buttonY, textToDisplay);

      textToDisplay = "Exit Game";
      StdDraw.text(buttonX, buttonY - 2.5, textToDisplay);

      while(true){
         StdDraw.show();
         StdDraw.pause(50);

         if (StdDraw.isMousePressed()) {
            double mouseX = StdDraw.mouseX(), mouseY = StdDraw.mouseY();
            if (mouseX > buttonX - buttonW / 2 && mouseX < buttonX + buttonW / 2)
               if (mouseY > (buttonY) - buttonH / 2 && mouseY < (buttonY) + buttonH / 2){
                  restartGame();
               }

               else if(mouseY > (buttonY - 2.5) - buttonH / 2 && mouseY < (buttonY - 2.5) + buttonH / 2){
                  System.exit(0); // exit the application.
               }
         }
      }
   }
   public static void restartGame() throws InterruptedException {
      isRestarted = true;
      main(null);
   }
}
