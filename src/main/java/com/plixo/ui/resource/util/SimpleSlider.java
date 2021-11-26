package com.plixo.ui.resource.util;


import com.plixo.util.Util;

public class SimpleSlider {
    public float value = 0;
    public transient float min , max;

    public SimpleSlider() {

    }

    public SimpleSlider(float value , float min,float max) {
        this.value = value;
        this.min = min;
        this.max = max;
    }

    public void setValue(float value) {
        this.value = Util.clampFloat(value,max,min);
    }

    public float getValue() {
        value = Util.clampFloat(value,max,min);
        return value;
    }
}
