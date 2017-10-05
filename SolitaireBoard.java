// Name:
// USC NetID:
// CSCI455 PA2
// Fall 2017

import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

/*
   class SolitaireBoard
   The board for Bulgarian Solitaire.  You can change what the total number of cards is for the game
   by changing NUM_FINAL_PILES, below.  Don't change CARD_TOTAL directly, because there are only some values
   for CARD_TOTAL that result in a game that terminates.
   (See comments below next to named constant declarations for more details on this.)
 */


public class SolitaireBoard {
   
   public static final int NUM_FINAL_PILES = 9;
   // number of piles in a final configuration
   // (note: if NUM_FINAL_PILES is 9, then CARD_TOTAL below will be 45)
   
   public static final int CARD_TOTAL = NUM_FINAL_PILES * (NUM_FINAL_PILES + 1) / 2;
   // bulgarian solitaire only terminates if CARD_TOTAL is a triangular number.
   // see: http://en.wikipedia.org/wiki/Bulgarian_solitaire for more details
   // the above formula is the closed form for 1 + 2 + 3 + . . . + NUM_FINAL_PILES

    // Note to students: you may not use an ArrayList -- see assgt description for details.
   
   
   /**
      Representation invariant:

      <put rep. invar. comment here>

    */
  
   // <add instance variables here>
   private int[] piles = new int[4096]; // internal piles array
   private int numPiles = 0;
 
   /**
     Creates a solitaire board with the configuration specified in piles.
     piles has the number of cards in the first pile, then the number of cards in the second pile, etc.
     PRE: piles contains a sequence of positive numbers that sum to SolitaireBoard.CARD_TOTAL
   */
   public SolitaireBoard(ArrayList<Integer> piles) 
   {
      int tempMax = -4096;
      int rmvElem = 0;
      /**
       * find the maximum element in piles and put it into internal piles in descending order
       */
      for (int j = 0; j < piles.size(); j++) 
      {
        for (int i = 0; i < piles.size(); i++)
        {
            if(piles.get(i) > tempMax)
            {
              tempMax = piles.get(i);
              rmvElem = i;
            }  
        }
        this.piles[j] = tempMax;
        piles.set(rmvElem, 0);
        tempMax = -4096;  
      }

      assert isValidSolitaireBoard();   // sample assert statement (you will be adding more of these calls)
      System.out.println(this.configString(true));
    }
 
   
   /**
      Creates a solitaire board with a random initial configuration.
   */
  public SolitaireBoard() 
  {
      Random rand = new Random();
      int runningTotal = 0;
      
      for (int i = 0; i < CARD_TOTAL; i++) // boopidibop
      {   
          if (runningTotal < CARD_TOTAL)
          {
            this.piles[i] = rand.nextInt(CARD_TOTAL - runningTotal) + 1;
            runningTotal += this.piles[i];
          }
          else
            break; // please don't judge, I'm only human :)
      }
      assert isValidSolitaireBoard();   // sample assert statement (you will be adding more of these calls)
      this.piles = this.sortArray();
      System.out.println(this.configString(true));      
  }
  
   
   /**
      Plays one round of Bulgarian solitaire.  Updates the configuration according to the rules
      of Bulgarian solitaire: Takes one card from each pile, and puts them all together in a new pile.
      The old piles that are left will be in the same relative order as before, 
      and the new pile will be at the end.
    */
    public void playRound() 
  {
    int newPile = 0;
    boolean rmvFlag = false;

    for (int i = 0; i < this.numPiles; i++)
    {
        if (this.piles[i] > 0)
        {
          this.piles[i]--;
          newPile++;
        }    
    }
    //System.out.println("(AFTER SUBTRACTION) " + this.configString(false));
    
    rmvFlag = this.rmvZero();

    if (this.numPiles < CARD_TOTAL && !rmvFlag)   // boopidibop
      this.numPiles++;

    this.piles[this.numPiles-1] = newPile;

    System.out.println(this.configString(false));
  }
   
   /**
      Returns true iff the current board is at the end of the game.  That is, there are NUM_FINAL_PILES
      piles that are of sizes 1, 2, 3, . . . , NUM_FINAL_PILES, in any order.
    */
   
