package xxk.pcfg;

/**
 * l->r1 r2, where l is a non-terminal , r1,r2 are terminal
 * @author xxk
 *
 */
public class SyntacticRule {
	public String l;
	public String r1,r2;
	//public double p;//possibility
	
	public SyntacticRule(String l,String r1,String r2){
		this.l=l;
		this.r1=r1;
		this.r2=r2;
		//p=0;
	}
	
	@Override
	public int hashCode(){
		return l.hashCode()^r1.hashCode()^r2.hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		SyntacticRule sr=(SyntacticRule)obj;
		if(sr.l.equals(l)&&sr.r1.equals(r1)&&sr.r2.equals(r2)){
			return true;
		}
		return false;
	}
}
