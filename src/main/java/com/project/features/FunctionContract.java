package com.project.features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.project.model.connetions.ConnectionType;

public class FunctionContract {

	protected ArrayList<Set<ConnectionType>> list = new ArrayList<>();
	
	public void add(ConnectionType...types){
		list.add(Arrays.stream(types).collect(Collectors.toSet()));
	}
	
	public ArrayList<Set<ConnectionType>> getSet(){
		return list;
	}
}
