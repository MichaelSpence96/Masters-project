import java.util.Scanner; 
import java.io.*;
import java.util.LinkedList;
/**
* The Tabby class is simply a tableau paired with an integer coefficent. This class contains an implementation of the Garnir algorithm
* @author Michael Spence
*/
public class Tabby{
	
	private Tab tab;
	private int coeff;

	// Constructors

	public Tabby(){}
/**
* Constructs a Tabby
* @param t The tableau
* @param c The coefficent
*/
	public Tabby(Tab t, int c){
		this.tab = t;
		this.coeff = c;
	}
/**
* Returns the tableau 
* @return The tableau
*/
	public Tab tab(){
		return this.tab;
	}
/**
*Returns the integer coefficent
* @return The integer coefficent
*/
	public int coeff(){
		return this.coeff;
	}

	// multiplies by an intger
/**
* Represents multiplication
* @param m The proportion by which the integer coefficent is increased
*/
	public void mult(int m){
		this.coeff=this.coeff*m;
	}

	// a method to print 

	public void print(){
		System.out.println("("+this.coeff+")\n");
		this.tab.print();
	}

	// a method to order a list and return the sign of the permutation
	public static int order(LinkedList<Integer> l){
		return Tab.Order(l,0,l.size()-1);
	}


	// a method to order the columns of a tableaux and multiply by the perm sign

	public void orderCol(){
		this.coeff = this.coeff*this.tab.orderCol();
		}


// apllies the garnir algorithm to a tabby and returns a linkedlist
		/**
		* Applies the Garnir algorithm to a tableau and represents the result as list of Tabbys
		* @return A list representing a linear combination of tableau
		*/
	public LinkedList<Tabby> garnir(){
		this.orderCol();
		LinkedList<Tabby> result = new LinkedList<Tabby>();
		if(this.tab.isStan()){
			result.add(this);
			return result;
		}
		Tab tab = this.tab;
		int coeff = this.coeff;
		
		LinkedList<Integer> original = tab.stripBad();
		LinkedList<LinkedList<Integer>> perms = tab.stripPerms();
		for(LinkedList<Integer> l: perms){
			if(!Tab.equal(original,l)){
			result.add(new Tabby(tab.permute(original,l),-1*Tab.permSign(original,l)*coeff));
		}
		}
		return result;


	}

// expresses a given tabby as a sum of standard tabbys

/**
* Iterativley applies the Garnir algorithm until all tableau in the sum are standard. Known as the straightening algorithm. Allows us to express an tableau as a sum of standard ones.
* @return A linear combination of standard tableaux represented as a list of Tabbys
*/
	public LinkedList<Tabby> straight(){
		LinkedList<Tabby> result = this.garnir();
		for(int j = 0;j<result.size();j++){
			if(!((result.get(j).tab).isStan())){
				LinkedList<Tabby> temp = result.get(j).garnir();
				result.remove(j);
				result.addAll(j,temp);
				j=j-1;
			}
		}
		return result;
	}
}