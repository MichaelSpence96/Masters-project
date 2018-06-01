import java.util.Scanner; 
import java.io.*;
import java.util.LinkedList;

/**
* This program contains all methods to construct and manipulate Young tableaux, with several combinatorial algorithms for the manipulation of integer lists.
* @author Michael Spence
*/
public class Tab{
	// Linked lists represent columns
	private LinkedList<LinkedList<Integer>> tab;


//constructors


	public Tab(){}

    /**
 * This method constructs a tableau from a list of integer lists with the integer lists representing columns
 * @param l  A LinkedList of integer LinkedLists representing columns
 */
public Tab(LinkedList<LinkedList<Integer>> l){

    	this.tab = l;
    }

    //constructs from a given column partition

    /**
    *Creates a tableau from two integer arrays
    * @param entries This array represents the entries as read down the columns
    * @param type This represents the shape of the diagram with the entries corresponding to the different column lengths
    */

    public Tab(int[] entries, int[] type){
    	int temp =0;
    	LinkedList<LinkedList<Integer>> tab = new LinkedList<LinkedList<Integer>>();
    	for(int i =0;i<type.length;i++){
    		tab.add(new LinkedList<Integer>());
    		for(int j = 0; j<type[i];j++){
                tab.get(i).add(entries[temp]);
                temp =temp+1;

    		}
    	}
    	this.tab = tab;
    }

    // getters
   // returns the ith column beginning from 0

    /**
    * Retruns the ith column of a tableau counting from zero
    * @param i The ith column 
    * @return A list represnting the ith column
    */

    public LinkedList<Integer> column(int i){
    	if(i+1>this.colNum()){
    		return new LinkedList<Integer>();
    	}
      return this.tab.get(i);
    }

    /**
    * Removes the ith column of a tableau
    * @param i the ith column
    */

  // removes a column
    public void removeCol(int i){
      this.tab.remove(i);
    }

    /**
    * Returns the ith row of a tableaux
    * @param i the row number
    * @return the ith row in list form
    */

    public LinkedList<Integer> row(int i){
    	LinkedList<Integer> row = new LinkedList<Integer>();
    	for(LinkedList<Integer> l: this.tab){
    		if(l.size()<=i){break;}
    		row.add(l.get(i));
    	}
    	return row;
    }

    /**
    * Returns the number of boxes in a tableau
    * @return The number of boxes in the tableau
    */

   // gets the number of boxes in a Tableaux
    public int size(){
      int size = 0;
      for(LinkedList<Integer> l: this.tab){
        size = size + l.size();
      }
      return size;
    }

// gets the ith element in the tableaux counting down the columns

    /**
    * Returns the ith element of a tableaux counting down the columns and starting at zero
    * @param i The element number
    * @return The ith element
    */

    public int element(int i){
    	int[] type = this.type();
    	for(int k =0; k<type.length;k++){
    		if(i-type[k]<1){
    			return this.tab.get(k).get(i-1);
    		}
    		i = i-type[k];
    	}
    	return 0;
    }
/**
* Sets the ith element to the desired value
* @param i The element to be changed
* @param n The value the element will be set to
*/    

//sets the element
    public void set(int i,int n){
    	int[] type = this.type();
    	for(int k =0; k<type.length;k++){
    		if(i-type[k]<1){
    		this.tab.get(k).set(i-1,n);
    		return;
    		}
    		i = i-type[k];
    	}
    	

    }


// swaps two elements 

/**
* Interchanges the ith and jth elements in tableau
* @param i the first element to get swapped
* @param j the other element
* @return the altered tableau
*/    
    public Tab swap(int i, int j){
    	Tab result = this.copy();
    	int temp = result.element(i);
    	result.set(i,result.element(j));
    	result.set(j,temp);
    	return result;
    }

