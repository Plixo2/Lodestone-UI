package com.plixo.ui.resource.resource;


import com.plixo.ui.resource.UIReferenceHolderCanvas;
import com.plixo.util.Vector2f;
import com.plixo.util.reference.InterfaceReference;
import com.sun.javafx.geom.Vec2f;


public class UIVector2 extends UIReferenceHolderCanvas<Vector2f> {
    //UIPointNumber for XYZ components
     UIPointNumber spinnerX;
     UIPointNumber spinnerY;

    //set number field position
    @Override
    public void setDimensions(float x, float y, float width, float height) {
        super.setDimensions(x, y, width, height);
        float spinnerWidth = width / 2;
        setColor(0);
        clear();

        spinnerX = new UIPointNumber();

        spinnerX.setRoundness(getRoundness());
        spinnerX.setOutlineWidth(getOutlineWidth());
        spinnerX.setOutlineColor(getOutlineColor());
        spinnerX.setReference(new InterfaceReference<>(ref -> {
            Vector2f value = reference.getValue();
            reference.setValue(new Vector2f(ref , value.y));
        }, () -> (float) reference.getValue().x));
        spinnerX.setDimensions(0, 0, spinnerWidth, height);

        spinnerY = new UIPointNumber();

        spinnerY.setRoundness(getRoundness());
        spinnerY.setOutlineWidth(getOutlineWidth());
        spinnerY.setOutlineColor(getOutlineColor());
        spinnerY.setReference(new InterfaceReference<>(ref -> {
            Vector2f value = reference.getValue();
            reference.setValue(new Vector2f(value.x , ref));
        }, () -> (float) reference.getValue().y));
        spinnerY.setDimensions(spinnerWidth, 0, spinnerWidth, height);

        //0xFFFA3237
        //  0xFF32FA68
        //  0xFF4053FA
        spinnerX.setColor(0xFFC2272B);
        spinnerY.setColor(0xFF1B8738);


        add(spinnerX);
        add(spinnerY);
    }


}
