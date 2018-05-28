package com.project.model.functions;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class FunctionController {

	private static Map<String, ActivatedFunction> map = new HashMap<>();
	
	static {
		map.put("LINEAR", new LinearFunction());
		map.put("SIGMOID", new SigmoidFunction());
		map.put("THRESHOLD", new ThresholdFunction());
		map.put("LINEAR_BOUNDER", new LinearBounderFunction());
	}
	
	public static ActivatedFunction getFunction(String key){
		ActivatedFunction function = map.get(key.toUpperCase());
		if (function != null) {
			return function;
		} else {
			throw new RuntimeException("Function didn't find!");
		}	
	}
	
	public static String getFunctionKey(ActivatedFunction value){
	    for (Entry<String, ActivatedFunction> entry : map.entrySet()) {
	        if (entry.getValue().equals(value)) {
	            return entry.getKey();
	        }
	    }
	    throw new RuntimeException("Key didn't find!");
	}
	
	public static String[] names(){
		return map.keySet().stream().toArray(String[]::new);
	}
}
