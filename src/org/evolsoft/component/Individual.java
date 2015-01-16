package org.evolsoft.component;

import java.util.ArrayList;
import java.util.List;

public class Individual implements Cloneable{
	private double fitness;
	private List<Code> codeSection;
	private Object solution;
	
	public Individual() {
		fitness = 0.0;
		codeSection = new ArrayList<Code>();
		solution = new Object();
	}
	
	public void addSection(Code code) {
		codeSection.add(code);
	}
	public Code getSection(int codeId) {
		return codeSection.get(codeId);
	}
	public Code getSingleSection() {
		return codeSection.get(0);
	}
	public int getSectionSize() {
		return codeSection.size();
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	public double getFitness() {
		return this.fitness;
	}
	public void setSolution(Object solution) {
		this.solution = solution;
	}
	public Object getSolution() {
		return this.solution;
	}
	public Object clone(){
	    Object o=null;    
	    try {    
	    	o=(Individual)super.clone();    
	    }    
	    catch(CloneNotSupportedException e) {    
	        System.out.println(e.toString());    
	    }    
	    return o;    
	}    
}
