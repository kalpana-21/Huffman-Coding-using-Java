import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

//Node class for Tree Nodes
class Node{
    char letter;
    int freq;
    Node left;
    Node right;
}

public class Huffman {

    String s;
    Map <Character, Integer> freqMap;
    static Map<Character,String> encoded;
    PriorityQueue<Node> pq;

//Constructor for frequency map and priorityqueue
   public  Huffman(String s){
       this.s = s;
       freqMap = new HashMap<>();
       encoded = new HashMap<>();
       pq = new PriorityQueue<>((a,b)->(a.freq != b.freq)? a.freq-b.freq : a.letter-b.letter );
       for(int i=0;i<s.length();i++){
           char p = s.charAt(i);
           if(!freqMap.containsKey(p))
               freqMap.put(p,1);
           else{
               freqMap.put(p,freqMap.get(p)+1);
           }
       }
       for(Map.Entry<Character,Integer> entry: freqMap.entrySet() ){
           Node n = new Node();
           n.letter= entry.getKey();
           n.freq= entry.getValue();
           n.left=null;
           n.right=null;
           pq.add(n);
       }


   }

//   Builds tree by taking least frequent leaf nodes and merges these nodes
   public Node buildTree(){
       Node n=new Node();
        while(pq.size() > 1){
            Node l1 = pq.peek();
            pq.poll();
            Node l2 = pq.peek();
            pq.poll();
            n = new Node();
            n.letter = l1.letter;
            n.freq = l1.freq + l2.freq;
            n.left=l1;
            n.right=l2;
            pq.add(n);
        }
        return pq.poll();
   }


//   prints and stores the prefixcode for each character in the given string
   public void printNode(Node root, String s){
       if(root.left == null && root.right == null) {
           System.out.println(root.letter + ":" + s);
           encoded.put(root.letter,s);
           return;
       }
       printNode(root.left,s+"0");
       printNode(root.right,s+"1");
   }

   public static void main(String[] args){

       Scanner sc = new Scanner(System.in);
       String s = sc.next();
       Huffman h = new Huffman(s);
       Node root = h.buildTree();
       h.printNode(root,"");
       String ans="";
       for(int i=0;i<s.length();++i){
           ans += encoded.get(s.charAt(i));
       }

       System.out.println(ans);
   }


}
