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
}
