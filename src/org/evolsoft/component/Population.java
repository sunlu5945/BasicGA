package org.evolsoft.component;

import java.util.ArrayList;
import java.util.List;

public class Population {
	List<Individual> individualList;
	
	public Population() {
		individualList = new ArrayList<Individual>();
	}
	
	public void addIndividual(Individual individual) {
		individualList.add(individual);
	}
	public void addPopulation(Population population) {
		for(int i=0; i<population.getSize(); i++) {
			individualList.add(population.getIndividual(i));
		}
	}
	public Individual getIndividual(int id) {
		return individualList.get(id);
	}
	public void removeIndividual(int id) {
		individualList.remove(id);
	}
	
	public int getSize() {
		return individualList.size();
	}
}
