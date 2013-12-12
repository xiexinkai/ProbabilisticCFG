package xxk.pcfg;

import java.util.*;
import java.io.*;

/**
 * Train from Rules.trees, get every grammar and their possibilities
 * @author xxk
 *
 */
public class Train {

	//model
	public Map<LexicalRule,Double> lrules=new HashMap<LexicalRule,Double>();
	public Map<SyntacticRule,Double> srules=new HashMap<SyntacticRule,Double>();
	
	
	private Map<String,Double> map=new HashMap<String,Double>();
	
	public void trainFromTrees(List<Node> trees){
		for(int i=0;i<trees.size();i++){
			Node root=trees.get(i);
			iterTree(root);
		}
		//
		Iterator<LexicalRule> il=lrules.keySet().iterator();
		while(il.hasNext()){
			LexicalRule lr=il.next();
			double p=lrules.get(lr);
			lrules.put(lr, p/map.get(lr.l));
		}
		//
		Iterator<SyntacticRule> is=srules.keySet().iterator();
		while(is.hasNext()){
			SyntacticRule sr=is.next();
			double p=srules.get(sr);
			srules.put(sr, p/map.get(sr.l));
		}
	}
	private void iterTree(Node n){
		//update map
		double v=0;
		if(map.containsKey(n.sym)){
			v=map.get(n.sym);
		}
		map.put(n.sym, v+1);
		//
		if(n.right==null){//update lrules
			LexicalRule lr=new LexicalRule(n.sym,n.left.sym);
			if(lrules.containsKey(lr)){
				v=lrules.get(lr);
			}else{
				v=0;
			}
			lrules.put(lr, v+1);
		}else{//update srules
			SyntacticRule sr=new SyntacticRule(n.sym,n.left.sym,n.right.sym);
			if(srules.containsKey(sr)){
				v=srules.get(sr);
			}else{
				v=0;
			}
			srules.put(sr, v+1);
			iterTree(n.left);
			iterTree(n.right);
		}
	}
	
	public void printRules(String fileName){
		try{
			BufferedWriter fout=new BufferedWriter(new FileWriter(fileName));
			Iterator<LexicalRule> iter=lrules.keySet().iterator();
			while(iter.hasNext()){
				LexicalRule lr=iter.next();
				fout.write(lr.l+" # "+lr.r1+" # "+lrules.get(lr)+"\n");
			}
			Iterator<SyntacticRule> is=srules.keySet().iterator();
			while(is.hasNext()){
				SyntacticRule sr=is.next();
				fout.write(sr.l+" # "+sr.r1+" "+sr.r2+" # "+srules.get(sr)+"\n");
			}
			fout.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
