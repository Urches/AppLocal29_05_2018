package com.project.elements.generators;

import com.project.model.exceptions.LogicException;

import java.util.*;
import java.util.stream.Collectors;

public class AsymmetricalGenerator extends Generator {

	private Double defaultValue = DEFAULT_VALUE;

	private Set<TimeInterval> intervals = new HashSet<>();

	public void setDefaultValue(Double defaultValue){
		this.defaultValue = defaultValue;
	}

	/**
	 * A unique point-in-time value is not supported!
	 * This logic should realize user!
	 * @return this
	 */
	public AsymmetricalGenerator addValue(long fromTime, long toTime, Double value){
		intervals.add(new TimeInterval(fromTime, toTime, value));
		return this;
	}

	@Override
	public Double getValue() {
		if(timer != null){
			long time = timer.getCurrentModelTime();
			List<TimeInterval> filtred = intervals.stream().filter(interval -> interval.getFromTime() <= time && time < interval.getToTime())
					.collect(Collectors.toList());
			if(filtred.size() == 1){
				return  filtred.get(0).getValue();
			} else if(filtred.isEmpty()){
				System.out.println("Value at time " + time + " doesn't found! Default value will be return!");
				return defaultValue;
			} else {
				//throw new LogicException("Found " + filtred.size() + " values at time " + time);
				//return last
				return filtred.get(filtred.size() - 1).getValue();
			}
		} else {
			return defaultValue;
		}

	}

	protected class TimeInterval {
		private long fromTime;
		private long toTime;
		private Double value;

		public TimeInterval(long fromTime, long toTime, Double value){
			this.fromTime = fromTime;
			this.toTime = toTime;
			this.value = value;
		}

		public Double getValue(){
			return value;
		}

		public long getFromTime() {
			return fromTime;
		}

		public long getToTime() {
			return toTime;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			TimeInterval that = (TimeInterval) o;
			return fromTime == that.fromTime &&
					toTime == that.toTime;
		}

		@Override
		public int hashCode() {
			return Objects.hash(fromTime, toTime);
		}

		@Override
		public String toString() {
			return "TimeInterval{" +
					"fromTime=" + fromTime +
					", toTime=" + toTime +
					", value=" + value +
					'}';
		}
	}
}