    public int colNum(){
    	return this.tab.size();
    }
// returns number of rows from the length of the first column
/**
* Returns the number of rows which is equal to the length of the first column
* @return The number of rows in the tableau
*/    
    public int rowNum(){
    	return this.tab.get(0).size();
    }
// a method to determine if two lists are equal


    public static boolean equal(LinkedList<Integer> i, LinkedList<Integer> j){
    	int size1 = i.size();
    	int size2 = j.size();
    	if(size1!=size2){
    		return false;
    	}

    	for(int k =0;k<size1;k++){
    		if(i.get(k)!=j.get(k)){
    			return false;
    		}
    	}
    	return true;
    }

// determines if two tableauxs are the same

/**
* Determines if two tableau are equal
* @param t The tableau to be compared
* @return The result of comparison
*/
public boolean equal(Tab t){
	int size1 = this.tab.size();
	for(int k =0;k<size1;k++){
		if(!equal(t.tab.get(k),this.tab.get(k))){
			return false;
		}
	}
return true;
}   

/**
* Checks if an integer list is ordered from lowest to highest 
* @param l The list to be tested
* @return the result
*/

//a method to check if a list of integers is ordered
	public static boolean isOrdered(LinkedList<Integer> l){
		int n = l.size();

		for(int i =0; i<n-1; i++){
			if(l.get(i)>l.get(i+1)){
				return false;

			}
		}
		return true;
	}

// ,ethods to determine if rows and columns are ordered
/** 
* Determines if the rows of tableau are ordered from lowest to highest
* @return the result
*/
public boolean rowOrd(){
	for(int j=0;j<this.rowNum();j++){
		if(!(isOrdered(this.row(j)))){
			return false;
		}
	}
	return true;
} 	

/**
* Determines if the columns in a tableau are ordered from lowest to highest
* @return the result
*/
public boolean colOrd(){
	for(LinkedList<Integer> l: this.tab){
		if(!isOrdered(l)){
			return false;
		}
	}
	return true;
}
/**
*Determines if a tableau is standard by checking if both the rows and the columns are ordered
* @return the result
*/
// a method to determine if a Tableaux is standard
public boolean isStan(){
	return (this.colOrd())&&(this.rowOrd());
}

public void print(){
	for(int i=0;i<this.rowNum();i++){
		String row = "";
		for(int k: this.row(i)){
			row = row + " "+k;
		}
		System.out.println(row);
	}
}

public static void print(LinkedList<Integer> l){
	
	for(int k:l){
System.out.println(k);
	}
}

// copy methods


	public static LinkedList<Integer> copy(LinkedList<Integer> ints){
		LinkedList<Integer> result = new LinkedList<Integer>();
		for(int i : ints){
			result.add(i);
		}
		return result;
	}

public Tab copy(){
	LinkedList<LinkedList<Integer>> result = new LinkedList<LinkedList<Integer>>();
	for(LinkedList<Integer> l:this.tab){
		LinkedList<Integer> temp = copy(l);
		result.add(temp);
	}
	return new Tab(result);
}

	// a method to order a list of integers and returns the sign of the permutation
   public static int Order(LinkedList<Integer> i, int start, int end){
   	if(start==end){
   		return 1;
   	}
   	int sign = 1;
   	  int n = i.get(start);
   	  for(int j = start; j < end+1; j++){
   	  	if(i.get(j)<n){
   	  		int m = i.get(j);
   	  		i.set(start, m);
   	  		i.set(j,n);
   	  		n= m;
   	  		sign = sign*-1;
   	  	}


   	  }
   	  sign = sign*Order(i, start+1, end);
   	  return sign;


   }
/**
* Orders a list of integers and determines the sign of the required permutation
* @param l The list to be ordered
* @return The sign of the ordering permutation
*/
   public static int order(LinkedList<Integer> l){
		return Order(l,0,l.size()-1);
	}

