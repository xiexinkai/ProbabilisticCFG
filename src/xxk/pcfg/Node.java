package xxk.pcfg;

/**
 * This is data structure 
 * @author xxk
 *
 */
public class Node {
	public String sym;//symbol
	Node left,right;
	
	@Override
	public String toString(){
		String str="("+sym;
		if(left!=null){
			str+=left.toString();
		}
		if(right!=null){
			str+=right.toString();
		}
		str+=")";
		return str;
	}
}
