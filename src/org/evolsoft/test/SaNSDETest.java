package org.evolsoft.test;

import org.evolsoft.algorithms.DE.SaNSDE;
import org.evolsoft.component.*;
import org.evolsoft.problem.jsp.Jsp;
import org.evolsoft.problem.jsp.JspImport;

/**
 * Created by wwhh on 2015/3/8.
 */
public class SaNSDETest {
	public static void main(String args[]){
		// input
		Jsp jsp = JspImport.txtDataImport("JspData01");
		Parameter parameter = ParameterImport.gaParameterTxtImport("GaParameter.txt");


		int t = 0;
		Population population = Initialization.oneDimensionDoublePriorityInitialization
				(parameter.getPopulationSize(), 2 * jsp.getJobSize() * jsp.getMachineSize());
		Individual gbest = SolutionUpdate.oneMinObjectiveUpdate(population);
		SaNSDE saNSDE = new SaNSDE(parameter.getPopulationSize(), jsp);

		while (t<parameter.getNumOfEvolvedIndividual()){
			if (t % 1000 == 0) {
				System.out.println("" + t + ": Best:" + gbest.getFitness());
			}
			population = saNSDE.optimizer(population, t);
			gbest = SolutionUpdate.oneMinObjectiveUpdate(population);
		}
	}
}
