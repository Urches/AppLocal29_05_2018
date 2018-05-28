package com.project.model.connetions;

import java.util.Arrays;

public enum ConnectionType {
	DIGITAL, LOGIC, INTERVAL;
	
	public static String[] names(){
		String[] names = new String[ConnectionType.values().length];
		for (int i = 0; i < names.length; i++) {
			names[i] = ConnectionType.values()[i].name();
		}
		return names;
	}
	
	public static ConnectionType contain(String str){
		int pos = Arrays.binarySearch(names(), str);
		if(pos > 0){
			return valueOf(names()[pos]);
		}
		return null;
	}
}
