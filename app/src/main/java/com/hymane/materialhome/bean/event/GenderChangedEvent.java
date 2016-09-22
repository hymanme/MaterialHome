package com.hymane.materialhome.bean.event;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/22
 * Description:
 */

public class GenderChangedEvent {
    private String gender;

    public GenderChangedEvent(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
