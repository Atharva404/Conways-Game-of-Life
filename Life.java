import java.util.*;
import java.awt.*;

public class Life{

  private int[][] grid;// holds organisms, 0 if dead, 1 if alive
  
  Scanner in = new Scanner(System.in);//get input from user

  private int gen = 0;

  public static void main(String[] args){
     new Life().start();
  }
  /** This method launches the following process:
   *    1.  Prompts the user to input the rows, cols and number of organisms to be 
   *        randomly placed in the grid.
   *    2.  Repeatedly prints menu that allows the user to 
   *          a)  advance to next generation or
   *          b)  add or remove an organism at a specified location
   *          c)  count the number of organisms in a specified row, col 
   *              or the whole grid
   *          d)  quit
   */
  public void start(){
    //  prompt for size of grid and number of organisms
    Scanner scanner = new Scanner(System.in);
   System.out.println("Row (1-10): ");
    int r = scanner.nextInt();
    System.out.println("Col (1-10): ");
    int c = scanner.nextInt();
    System.out.println("Number of organisms: ");
    int answer = scanner.nextInt();
    int rows=r, cols=c, lifes=answer;

    setUpGrid(rows, cols, lifes);
    System.out.println("Your grid of "+rows+ " rows and "+cols+" cols and\n"+
                       lifes+" random organisms is below");
    display();
    int response = -1;
    do{
      response = menu();
      if(response == 1){
        generate();
        display();
      }
      else if(response == 2){
        int[] rcArr = promptForRowAndCol();
        flip(rcArr);
        display();
      }
      else if(response == 3){
        int[] rcArr = promptForRowAndCol();
        int num = countOrganisms(rcArr);
        System.out.println("Count:" + num);
      }

    }while(response != 4);
  }
  
