package org.evolsoft.component;


public class Initialization {
	public static Population oneDimensionIntPriorityInitialization(int populationSize, int codeSize) {
		Population population = new Population();
		for(int i=0; i<populationSize; i++) {
			Individual individual = new Individual();
			Code code = new Code();
			code = Encode.createIntPriorityCode(codeSize);
			individual.addSection(code);
			population.addIndividual(individual);
		}
		return population;
	}
	public static Population oneDimensionDoublePriorityInitialization(int populationSize, int codeSize) {
		Population population = new Population();
		for(int i=0; i<populationSize; i++) {
			Individual individual = new Individual();
			Code code = new Code();
			code = Encode.createDoublePriorityCode(codeSize);
			individual.addSection(code);
			population.addIndividual(individual);
		}
		return population;
	}
}
