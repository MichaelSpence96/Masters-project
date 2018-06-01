import java.util.Scanner; 
import java.io.*;
import java.util.LinkedList;
/**
* This program contains all methods for rational numbers, called here RatNum's. These are simply represented by an ordered pair of integers
* @author Michael Spence
*/
public class RatNum{
	private int nom;
	private int denom;

	public RatNum(){
		this.nom = 0;
		this.denom = 1;
	}
/**
*Constructs a rational number from two integers
* @param n The nominator
* @param d The donominator
*/
	public RatNum(int n, int d){
		this.nom = n;
		this.denom = d;
	}

	public int nom(){
		return this.nom;
	}

	public int denom(){
		return this.denom;
	}

	public static RatNum convert(int i){
		return  new RatNum(i,1);
	}

	// the greatest common divisor of tow ints
/**
* Determines the greatest common divisor of two integers
* @param i The first integer
* @param j the second
* @return gcd(i,j)
*/
	public static int gcd(int i , int j){
		
		int n;
		int m;
		if(i<j){
			n = j;
			m=i;
		}
		else{
			n=i;
			m=j;
		}
		if(n<0){
			n=-n;
		}
		if(m<0){
			m=-m;
		}
        if(m==0){
        	return n;
        }
		if(n%m==0){
			return m;
		}
		


		return gcd(m,n%m);
	}
/**
* Divides both the nominator and denominator by their gcd, so that the rational number is represented in the simplest fashion
*/
	public void reduce(){
		if(this.nom==0){
			this.denom = 1;
			return;
		}
		int n = this.nom;
		int d = this.denom;

		int g = gcd(n,d);
		this.nom = (int) n/g;
		this.denom = (int) d/g;
	}

	



	public static RatNum add(RatNum m, RatNum n){
		return new RatNum((m.nom()*n.denom())+(n.nom()*m.denom()),n.denom()*m.denom());
	}

	public static RatNum sub(RatNum m, RatNum n){
		return new RatNum((m.nom()*n.denom())-(n.nom()*m.denom()),n.denom()*m.denom());
	}

	public static RatNum mult(RatNum m, RatNum n){
		return new RatNum(m.nom()*n.nom(),n.denom()*m.denom());
	}
    
    //divides x by y
	public static RatNum divide(RatNum x, RatNum y){
		return new RatNum(x.nom()*y.denom(),x.denom()*y.nom());
	}

	public static String toString(RatNum n){
		n.reduce();
		if(n.denom()==1){
			return n.nom()+"";
		}

		if(n.denom()==n.nom()&&n.nom()!=0){
			return 1 + "";
		}
        if(n.denom()==-n.nom()&&n.nom()!=0){
			return -1 + "";
		}


		if(n.nom()==0){
			return 0 +"";
		}
		return n.nom()+"/"+n.denom;
	}

	public RatNum power(int p){
		if(p==0){
			return new RatNum(1,1);
		}
		

		return mult(this,this.power(p-1));
	}

	public void print(){
		System.out.println(toString(this));
	}

	public static int factorial(int n){
		if(n==0){
			return 1;
		}
		return n*factorial(n-1);
	}	











	// temporary alternate permutation tests

	public static LinkedList<Integer> copy(LinkedList<Integer> ints){
		LinkedList<Integer> result = new LinkedList<Integer>();
		for(int i : ints){
			result.add(i);
		}
		return result;
	}

	// a method to order a list of integers
   public static void Order(LinkedList<Integer> i, int start, int end){
   	if(start==end){
   		return;
   	}
   	  int n = i.get(start);
   	  for(int j = start; j < end+1; j++){
   	  	if(i.get(j)<n){
   	  		int m = i.get(j);
   	  		i.set(start, m);
   	  		i.set(j,n);
   	  		n= m;
   	  	}


   	  }
   	  Order(i, start+1, end);


   }

   // orders a set of integers competley
   /** 
   * Completley orders a list of integers from lowest to highest
   * @param list The list we want to order
   * @return The ordered list
   */
   public static LinkedList<Integer> order(LinkedList<Integer> list){
    LinkedList<Integer> result  = copy(list);
    Order(result,0,result.size()-1);
    return result;
   }


