package com.github.microkibaco.mildom.bean;

import java.io.Serializable;

public class MildomInfo implements Serializable {


    private String path;

    public MildomInfo(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }


}
