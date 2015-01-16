package org.evolsoft.component;

public class DoubleGene extends Gene{
	double value;
	public DoubleGene() {
		value = 0;
	}
	public void set(double value) {
		this.value = value;
	}
	public double getDouble() {
		return this.value;
	}
	public Gene clone(){
		DoubleGene res = new DoubleGene();
		res.set(value);
		return res;
	}
	public String getString(){
		return ""+value;
	}
}
