package com.project.model.connetions;

public enum PortPosition {
	IN, OUT, NONE;
	
	public static String[] names() {
		String[] names = new String[PortPosition.values().length];
		for (int i = 0; i < names.length; i++) {
			names[i] = PortPosition.values()[i].name();
		}
		return names;
	}
}
