import java.util.Scanner; 
import java.io.*;
import java.util.LinkedList;

/**
* This program contains all matrix methods, including the methods to construct projection matrices from a Young tableau in any symmetric group irrep. Matricies are stored as 2d arrays of rational numbers
* @author Michael Spence
*/
public class MatrixRat {
	private RatNum[][] matrix;

	public MatrixRat(){}
/**
* Constructs a matrix from a 2d array of rational numbers
* @param n A 2d array of rational numbers
*/
	public MatrixRat(RatNum[][] n){
		this.matrix = n;
	}

/**
* Retruns the number of columns in a matrix. Since I'm only interested in square matrices this is also the number of rows
* @param m The matrix we are interested in
* @return The number of columns
*/
	public static int size(MatrixRat m){
		return m.matrix.length;
		
	}

/**
* Generates the n dimensional identity matrix
* @param m The dimension of the desired matrix	
* @return The nxn identity matrix
*/
	// creates identity matrix of dimension m
	public static MatrixRat identity(int m){
RatNum[][] result = new RatNum[m][m];
for(int i =0;i<m;i++){
	for(int j =0;j<m;j++){
		if(i==j){
			result[i][j] =  RatNum.convert(1);
		}
		else{
			result[i][j]=  RatNum.convert(0);
		}
	}

}
return new MatrixRat(result);
	}
/**
* Generates the square zero matrix of desired size
* @param m Size of matrix
* @return mxm zero matrix
*/
// creates zero matrix of dimension m
	public static MatrixRat zero(int m){
RatNum[][] result = new RatNum[m][m];
for(int i =0;i<m;i++){
	for(int j =0;j<m;j++){
		
		
			result[i][j]=  RatNum.convert(0);
		}

}
return new MatrixRat(result);
	}

/**
* Divides through all denominators and nominators of entries by their respective gcds for simplest representation
*/
// divides thorugh all entries by gcd for convienent display
	public void reduce(){
		RatNum[][] matrix = this.matrix;
		int size = matrix.length;
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				matrix[i][j].reduce();
			}
		}

	}
/**
* Creates a matrix with all entries null
* @param columns The number of columns
* @param rows The number of rows
*/
		// creates an empty matrix of a certain size
	public MatrixRat(int rows, int columns){
		RatNum[][] result = new RatNum[rows][columns];
		
		this.matrix = result;
	}
/**
* Returns the i,jth component of a matrix
* @param i The row number
* @param j The column number
* @return the desired entry
*/
	// a method to get the i,jth component of a matrix, i being row number
	public RatNum component(int i , int j){
		return this.matrix[i][j];
	}
/**
* sets the i,jth component of a matrix
* @param i The row number
* @param j The column number
* @param n the desired value
*/
	// sets the element in the ith row and jth column
	public void set(RatNum n, int i, int j){
		this.matrix[i][j]=n;
	}

/**
*Adds two matrices together
* @param mat1 The first matrix
* @param mat2 The second matrix
* @return The sum of the two
*/
		// a method for matrix addition
	public static MatrixRat add(MatrixRat mat1 , MatrixRat mat2){
		int s = size(mat1);
		RatNum[][] result = new RatNum[s][s];
		for(int i = 0; i<s;i++){
			for(int j =0; j<s;j++){
				result[i][j] = RatNum.add(mat1.component(i,j),mat2.component(i,j));
				result[i][j].reduce();
			}
		}
    return new MatrixRat(result);

	}

			// a method for matrix subtraction
	public static MatrixRat sub(MatrixRat mat1 , MatrixRat mat2){
		int s = size(mat1);
		RatNum[][] result = new RatNum[s][s];
		for(int i = 0; i<s;i++){
			for(int j =0; j<s;j++){
				result[i][j] = RatNum.sub(mat1.component(i,j),mat2.component(i,j));
				result[i][j].reduce();
			}
		}
    return new MatrixRat(result);

	}

    // multiplies by a rational number
    /**
    * Multiplies every entry by a desired rational number
    * @param m The number which we want to multiply all entries by
    */
	public void times(RatNum m){
		RatNum[][] matrix = this.matrix;
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j<matrix[i].length;j++){
				matrix[i][j] = RatNum.mult(matrix[i][j],m);
			}
		}
	}
