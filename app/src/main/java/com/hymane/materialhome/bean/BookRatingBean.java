package com.hymane.materialhome.bean;

import java.io.Serializable;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/1/8
 * Description:
 */
public class BookRatingBean implements Serializable {
    private int max;
    private int numRaters;
    private String average;
    private int min;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getNumRaters() {
        return numRaters;
    }

    public void setNumRaters(int numRaters) {
        this.numRaters = numRaters;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