  public boolean isDone() 
  {
      int[] finArr = new int[NUM_FINAL_PILES];
      boolean allMatch = true;
      boolean[] elemsMatch = new boolean[NUM_FINAL_PILES];
      Arrays.fill(elemsMatch, false);

      for (int i = 0; i < NUM_FINAL_PILES; i++)
      {
        finArr[i] = NUM_FINAL_PILES - i;
      }
      for (int i = 0; i < NUM_FINAL_PILES; i++)
      {
        for (int j = 0; j < this.numPiles; j++)
        {
          if (this.piles[j] == finArr[i])
          {
            elemsMatch[i] = true;
          }
        }
        allMatch &= elemsMatch[i];
      }
       return allMatch;  // dummy code to get stub to compile
  }

   
   /**
      Returns current board configuration as a string with the format of
      a space-separated list of numbers with no leading or trailing spaces.
      The numbers represent the number of cards in each non-empty pile.
    */
  public String configString(boolean chooseHeader) 
  {
      String currBoard;
      if (chooseHeader)
        currBoard = "Initial configuration: ";
      else
        currBoard = "Current configuration: ";
      for (int i = 0; i < this.numPiles; i++)
      {
          if (i == this.numPiles - 1) {
            currBoard += this.piles[i] + "\n";
          }
          else
            currBoard += this.piles[i] + " ";
      }
      return currBoard; 
  }
   
   /**
      Returns true iff the solitaire board data is in a valid state
      (See representation invariant comment for more details.)
    */
  private boolean isValidSolitaireBoard() 
  {
    // build numPiles
    for (int i = this.piles.length - 1; i >= 0; i--)
    {
      if (this.piles[i] != 0)
      {
        this.numPiles = i + 1;
        break;
      }
    }
    // ------------------------- VALIDATE --------------------
    boolean validLength = true;
    boolean validPileVals = true;
    boolean validTotal = true;
    int total = 0;

    // verify length
    // if (this.numPiles > NUM_FINAL_PILES)
    //   validLength = false;
    
    for (int i = 0; i < this.numPiles; i++)
    {
      // verify pilevals
      if (this.piles[i] <= 0)
        validPileVals = false;
      // verify total
      total += this.piles[i];
    }
    if (total != CARD_TOTAL)
      validTotal = false;
    
    //   // -------------------------- TEST ------------------------
    // if (!validLength)
    //   System.out.println("ERROR: Number of Piles (" + this.numPiles + ") is too many. Max = 9.");
    // if (!validPileVals)
    //   System.out.println("ERROR: You must have one or more cards in a pile.");
    // if (!validTotal)
    //   System.out.println("ERROR: Total (" + total + ") is high. Max = " + CARD_TOTAL);
    // // -------------------------- TEST ------------------------

    if (!validLength || !validPileVals || !validTotal)
      System.out.println("ERROR: Each pile must have at least one card and the total number of cards must be " + CARD_TOTAL);
    return validLength && validPileVals && validTotal;
  }

    // <add any additional private methods here>
  private int[] sortArray()
  {
    int[] sortedPiles = new int[4096];
    int tempMax = 0;
    int rmvElem = 0;
    for (int j = 0; j < this.numPiles; j++) 
    {
      for (int i = 0; i < this.numPiles; i++)
      {
          if(this.piles[i] > tempMax)
          {
            tempMax = this.piles[i];
            rmvElem = i;
          }  
      }
      sortedPiles[j] = tempMax;
      this.piles[rmvElem] = 0;
      tempMax = 0;
    }
    return sortedPiles;
  }

    /**
     * removes all zeros from array after subtraction
     * current issues:
     *    doesn't remove consecutive zeros
     */
  private boolean rmvZero()
  {
    boolean rmvFlag = false;
    int[] tempArr = new int[CARD_TOTAL]; // boopidibop

    int tempInd = 0;

    for (int i = 0; i < this.numPiles; i++)
    {
      if (this.piles[i] != 0)
      {            
        tempArr[tempInd] = this.piles[i];
        tempInd++;
      }
      else
        rmvFlag = true; 
    } 

    // // ----------------------------- TEST ---------------------------   
    // System.out.println("tempInd = " + tempInd); 
    // System.out.print("tempArr (before cleanup) = ");
    // for (int i = 0; i < this.numPiles; i++)
    // {
    //     if (i == this.numPiles - 1) {
    //       System.out.print(tempArr[i] + "\n");
    //     }
    //     else
    //       System.out.print(tempArr[i] + " ");
    // }
    // System.out.println();

    // int[] cleanArr = new int[tempInd];
    // System.arraycopy(tempArr, 0, cleanArr, 0, tempInd);  
    
    // // ----------------------------- TEST ---------------------------
    // System.out.print("cleanArr = ");
    // for (int i = 0; i < tempInd; i++)
    // {
    //     if (i == tempInd - 1) {
    //       System.out.print(cleanArr[i] + "\n");
    //     }
    //     else
    //       System.out.print(cleanArr[i] + " ");
    // }
    // System.out.println();
    // // ----------------------------- TEST ---------------------------
    
    this.piles = tempArr.clone();
    return rmvFlag; 
  }
}