/** 
* Multiplies two matrices
* @param mat1 The first matrix
* @param mat2 The second matrix
* @return mat1*mat2
*/
	public static MatrixRat mult(MatrixRat mat1, MatrixRat mat2){
		int s = size(mat1);
		RatNum[][] res = new RatNum[s][s];
		MatrixRat result = new MatrixRat(res);
		for(int i = 0 ; i<s; i++){
			for(int j = 0;j<s; j++){
                RatNum r = new RatNum(0,1);
				for(int k = 0;k<s;k++){
                r = RatNum.add(r ,RatNum.mult(mat1.component(i,k),mat2.component(k,j)));
                r.reduce();
                
				}
				r.reduce();
				result.set(r,i,j);
			}
		}
		return result;
	}

/**
*Raises a matrix to a given integer power
* @param i The desired power
* @param m The target matrix
* @return m^i
*/
	public static MatrixRat power(int i, MatrixRat m){
		if(i==1){
			return m;
		}
		return mult(m, power(i-1,m));
	}

	// a print method
	public void print(){
		RatNum[][] matrix = this.matrix;
		int s = matrix.length;
		String[] rows = new String[s];
		for(int i = 0;i<s;i++){
			rows[i] = "";
			for(int j = 0;j<s;j++){
				rows[i] = rows[i]+" "+RatNum.toString(matrix[i][j]);
			}
			System.out.println(rows[i]+"\n");
		}
	}

// generates the matrix corresponding to a transposition
/**
* Generates the matrix corresponding to a pairwise swap. It does this by applying the permutation to every standard tableaux in the basis to produce non-standard tableaux, then expressing these in terms of standard ones using the garnir algorithm. We can then construct the matrix.
* @param swap The two cycle
* @param basis A list of all standard tableaux of the same shape representing the basis of the symmetric group irrep we're interested in
* @return The matrix representing this pairwise swap in the desired irrep
*/
	public static MatrixRat swapMatrix(int[] swap, LinkedList<Tab> basis){
		int dim = basis.size();
		int[][] result = new int[dim][dim];
		LinkedList<Integer> original = new LinkedList<Integer>();
		original.add(swap[0]);
		original.add(swap[1]);
		LinkedList<Integer> perm = new LinkedList<Integer>();
		perm.add(swap[1]);
		perm.add(swap[0]);
		for(int i = 0 ;i<dim;i++){
			Tabby temp = new Tabby(basis.get(i).permute(original,perm),1);

			LinkedList<Tabby> columni = temp.straight();
			for(int j =0;j<dim;j++){
				int entryji = 0;
				for(Tabby t: columni){

					if(basis.get(j).equal(t.tab())){
						entryji = entryji + t.coeff();
						
					}
				}
				result[j][i] = entryji;

			}
		}

	RatNum[][] fin = new RatNum[dim][dim];
	for(int k = 0;k<dim;k++){
		for( int l=0;l<dim;l++){
			fin[k][l] = RatNum.convert(result[k][l]);
		}
	}

		return new MatrixRat(fin);

	}


// generates symmeterizer over a given set of indices

/**
* Generates the matrix corresponding to a symmeterizer on a given set of indices using the recursive definition of them
* @param indices A list of integers representing the indicies being symmeterized over
* @param basis A list of all standard tableaux of the same shape representing the basis of the symmetric group irrep we're interested in
* @return The matrix representing this symmeterizer in the desired symmetric group irrep
*/
	public static MatrixRat symmMatrix(LinkedList<Integer> indices, LinkedList<Tab> basis){
		if(indices.size()==1){
			return identity(basis.size());
		}
        LinkedList<Integer> dummy = Tab.copy(indices);
		MatrixRat swapper = swapMatrix(new int[]{indices.get(0),indices.get(1)},basis);
		dummy.remove(0);
		MatrixRat next = symmMatrix(dummy, basis);
		MatrixRat temp = mult(next,mult(swapper,next));
		temp.times(RatNum.convert(indices.size()-1));
		MatrixRat result = add(next,temp);
		result.times(new RatNum(1,indices.size()));
		return result;
	}


