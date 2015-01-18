package org.evolsoft.functions;

import org.evolsoft.component.Code;

/**
 * Created by wwhh on 2015/1/18.
 */
public class Function_3 extends Function{
	public Function_3(){
		super(-32, 32, 1000);
	}
	public double run(Code code){
		Code shiftedSolution = getShiftedSolution(code);
		return ackleyFunction(shiftedSolution);
	}
}
