package org.evolsoft.ccSaNSDE;

import org.evolsoft.component.Code;
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
}
