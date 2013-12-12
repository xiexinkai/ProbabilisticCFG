package xxk.pcfg;

import java.util.*;
import java.io.*;

/**
 * This class will parse when created.
 * @author xxk
 *
 */
public class Parse {
	public Parse(String sentence,Train train){
		words=sentence.split(" ");
		lrules=train.lrules;
		srules=train.srules;
		int m=words.length;
		chart=new Chart[m][m];
		CYK();
		calAlpha();
	}
	
	public void printParse(String fileName){
		try{
			BufferedWriter fout=new BufferedWriter(new FileWriter(fileName));
			Chart c=chart[0][words.length-1];
			printTree(c,fout);
			fout.write("\n");
			fout.write(c.p+"\n");
			printNode(c,fout);
			fout.close();
		}catch(IOException e){
			
		}
	}
	private void printTree(Chart c,BufferedWriter fout) throws IOException{
		fout.write("("+c.s);
		if(c.lC!=null){
			printTree(c.lC,fout);
			printTree(c.rC,fout);
		}else{
			fout.write(" "+c.word);
		}
		fout.write(")");
	}
	private void printNode(Chart c,BufferedWriter fout) throws IOException{
		fout.write(c.s+" # ");
		fout.write(c.l+" # ");
		fout.write(c.r+" # ");
		fout.write(c.beta+" # ");
		fout.write(c.alpha+"\n");
		if(c.lC!=null){
			printNode(c.lC,fout);
			printNode(c.rC,fout);
		}
	}
	
	private String[] words;
	private Map<LexicalRule,Double> lrules;
	private Map<SyntacticRule,Double> srules;
	private Chart chart[][];
	private void CYK(){
		int m=words.length;
		for(int j=0;j<m;j++){
			chart[j][j]=findLR(words[j],j,j);
			chart[j][j].updateBeta(0, j, j);
			//chart[j][j].printDebug();
			//calculate:Alpha and Beta
			for(int i=j-1;i>=0;i--){
				//System.out.println("i="+i+"   j="+j);
				chart[i][j]=new Chart(i,j);
				for(int k=i;k<j;k++){
					Iterator<SyntacticRule> iter=srules.keySet().iterator();
					while(iter.hasNext()){
						SyntacticRule sr=iter.next();
						if(sr.r1.equals(chart[i][k].s) && sr.r2.equals(chart[k+1][j].s)){
							double psr=srules.get(sr);
							if(psr*chart[i][k].p*chart[k+1][j].p>chart[i][j].p){//find a better parse,update
								chart[i][j].s=sr.l;
								chart[i][j].p=psr*chart[i][k].p*chart[k+1][j].p;
								chart[i][j].lC=chart[i][k];
								chart[i][j].rC=chart[k+1][j];
							}
							chart[i][j].updateBeta(psr, i, j);
							//chart[i][j].printDebug();
						}
					}
									
					//for(every A->BC){
					//	if(B in chart[i][k] && C in chart[k+1][j]){
					//		chart[i][j].add(A);
					//		calculate:Alpha and Beta
					//	}//end if
					//}//end for
				}//end for
			}//end for
		}//end for
	}//end void
	
	private Chart findLR(String word,int x,int y){
		Iterator<LexicalRule> iter=lrules.keySet().iterator();
		//System.out.println(lrules.keySet().size());
		while(iter.hasNext()){
			LexicalRule lr=iter.next();
			//System.out.println(lr.r1);
			if(lr.r1.toLowerCase().equals(word.toLowerCase())){
				return new Chart(lr.l,x,y,lrules.get(lr),word);
			}
		}
		System.err.println("cannot find the word in LexicalRules!");
		return null;
	}
	
	private void calAlpha(){
		chart[0][words.length-1].alpha=1.0;
		for(int d=words.length-1;d>=0;d--){
			for(int i=0;i+d<words.length;i++){
				//for every p=i,q=i+d
				Chart c=chart[i][i+d];
				for(int j=i+d+1;j<words.length;j++){//left 
					Chart cc=chart[i][j];
					if(cc.lC==c){
						double p=srules.get(new SyntacticRule(cc.s,cc.lC.s,cc.rC.s));
						c.alpha+=p*cc.alpha*cc.rC.beta;
					}
				}
				for(int j=0;j<=i-1;j++){
					Chart cc=chart[j][i+d];
					if(cc.rC==c){
						double p=srules.get(new SyntacticRule(cc.s,cc.lC.s,cc.rC.s));
						c.alpha+=p*cc.alpha*cc.lC.beta;
					}
				}
				c.printDebug();
			}
		}
	}

}


