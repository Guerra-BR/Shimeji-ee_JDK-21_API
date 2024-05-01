package com.group_finity.mascot.controller;

import java.util.ArrayList;
import java.util.Set;

public class MascotData {
    private Integer id;
    private String imageSet;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageSet() {
        return imageSet;
    }

    public void setImageSet(String imageSet) {
        this.imageSet = imageSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getBehaviours() {
        return behaviours;
    }

    public void setBehaviours(Set<String> behaviours) {
        this.behaviours = behaviours;
    }

    public MascotData(Integer id, String imageSet, String name, Set<String> behaviours) {
        this.id = id;
        this.imageSet = imageSet;
        this.name = name;
        this.behaviours = behaviours;
    }

    private Set<String> behaviours;
}
