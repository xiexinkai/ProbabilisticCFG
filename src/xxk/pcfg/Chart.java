package xxk.pcfg;


/**
 * This class is for CYK algorithm. 
 * It will record the max possibility
 * @author xxk
 *
 */
//S->A B
public class Chart{
	public String s;
	public int l,r;
	public Chart lC,rC;
	public double p;
	public String word;
	public double alpha,beta;
	public Chart(String s,int l,int r,double p,String word){
		this.s=s;
		this.l=l;
		this.r=r;
		this.p=p;
		this.word=word;
	}
	
	public Chart(int l,int r){
		this.l=l;
		this.r=r;
	}
	
	public void updateBeta(double p,int x,int y){
		if(x==y){
			beta=this.p;
		}else{
			beta+=p*lC.beta*rC.beta;
		}
	}
}