// generates the antisymm over some indicies
/**
* Generates the matrix corresponding to an antisymmeterizer on a given set of indices using the recursive definition of them
* @param indices A list of integers representing the indicies being antisymmeterized over
* @param basis A list of all standard tableaux of the same shape representing the basis of the symmetric group irrep we're interested in
* @return The matrix representing this antisymmeterizer in the desired symmetric group irrep
*/	
	public static MatrixRat antisymmMatrix(LinkedList<Integer> indices, LinkedList<Tab> basis){
		if(indices.size()==1){
			return identity(basis.size());
		}
        LinkedList<Integer> dummy = Tab.copy(indices);
		MatrixRat swapper = swapMatrix(new int[]{indices.get(0),indices.get(1)},basis);
		dummy.remove(0);
		MatrixRat next = antisymmMatrix(dummy, basis);
		MatrixRat temp = mult(next,mult(swapper,next));
		temp.times(RatNum.convert(-(indices.size()-1)));
		MatrixRat result = add(next,temp);
		result.times(new RatNum(1,indices.size()));
		return result;
	}


// generates the matrix corresponding to a product of 3 projection operators
/**
* If the projection operator of a tableau Y is written P_Y, then this method returns P_Y.P_X.P_Z.P_Y=M(X,Z,Y)P_Y for three tableaux, X,Y,Z
* @param y The largest tableau
* @param x One of the smaller tableau
* @param z One of the smaller tableau
* @return The corresponding matrix
*/
	public static MatrixRat threeJmatrix(Tab x, Tab z, Tab y){
		LinkedList<Tab> basis = Tab.basis(y.type());
		MatrixRat prox = proMatrix(x,basis);
		MatrixRat proz = proMatrix(z,basis);
		MatrixRat proy = proMatrix(y,basis);
		MatrixRat middle = mult(prox,proz);
		MatrixRat result = mult(proy,mult(middle,proy));
		return result;
	}

// returns the M(X,Z,Y) of three tableaux
/**
* For three tableaux returns the M(x.z.y) defined earlier. We need this to work out the 3-j
* @param y The largest tableau
* @param x One of the smaller tableau
* @param z One of the smaller tableau
* @return The rational number m(x,z,y) defined earlier
*/
	public static RatNum threeJM(Tab x, Tab z, Tab y){
		MatrixRat temp = threeJmatrix(x, z, y);
		MatrixRat proy = proMatrix(y,Tab.basis(y.type()));
			RatNum norm = new RatNum();
		
		for(int i = 0;i<size(proy);i++){
			if(temp.component(i,i).nom()!=0){
					norm = RatNum.divide(temp.component(i,i),proy.component(i,i));
					break;
				}
			}
		norm.reduce();
		return norm;

	}

// calculates the matrix used for working out a  6-j 
	// generates the matrix corresponding to a product of 3 projection operators
/**
* If the projection operator of a tableau Y is written P_Y, then this method returns P_Y.P_X.P_W.P_V.P_Z.P_U.P_Y=M(U,V,W,X,Z,Y)P_Y for six tableaux, X,Y,Z,U,V,W. We need this in order to work out a six-j
* @param y The largest tableau
* @param x One of the smaller tableau
* @param z One of the smaller tableau
* @param v The largest tableau
* @param w One of the smaller tableau
* @param u One of the smaller tableau
* @return The corresponding matrix
*/
public static MatrixRat sixJmatrix(Tab v, Tab w, Tab x, Tab u, Tab z, Tab y){
		LinkedList<Tab> basis = Tab.basis(y.type());
		MatrixRat prov = proMatrix(v,basis);
		MatrixRat prow = proMatrix(w,basis);
		MatrixRat prox = proMatrix(x,basis);
		MatrixRat prou = proMatrix(u,basis);
		MatrixRat proz = proMatrix(z,basis);
		MatrixRat proy = proMatrix(y,basis);
		MatrixRat middle = mult(prov,mult(prow,prox));
		MatrixRat first = mult(prou,proy);
		MatrixRat last = mult(proy,proz);
		MatrixRat result = mult(last,mult(middle,first));
		return result;
	}

