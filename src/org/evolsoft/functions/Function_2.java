package org.evolsoft.functions;

import org.evolsoft.component.Code;

/**
 * Created by wwhh on 2015/1/18.
 */
public class Function_2 extends Function{
	public Function_2(){
		super(-5, 5, 1000);
	}
	public double run(Code code){
		Code shiftedSolution = getShiftedSolution(code);
		return rastriginFunction(shiftedSolution);
	}

}
