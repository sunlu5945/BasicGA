package org.evolsoft.algorithms.DE;

import org.apache.commons.math3.distribution.CauchyDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.evolsoft.component.Individual;
import org.evolsoft.component.Population;
import org.evolsoft.component.SolutionUpdate;
import org.evolsoft.problem.jsp.Jsp;
import org.evolsoft.problem.jsp.JspDecode;
import org.evolsoft.scheduling.Schedule;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by wwhh on 2015/3/8.
 */
public class SaNSDE {
	private int groupSize; //种群大小
	private Jsp jspData;

	/*用于p的更新*/
	private double P;
	private int indicatorOfP[];//用来指示本次世代用的是哪个公式 1:(1),2:(3)
	private int Pns1;
	private int Pns2;
	private int Pnf1;
	private int Pnf2;
	/*用于权重的概率值的更新*/
	private double FP;
	private int indicatorOfFP[];//用来指示本次世代用的是哪个分布,1:normal, 2:cauchy
	private int Fns1;
	private int Fns2;
	private int Fnf1;
	private int Fnf2;
	/*用于变异率的更新*/
	private double CRm;
	private double CR[];
	private ArrayList<Double> deltaFunc_rec;
	private ArrayList<Double> CR_rec;
	public SaNSDE(int groupSize, Jsp jspData) {
		this.groupSize = groupSize;
		this.jspData = jspData;
		P = 0.5;
		indicatorOfP = new int[groupSize];
		Pns1 = 0;
		Pns2 = 0;
		Pnf1 = 0;
		Pnf2 = 0;
		FP = 0.5;
		indicatorOfFP = new int[groupSize];
		Fns1 = 0;
		Fns2 = 0;
		Fnf1 = 0;
		Fnf2 = 0;

		CRm = 0.5;
		CR = new double[groupSize];
		NormalDistribution normalDistributionCR = new NormalDistribution(CRm, 0.1);
		for(int i=0; i<groupSize; i++) {
			CR[i] = normalDistributionCR.sample();
		}
		deltaFunc_rec = new ArrayList<Double>();
		CR_rec = new ArrayList<Double>();
	}
	public Population optimizer(Population population, int iteration){
		Random random = new Random();
		Population res;
		//每次F都要重新生成
		double[] F = new double[population.getSize()];
		NormalDistribution normalDistributionF = new NormalDistribution(0.5, 0.3);
		CauchyDistribution cauchyDistribution = new CauchyDistribution();
		for(int i=0; i<population.getSize(); i++) {
			if (random.nextDouble() < FP) {
				F[i] = normalDistributionF.sample();
				indicatorOfFP[i] = 1;
			} else {
				F[i] = cauchyDistribution.sample();
				indicatorOfFP[i] = 2;
			}
		}
		res = mutation(population, F);
		res = crossover(population, res);
		res = selection(population, res);
		//每5次迭代重新生成CR
		if(iteration % 5 == 0) {
			NormalDistribution normalDistributionCR = new NormalDistribution(CRm, 0.1);
			for(int i=0; i<groupSize; i++){
				CR[i] = normalDistributionCR.sample();
			}
		}
		//每25世代调整CRm
		if(iteration % 25 == 0){
			double sumOfDeltaFunc_rec = 0.0;
			for(int i=0; i<deltaFunc_rec.size(); i++){
				sumOfDeltaFunc_rec += deltaFunc_rec.get(i);
			}
			CRm = 0;
			for(int i=0; i<CR_rec.size(); i++){
				CRm += (deltaFunc_rec.get(i) / sumOfDeltaFunc_rec) * CR_rec.get(i);
			}
			deltaFunc_rec.clear();
			CR_rec.clear();
		}
		//每50世代调整p和fp
		if(iteration % 50 == 0){
			double temp1 = (Pns1 * (Pns2 + Pnf2));
			double temp2 = (Pns2 * (Pns1 + Pnf1) + Pns1 * (Pns2 + Pnf2));
			P = temp1 / temp2;
			Pns1 = 0;
			Pns2 = 0;
			Pnf1 = 0;
			Pnf2 = 0;

			temp1 = Fns1 * (Fns2 + Fnf2);
			temp2 = Fns2 * (Fns1 + Fnf1) + Fns1 * (Fns2 + Fnf2);
			FP = temp1 / temp2;
			Fns1 = 0;
			Fns2 = 0;
			Fnf1 = 0;
			Fnf2 = 0;
		}
		return res;

	}

