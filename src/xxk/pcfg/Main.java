package xxk.pcfg;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main().go();
	}

	public void go(){
		//read input
		//input to trees
		Rules rules=new Rules();
		rules.Input2Tree("(S(NP(DT The)(NN boy))(VP(VP(VBD saw)(NP(DT a)(NN girl)))(PP(IN with)(NP(DT a)(NN telescope)))))");
		rules.Input2Tree("(S(NP(DT The)(NN girl))(VP(VBD saw)(NP(NP(DT a)(NN boy))(PP(IN with)(NP(DT a)(NN telescope))))))");
		//System.out.println(rules.trees.get(0).toString());
		//trees for train
		Train train=new Train();
		train.trainFromTrees(rules.trees);
		train.printRules("model.txt");
		//
		Parse parse=new Parse("A boy with a telescope saw a girl",train);
	}
}
