package com.project.elements.timers;

public class TimerProperties {

    private int duration;

    private int step;

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getDuration() {

        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "TimerProperties{" +
                "duration=" + duration +
                ", step=" + step +
                '}';
    }
}