	private Individual getPopBest(Population population){

		return SolutionUpdate.oneMinObjectiveUpdate(population);
	}
	private Population mutation(Population population, double[] F){
		Population res = new Population();
		Random random = new Random();
		for(int i=0; i<groupSize; i++) {
			population.getIndividual(i).setSolution
					(JspDecode.doublePriorityDecode(population.getIndividual(i).getSection(0), jspData));
			Schedule s = (Schedule)population.getIndividual(i).getSolution();
			population.getIndividual(i).setFitness(s.getMakespan());
		}
		for(int i=0; i<population.getSize(); i++) {
			if (random.nextDouble() < P) {
				indicatorOfP[i] = 1;
				int popSize = population.getSize();
				int i1 = random.nextInt(popSize);
				int i2 = random.nextInt(popSize);
				while (i2 == i1) {
					i2 = random.nextInt(popSize);
				}
				int i3 = random.nextInt(popSize);
				while (i3 == i1 || i3 == i2) {
					i3 = random.nextInt(popSize);
				}
				Individual temp = population.getIndividual(i1).clone();
				for (int j = 0; j < temp.getSection(0).getSize(); j++) {
					temp.getSection(0).setGene(j, Math.abs(temp.getSection(0).getDoubleGene(j) +
							F[i] * (population.getIndividual(i2).getSection(0).getDoubleGene(j) - population.getIndividual(i3).getSection(0).getDoubleGene(j))) % temp.getSection(0).getSize());
				}
				res.addIndividual(temp);

			} else {
				indicatorOfP[i] = 2;
				int popSize = population.getSize();
				Individual best = getPopBest(population);
				int i1 = random.nextInt(popSize);
				int i2 = random.nextInt(popSize);
				while (i2 == i1) {
					i2 = random.nextInt(popSize);
				}
				Individual temp = population.getIndividual(i).clone();
				for (int j = 0; j < temp.getSection(0).getSize(); j++) {
					temp.getSection(0).setGene(j, Math.abs(temp.getSection(0).getDoubleGene(j) +
							F[i] * (best.getSection(0).getDoubleGene(j) - population.getIndividual(i).getSection(0).getDoubleGene(j)) +
							F[i] * (population.getIndividual(i1).getSection(0).getDoubleGene(j) - population.getIndividual(i2).getSection(0).getDoubleGene(j)))  % temp.getSection(0).getSize());
				}
				res.addIndividual(temp);

			}
		}
		return res;
	}
	private Population crossover(Population population, Population candidate){
		Random random = new Random();
		Population res = new Population();
		for(int i=0; i<population.getSize(); i++){
			Individual temp = population.getIndividual(i).clone();
			int index = random.nextInt(temp.getSection(0).getSize());//保证至少有一个纬度上是变异的
			for(int j = 0; j<temp.getSection(0).getSize(); j++){
				if(random.nextDouble() <= CR[i] || j == index){
					temp.getSection(0).setGene(j, candidate.getIndividual(i).getSection(0).getDoubleGene(j));
				}
			}
			res.addIndividual(temp);
		}
		return res;
	}
	private Population selection(Population population, Population candidate){
		Population res = new Population();
		for(int i=0; i<groupSize; i++) {
			candidate.getIndividual(i).setSolution
					(JspDecode.doublePriorityDecode(candidate.getIndividual(i).getSection(0), jspData));
			Schedule s = (Schedule)candidate.getIndividual(i).getSolution();
			candidate.getIndividual(i).setFitness(s.getMakespan());
		}
		for(int i=0; i<population.getSize(); i++){
			double fitnessOfPop = population.getIndividual(i).getFitness();
			double fitnessOfCandidate = candidate.getIndividual(i).getFitness();
			if(fitnessOfPop < fitnessOfCandidate){
				//不比原来的好，更新失败
				res.addIndividual(population.getIndividual(i).clone());
				Pnf1 += (indicatorOfP[i] == 1) ? 1 : 0;
				Pnf2 += (indicatorOfP[i] == 2) ? 1 : 0;
				Fnf1 += (indicatorOfFP[i] == 1) ? 1 : 0;
				Fnf2 += (indicatorOfFP[i] == 2) ? 1 : 0;

			}
			else{
				//比原来的好，更新成功
				res.addIndividual(candidate.getIndividual(i).clone());
				Pns1 += (indicatorOfP[i] == 1) ? 1 : 0;
				Pns2 += (indicatorOfP[i] == 2) ? 1 : 0;
				Fns1 += (indicatorOfFP[i] == 1) ? 1 : 0;
				Fns2 += (indicatorOfFP[i] == 2) ? 1 : 0;
				CR_rec.add(CR[i]);
				deltaFunc_rec.add(fitnessOfPop - fitnessOfCandidate);
			}
		}
		return res;
	}


}
