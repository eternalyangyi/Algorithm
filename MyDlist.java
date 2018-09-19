import java.io.*;
import java.util.Scanner;


import java.io.File;
import java.io.FileNotFoundException;

public class MyDlist extends DList {
	protected DNode node;
	public MyDlist() {
		super();
	/** Class MyDlist extend Dlist, 
	 * so that use super() to initialize a empty double linked list.
	 */
	}
	public static void printList(MyDlist u){
		DNode temp = u.getFirst();
		/**Initialize a temp to store nodes of double link list which is needed to print.
		 * 
		 */
		while(true){	
		/** While loop to get string of each node.
		 * 
		 */
		if (temp.getNext() == null){
			break;
		/**When tail of list is touched, break loop.
		 * 
		 */
		}
		System.out.println(temp.getElement());
		temp = temp.getNext();
		/** Update temporary node.
		 * 
		 */
	}
		
	}
	public MyDlist(String f) {
		/** Store nodes from standrad input.
		 *  Use variable S to store every String.
		 */
		if (f == "stdin"){
		DataInputStream Standredinput = 
			      new DataInputStream( 
			        new BufferedInputStream(System.in));
		String s;
	try {
		/** If a empty line is inputed, end reading.
		 * 
		 */
		while((s = Standredinput.readLine()).length() != 0){
			/** Create node as a DNode to store string to its element.
			 *  Add each node to end of double linked list.
			 */
			node = new DNode(s,null,null);
			addLast(node);
		}
		}
	catch (IOException e) { 
	      e.printStackTrace(); 
	       }
		}
		else{
			/** Use scanner to read string one by one from file.
			 *  f is a variable to store file path.
			 */
			File file = new File(f);
			try {

		        Scanner sc = new Scanner(file);
		        while (sc.hasNextLine()) {
		            String i = sc.next();
		            /** Create node as a DNode to store string to its element.
					 *  Add each node to end of double linked list.
					 */
		            node = new DNode(i,null,null);
					addLast(node);
		        }
		        sc.close();
		    } 
		    catch (FileNotFoundException e) {
		        e.printStackTrace();
		    }
			
		}
	}
	public static MyDlist cloneList(MyDlist u){
		/** Initialize a empty list to store nodes from given list u.
		 *  tempu as a variable to get each node from u by using while loop.
		 */
		MyDlist v = new MyDlist();
		DNode tempu = u.getFirst();
		while (true){
			DNode tempv = new DNode(null,null,null);
			if (tempu.getNext() == null){
				break;
				/** If tempu reach the end of u, break loop.
				 * 
				 */
			}
			/** Setelmemt for new node in list v,
			 *  add them to end of list v.
			 */
			tempv.setElement(tempu.getElement());
			v.addLast(tempv);
			tempu = tempu.getNext();
			/** Update temporary node of list u,
			 * 
			 */
		}
		return v;
	}
	public static MyDlist union(MyDlist u, MyDlist v){
		/** Initialize a empty double linked list for store all elements of list u and v.
		 *  The time Time Complexity is O(n).
		 *  The idea of this part is same as clonelist function.
		 */
		MyDlist uniondlist = new MyDlist();
		DNode tempu = u.getFirst();
		DNode tempv = v.getFirst();
		while(true){
			if(tempu.getNext() == null){
				break;
			}
			DNode unionnode = new DNode(tempu.getElement(),null,null);
			uniondlist.addLast(unionnode);
			tempu = tempu.getNext();
		}
		while(true){
			if(tempv.getNext() == null){
				break;
			}
			DNode unionnode = new DNode(tempv.getElement(),null,null);
			uniondlist.addLast(unionnode);
			tempv = tempv.getNext();
		}
		/** When every nodes in both list u and v are stored into new list uniondlist,
		 *  we starting to get rid of duplicate nodes by using two while loop.
		 *  The out while loop is used for traversal each nodes in uniondlist, 
		 *  initialized Distinct_unionnode which is contain a temporary node.  
		 *  And the inner loop is looking for other nodes 
		 *  which have same elements string value, and remove them from uniondlist.
		 *  Apparently, the time complexity is O(n*n).
		 */
		DNode Distinct_unionnode = uniondlist.getFirst();
		while(true){
			/** This outside while loop give the time complexity is O(n).
			 * 
			 */
			if(Distinct_unionnode.getNext() == null){
				break;
			}
			DNode uniontemp = uniondlist.getFirst();
			while(true){
				/** The inner while loop make the time complexity to O(n*n).
				 *  
				 */
				if(Distinct_unionnode.getElement().equals(uniontemp.getElement()) && uniontemp !=Distinct_unionnode ){
					uniondlist.remove(uniontemp);
				}
				if(uniontemp.getNext() == null){
					break;
				}
				uniontemp = uniontemp.getNext();
			}
			Distinct_unionnode = Distinct_unionnode.getNext();
		}
		return uniondlist;
	}
	/** The basic idea of intersection is find the node which contained by both list u and v.
	 *  The variable countu is for recording number of a single node's appearance in list u.
	 * 	The variable countv is for recording number of a single node's appearance in list v.
	 *  This parts time complexity is O(n*n).
	 *  If a same node appear more than twice in list u and v, we add it to intersectiondlist.
	 */
	public static MyDlist intersection(MyDlist u, MyDlist v){
		MyDlist intersectiondlist = new MyDlist();
		DNode NoDistinct_tempu = u.getFirst();
		while(true){
			int countu = 0;
			int countv = 0;
			if(NoDistinct_tempu.getNext() == null){
				break;
			}
			DNode tempu = u.getFirst();
			while(true){
				if(NoDistinct_tempu.getElement() .equals(tempu.getElement()) ){
					countu = countu + 1;
				}
				if(tempu.getNext() == null){
					break;
				}
				else{
					tempu = tempu.getNext();
				}
			}
			DNode tempv = v.getFirst();
			while(true){
				if(NoDistinct_tempu.getElement() .equals(tempv.getElement())){
					countv = countv + 1;
				}
				if(tempv.getNext() == null){
					break;
				}
				else{
					tempv = tempv.getNext();
				}
					}
			if (countu >= 1 && countv >=1 ){
			DNode unionnode = new DNode(NoDistinct_tempu.getElement(),null,null);
			intersectiondlist.addLast(unionnode);
			}
			NoDistinct_tempu = NoDistinct_tempu.getNext();
		}
		DNode Distinct_internode = intersectiondlist.getFirst();
		/** After the node which are contained in both list u and v, we need to get rid of the duplicate nodes.
		 *  This method is same as unionlist part.
		 *  The outside loop traversal whole intersectionlist by store each temporary node to Distinct_internode ,
		 *  the inner loop is looking for duplicate nodes of the temporary node (Distinct_internode), and remove them.
		 *  The time complexity is O(n*n).
		 */
		while(true){
			if(Distinct_internode.getNext() == null){
				break;
			}
			DNode intertemp = intersectiondlist.getFirst();
			while(true){
				if(Distinct_internode.getElement().equals(intertemp.getElement()) && intertemp !=Distinct_internode ){
					intersectiondlist.remove(intertemp);
				}
				if(intertemp.getNext() == null){
					break;
				}
				intertemp = intertemp.getNext();
			}
			Distinct_internode = Distinct_internode.getNext();
		}
		return intersectiondlist;
	}
	public static void main(String[] args) throws Exception{
 
   System.out.println("please type some strings, one string each line and an empty line for the end of input:");
    /** Create the first doubly linked list
    by reading all the strings from the standard input. */
    MyDlist firstList = new MyDlist("stdin");
    
   /** Print all elememts in firstList */
    printList(firstList);
   
   /** Create the second doubly linked list                         
    by reading all the strings from the file myfile that contains some strings. */
  
   /** Replace the argument by the full path name of the text file */  
    MyDlist secondList=new MyDlist("C:/Users/Hui Wu/Documents/NetBeansProjects/MyDlist/myfile.txt");

   /** Print all elememts in secondList */                     
    printList(secondList);

   /** Clone firstList */
    MyDlist thirdList = cloneList(firstList);

   /** Print all elements in thirdList. */
    printList(thirdList);

  /** Clone secondList */
    MyDlist fourthList = cloneList(secondList);

   /** Print all elements in fourthList. */
    printList(fourthList);
    
   /** Compute the union of firstList and secondList */
    MyDlist fifthList = union(firstList, secondList);

   /** Print all elements in thirdList. */ 
    printList(fifthList); 

   /** Compute the intersection of thirdList and fourthList */
    MyDlist sixthList = intersection(thirdList, fourthList);

   /** Print all elements in fourthList. */
    printList(sixthList);
  }
}