//Written by Yi YANG 5045056
import java.io.*;
import java.util.*;
/*  First of all, this assignment is the most hardest one for me. Due to the limit time, I just implement first two requirements.
 * 	For the constructer, I choose HashMap as my data structure. Then, I create Node class to store indexs to locate them in original
 * 	String.
 *  For findstring function, the time complexity is O(|S|). There are only one for loop to computeing location of target string,
 *  so that it is a linear function.
 */
public class CompactCompressedSuffixTrie {
	private String DNA_sequences = "" ;
	private static char end_symbol = '!';
	private Node Start_Node=new Node(-1,-1);
	private int End_position = -1;
	private Node Child_Node ;
	private Node Processing_Node = Start_Node;
	private int Processing_index = -1;
	private int Length = 0;
	private int Length_Of_Left = 0;
	public CompactCompressedSuffixTrie( String f ) throws FileDoesnotExist, IOException{
		ArrayList<String> file_content = readFile(f);
		DNA_sequences = convert(file_content);
		for (int index=0;index<DNA_sequences.length();index++){
			process_data_in_constructor(index);
		}
	}
	public static String convert(ArrayList<String> file_content){
		String sequences = "";
		for(int i=0; i < file_content.size();i++){
			if (file_content.get(i) != ","){
				sequences += file_content.get(i);
			}
		}
		sequences =sequences + end_symbol;
		return sequences;
		
	}
	/* This function is designed to processing string into a constructor which is Hashmap.
	 * It is a linear method achieved by only processing each character once.
	 * 
	 */
	public void process_data_in_constructor(int index){
		End_position=index;
    	Length_Of_Left+=1;
    	Child_Node=null;
    	while(Length_Of_Left > 0){
    		if (Length==0){
    			Processing_index=index;
    		}
    		//If current key exist, put current node into next node.
    		if(Processing_Node.map.containsKey(DNA_sequences.charAt(Processing_index))){		
    			Node next = Processing_Node.map.get(DNA_sequences.charAt(Processing_index));
    			if (check(next)){
    				continue;
    			}
    			//Set up child node for HashMap.
    			if(DNA_sequences.charAt(next.start+Length)==DNA_sequences.charAt(index)){
    				if (Child_Node !=null && Processing_Node !=Start_Node){
    					Child_Node.sub_suffix=Processing_Node;
    					Child_Node=null;
    				}
    				Length+=1;
    				break;
    			}
    			int Operating_Point=next.start+Length-1;
    			Node OP=new Node(next.start,Operating_Point);
    			Processing_Node.map.put(DNA_sequences.charAt(Processing_index), OP);
    			OP.map.put(DNA_sequences.charAt(index), new Node(index,-1));
    			next.start+= Length;
    			OP.map.put(DNA_sequences.charAt(next.start), next);
    			if(Child_Node!=null){
    				Child_Node.sub_suffix=OP;
    			}
    			Child_Node=OP;
    		}
    		else{
    			//Otherwise, put current node into Hashmap.
    			Processing_Node.map.put(DNA_sequences.charAt(Processing_index), new Node(index,-1));
    			if (Child_Node!=null){
    				Child_Node.sub_suffix=Processing_Node;
    				Child_Node=null;
    			}
    		}
    		Length_Of_Left-=1;
    		if (Processing_Node==Start_Node && Length>0){
    			Length-=1;
    			Processing_index=index-Length_Of_Left+1;
    		}
    		else{
    			if(Processing_Node!=Start_Node){
    			Processing_Node=Processing_Node.sub_suffix;
    		}
    	}
    	}	
	}
    private boolean check(Node n){
    	int edge_length = length_of_node_edge(n);
    	if (Length >=edge_length){
    		Processing_Node=n;
    		Processing_index += edge_length;
    		Length -=edge_length;
    		return true;
    	}
    	return false;
    }
	private int length_of_node_edge(Node n) {
		if (n.end != -1){
			return n.end - n.start + 1;
		}
		return End_position - n.start + 1;
	}
	private static ArrayList<String> readFile(String FilePath) throws FileDoesnotExist, IOException {
        ArrayList<String> fileContent = new ArrayList<String>();
        File File1 = new File(FilePath);
        FileInputStream fis = new FileInputStream(File1);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line = null;
        while ((line = br.readLine()) != null) {
            fileContent.add(line);
        }
        br.close();
        return fileContent;
    }

   public int findString(String s){
		char[] target=s.toCharArray();
		char cur_character;
		int end_index=0;
		int start_node=0;
		int end_node=-1;
		Node current_node=Start_Node;
		for(int i =0;i<target.length;i++){
			cur_character=target[i];
			if(start_node>end_node){
				current_node=current_node.map.get(cur_character);
				if(current_node==null){
					return -1;
				}
				else{
					start_node=current_node.start;
					if(current_node.end==-1){
						end_node=DNA_sequences.length()-1;
					}
					else{
						end_node=current_node.end;
					}
				}
			}
			if(cur_character==DNA_sequences.charAt(start_node)){
				end_index=start_node;
				start_node+=1;				
			}
			else{
				return -1;
			}
		}
		return end_index-target.length+1;
	}

	class Node{
		Map<Character,Node>  map;
		int start;
		int end;
		int suffix_index;
		Node sub_suffix;
		public Node(int start,int end){
			map = new HashMap<>();
			this.start = start;
			this.end = end;
			this.sub_suffix = Start_Node;
			this.suffix_index = -1;
			
	}
		
	}
	class FileDoesnotExist extends Exception{
		public FileDoesnotExist() {
			System.out.println("File does not exist");
		}
	}
	public static void main(String args[]) throws Exception{
        
		 /** Construct a compact compressed suffix trie named trie1
		  */       
		 CompactCompressedSuffixTrie trie1 = new CompactCompressedSuffixTrie("file1.txt");
		         
		 System.out.println("ACTTCGTAAG is at: " + trie1.findString("ACTTCGTAAG"));

		 System.out.println("AAAACAACTTCG is at: " + trie1.findString("AAAACAACTTCG"));
		         
		 System.out.println("ACTTCGTAAGGTT : " + trie1.findString("ACTTCGTAAGGTT"));
		         
		 //CompactCompressedSuffixTrie.kLongestSubstrings("file2.txt", "file3.txt", "file4.txt", 6);
		  }
}