package org.evolsoft.ccSaNSDE;

import org.evolsoft.component.Code;
import org.evolsoft.component.Individual;
import org.evolsoft.component.Initialization;
import org.evolsoft.component.Population;
import org.evolsoft.functions.Function;

import java.util.ArrayList;

/**
 * Created by wwhh on 2015/1/16.
 */
public class CCFrame {
	private ArrayList<ArrayList<Integer>> allGroups;
	private Function function;
	private double xn;

	public CCFrame(Function function){
		//在这里指定要用到的函数
		this.function = function;
		xn = 1e-3;
	}
	public void setXn(double xn){
		this.xn = xn;
	}
	public void grouping(int dimension){
		function.setDimension(dimension);
		ArrayList<Integer> dims = new ArrayList<Integer>();
		for(int i=0; i<function.getDimension(); i++){
			dims.add(i);
		}
		ArrayList<Integer> seps = new ArrayList<Integer>();
		allGroups = new ArrayList<ArrayList<Integer>>();

		int sign[] = new int[function.getDimension()];

		Code p1 = ones(function.getLbound(), function.getDimension());
		Code p2 = p1.clone();
		for(int i=0; i<function.getDimension(); i++){
			if(sign[i] == 1){
				continue;
			}
			ArrayList<Integer> group = new ArrayList<Integer>();
			group.add(i);
			sign[i] = 1;
			p2.setGene(i, function.getUbound());
			double delta1 = function.run(p1) - function.run(p2);
			for(int j=i+1; j<function.getDimension(); j++) {
				if (sign[j] == 1) {
					continue;
				}
				p1.setGene(j, (function.getUbound() + function.getLbound()) / 2.0);
				p2.setGene(j, (function.getUbound() + function.getLbound()) / 2.0);
				double delta2 = function.run(p1) - function.run(p2);
				if(delta1 - delta2 > xn){
					group.add(j);
				}
				p1.setGene(j, function.getLbound());
				p2.setGene(j, function.getLbound());
			}
			p2.setGene(i, function.getLbound());
			listMinusList(sign, group);
			if(group.size() == 1){
				seps.add(group.get(0));
			}
			else{
				allGroups.add(group);
			}
		}
		allGroups.add(seps);

	}
	public Code ones(double initialValue, int dim){
		Code res = new Code();
		for(int i=0; i<dim; i++){
			res.addGene(initialValue);
		}
		return res;
	}
	public void listMinusList(int[] sign, ArrayList<Integer> list2){
		for(int i=0; i<list2.size(); i++){
			sign[list2.get(i)] = 1;
		}
	}
	public void showAllGroups(String arg){
		for(int i=0; i<allGroups.size(); i++){
			arg += "\n";
			for(int j=0; j<allGroups.get(i).size(); j++){
				arg += allGroups.get(i).get(j) + " ";
			}
		}
		System.out.println(arg);
	}
	public void CCRun(int dimension, int popSize, int iteration){
		grouping(dimension);
		Population pop = Initialization.populationInitialization(popSize, dimension, function.getLbound(), function.getUbound());
		Individual best = getMinIndividual(pop);
		ArrayList<SaNSDE> optimizers = new ArrayList<SaNSDE>();
		for(int i=0; i<allGroups.size(); i++){
			optimizers.add(new SaNSDE(popSize, function));
		}
		for(int i=1; i<=iteration; i++){
			for(int j=0; j<allGroups.size(); j++){
				ArrayList<Integer> indices = allGroups.get(j);
				Population subPop = getSubPop(pop, indices);
				subPop = optimizers.get(j).optimizer(best, subPop, indices, i);
				pop = setSubPop(pop, subPop, indices);
				best = getMinIndividual(pop);

				if(i % 25 == 0){
					System.out.println(""+ i + "th iteration" + best.getFitness() );
				}
			}
		}


	}
	private Individual getMinIndividual(Population pop){
		int index = -1;
		double bestFitness = Double.MAX_VALUE;
		for(int i=0; i<pop.getSize(); i++){
			double tempFitness = function.run(pop.getIndividual(i).getSection(0));
			pop.getIndividual(i).setFitness(tempFitness);
			if(tempFitness < bestFitness){
				index = i;
				bestFitness = tempFitness;
			}
		}
		return pop.getIndividual(index).clone();
	}
	public Population getSubPop(Population pop, ArrayList<Integer> indices){
		Population subPop = new Population();
		for(int i=0; i<pop.getSize(); i++){
			Individual individual = new Individual();
			Code code = new Code();
			for(int j=0; j<indices.size(); j++){
				code.addGene(pop.getIndividual(i).getSection(0).getDoubleGene(indices.get(j)));
			}
			individual.addSection(code);
			subPop.addIndividual(individual);
		}
		return subPop;
	}
	public Population setSubPop(Population pop, Population subPop, ArrayList<Integer> indices){
		for(int i=0; i<pop.getSize(); i++){
			for(int j=0; j<indices.size(); j++){
				pop.getIndividual(i).getSection(0).setGene(indices.get(j),
						subPop.getIndividual(i).getSection(0).getDoubleGene(j));
			}
		}
		return pop;
	}

}
