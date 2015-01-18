package org.evolsoft.functions;

import org.ejml.ops.RandomMatrices;
import org.evolsoft.component.Code;
import org.evolsoft.component.Encode;

import java.util.Random;

/**
 * Created by wwhh on 2015/1/18.
 */
public class Function_8 extends Function{
	private int groupSize;
	public Function_8(){
		super(-100, 100, 1000);
		groupSize = 50;
		M = RandomMatrices.createOrthogonal(groupSize, groupSize, new Random());
	}
	public double run(Code code){
		Code P = Encode.createIntPriorityCode(code.getSize());
		Code z = getShiftedSolution(code);
		Code z1 = new Code();
		for(int i=0; i<groupSize; i++){
			z1.addGene(z.getDoubleGene(P.getIntGene(i)));
			z.delGene(P.getIntGene(i));
		}
		return rosenbrockFunction(z1) * 1e6 + sphereFunction(z);
	}
}
