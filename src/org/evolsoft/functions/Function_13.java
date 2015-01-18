package org.evolsoft.functions;

import org.evolsoft.component.Code;
import org.evolsoft.component.Encode;

/**
 * Created by wwhh on 2015/1/18.
 */
public class Function_13 extends Function {
	private int groupSize;
	public Function_13(){
		super(-100, 100, 1000);
		groupSize = 50;
	}
	public double run(Code code){
		Code P = Encode.createIntPriorityCode(code.getSize());
		Code z = getShiftedSolution(code);
		double res = 0.0;
		for(int i=0; i<(dimension/(2 * groupSize)); i++) {
			Code z1 = new Code();
			for (int j = i * groupSize; j < (i + 1) * groupSize; j++) {
				z1.addGene(z.getDoubleGene(P.getIntGene(j)));
			}
			res += rosenbrockFunction(z1);
		}
		Code z2 = new Code();
		for(int i=dimension/2; i<dimension; i++){
			z2.addGene(z.getDoubleGene(P.getIntGene(i)));
		}
		return res + sphereFunction(z2);
	}
}
