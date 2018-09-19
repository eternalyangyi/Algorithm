/**
 * Written by Yi YANG.
 * Student number: z5045056.
 */
package net.datastructures;
import java.util.ArrayList; 
import javax.swing.*;
import java.awt.*;

public class ExtendedAVLTree<K,V> extends AVLTree<K,V> {
	/**
	 * Q1:
	 *  In order to clone a exist AVLTree, it is necessary to declare a new AVLTree (clonednewTree).
	 *  First of all, a root is needed to insert to the clonednewTree, we simply using  the insert function of AVLTree
	 *  to add a root from original tree to new tree.
	 *  After that, I define a recursive function to traversal original tree and using the set function to make connections
	 *  between parent node and child nodes, the time complexity is O(n).
	 */
	public static <K, V> AVLTree<K, V> clone(AVLTree<K,V> tree) {
		AVLTree<K, V> clonednewTree = new AVLTree<K, V>();	
		clonednewTree.insert(tree.root.element().getKey(), tree.root.element().getValue());
		SetKidNode(clonednewTree,clonednewTree.root,tree.root);
		return clonednewTree;
	}
	public static <K, V> void SetKidNode(AVLTree<K,V> clonednewTree,BTPosition<Entry<K,V>> newT,BTPosition<Entry<K,V>> oldT){
		AVLNode<K,V> newLeft = new AVLNode<K,V>();
		AVLNode<K,V> newRight = new AVLNode<K,V>();
		newT.setLeft(newLeft);
		newLeft.setParent(newT);
		newT.setRight(newRight);
		newRight.setParent(newT);
		/**
		 * Set left node or right node of new cloned Tree by determining 
		 * that whether child nodes of parent nodes in original list have null element.
		 * If one node have a left node or right node with null element, this node must have externel nodes. 
		 */
		if (oldT.getLeft().element() != null){
			newLeft.setElement(oldT.getLeft().element());;
			SetKidNode(clonednewTree,newLeft,oldT.getLeft());
		}
		if (oldT.getRight().element() != null){
			newRight.setElement(oldT.getRight().element());
			SetKidNode(clonednewTree,newRight,oldT.getRight());
		}
		/**
		 * Because we use set function to build tree. The height of each nodes are not determined yet.
		 * In order to complete this tree, we should setHeight for each internal node.
		 */
		if(newT.element() != null){
			clonednewTree.setHeight(newT);
		}
	}
	/**
	 * Q2:
	 * In order to achieve a sorted arraylist in O(m+n) time complexity, 
	 * in the stage of saving nodes from each tree we should store them by increasing order.
	 * So in this situation, we must use inorder traversal to achieve that. 
	 * Then when nodes are inserted into new mergedTree, select node in middle position of sorted list as parent node recursively.
	 * so that the time complexity of insertation is also O(m+n).
	 */
	public static <K, V> AVLTree<K, V> merge(AVLTree<K,V> tree1, AVLTree<K,V> tree2 ){
		AVLTree<K, V> mergedTree = new AVLTree<K, V>();
		ArrayList<BSTEntry<K,V>> list1 = new ArrayList<BSTEntry<K,V>>();
		ArrayList<BSTEntry<K,V>> list2 = new ArrayList<BSTEntry<K,V>>();
		ArrayList<BSTEntry<K,V>> list = new ArrayList<BSTEntry<K,V>>();
		storenodefromtree(tree1.root,list1);
		storenodefromtree(tree2.root,list2);
		/**
		 *  After nodes of two trees are stored in separate sorted lists, 
		 *  we start to compare the first(the smallest) node from each list 
		 *  and add the smaller one into new list until either list contains none node and remove it from that list.
		 */
		while(list1.size() != 0 || list2.size() !=0){
			if(list1.size() != 0 && list2.size() ==0){
				list.add(list1.get(0));
				list1.remove(0);	
				continue;
			}
			if(list1.size() == 0 && list2.size() !=0){
				list.add(list2.get(0));
				list2.remove(0);
				continue;
			}
			if(((int) list1.get(0).getKey()) <= (int)list2.get(0).getKey()){
				list.add(list1.get(0));
				list1.remove(0);
				continue;
			}
			if(((int) list1.get(0).getKey()) > (int)list2.get(0).getKey()){
				list.add(list2.get(0));
				list2.remove(0);
				continue;
			}
		}
		mergedTree.root = insertnodetomergedTree(list,0,list.size()-1);
		/**
		 * Because we use set function to build AVLTree, so for each node we need setHeight.
		 * Without this process, the mergedTree is not complete.
		 */
		setheight(mergedTree,mergedTree.root);
		tree1 = null;
		tree2 = null;
		return mergedTree;
	}
	public static <K,V> void setheight(AVLTree<K,V> newTree,BTPosition<Entry<K,V>> node){
		if(node.getLeft().element()!=null){
			setheight(newTree,node.getLeft());
		}
		if(node.getRight().element() !=null){
			setheight(newTree,node.getRight());
		}
		if (node.element() != null){
		newTree.setHeight(node);
		}
	}
	/**
	 * Inorder traversal each tree and add nodes into a list.
	 */
	private static <K, V> void storenodefromtree(BTPosition<Entry<K,V>> treenode, ArrayList<BSTEntry<K,V>> list){
		if (treenode.getLeft().element() != null){
			storenodefromtree(treenode.getLeft(),list);
		}
		list.add((net.datastructures.BinarySearchTree.BSTEntry<K, V>) treenode.element());
		if (treenode.getRight().element() != null){
			storenodefromtree(treenode.getRight(),list);
		}
	/**
	 *  After we get sorted list which is prepare for inserting to a new AVLTree,
	 *  a recursive function to insert node to new tree.	
	 *  In order to achieving O(m+n) time complexity is the node in middle position of list
	 *  should be the root of whole AVLTree.Then chunk list into two parts.
	 *  The nodes before the middle one are inserted in its left tree,the nodes
	 *  after the middle one are inserted in its right tree.
	 *  Then, we continue to choose the nodes in middle position in both two parts of original list as parent node,
	 *  chunk rest nodes into four parts.And this process is recursive.
	 */
	}
	
