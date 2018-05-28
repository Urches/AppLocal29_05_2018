package com.project.elements.timers;

import com.project.model.exceptions.ModelTimerException;

public class ModelSimpleTimer implements ModelTimer {

    private static final long DEFAULT_FINISH_TIME = 1000;
    private static final long DEFAULT_START_TIME = 0;
    private static final long DEFAULT_STEP = 10;
    private static final long DEFAULT_LAST_TIME = -9999;

    private long startTime = DEFAULT_START_TIME;
    private long finishTime = DEFAULT_FINISH_TIME;
    private long lastTime = DEFAULT_LAST_TIME;
    private long step = DEFAULT_STEP;
    private boolean started = false;

    public void start(){
        this.started = true;
        reset();
    }

    public void start(long finishTime){
        this.finishTime = finishTime;
        this.started = true;
        reset();
    }

    @Override
    public void reset(){
        lastTime = DEFAULT_LAST_TIME;
    }
    @Override
    public void stop(){
        this.started = false;
    }
    @Override
    public long getStep(){
        return this.step;
    }
    @Override
    public void setStep(long step) {
        if(step > 0){
            this.step = step;
        } else {
            throw new ModelTimerException("Timer can't have step value " + step);
        }
    }
    @Override
    public long getFinishTime() {
        return finishTime;
    }
    @Override
    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    private boolean isOver(){
        if(started){
            if(lastTime + step > finishTime){
                stop();
                return true;
            } else {
                return false;
            }
        }  else {
            return true;
        }
    }
    @Override
    public boolean isStarted() {
        return started;
    }

    @Override
    public boolean hasNext() {
        if(!isOver()){
            if(lastTime != DEFAULT_LAST_TIME){
                lastTime += step;
            } else {
                lastTime = startTime;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public long getLastTime() {
        return lastTime;
    }

    @Override
    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    @Override
    public long getCurrentModelTime() {
        System.out.println(lastTime);
        if(lastTime != DEFAULT_LAST_TIME){
            return lastTime;
        } else throw new ModelTimerException("Timer isn't started or already finished, or method nasNext doesn't invoked!");
    }

    @Override
    public String toString() {
        return "ModelSimpleTimer{" +
                "startTime=" + startTime +
                ", finishTime=" + finishTime +
                ", lastTime=" + lastTime +
                ", step=" + step +
                ", started=" + started +
                '}';
    }
}