   //returns ordered perms of given length built from a list of integers
   public static LinkedList<LinkedList<Integer>> partPerm(LinkedList<Integer> ints,int length, int start){

    int size = ints.size();

    LinkedList<Integer> ordered = order(ints);
   	LinkedList<LinkedList<Integer>> result = new LinkedList<LinkedList<Integer>>();
   	if(length==1){
   		for(int i = start; i<size;i++){
   			LinkedList<Integer> temp = new LinkedList<Integer>();
   			temp.add(ints.get(i));
   			result.add(temp);
   		}
   		return result;
   	}



   	for(int i = start; i<=size-length;i++){
       LinkedList<LinkedList<Integer>> temp = partPerm(ordered,length-1,i+1);
       for(LinkedList<Integer> l : temp){
       	l.addFirst(ints.get(i));
       }
       result.addAll(temp);


   		


   	
   	}
   	return result;


   }
/**
* Returns all possible ordered lists of certain length which can be produced from a larger list
* @param ints The list we can chose from
* @param length The length of the desired lists
* @return A list of the possible results
*/
   public static LinkedList<LinkedList<Integer>> partPerm(LinkedList<Integer> ints, int length ){
   	return partPerm( ints,length, 0);
   }
/**
* Removes all instances of a specified integer from a list
* @param input The list
* @param trash The number we want to remove
* @return The new purified list
*/
   public static LinkedList<Integer> binIt(LinkedList<Integer> input, int trash){
  int n = input.size();
  LinkedList<Integer> result = new LinkedList<Integer>();
  for(int i : input){
    if(i!=trash){
      result.add(i);
    }

  }
  return result;
}



   public static LinkedList<LinkedList<Integer>> partit(LinkedList<Integer> ints, int length){
   	LinkedList<LinkedList<Integer>> result = new LinkedList<LinkedList<Integer>>();
   	for(LinkedList<Integer> l : partPerm(ints,length)){
   		LinkedList<Integer> temp = order(ints);
   		for(int i: l){
      temp = binIt(temp,i);
   		}
   		l.addAll(temp);
   		result.add(l);

   	}
   	return result;
   }

// returns all partions of n with no higher terms than maxval and no more terms than maxcount
   public static LinkedList<LinkedList<Integer>> partition(int n, int maxcount, int maxval ){
LinkedList<LinkedList<Integer>> result = new LinkedList<LinkedList<Integer>>();
if(n<1){
	return result;
}
if(n>maxcount*maxval){
	return result;
}



if(maxcount*maxval==n){
	LinkedList<Integer> temp = new LinkedList<Integer>();
	for(int i =0;i<maxcount;i++){
		temp.add(maxval);
	}
	result.add(temp);
	return result;
}



for(int i=1;i<=maxval;i++){
	LinkedList<LinkedList<Integer>> temp = partition(n-i,maxcount-1,i);
	for(LinkedList<Integer> l:temp){
		l.addFirst(i);
	}
	result.addAll(temp);
}

if(n<=maxval){
	LinkedList<Integer> temp = new LinkedList<Integer>();
	temp.add(n);
	result.add(temp);
}

return result;

}

// returns all possible partitions of an integer as a linkedlist of int arrays
/**
* Returns all possible partitions of an integer
* @param n The integer we wish to partition
* @return A list of int arrays representing the possible partitions
*/
public static LinkedList<int[]> partition(int n){
	LinkedList<LinkedList<Integer>> temp = partition(n,n,n);
	LinkedList<int[]> result = new LinkedList<int[]>();
	for(LinkedList<Integer> l : temp){
		int[] a = tointArray(l);
		result.add(a);
	}
return result;

}

public static int[] tointArray(LinkedList<Integer> l){
	
	int[] result = new int[l.size()];
	for(int i=0;i<l.size();i++){
		if(l.get(i)==null){
			result[i]=0;
		}
		result[i] = l.get(i);
		
	}
	return result;
}


}