  /**This method prints a menu to the console, prompting the user for an int choice that
   * matches the following choices:  1 to go to the next generation and display,
   * 2 to prompt to edit the grid, 3 to count organisms in a row or col or the entire grid,
   * 4 to quit.  This method should check the user's answer and if the user entered an invalid 
   * choice, then the user should be told this and reprompted.
   */
  public int menu() {
    int answer = 0;
    Scanner scanner = new Scanner(System.in);
    while(answer != 1 && answer != 2 && answer != 3 && answer != 4) {
      System.out.println("Please type a number that matches your choice: \n 1: go to the next generation and display \n 2: prompt and edit the grid \n 3: count organisms in a row or col or the entire grid \n 4: quit");
      answer = scanner.nextInt();
      if(answer != 1 && answer != 2 && answer != 3 && answer != 4) {
        System.out.println("Please type a number from 1 through 4");
      }
      
    }
   
    return answer;
  }
  /**This method flips the state of the location in the specified int array. If the location 
   * previously contained an organism, then it is removed.  If it previously was empty, then 
   * an organism is placed into that location. 
   * @param rcArr contains two ints.  The first element represents the row of the location and
   * rcArr[1] contains the column of the location.  If rcArr does not contain exactly two ints, 
   * or if the ints contained in the array are invalid indices of grid, there is no change made.
   */
  public void flip(int[] rcArr) {
    if(rcArr.length == 2 && rcArr[0] <= grid.length && rcArr[1] <= grid[0].length) {
        if(rcArr[0] == -1) {
          for(int i = 0; i < grid.length; i++) {
            int num = grid[i][rcArr[1]];
            if(num == 0) {
              grid[i][rcArr[1]] = 1;
            } else {
              grid[i][rcArr[1]] = 0;
            }
          }
        } else if(rcArr[1] == -1) {
          for(int i = 0; i < grid[0].length; i++) {
            int num = grid[rcArr[0]][i];
            if(num == 0) {
              grid[rcArr[0]][i] = 1;
            } else {
              grid[rcArr[0]][i] = 0;
            }
          }
        }
        else if(grid[rcArr[0]][rcArr[1]] == 0) {
          grid[rcArr[0]][rcArr[1]] = 1;
        } else {
          grid[rcArr[0]][rcArr[1]] = 0;
        }
    }
    
  }
  /**
   * This method prompts the user for the row and column of the grid.  The prompt 
   * should include reasonable interval of values to be input, or -1 for the whole row (or col).  
   * For instance, if there are 10 rows then the user should be asked to input a number 
   * from 0 - 9 inclusive or 1 - 10 inclusive for the row (or -1 for the whole row).  
   * Similarly for the column. This value should be checked to make sure the user has 
   * input a correct value.  If not, the user should be prompted again.
   * @return a one-dimensional array with two elements.  The first element is the row and the 
   * second element represents the column of the location.
   */
  public int[] promptForRowAndCol() {
    int[] arr = new int[2];
    Scanner scanner = new Scanner(System.in);
    System.out.println("Row (1 - " + grid.length + ") Type -1 for whole row: ");
    int answer = scanner.nextInt();
    if(answer == -1) {
      arr[0] =-1;
    } else {
      arr[0] = answer;
    }
    System.out.println("Col (1 - " + grid[0].length + ") Type -1 for whole col: ");
    int answer2 = scanner.nextInt();
    if(answer2 == -1) {
      arr[1] = -1;
    } else {
      arr[1] = answer2;
    }

    return arr;
  }
  /**
   * This method counts the organisms in the row and col (or entire row or entire col) determined
   * by the specified contents of the specified int array.
   * @param rcArr This contains the row and col to be counted.  If rcArr[0]==4 and rcArr[1]==7 then
   * it returns 1 if grid[4][7] contains an organism, else 0.  If rcArr[0]==-1 and rcArr[1]==7 then
   * the method returns the number of organisms in column 7  (every row). If rcArr[0]==4 and 
   * rcArr[1]==-1 then the method returns the number of organisms in row 4 (every column).  
   * If rcArr[0]==-1 and rcArr[1]==-1 then the method returns the number of organisms in 
   * the entire grid (every row and every column). */
  public int countOrganisms(int[] rcArr) {
    int row = rcArr[0];
    int col = rcArr[1];
    if(row == 4 && col == 7) {
      if(grid[row][col] == 1) {
        return 1;
      } else {
        return 0;
      }
    } else if(row == -1 && col == 7) {
      int count = 0;
      for(int i = 0; i < grid.length; i++) {
        if(grid[i][7] == 1) {
          count++;
        }
      }
      return count;
    } else if(row == 4 && col == -1) {
      int count = 0;
      for(int i = 0; i < grid[0].length; i++) {
        if(grid[4][i] == 1) {
          count++;
        }
      }
      return count;
    } else if (row == -1 && col == -1) {
      int count = 0;
      for(int i = 0; i < grid.length; i++) {
        for(int j = 0; j < grid[0].length; j++) {
          if(grid[i][j] == 1) {
            count++;
          }
        }
      }
      return count;
    }
    return 0;
  }
  
