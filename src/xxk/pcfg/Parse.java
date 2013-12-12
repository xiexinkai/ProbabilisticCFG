package xxk.pcfg;

import java.util.*;

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
	}
	
	public void printParse(){
		
	}
	
	private String[] words;
	private Map<LexicalRule,Double> lrules;
	private Map<SyntacticRule,Double> srules;
	private Chart chart[][];
	private void CYK(){//TODO
		int m=words.length;
		for(int j=0;j<m;j++){
			chart[j][j]=findLR(words[j],j,j);
			chart[j][j].updateBeta(0, j, j);
			//calculate:Alpha and Beta
			for(int i=j-1;i>=0;i--){
				System.out.println("i="+i+"   j="+j);
				chart[i][j]=new Chart(i,j);
				for(int k=i;k<j;k++){
					Iterator<SyntacticRule> iter=srules.keySet().iterator();
					while(iter.hasNext()){
						SyntacticRule sr=iter.next();
						if(sr.r1.equals(chart[i][k].s) && sr.r2.equals(chart[k+1][j].s)){
							double psr=srules.get(sr);
							if(psr*chart[i][k].p*chart[k+1][j].p>chart[i][j].p){//find a better parse,update
								chart[i][j].p=psr*chart[i][k].p*chart[k+1][j].p;
								chart[i][j].lC=chart[i][k];
								chart[i][j].rC=chart[k+1][j];
							}
							chart[i][j].updateBeta(psr, i, j);
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
}


