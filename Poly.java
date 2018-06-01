import java.util.Scanner; 
import java.io.*;
import java.util.LinkedList;
public class Poly{
	private class Node{
		private RatNum coeff;
		private int exponent;
        private Node next; 

        public Node(){
        	this.coeff = new RatNum(0,1);
        	this.exponent = 0;
        	this.next = null;
        	
        }

		public Node(RatNum r, int c,Node n){
			this.coeff=r;
			this.exponent = c;
			this.next = n;
		}

		public Node(RatNum r, int c){
			this.coeff = r;
			this.exponent = c;
			this.next = null;
		}

		public void setNext(Node n){
			if(this.getCo()==null){
				this.coeff = n.coeff;
				this.exponent = n.exponent;
				this.next = n.next;
			}
			else{
			this.next = n;
		}
		}

		public Node getNext(){
			return this.next;
		}

		public RatNum getCo(){
			return this.coeff;
		}

		public int getExpo(){
			return this.exponent;
		}





		
	}

	private Node  head;
	

	public Poly(){
		head = null;
	};

	public Poly(RatNum n, int c){
		Node head = new Node(n,c);
		this.head = head;
	}

	public Poly(int n, int c){
		Node head = new Node(RatNum.convert(n),c);
		this.head = head;
	}

	private  Poly(Node n){
		head =n;
	}



	public void plus(RatNum n, int c){
		if(this.head==null){
			this.head = new Node(n,c);
			return;
		}
      Node mono = new Node(n,c);
      Node temp = this.head;
      while(temp.getNext()!=null){
      	temp = temp.getNext();
      }
      temp.setNext(mono);
		
	}

	public void times(RatNum r){
		Node temp = this.head;
		while(temp!=null){
			temp.coeff = RatNum.mult(temp.coeff,r);
			temp = temp.getNext();
		}
	}

	public void reduce(){
		Node temp = this.head;
		while(temp!=null){
		temp.coeff.reduce();
		temp = temp.getNext();
		}
	}

		public void plus(int n, int c){
		if(this.head==null){
			this.head = new Node(RatNum.convert(n),c);
			return;
		}
      Node mono = new Node(RatNum.convert(n),c);
      Node temp = this.head;
      while(temp.getNext()!=null){
      	temp = temp.getNext();
      }
      temp.setNext(mono);
		
	}

	public Poly add( Poly q){
        
		Node temp1 = this.head;
		Node temp2 = q.head;
		Node head = new Node();
		Node temp = head;
	while(temp1!=null&&temp2!=null){
		int px = temp1.getExpo();
		int qx = temp2.getExpo();
		

		if(px==qx){
			temp.setNext(new Node(RatNum.add(temp1.getCo(),temp2.getCo()),px));
			temp1 = temp1.getNext();
			temp2 = temp2.getNext();

		}
         else{
         	if(px>qx){
         		temp.setNext(new Node(temp1.getCo(),px));
         		temp1 = temp1.getNext();
         	}
         	else{
         		temp.setNext(new Node(temp2.getCo(),qx));
         		temp2 = temp2.getNext();
         	}
         }

         temp = temp.getNext();
	}

	if(temp1!=null){
		temp.setNext(temp1);
	}
	if(temp2!=null){
		temp.setNext(temp2);
	}



return new Poly(head);

}


public Poly mult(Poly q){
	Node temp1 = this.head;
	
	Node head = new Node();
	Node temp = head;
	while(temp1!=null){
		Node temp2 = q.head;
		while(temp2!=null){
			temp.setNext(new Node(RatNum.mult(temp1.getCo(),temp2.getCo()),temp1.getExpo()+temp2.getExpo()));
			temp = temp.getNext();
			temp2 = temp2.getNext();

		}
		temp1 = temp1.getNext();
	}
	return new Poly(head);

}

public RatNum evaluate(RatNum r){
	RatNum result = new RatNum(0,1);
	Node temp = this.head;
	while(temp!=null){
		if(temp.getCo().nom()!=0){
		RatNum term = RatNum.mult(temp.getCo(),r.power(temp.getExpo()));
		result = RatNum.add(result,term);
	}
	
		temp = temp.getNext();
	}
	return result;

}





public void print(){
	Node temp = this.head;
	String result = "";
	int i = 0;
	while(temp!=null){
		if(temp.getCo().nom()==0){
			temp = temp.getNext();
			continue;
		}
		
		if(i==0){
		result = result+RatNum.toString(temp.getCo())+"x^"+temp.getExpo();	
		}
		else{
       if(temp.getExpo()!=0){
       	result = result + " + "+RatNum.toString(temp.getCo())+"x^"+temp.getExpo();
       }
       else{
       	result = result + " + "+RatNum.toString(temp.getCo());
       }
   }
       temp = temp.getNext();
       i=i+1;

	
}

	System.out.println(result);
}

// Takes a Tableaux and returns the Polynomial corresponding to it's representation dimension
public static Poly dimEq(Tab t){
	int[] type = t.type();
	Poly result = new Poly(1,0);
	for(int i =0;i< type.length;i++){
		for(int j =0;j<type[i];j++){
			Poly temp = new Poly(1,1);
			temp.plus(i-j,0);
			
			result = result.mult(temp);
		}
	}
	return result;
}






}