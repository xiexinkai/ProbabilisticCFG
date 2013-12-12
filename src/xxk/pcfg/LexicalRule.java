package xxk.pcfg;

/**
 * l->r1, where l is a non-terminal , r1 is a terminal
 * @author xxk
 *
 */
public class LexicalRule {
	public String l;
	public String r1;
	//public double p;//possibility
	
	public LexicalRule(String l,String r1){
		this.l=l;
		this.r1=r1;
		//p=0;
	}
	
	@Override
	public int hashCode(){
		return l.hashCode()^r1.hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		LexicalRule lr=(LexicalRule)obj;
		if(lr.l.equals(l) && lr.r1.equals(r1)){
			return true;
		}
		return false;
	}
	
}
