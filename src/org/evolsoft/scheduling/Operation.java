package org.evolsoft.scheduling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Operation {
	private int id;
	
	private double startingTime;
	private double endingTime;
	private double processingTime;
	//todo 继承后添加一个 map，用来存该操作在所有机器上的时间，用一个decode函数根据染色体确定当前的processingTime
	private Map<Integer,Double> allProcessTime;

	private List<Integer> importOperationIdList;
	private List<Integer> exportOperationIdList;
	private List<Resource> resourceList;

	public Operation() {
		importOperationIdList = new ArrayList();
		exportOperationIdList = new ArrayList();
		resourceList = new ArrayList<Resource>();
		allProcessTime  = new HashMap<Integer, Double>();
	}
	
	public void setID(int id) {
		this.id=id;
	}
	public int getID() {
		return this.id;
	}
	
	public void setStartingTime(double startingTime) {
		this.startingTime = startingTime;
	}
	public double getStartingTime() {
		return this.startingTime;
	}
	
	public void setEndingTime(double endingTime) {
		this.endingTime = endingTime;
	}
	public double getEndingTime() {
		return this.endingTime;
	}
	
	public void setProcessingTime(double processingTime) {
		this.processingTime = processingTime;
	}
	//todo 改成获取获取随机的时间值
	public double getProcessingTime() {
		return this.processingTime;
	}
	
	//ToDo  importOperationID, exportOperationID, resource
	public void addResource(Resource resource) {
		resourceList.add(resource);
	}
	public Resource getResource(int resourcId) {
		return resourceList.get(resourcId);
	}
	public int getResourceSize() {
		return resourceList.size();
	}
	
	public void addNewRessource(String name) {
		int tempId = resourceList.size();
		Resource re = new Resource();
		re.setName(name);
		re.setID(tempId);
		resourceList.add(re);
	}
	public void addProcessTime(int machineID, double processingTime){
		allProcessTime.put(machineID, processingTime);
	}
	public void setProcessTime(int machineID){
		this.setRessourceAttributeID(0, machineID);
		processingTime = allProcessTime.get(machineID);
	}
	
	public void setRessourceAttributeID(int resourceId, int attributeId) {
		resourceList.get(resourceId).setAttributeID(attributeId);
	}
	public int getRessourceAttributeID(int resourceId) {
		return resourceList.get(resourceId).getAttributeID();
	}
	
}
