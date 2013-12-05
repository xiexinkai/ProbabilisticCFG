package xxk.pcfg;

import java.util.ArrayList;

/**
 * The Rules are binary trees. Each input line, can form a grammar tree.
 * Input line example: (S(NP(DT The)(NN boy))(VP(VP(VBD saw)(NP(DT a)(NN girl)))(PP(IN with)(NP(DT a)(NN telescope)))))
 * @author xxk
 *
 */
public class Rules {
	public ArrayList<Node> trees=new ArrayList<Node>();
	
	//Input will be transform into a tree
	public void Input2Tree(String line){
		bias=0;
		trees.add(makeTree(line));
	}	
	//for String line 
	private int bias;
	//call when ( and return when )
	private Node makeTree(String line){
		Node n=new Node();
		n.sym="";
		bias++;//at this time the char should be '('
		char c;
		while((c=line.charAt(bias))!=')'){
			switch(c){
			case '('://recursively call this function
				if(n.left==null){
					n.left=makeTree(line);
				}else{
					n.right=makeTree(line);
				}
				break;
			case ' '://reach the bottom, read the lexical rule
				bias++;
				Node m=new Node();
				m.sym="";
				while(line.charAt(bias)!=')'){
					m.sym+=line.charAt(bias);
					bias++;
				}
				n.left=m;
				break;
			default:
				n.sym+=c;
				bias++;	
			}
		}
		bias++;//at this time the char should be ')'
		return n;
	}
	
	//for debug
	public static void main(String[] args){
		Rules r=new Rules();
		r.Input2Tree("(S(NP(DT The)(NN boy))(VP(VP(VBD saw)(NP(DT a)(NN girl)))(PP(IN with)(NP(DT a)(NN telescope)))))");
		System.out.println(r.trees.get(0).toString());
	}
}