  /** 
   * This method outputs the contents of the grid to the console.  The row and col should be indicated
   * in the printout.  You can play around with which character to use for an organism and which char
   * to use for an empty space.  I suggest "*" for organism and "-" for nothing.  The current gen should
   * be displayed as well.
   */
  public void display(){
    System.out.println("gen "+gen);
    for(int i = 0; i < grid.length; i++) {
      for(int j = 0; j < grid[0].length; j++) {
        if(grid[i][j] == 1) {
          System.out.print("*");
        } else {
          System.out.print("-");
        }
      }
      System.out.println();
     }

  }
  /**
   * Assigns grid to a new 2D array with specified size and organisms.
   * @param rows The number of rows
   * @param cols The number of cols
   * @param lifes The number of organisms randomly placed
  */
  public void setUpGrid(int rows, int cols, int lifes){
    grid = new int[rows][cols];

    //random placemennt of organisms
    for(int i = 0; i < lifes; i++) {
         int row = ((int)(Math.random() * (rows)) + 0);
         int col = ((int)(Math.random() * (cols)) + 0);
         if(grid[row][col] == 0) {
            grid[row][col] = 1;
         } else {
            while(grid[row][col] == 1) {
               row = ((int)(Math.random() * (rows)) + 0);
               col = ((int)(Math.random() * (cols)) + 0);
            }
            grid[row][col] = 1;
         }
      }
      for(int i = 0; i < grid.length; i++) {
         for(int j = 0; j < grid[0].length; j++) {
            if(grid[i][j] != 1) {
               grid[i][j] = 0;
            }
         }  
      }

  }
  /**
   * Returns the number of organisms found in the locations neighboring 
   * the specified row and col
   * @param r The row of the center location
   * @param c The col of the center location
   * @return the number of neighboring organisms
   * 123
   * 4x5
   * 678
   * x represents the location of r,c and the numbers represent the locations 
   * around the specified location
   * Be careful to make sure the relative location is in bounds before looking at the element
   */ 
  public int countNeighbors(int r, int c){
    int count = 0;
    if((r-1) <= grid.length && r-1 >= 0 && (c-1) <= grid[0].length && c-1 >= 0 && grid[r-1][c - 1] == 1) {
      count++;
    }
    if((r-1) <= grid.length && r-1 >= 0 && (c) <= grid[0].length && grid[r - 1][c] == 1) {
      count++;
    }
    if((r-1) <= grid.length &&  r-1 >= 0 && (c+1) < grid[0].length &&  grid[r - 1][c + 1] == 1) {
      count++;
    }
    if((r) <= grid.length && (c-1) >= 0 && (c-1) <= grid[0].length && grid[r][c-1] == 1) {
      count++;
    }
    if((r) <= grid.length && (c+1) < grid[0].length && grid[r][c + 1] == 1) {
      count++;
    }
    if((r+1) < grid.length && c-1 >= 0 && (c-1) <= grid[0].length && grid[r + 1][c-1] == 1) {
      count++;
    }
    if((r+1) < grid.length && (c) <= grid[0].length && grid[r + 1][c] == 1) {
      count++;
    }
    if((r+1) < grid.length && (c+1) < grid[0].length && grid[r + 1][c + 1] == 1) {
      count++;
    }
    return count;
  }

  /**
   * This method creates the next generation and saves it as grid.  This is VERY 
   * difficult to try and do it without a second 2D array in which to store the 
   * next generation.  The rules that determine if a location will have an organism
   * in the next generation are as follows:
   * 1.  If there are exactly three neighbors around a location, that location will have an 
   *     organism in the next generation.
   * 2.  If there are exactly two neighbors around a location, AND that location currently
   *     has an organism, that location will have an organism in the next generation.
   * 3.  Any other condition and that location will be empty in the next generation.
   */
  
  public void generate(){
    int[][] tempGrid = new int[grid.length][grid[0].length];
    for(int i = 0; i < grid.length; i++) {
      for(int j = 0; j < grid[0].length; j++) {
        if(countNeighbors(i, j) == 3) {
          tempGrid[i][j] = 1;
        } else if(countNeighbors(i, j) == 2 && grid[i][j] == 1) {
          tempGrid[i][j] = 1;
        } else {
          tempGrid[i][j] = 0;
        }
      }
      }
    grid = tempGrid;

  }
  /**
   * Returns true if the specified location is a valid location in the grid
   * @param r The row to check
   * @param c The col to check
   * @return true if r and c are valid indexes of grid; false otherwise.
   */
  public boolean inBounds(int r, int c){
     if (r > grid.length || c > grid[0].length) {
       return false;
     }
    return true;
  }

  /**
   * This method will be used for drawing on a JPanel
   * @param g
   */
  public void draw(Graphics g){
    System.out.println("Drawing life!");
  }
}