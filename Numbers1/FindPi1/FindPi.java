
/**
 * Find PI to the Nth Digit - Enter a number and have the program generate PI up to that many decimal places. Keep a limit to how far the program will go.
 * 
 * The limit is 1 to 100
 * 
 * By blackd000
 */

package numbers1.findPi1;

import java.util.Scanner;
import java.math.BigDecimal;
import java.text.DecimalFormat; //to retrive   

public class FindPi {
  
  final static BigDecimal PI = new BigDecimal(3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679);

  public static void main(String[] args) {
    
    Scanner input = new Scanner(System.in);
    boolean isDigit = false;
    int digit = 0;

    while(!isDigit) {

      for(int i = 0; i < 30; i++) {
        System.out.println();
      }

      /**
       * To make sure end user enter a integer number, not dec not string or char
       */
      try {
        System.out.print("Enter your choise (min: 1, max: 100): ");
        digit = input.nextInt();
      } catch (Exception e) {
        System.out.println("\nYou must enter a integer not String, decimal or somethings else doesn't relate");
        input = new Scanner(System.in); //because if you dont do this you will be stuck in the infinit loop
        System.out.print("Press enter to continue...");
        new Scanner(System.in).nextLine();
        continue;
      }

      if(digit < 1 || digit > 100) {
        System.out.println("\nYou must enter a digit between 1 and 100");
        System.out.print("Press enter to continue...");
        new Scanner(System.in).nextLine();
      } else {
        isDigit = true;
      }
    }

    String toAppen = "0.";

    for(int i = 1; i <= digit; i++) {
      toAppen = toAppen + "0";
    }

    DecimalFormat digitFormat = new DecimalFormat(toAppen);

    System.out.println(digitFormat.format(PI));
    input.close();
  }
}
