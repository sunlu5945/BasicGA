package org.evolsoft.component;

public class IntGene extends Gene{
	int value;
	public IntGene() {
		value = 0;
	}
	public void set(int value) {
		this.value = value;
	}
	public int getInt() {
		return this.value;
	}

	@Override
	public Gene clone() {
		IntGene res = new IntGene();
		res.set(value);
		return res;
	}
	public String getString(){
		return ""+value;
	}
}
