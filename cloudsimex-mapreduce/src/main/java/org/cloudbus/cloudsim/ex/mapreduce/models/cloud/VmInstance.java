package org.cloudbus.cloudsim.ex.mapreduce.models.cloud;

import java.util.ArrayList;
import java.util.List;

import org.cloudbus.cloudsim.ex.mapreduce.models.request.MapTask;
import org.cloudbus.cloudsim.ex.mapreduce.models.request.ReduceTask;
import org.cloudbus.cloudsim.ex.mapreduce.models.request.Request;

public class VmInstance extends VmType {

	public int vmTypeId;
	public Request request;
	
	public VmInstance(VmType vmType, Request request) {
		super(vmType.name, vmType.vmCostPerHour, vmType.transferringCost, vmType.getMips(), vmType.getNumberOfPes(), vmType.getRam(), vmType.bootTime);
		
		vmTypeId = vmType.getId();
		this.request = request;
	}

	public int getVmTypeId() {
		return vmTypeId;
	}

	public void setVmTypeId(int vmTypeId) {
		this.vmTypeId = vmTypeId;
	}

	public int getRequestId() {
		return request.getId();
	}

	public void setRequest(Request request) {
		this.request = request;
	}
	
	public double getExecutionCost() {
		return Math.ceil(getExecutionTime() / 3600.0) * vmCostPerHour;
	}
	
	public double getExecutionTime() {
		double totalExecutionTime = 0.0;
		for (MapTask mapTask : request.job.mapTasks)
			if(mapTask.getVmId() == getId())
				totalExecutionTime += mapTask.getFinalExecTime();
		for (ReduceTask reduceTask : request.job.reduceTasks)
			if(reduceTask.getVmId() == getId())
				totalExecutionTime += reduceTask.getFinalExecTime();
		return totalExecutionTime;
	}
	
	public List<Integer> getTasksId()
	{
		List<Integer> tasksId = new ArrayList<Integer>();
		for (MapTask mapTask : request.job.mapTasks)
			if(mapTask.getVmId() == getId())
				tasksId.add(mapTask.getCloudletId());
		for (ReduceTask reduceTask : request.job.reduceTasks)
			if(reduceTask.getVmId() == getId())
				tasksId.add(reduceTask.getCloudletId());
		
		return tasksId;
	}
	
	public String getTasksIdAsString()
	{
		List<Integer> tasksId = getTasksId();
		String tasksIdAsString = "[";
		Boolean isFirst = true;
		for (Integer id : tasksId) {
			if(isFirst)
			{
				tasksIdAsString+=id;
				isFirst = false;
			}
			else
				tasksIdAsString+= "-"+id;
		}
		return tasksIdAsString +"]";
	}
	
	public String getPolicy()
	{
		return request.getPolicy();
	}
	
	public String getJ()
	{
		return request.getJ();
	}
}