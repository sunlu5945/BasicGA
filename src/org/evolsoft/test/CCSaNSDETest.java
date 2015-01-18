package org.evolsoft.test;

import org.evolsoft.ccSaNSDE.CCFrame;
import org.evolsoft.ccSaNSDE.CCParameter;
import org.evolsoft.functions.Function;
import org.evolsoft.functions.Function_1;

/**
 * Created by wwhh on 2015/1/18.
 */
public class CCSaNSDETest {
	public static void main(String args[]){
		CCParameter ccParameter = new CCParameter("CCSaNSDEParameter");
		Function function = new Function_1();
		CCFrame test = new CCFrame(function);
		test.CCRun(ccParameter.getDimension(), ccParameter.getPopulationSize(), ccParameter.getIteration(), ccParameter.getInterval());
	}
}
