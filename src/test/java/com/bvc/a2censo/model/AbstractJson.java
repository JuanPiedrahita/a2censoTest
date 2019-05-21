package com.bvc.a2censo.model;

import java.util.List;

public abstract class AbstractJson {

    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Object getByName (AbstractJson[] list, String name) {
        Object element = null;
        for (int i = 0; i < list.length ; i++) {
            if (list[i].getName().equals(name)) {
                element = list[i];
            }
        }
        return element;
    }
}
