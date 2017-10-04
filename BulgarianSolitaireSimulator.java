// Name:        Spencer McDonough
// USC NetID:   6341617166
// CSCI455 PA2
// Fall 2017

// imports
import java.util.ArrayList;
import java.util.Scanner;

/**
   <add main program comment here>
 */

public class BulgarianSolitaireSimulator {

   public static void main(String[] args) {
     
      boolean singleStep = false;
      boolean userConfig = false;

      for (int i = 0; i < args.length; i++) {
         if (args[i].equals("-u")) {
            userConfig = true;
         }
         else if (args[i].equals("-s")) {
            singleStep = true;
         }
      }

      // <add code here>
      ArrayList<Integer> pilesIn = new ArrayList<Integer>();

      if (userConfig)
      {
        System.out.println("You will be entering the initial configuration of the cards (i.e., how many in each pile).");
        System.out.println("Please enter a space-separated list of positive integers followed by newline:");
        Scanner scanMult = new Scanner(System.in).useDelimiter("[,\\s+]");
        
        while (scanMult.hasNextInt())
        {
          int input = scanMult.nextInt();
          pilesIn.add(input);
        }

        // -------------------------- TEST ------------------------
        int listSize = pilesIn.size();
        System.out.print("Given Piles: [");
        for (int i = 0; i < listSize; i++)
        {
            if (i == listSize - 1) {
                System.out.print(pilesIn.get(i));
            }
            else
                System.out.print(pilesIn.get(i) + ", ");
        }
        System.out.print("]\n");
        // -------------------------- TEST ------------------------

        // create new solitaire board
        SolitaireBoard myBoard = new SolitaireBoard(pilesIn);
        
        if (singleStep)
        {
          
        }
        else
        {

        }
      }
      else
      {
        SolitaireBoard myBoard = new SolitaireBoard(); // generate randome board

        if (singleStep)
        {
          int run = 1;
          while (run == 1) // subs with isDone()
          {
            Scanner scanSingle = new Scanner(System.in);
            System.out.print("Enter 1 to continue: ");
            run = scanSingle.nextInt();
            myBoard.playRound();
            System.out.println();
          }
        }
        else
        {

        }
      }
   }
   
    // <add private static methods here>
}
