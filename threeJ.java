import java.util.Scanner; 
import java.io.*;
import java.util.LinkedList;
/**
* This program produces the group theoretic number known as a three j coefficent. This is produced from three Young tableaux.
* @author Michael Spence
*/
public class threeJ{

public static void main(String[] args){

    // First you input the three tableaux. The first integer array is the entries in the tableaux read down the columns. 
    // The second integer array determines the shape of tableaux based of the number and length of its columns. 
    // The array {3,2} for example would produce a tableau with a column of length 3, and a column of length 2.
	Tab x = new Tab(new int[]{1,2},new int[]{2});
	Tab z = new Tab(new int[]{3,4},new int[]{2});
	Tab y = new Tab(new int[]{1,2,3,4},new int[]{3,1});
	// We then print the three tableaux to the command line followed by their associated three j.
    y.print();
    System.out.println("\n");
    x.print();
    System.out.println("\n");
    z.print();
    System.out.println("\n");
    MatrixRat.threeJM(z,x,y).print();
  

 
    
    	}
}