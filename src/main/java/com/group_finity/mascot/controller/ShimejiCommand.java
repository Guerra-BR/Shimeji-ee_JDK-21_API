package com.group_finity.mascot.controller;

import java.util.ArrayList;

public class ShimejiCommand {
    private ArrayList<Integer> mascotId;
    private String mascotImageSet;
    private String behaviour;

    public ArrayList<Integer> getMascotId() {
        return mascotId;
    }

    public void setMascotId(ArrayList<Integer> mascotId) {
        this.mascotId = mascotId;
    }

    public String getMascotImageSet() {
        return mascotImageSet;
    }

    public void setMascotImageSet(String mascotImageSet) {
        this.mascotImageSet = mascotImageSet;
    }

    public String getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(String behaviour) {
        this.behaviour = behaviour;
    }

}