	private static <K,V> AVLNode<K,V> insertnodetomergedTree(ArrayList<BSTEntry<K,V>> list,int starting,int ending){
		int mid = (starting + ending)/2;
		AVLNode<K,V> node = new AVLNode<K,V>(list.get(mid),null,null,null);
		AVLNode<K,V> left = new AVLNode<K,V>(null,null,null,null);
		AVLNode<K,V> right = new AVLNode<K,V>(null,null,null,null);

		/**
		 * When starting == ending, it means this node will be the internal node with two external child nodes.
		 * So we simply set two child with null element.
		 */
		if(starting == ending) {
			left.setParent(node);
			right.setParent(node);
			node.setLeft(left);
			node.setRight(right);
			return node;
		}
		/**
		 * Another situation is if mid == starting, it means we have already at left-most branch in a subtree,
		 * otherwise, we need find left-most branch recursively.
		 */
		if(mid != starting) {
			left = insertnodetomergedTree(list, starting, mid-1);
			left.setParent(node);
			node.setLeft(left);
		} else {
			/** There is a situation that a internal node have a external left node and a internal right node
			 * this else is designed to deel with this situation.
			 * 
			 */
			left.setParent(node);
			node.setLeft(left);
		}
		right = insertnodetomergedTree(list, mid+1, ending);
		right.setParent(node);
		node.setRight(right);
		return node;
	}
	/**
	 * Q3:
	 * Initialize a JFrame to create a new window to display AVLTree.
	 * Create four class(circles,rects,lines and keys) extends JComponent to display circles, rectangles,lines and keys in new window.
	 * For each component class, we set some parameters in order to specifically determine each position.
	 */
	public static class circles extends JComponent{
		int x = 0;
		int y = 0;
		int depth = 0;
		circles(int x, int y,int depth){
			this.x=x;
			this.y=y;
			this.depth = depth;
		}
		public void paintComponent (Graphics g){
			g.setColor(Color.black);
			g.drawOval(x, y, depth,depth);
			
		}
	}
	public static class rects extends JComponent{
		int x = 0;
		int y = 0;
		int width = 0;
		int height = 0;
		rects(int x,int y,int width,int height){
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
		public void paintComponent(Graphics g){
			g.setColor(Color.black);
			g.drawRect(x, y, height,width);
			
		}
	}
	public static class lines extends JComponent{
		int xa = 0;
		int ya = 0;
		int xb = 0;
		int yb = 0;
		lines(int xa,int ya,int xb,int yb){
			this.xa = xa;
			this.xb = xb;
			this.ya =ya;
			this.yb = yb;
		}
		public void paintComponent(Graphics g){
			g.setColor(Color.black);
			g.drawLine(xa, ya, xb, yb);
			
		}
	}
	public static class keys extends JComponent{
		String key;
		int x = 0;
		int y = 0;
		keys(String key,int x,int y){
			this.key = key;
			this.x = x;
			this.y = y;	
		}
		public void paintComponent(Graphics g){
			g.setColor(Color.orange);
			g.drawString(key, x, y);
	}
		/** As a practice of java processing and queue's using, 
		 * I using three Irregular two-dimensional arrays store nodes to print tree
		 * rather than using traversal nodes.
		 */
	}
	public static <K, V> void print(AVLTree<K, V> tree){
		// Use ArrayList queue as a queue.
		ArrayList<BTPosition<Entry<K,V>>> queue = new ArrayList<BTPosition<Entry<K,V>>>();
		// listofnode is defined to store every node in a tree.
		ArrayList<BTPosition<Entry<K,V>>> listofnode = new ArrayList<BTPosition<Entry<K,V>>>();
		BTPosition<Entry<K,V>> tempnode = new AVLNode<K,V>();
		BTPosition<Entry<K,V>> nullnode = new AVLNode<K,V>();
		//nullnode including eternal node.
		nullnode.setElement(null);
		//Using height of tree to determine how many lines in Irregular two-dimensional array.
		//Because of external nodes,we need to use height + 1. 
		int height = tree.height(tree.root());
		//nodelist stores nodes.
		String [][] nodelist = new String[height+1][];
		//nodex and nodey store coordinates in order to drawlines.
		int [][] nodex =  new int[height+1][];
		int [][] nodey =  new int[height+1][];
		//determine how many possible node in each level.
		for(int i=0;i<height+1;i++){
			nodelist[i] = new String[(int)Math.pow(2, i)];
			nodex[i] = new int [(int)Math.pow(2, i)];
			nodey[i] = new int [(int)Math.pow(2, i)];
		}
		queue.add(tree.root);
		listofnode.add(tree.root);
		/**
		 * Using a queue to get child nodes of each parent node.
		 * If child node have null element, add a nullnode. 
		 */
		while(queue.size()!=0 ){
			tempnode = queue.remove(0);
			if (tempnode.getLeft().element() != null){
				queue.add(tempnode.getLeft());
				listofnode.add(tempnode.getLeft());
			}
			if (tempnode.getLeft().element() == null){
				listofnode.add(nullnode);
				
			}
			if(tempnode.getRight().element() != null){
				queue.add(tempnode.getRight());
				listofnode.add(tempnode.getRight());
			}
			if (tempnode.getRight().element() == null){
				listofnode.add(nullnode);
			}
			
		}
		/**
		 * This for loop is used for add each nodes into right position in a Irregular two-dimensional array,
		 * based on their level and position.
		 * Again, (height + 1) for extenal nodes.
		 * Simple print of nodelist for tree1 as follows:
		 *                                  22
		 *	                        8                30
		 *	                    5      12      null     40
		 *	                  3  5   10  20 null null null null
		 *	     nullnullnullnullnullnullnullnullnullnullnullnullnullnullnullnull
		 */
		for(int i=0;i<height+1;i++){
			for(int j=0;j<nodelist[i].length;j++){
				if (listofnode.size() !=0){
				tempnode = listofnode.remove(0);
					if (tempnode != nullnode){
						nodelist[i][j] = tempnode.element().getKey().toString();
					}
					else{
						nodelist[i][j] = null;
					}
				}
				else{
					nodelist[i][j] = null;
				}
			}
		}
		printtree(nodelist,nodex,nodey);
	    
	}
	/** Use JFrame to create a new window to display components.
	 *  Size of this window will be 1500 * 800.
	 */
			
