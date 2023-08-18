// Initializes a number based game board 
// Can do so by writing to a file or returning a 2d list 

import java.util.Arrays;

public class Board {
   private final int[][] coords = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,0},{0,1},{1,-1},{1,0},{1,1}};
    private int[][] board; 
    private int width;
    private int height; 
    private String[][] coverBoard; // This represents the board you see "#" denoting undiscovered spaces

     public Board(int w, int h){
        width = w;
        height = h;
        coverBoard = new String[h][w];  // Initializing the cover board
        for(int i = 0; i< coverBoard.length; i++){
         for(int j = 0; j<coverBoard[0].length; j++){
            coverBoard[i][j] = "#";
         }
        }
     }
     // Getter and setter methods 
     public int getWidth(){
        return width;
     }
     public int getHeight(){
        return height; 
     }
     public void setWidth(int w){
        width = w;
     }
     public void setHeight(int h){
        height = h; 
     }
     public int getValue(int c, int r){
      return board[r][c];
     }

     // Create board as 2d array
     public int[][] InitNewBoard(int amntBombs, int selectX, int selectY ){ // SelectX and selectY represent first guess.
        int maxBombsPerRow = height/amntBombs+1;
        int minBombsPerRow = maxBombsPerRow-2;
        if(minBombsPerRow<0){
         minBombsPerRow=0;
        }
        int[][] newBoard = new int[height][width];
        // KEY
        // -1: Bomb 
        // 0-9: Near-Bomb-Marker
        for(int r = 0; r<height; r++){ // Puts bombs on the board. 
            for(int c = 0; c<width;c++){
               if(amntBombs != 0){
                  if(Math.abs((selectX)-c)>1&&Math.abs((selectY)-r)>1){  // This ensures there are no bombs placed on first click
                     if((int)(Math.random()*21)==0){     // Sets the chance for a bomb to be placed. Bigger the number the more sparse
                        newBoard[r][c] = -1;             // Set a bomb
                        amntBombs--;
                     }
                  }
               }
            }
            if(r==height-1&&amntBombs!=0){  // If not enough bombs have been placed go back to the top
               r=0;
            }
        }
        
        for(int r = 0; r<height; r++){   // This updates the tile tickers for tiles near bombs
         for(int c= 0; c<width; c++){
            int num = newBoard[r][c];
            if(num ==  -1){
               for(int i = -1; i<=1;i++){
                  for(int j = -1; j<=1;j++){
                     if(((r+i>=0&&c+j>=0)&&(r+i<height&&c+j<width))){   // Makes sure we don't get out of bounds errors when updating tickers. 
                        if(newBoard[r+i][c+j]!=-1){
                           newBoard[r+i][c+j]++;
                        }
                     }
                  }
               }
            }
         }
        }
        board = newBoard; 
        return newBoard;
     }
     public boolean flag(int r, int c){   // Flags a block on the coverboard 
      if(coverBoard[r][c].equals("#")){
         coverBoard[r][c] = "F";
         return true;
      }
      return false; 
     }
     public boolean unFlag(int r, int c){  // Un flags a block if there is a flag in specfied position
      if(coverBoard[r][c].equals("F")){
         coverBoard[r][c]= "#";
         return true;
      }
      return false; 
     }

     public void exposeSpace(int r, int c ){ // Exposes a block in specified position
      if(coverBoard[r][c].equals("#")&&(board[r][c]!=0&&board[r][c]!=-1)){
         coverBoard[r][c] = board[r][c] + "";
      } else if(board[r][c]==0&&coverBoard[r][c].equals("#")){
         coverBoard[r][c] = board[r][c]+"";
         uncoverEmptySpaces(r, c);
      } else if(board[r][c]==-1){
         coverBoard[r][c] = "B";
      }
     }
     private void uncoverEmptySpaces(int r, int c){ // Uses recursion to uncover all empty spaces touching each other 
      for(int[] rc: coords){
         if(((r+rc[0]>=0&&c+rc[1]>=0)&&(r+rc[0]<height&&c+rc[1]<width)))
         exposeSpace(r+rc[0],c+rc[1]);
      }
     }
     public void printBoard(){  // Prints the user board what's known as the cover board
      String line = "";
      
      int c = 1;
      for(String[] arr : coverBoard){
         line = "";
         
         for(String str: arr){
            line += " "+str;
         }
         line += "   "+c;
         c++;
         System.out.println(line);
      }
      
      line = "";
      System.out.println();
      for(int i = 1; i<11; i++){
         line+=" "+i;
      }
      System.out.println(line);
     }
     // Returns a formatted board that can be printed. 
     // Is automatically called with println() or print()
     public String toString(){
        String formattedBoard = "";
        for(int[] arr:board){
            formattedBoard += Arrays.toString(arr);
            formattedBoard += "\n";
        }
        return formattedBoard;
     }
}