// calculates the M associated to a 6j coefficent
public static RatNum sixJM(Tab v, Tab w, Tab x, Tab u, Tab z, Tab y){
		LinkedList<Tab> basis = Tab.basis(y.type());
		MatrixRat prov = proMatrix(v,basis);
		MatrixRat prow = proMatrix(w,basis);
		MatrixRat prox = proMatrix(x,basis);
		MatrixRat prou = proMatrix(u,basis);
		MatrixRat proz = proMatrix(z,basis);
		MatrixRat proy = proMatrix(y,basis);
		MatrixRat middle = mult(prov,mult(prow,prox));
		MatrixRat first = mult(prou,proy);
		MatrixRat last = mult(proy,proz);
		MatrixRat result = mult(last,mult(middle,first));
		int n =0;
		for(Tab temp:basis){
			if(y.equal(temp)){
				break;
			}
			n=n+1;
		}
		return result.component(n,n);
}

// generates the projection matrix associated with a Tableaux
/**
* Calculates the Young projection operator produced by tableau in the desired symmetric group irrep
* @param t The tableau
* @param basis A list of tableaux representing the basis of whater irrep we're working in
* @return The corresponding matrix
*/
	public static MatrixRat proMatrix(Tab t, LinkedList<Tab> basis){
		MatrixRat result = identity(basis.size());
		for(int i=0;i<t.rowNum();i++){
			result = mult(symmMatrix(t.row(i),basis),result);
		}
		for(int j =0;j<t.colNum();j++){
			result = mult(antisymmMatrix(t.column(j),basis),result);
		}
		RatNum norm = new RatNum();
		MatrixRat temp = mult(result,result);
		outerloop:
		for(int i = 0;i<basis.size();i++){
			for(int j=0;j<basis.size();j++){
				if(result.component(i,j).nom()!=0){
					norm = RatNum.divide(result.component(i,j),temp.component(i,j));
					break outerloop;
				}
			}
		}

		result.times(norm);
		return result;
	}

// constructs the projector which is a sum of young projectors of the same type

public static MatrixRat youngPro(LinkedList<Tab> basis, int[] type, int shift){
	LinkedList<Tab> sum = Tab.basis(type,shift);
	MatrixRat result = zero(basis.size());
	for(Tab t : sum){
		result = add(result, proMatrix(t,basis));
	}
	return result;


}	

// type1 is the largest
public static MatrixRat wignerMat(int[] type1, int[] type2, int[] type3){

	int size2 = 0;
	for(int j : type2){
		size2 = size2 +j;
	}
	

	LinkedList<Tab> basis = Tab.basis(type1);
	MatrixRat pro1 = youngPro(basis,type1,0);
	MatrixRat pro2 = youngPro(basis,type2,0);
	MatrixRat pro3 = youngPro(basis,type3,size2);
	MatrixRat pro4 = youngPro(basis,type2,1);
	
	MatrixRat middle = mult(pro2,pro3);

	return mult(pro1,mult(middle,pro1));



}

// returns matrix corresponding to a cycle
/**
* Generates the matrix corresponding to any cyclic permutation
* @param cycle The permutation we wish to turn into a matrix
* @param basis A list of tableaux representing the basis of whatever irrep we're working in
* @return The desired matrix
*/
	public static MatrixRat cycleMatrix(int[] cycle, LinkedList<Tab> basis){
	int dim = basis.size();
		int[][] result = new int[dim][dim];
		for(int i = 0 ;i<dim;i++){
			Tabby temp = new Tabby(basis.get(i).cycle(cycle),1);

			LinkedList<Tabby> columni = temp.straight();
			for(int j =0;j<dim;j++){
				int entryji = 0;
				for(Tabby t: columni){

					if(basis.get(j).equal(t.tab())){
						entryji = entryji + t.coeff();
						
					}
				}
				result[j][i] = entryji;

			}
		}

	RatNum[][] fin = new RatNum[dim][dim];
	for(int k = 0;k<dim;k++){
		for( int l=0;l<dim;l++){
			fin[k][l] = RatNum.convert(result[k][l]);
		}
	}

		return new MatrixRat(fin);
	}


