package org.evolsoft.functions;

import org.evolsoft.component.Code;

/**
 * Created by wwhh on 2015/1/16.
 */
public abstract class Function {
	private double lbound;
	private double ubound;
	private int dimension;
	private Code globalOptimum;
	public abstract double run(Code code);

	public void setGlobalOptimum(Code code){
		globalOptimum = code;
	}
	public void setDimension(int tempDimension){
		dimension = tempDimension;
	}
	public int getDimension(){
		return dimension;
	}
	public double getLbound(){
		return lbound;
	}
	public double getUbound(){
		return ubound;
	}
	public Code getShiftedSolution(Code code){
		Code res = new Code();
		for(int i=0; i<dimension; i++){
			res.addGene(code.getDoubleGene(i) - globalOptimum.getDoubleGene(i));
		}
		return res;
	}

	/* Basic Functions */
	public double sphereFunction(Code code){
		double res = 0.0;
		double temp;
		for(int i=0; i<code.getSize(); i++){
			temp = code.getDoubleGene(i);
			res += temp * temp;
		}
		return res;
	}
	public double ellipticFunction(Code code){
		double res = 0.0;
		double temp;
		for(int i=0; i<code.getSize(); i++){
			temp = code.getDoubleGene(i);
			res += Math.pow( 1e6, i / ((double)(code.getSize()-1)) ) * temp * temp;
		}
		return res;
	}
}
