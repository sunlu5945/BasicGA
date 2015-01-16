package org.evolsoft.problem.jsp;

import org.evolsoft.component.*;
import org.evolsoft.scheduling.Schedule;

public class JspRkGa {
	
	Jsp jsp;
	Parameter parameter;
	Population population;
	Individual gbest;

	public void run(String JspDataFileName, String GaParameterFileName) {
		jsp = JspImport.txtDataImport(JspDataFileName);
		parameter = ParameterImport.gaParameterTxtImport(GaParameterFileName);
		gbest = new Individual();
		
		// initialization
		population = Initialization.oneDimensionDoublePriorityInitialization
				(parameter.getPopulationSize(), jsp.getJobSize()*jsp.getMachineSize());
		
		for(int i=0; i<parameter.getPopulationSize(); i++) {
			population.getIndividual(i).setSolution
				(JspDecode.doublePriorityDecode(population.getIndividual(i).getSection(0), jsp));
			Schedule s = (Schedule)population.getIndividual(i).getSolution();
			population.getIndividual(i).setFitness(s.getMakespan());
		}		
		
		
		
		
		for(int i=0; i<parameter.getPopulationSize(); i++) {
			Encode.printDoubleCode(population.getIndividual(i).getSection(0));
			JspDecode.printJspSchedule((Schedule)population.getIndividual(i).getSolution());
		}		
		
	}
	
	public static void main(String[] args) {
		JspRkGa ga = new JspRkGa();
		ga.run("JspData3x3.txt","GaParameter.txt");
	}

}
