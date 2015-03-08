package org.evolsoft.test;

import org.evolsoft.component.*;
import org.evolsoft.problem.jsp.Jsp;
import org.evolsoft.problem.jsp.JspDecode;
import org.evolsoft.problem.jsp.JspImport;
import org.evolsoft.scheduling.Schedule;

/**
 * Created by wwhh on 2015/3/7.
 */
public class GATest {
	public static void main(String[] args) {

		// input
		Jsp jsp = JspImport.txtDataImport("JspData01");
		Parameter parameter = ParameterImport.gaParameterTxtImport("GaParameter.txt");

		// output
		Individual gbest = new Individual();

		// initialization
		int t = 0;
		Population population = Initialization.oneDimensionDoublePriorityInitialization
				(parameter.getPopulationSize(), 2 * jsp.getJobSize() * jsp.getMachineSize());
		for(int i=0; i<parameter.getPopulationSize(); i++) {
			population.getIndividual(i).setSolution
					(JspDecode.doublePriorityDecode(population.getIndividual(i).getSection(0), jsp));

			Schedule s = (Schedule)population.getIndividual(i).getSolution();
			population.getIndividual(i).setFitness(s.getMakespan());
		}
		gbest = SolutionUpdate.oneMinObjectiveUpdate(population);

		// iteration
		while (t<parameter.getNumOfEvolvedIndividual()) {
			if(t % 5 == 0){
				System.out.println("" + t + ": Best:" + gbest.getFitness());
			}
			Population mutationOffspring = Mutation.swapMutation(population, parameter.getMutationProbability());
			for(int i=0; i<mutationOffspring.getSize(); i++) {
				mutationOffspring.getIndividual(i).setSolution
						(JspDecode.doublePriorityDecode(mutationOffspring.getIndividual(i).getSection(0), jsp));
				Schedule s = (Schedule)mutationOffspring.getIndividual(i).getSolution();
				mutationOffspring.getIndividual(i).setFitness(s.getMakespan());
			}

			Population crossoverOffspring = Crossover.doublewmxCrossover(population, parameter.getCrossoverProbability());
			for(int i=0; i<crossoverOffspring.getSize(); i++) {
				crossoverOffspring.getIndividual(i).setSolution
						(JspDecode.doublePriorityDecode(crossoverOffspring.getIndividual(i).getSection(0), jsp));
				Schedule s = (Schedule)crossoverOffspring.getIndividual(i).getSolution();
				crossoverOffspring.getIndividual(i).setFitness(s.getMakespan());
			}

			population.addPopulation(mutationOffspring);
			population.addPopulation(crossoverOffspring);
			gbest = SolutionUpdate.oneMinObjectiveUpdate(population, gbest);


			population = Selection.minElitistSelection(population, parameter.getPopulationSize());
			//population = Selection.minRouletteWheelSelection(population, parameter.getPopulationSize());
			population = Selection.replaceWorstIndividual(population, gbest);

			System.out.println(" "+gbest.getFitness());

			t=t+crossoverOffspring.getSize()+mutationOffspring.getSize();
		}

		System.out.println("Best:");
		Encode.printDoubleCode(gbest.getSection(0));
		JspDecode.printJspSchedule((Schedule)gbest.getSolution());

		// print
		/*
		for(int i=0; i<parameter.getPopulationSize(); i++) {
			Encode.printIntCode(population.getIndividual(i).getSection(0));
			JspDecode.printJspSchedule((Schedule)population.getIndividual(i).getSolution());
		}
		System.out.println("Best:");
		Encode.printIntCode(gbest.getSection(0));
		JspDecode.printJspSchedule((Schedule)gbest.getSolution());
		*/
	}
}