	private static void printtree(String[][] nodelist,int[][] nodex,int[][] nodey) {
		JFrame frame = new JFrame( "AVL Tree" );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setSize( 1500,800 );
		frame.setBackground( Color.WHITE );
		int level = nodelist.length; //Determine how many possible levels of tree.
		int maxnum = (int) Math.pow(2,level-1); //Determine how many possible nodes in bottom level.
		// Using maxnum to set adjustable size of circles and rectangles.
		int width = (int) ((1300/maxnum) *0.5);
		int height = (int) ((1300/maxnum) *0.5);
		int depth = (int) ((1300/maxnum) *0.5);
		//Initialize coordinate.
		int x = 0;
		int y = 0;
		
		/** For loop to add components in JFrame.
		 *  y is based on which level these node in and size of component.
		 *  x is based on how many node in this level. 
		 * 
		 */
		for (int i=0; i<nodelist.length;i++){
			y = depth * i + 100;
			x = (1300/(int)Math.pow(2, i+1)) - depth/2;
			for(int j=0; j<nodelist[i].length;j++){
				if(nodelist[i][j] == null){
					//i-1 and j/2 will determine this node's parent node.
					if(i>0 && nodelist[i-1][j/2] == null){
						// if a node have null element and it is not a external node, it should not  be displayed.
						frame.getContentPane().add(new rects(x,y,width,height));
					}
					else{
						//if a node have null element and it is a external node, it should be displayed.
						frame.getContentPane().add(new rects(x,y,width,height));
						frame.setVisible(true);
					}
					
				}
				else{
					// if a node have element, display it with its key.
					frame.getContentPane().add(new circles(x,y,depth));
					frame.setVisible(true);
					frame.getContentPane().add(new keys(nodelist[i][j],x+(width/4),y+(width/2)));
					frame.setVisible(true);
				}
				//store coordinate of each node.
				nodex[i][j] = x + depth/2;
				nodey[i][j] = y + depth/2;
				//(1300/(int) Math.pow(2, i+1)) is a gap between nodes in same level.
				x = x + 2 * (1300/(int) Math.pow(2, i+1)) ;
			}
		}
		
		for(int i=0;i<nodelist.length;i++){
			for(int j=0; j<nodelist[i].length;j++){
				if (i>0 && nodelist[i-1][j/2] != null){
					//if a node is not a child node of  a external node, we draw a line between it and its parent.
					frame.getContentPane().add(new lines(nodex[i-1][j/2],nodey[i-1][j/2],nodex[i][j],nodey[i][j]));
					frame.setVisible(true);
				}
			}
		}
	}
	public static void main(String[] args)
    { 
      String values1[]={"Sydney", "Beijing","Shanghai", "New York", "Tokyo", "Berlin",
     "Athens", "Paris", "London", "Cairo"}; 
      int keys1[]={20, 8, 5, 30, 22, 40, 12, 10, 3, 5};
      String values2[]={"Fox", "Lion", "Dog", "Sheep", "Rabbit", "Fish"}; 
      int keys2[]={40, 7, 5, 32, 20, 30};
         
      /* Create the first AVL tree with an external node as the root and the
     default comparator */ 
         
        AVLTree<Integer, String> tree1=new AVLTree<Integer, String>();

      // Insert 10 nodes into the first tree
         
        for ( int i=0; i<10; i++)
            tree1.insert(keys1[i], values1[i]);
       
      /* Create the second AVL tree with an external node as the root and the
     default comparator */
         
        AVLTree<Integer, String> tree2=new AVLTree<Integer, String>();
       
      // Insert 6 nodes into the tree
         
        for ( int i=0; i<6; i++)
            tree2.insert(keys2[i], values2[i]);
         
        ExtendedAVLTree.print(tree1);
        ExtendedAVLTree.print(tree2); 
        ExtendedAVLTree.print(ExtendedAVLTree.clone(tree1));
        ExtendedAVLTree.print(ExtendedAVLTree.clone(tree2));
        
        ExtendedAVLTree.print(ExtendedAVLTree.merge(ExtendedAVLTree.clone(tree1), 
        ExtendedAVLTree.clone(tree2)));
      }
}