// an alternate method of generating orthogonal projection operators as described by Littlewood. 
//Returns the projector corresponding to the nth tableaux for a partition
/**
* Generates the Littlewood-Young projector corresponding to a particular tableau specified by in posistion in the ordering of tableaux of a particular shape
* @param n The position in the ordering of the desired tableau
* @param type The shape of the relevant tableau
* @param basis A list of tableaux representing the basis of whatever irrep we're working in
* @return The projector in the desired representation
*/
	public static MatrixRat littlewoodPro(int n, int[] type, LinkedList<Tab> basis){
		LinkedList<Tab> all = Tab.basis(type);
		if(n==all.size()-1){
			return proMatrix(all.get(all.size()-1),basis);
		}

      MatrixRat temp = identity(basis.size());
      for(int i =0;i<all.size()-1-n;i++){
      	MatrixRat pro = littlewoodPro(all.size()-1-i,type,basis);
      	pro.times(new RatNum(-1,1));
      	temp = add(temp, pro);
      }

      return mult(proMatrix(all.get(n),basis),temp);
		




	}


// generates the Littlewood-Young Projectors for a Young diagram in a particular representation specified by the basis input. They act on lines starting at shift+1.
/**
* Generates all Littlewood-Young projectors corresponding to a particular Young diagram
* @param diagram Represents the relative diagram
* @param basis A list of tableaux representing the basis of whatever irrep we're working in
* @return A list of the corresponding matrices, ordering in the same way as the respective tableaux
*/
	public static LinkedList<MatrixRat> littlePros(int[] diagram, LinkedList<Tab> basis){

		LinkedList<MatrixRat> result = new LinkedList<MatrixRat>();
		int k = 0;
		for(int l: diagram){
			k=k+l;
		}
		if(k<5){
			LinkedList<Tab> tabs = Tab.basis(diagram);
			for(Tab t:tabs){
				result.add(proMatrix(t,basis));

			}
			return result;
		}
		MatrixRat identity = identity(basis.size());
		LinkedList<Tab> tabs = Tab.basis(diagram);
		int size = tabs.size();
		MatrixRat first = proMatrix(tabs.get(size-1),basis);
		result.add(first);
		for(int i=1;i<=size-1;i++){
			MatrixRat sum = zero(basis.size());
			for(MatrixRat e : result){
                sum = add(sum,e);
			}
			MatrixRat minus = sub(identity,sum);
			MatrixRat next = mult(proMatrix(tabs.get(size-1-i),basis),minus);

			result.add(next);
		}
        return result;
	}

// generates the Littlewood-Young Projectors for a Young diagram in a particular representation specified by the basis input. They act on lines starting at shift+1.
/**
* Generates all Littlewood-Young projectors corresponding to a particular Young diagram. Also shifts the entries of all tableaux by a desired integer amount
* @param diagram Represents the relative diagram
* @param basis A list of tableaux representing the basis of whatever irrep we're working in
* @param shift How much we want the entries shift up
* @return A list of the corresponding matrices, ordering in the same way as the respective tableaux
*/
// an alternative method which shifts all tableaux entries by shift
	public static LinkedList<MatrixRat> littlePros(int[] diagram, LinkedList<Tab> basis,int shift){

		LinkedList<MatrixRat> result = new LinkedList<MatrixRat>();
		int k = 0;
		for(int l: diagram){
			k=k+l;
		}
		if(k<5){
			LinkedList<Tab> tabs = Tab.basis(diagram,shift);
			for(Tab t:tabs){
				result.add(proMatrix(t,basis));

			}
			return result;
		}
		LinkedList<Tab> tabs = Tab.basis(diagram,shift);
		int size = tabs.size();
		MatrixRat first = proMatrix(tabs.get(size-1),basis);
		result.add(first);
		for(int i=1;i<=size-1;i++){
			MatrixRat temp = identity(basis.size());
			for(MatrixRat e : result){
                temp = sub(temp,e);
			}
			MatrixRat next = mult(proMatrix(tabs.get(size-1-i),basis),temp);
			result.add(next);
		}
        return result;
	}



      














}