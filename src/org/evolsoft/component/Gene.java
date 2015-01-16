package org.evolsoft.component;

public abstract class Gene {
	Object value;
	public int getInt() {
		return (int)this.value;
	}
	public double getDouble() {
		return (double)this.value;
	}
	public String getString() {
		return (String)this.value;
	}
	public void set(Object value) {
		this.value = value;
	}
}
