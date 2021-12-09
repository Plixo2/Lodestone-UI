package com.plixo.ui;

public class DropTarget {
    public static DropTarget currentDropTarget = null;

    public Object obj;

    public DropTarget(Object obj) {
        this.obj = obj;
    }
}
