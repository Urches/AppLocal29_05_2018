package com.project.elements.timers;

import com.project.model.exceptions.ModelTimerException;

public interface ModelTimer {

    void start();

    /**
     * It's available to reset started and not started timer!
     */
    void reset();

    void stop();

    long getStep();

    void setStep(long step);

    long getFinishTime();

    void setFinishTime(long finishTime);

    boolean isStarted();



    /**
     * @return true if time isn't over and increment current model time
     */
    boolean hasNext();

    long getLastTime();

    void setLastTime(long lastTime);

    /**
     * Be carefully! it haven't any protection and always return inner current time!
     * @return
     * @throws ModelTimerException
     */
    long getCurrentModelTime() throws ModelTimerException;
}
