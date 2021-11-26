package com.plixo.ui.resource.resource;


import com.plixo.ui.resource.UIReferenceHolderCanvas;
import com.plixo.util.Vector3f;
import com.plixo.util.reference.InterfaceReference;



public class UIVector3 extends UIReferenceHolderCanvas<Vector3f> {
    //UIPointNumber for XYZ components
     UIPointNumber spinnerX;
     UIPointNumber spinnerY;
     UIPointNumber spinnerZ;

    //set number field position
    @Override
    public void setDimensions(float x, float y, float width, float height) {
        super.setDimensions(x, y, width, height);
        float spinnerWidth = width / 3;
        setColor(0);
        clear();

        spinnerX = new UIPointNumber();

        spinnerX.setRoundness(getRoundness());
        spinnerX.setOutlineWidth(getOutlineWidth());
        spinnerX.setOutlineColor(getOutlineColor());
        spinnerX.setReference(new InterfaceReference<>(ref -> {
            Vector3f value = reference.getValue();
            reference.setValue(new Vector3f(ref , value.y , value.z));
        }, () -> (float) reference.getValue().x));
        spinnerX.setDimensions(0, 0, spinnerWidth, height);

        spinnerY = new UIPointNumber();

        spinnerY.setRoundness(getRoundness());
        spinnerY.setOutlineWidth(getOutlineWidth());
        spinnerY.setOutlineColor(getOutlineColor());
        spinnerY.setReference(new InterfaceReference<>(ref -> {
            Vector3f value = reference.getValue();
            reference.setValue(new Vector3f(value.x , ref, value.z));
        }, () -> (float) reference.getValue().y));
        spinnerY.setDimensions(spinnerWidth, 0, spinnerWidth, height);

        spinnerZ = new UIPointNumber();

        spinnerZ.setRoundness(getRoundness());
        spinnerZ.setOutlineWidth(getOutlineWidth());
        spinnerZ.setOutlineColor(getOutlineColor());
        spinnerZ.setReference(new InterfaceReference<>(ref -> {
            Vector3f value = reference.getValue();
            reference.setValue(new Vector3f(value.x , value.y , ref));
        }, () -> (float) reference.getValue().z));
        spinnerZ.setDimensions(spinnerWidth * 2, 0, spinnerWidth, height);

        //0xFFFA3237
        //  0xFF32FA68
        //  0xFF4053FA
        spinnerX.setColor(0xFFC2272B);
        spinnerY.setColor(0xFF1B8738);
        spinnerZ.setColor(0xFF303FBA);

        add(spinnerX);
        add(spinnerY);
        add(spinnerZ);
    }


}
