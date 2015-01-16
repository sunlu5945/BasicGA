package org.evolsoft.functions;

import org.evolsoft.component.Code;

/**
 * Created by wwhh on 2015/1/16.
 */
public class Function_1 extends Function {
	public Function_1(){
		lbound = -100;
		ubound = 100;
		dimension = 1000;
		globalOptimum = new Code();
		for(int i=0; i<dimension; i++){
			globalOptimum.addGene(0.0);
		}
	}
	public double run(Code code){
		Code shiftedSolution = getShiftedSolution(code);
		return ellipticFunction(shiftedSolution);
	}
}