   // orders the columns and returns the sign
/**
* Orders the columns of a tableau and returns the sign of the required permutation
* @return The sign of the needed permutation
*/
   public int orderCol(){
   	int result = 1;
   	for(LinkedList<Integer> l: this.tab){
   		result = result*order(l);
   	}
   	return result;
   }

// methods to return a truanced version of a list
   // the new list begins at the ith element
/**
* A method to produce a truanced version of a list
* @param l The list to be truanced
* @param i The ith element is the first element of the new list
* @return The truanced version
*/
   public static LinkedList<Integer> cutStart(LinkedList<Integer> l, int i){
   	LinkedList<Integer> result = new LinkedList<Integer>();
   	for(int k =i;k<l.size();k++){
   		result.add(l.get(k));
   	}
   	return result;
   }

  /** 
* A method to produce a truanced version of a list, removing the latter part of it
* @param l The list to be truanced
* @param i The ith element and everything beyond it is removed
* @return The truanced version
*/
// removes everything past and including the ith element
   public static LinkedList<Integer> cutEnd(LinkedList<Integer> l, int i){
LinkedList<Integer> result = new LinkedList<Integer>();
for(int k = 0;k<i;k++){
	result.add(l.get(k));
   }
   return result;
}

   //returns ordered perms of given length built from a list of integers
   public static LinkedList<LinkedList<Integer>> partPerm(LinkedList<Integer> ints,int length, int start){

    int size = ints.size();
    LinkedList<Integer> ordered = copy(ints);
    int dummy = order(ordered);
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
   * Produces all ordered lists of a given length that be produced from a collection of integers
   * @param length The length of the return lists
   * @param ints The collection of integers to be chosen from
   * @return All the possible ordered lists of the desired length
   */

   public static LinkedList<LinkedList<Integer>> partPerm(LinkedList<Integer> ints, int length ){
   	return partPerm( ints,length, 0);
   }

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

/**
* Splits a list of integers into all possible parirs of lists with ordered elements
* @param length The length of one of the two result lists
* @param ints The list to be split
* @return The possible ordered partitions of the input list
*/

   public static LinkedList<LinkedList<Integer>> partit(LinkedList<Integer> ints, int length){
   	LinkedList<LinkedList<Integer>> result = new LinkedList<LinkedList<Integer>>();
   	for(LinkedList<Integer> l : partPerm(ints,length)){
   		LinkedList<Integer> temp = copy(ints);
        int dummy = order(temp);
   		for(int i: l){
      temp = binIt(temp,i);
   		}
   		l.addAll(temp);
   		result.add(l);

   	}
   	return result;
   }

   // returns the strip corresponding to a row descent

   /**
   * Finds the first place in tableau where two adjacent row elements are out of order then returns the corresponding column strips needed to perform the garnir algorithm
   * @return The column strips
   */

   public LinkedList<Integer> stripBad(){
   	LinkedList<Integer> result = new LinkedList<Integer>();
   	for(int i = 0; i<this.rowNum();i++){
   		for(int j = 0;j<this.row(i).size()-1;j++){
   			if(this.tab.get(j).get(i)>this.tab.get(j+1).get(i)){
          result.addAll(cutEnd(tab.get(j+1),i+1));
          result.addAll(cutStart(tab.get(j),i));
          return result;
      }
  }
}
return null;
   }



// returns all permuations of the strip associated with the first row descent
   /**
   * Returns all permutations of the column strips. Essentially returns the Garnir element
   * @return All possible permutation of the column strips produced from the first row descent
   */
   public LinkedList<LinkedList<Integer>> stripPerms(){
LinkedList<Integer> temp = new LinkedList<Integer>();

   	for(int i = 0; i<this.rowNum();i++){
   		for(int j = 0;j<this.row(i).size()-1;j++){
   			if(this.tab.get(j).get(i)>this.tab.get(j+1).get(i)){
          temp.addAll(cutEnd(tab.get(j+1),i+1));
          temp.addAll(cutStart(tab.get(j),i));
          
          return partit(temp, i+1);
   			}
   		}
   	}
   	
return null;
   }

 // permutes a given set of integers to a given permutation
/**
* Finds a particular set of entries in a tableau and permutes them to a given order
* @param original The entires beign permuted
* @param perm The new order of the integers
* @return A new tableau identical to the old one but with the chosen entries permuted
*/
   public Tab permute(LinkedList<Integer> original, LinkedList<Integer> perm){
   	Tab result = this.copy();
   	
   	for(LinkedList<Integer> l : result.tab){
   		for(int j = 0;j<l.size();j++){
   			for(int i=0;i<original.size();i++){
   				if(l.get(j)==original.get(i)){
   					l.set(j,perm.get(i));
   					break;
   				}
   			}

   		}
       }
       return result;
   
   
   }

 // returns sign of permutation
/**
* Takes in two orderings of set of integers and determines the sign of the permutation needed to transpose one to the other
* @param original One ordering of the integers
* @param perm Another ordering
* @return The sign of the permutation needed to get from one to the other
*/
   public static int permSign(LinkedList<Integer> original, LinkedList<Integer> perm){
   	if(original.size()<2){
   		return 1;
   	}
   LinkedList<Integer> temp1 = copy(original);
   LinkedList<Integer> temp2 = copy(perm);
   int sign = -1;
   int holder = original.get(0);
   int i =1;
   LinkedList<Integer> temp = new LinkedList<Integer>();
   	while(i!=0){
for(int j =0;j<perm.size();j++){
	if(perm.get(j)==holder){
		sign=sign*-1;
		holder = original.get(j);
		i=j;
        temp.add(holder);
        break;

	}
}


   	}
   	for(int k:temp){
	temp1 = binIt(temp1,k);
	temp2 = binIt(temp2,k);
}
return sign*permSign(temp1,temp2);

   }


// returns an int array corresponding to the column lengths
/**
* Determines the shape of a tableau
* @return The diagram shape as represented by an integer array. The entries correspond to column lengths
*/
public int[] type(){
  int[] result = new int[this.tab.size()];
  for(int i =0;i<this.tab.size();i++){
  	result[i] = this.tab.get(i).size();
  }
  return result;
}   

// adds an integer to the ith column of a tableaux
/**
* Adds an integers to the ith column of a tableau
* @param num The number being added
* @param column The column it's being added to
* @return A new tableau with the added number
*/

public Tab addNum(int num, int column){
	Tab result = this.copy();
	if(result.colNum()<column+1){
		LinkedList<Integer> temp = new LinkedList<Integer>();
		temp.add(num);
		result.tab.add(temp);
		return result;
	}
	result.tab.get(column).add(num);
	return result;
}


    // generates a basis of standard tableaux for a given partition
/**
* Generates all standard tableaux associated to a Young diagram
* @param partition The shape of the Young diagram
* @return All standard tableaux of the desired shape
*/
	public static LinkedList<Tab> basis(int[] partition){
		
		LinkedList<LinkedList<Integer>> start =  new LinkedList<LinkedList<Integer>>();
		LinkedList<Integer> now = new LinkedList<Integer>();
		now.add(1);
		start.add(now);
		Tab s = new Tab(start);
		LinkedList<Tab> tabs = new LinkedList<Tab>();
		tabs.add(s);
		int size = 0;
		int columns = partition.length;
		for(int a = 0; a<partition.length;a++){
			size = size + partition[a];
		}
        for(int k = 2;k<=size;k++){
        	LinkedList<Tab> temp = new LinkedList<Tab>();
        	for(Tab t : tabs){
        		// hold ensures that the same number isn't added in the same place twice
        		int hold=0;
        		//adds the number to first column which isn't at final size
        		for(int i = 0; i<columns;i++){
        			if(i>t.colNum()){break;}
        			if((t.column(i).size()<partition[i])&&t.column(i).size()>=t.column(i+1).size()){

        				temp.add(t.addNum(k,i));
        				hold = t.column(i).size();
        				
        			}
                }

                   	}
        	tabs = temp;
        	


        }
        return tabs;

	}

  // shifts all entries in the tableauxs by n
  /**
  * Generates all standard tableaux, but with every entry shifed up by a desired integer amount
  * @param partition The diagram shape
  * @param n The amount each entry is shifted
  * @return All shifted standard tableaux
  * @see basis
  */
  public static LinkedList<Tab> basis(int[] partition, int n){
    
    LinkedList<LinkedList<Integer>> start =  new LinkedList<LinkedList<Integer>>();
    LinkedList<Integer> now = new LinkedList<Integer>();
    now.add(1+n);
    start.add(now);
    Tab s = new Tab(start);
    LinkedList<Tab> tabs = new LinkedList<Tab>();
    tabs.add(s);
    int size = 0;
    int columns = partition.length;
    for(int a = 0; a<partition.length;a++){
      size = size + partition[a];
    }
        for(int k = 2+n;k<=size+n;k++){
          LinkedList<Tab> temp = new LinkedList<Tab>();
          for(Tab t : tabs){
            // hold ensures that the same number isn't added in the same place twice
            int hold=0;
            //adds the number to first column which isn't at final size
            for(int i = 0; i<columns;i++){
              if(i>t.colNum()){break;}
              if((t.column(i).size()<partition[i])&&t.column(i).size()>=t.column(i+1).size()){

                temp.add(t.addNum(k,i));
                hold = t.column(i).size();
                
              }
                }

                    }
          tabs = temp;
          


        }
        return tabs;

  }

// generates all tableaux with k boxes shifted by n
/**
* Generates all standard tableaux with k boxes with the entires shifted
* @param k The number of boxes in the tableaux
* @param n The amount each entry is shifted
* @return All k-box standard tableaux, with entries shifted by the desired amount
*/
  public static LinkedList<Tab> allTabs(int k, int n){
    LinkedList<Tab> result = new LinkedList<Tab>();
    LinkedList<int[]> parts = RatNum.partition(k);
    for(int[] p:parts){
      LinkedList<Tab> tabs = basis(p,n);
      result.addAll(tabs);
    }
    return result;
  }

 
// applies a cyclic permutation to a Tableaux, eg. (xyz), x->y,y->z,z->x
/**
* Applies a cyclic permutation to a tableau
* @param cycle The desired permutation in cyclic notation
* @return The permuted tableau
*/
public Tab cycle(int[] cycle){
	// stores the numbers which have already been shifted
	LinkedList<Integer> dummy = new LinkedList<Integer>();
	Tab result = this.copy();
	for(LinkedList<Integer> l: result.tab){
		for(int k=0;k<l.size();k++){
			
			for(int j=0;j<cycle.length;j++){
				if(l.get(k)==cycle[j]){
					if(j==0){
						l.set(k,cycle[cycle.length-1]);
                       
                        break;
						
					}
					l.set(k,cycle[j-1]);
					
					break;
				}

			}
		}
	}
	return result;

}


// genertates the canonical tableaux corresponding to a partition. The tableaux that has been filled down the columns
/**
* Generates the canonical tableaux associated to a Young diagram, ie. the one produced by filling down the columns
* @param type The diagram shape
* @return The canonical tableau
*/
public static Tab canonical(int[] type){
  LinkedList<LinkedList<Integer>> temp = new LinkedList<LinkedList<Integer>>();
  int size =0;
  for(int i: type){
    size=size+i;
  }
  int k =1;
  for(int j =0;j<type.length;j++){
    LinkedList<Integer> columnj = new LinkedList<Integer>();
   for(int n =0;n<type[j];n++){
columnj.add(k);
k=k+1;
   }
   temp.add(columnj);
  }
return new Tab(temp);

}



}