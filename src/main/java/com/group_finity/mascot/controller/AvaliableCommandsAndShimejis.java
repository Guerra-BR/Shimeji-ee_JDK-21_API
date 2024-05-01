package com.group_finity.mascot.controller;

import com.group_finity.mascot.Manager;
import com.group_finity.mascot.Mascot;
import com.group_finity.mascot.VShimejiApplication;
import com.group_finity.mascot.config.Configuration;

import java.util.*;

public class AvaliableCommandsAndShimejis {
    private ArrayList<MascotData> mascotList = new ArrayList<>();

    public Map<String, Set<String>> getAllowedBehaviors() {
        return allowedBehaviors;
    }

    public void setAllowedBehaviors(Map<String, Set<String>> allowedBehaviors) {
        this.allowedBehaviors = allowedBehaviors;
    }

    private Map<String, Set<String>> allowedBehaviors = new HashMap();
    private Set<String> imageSets = new HashSet<>();
    private ArrayList<String> shimejisName = new ArrayList<>();
    private ArrayList<Integer> ids = new ArrayList<>();

    public AvaliableCommandsAndShimejis() {
        Manager manager = Manager.getInstance();

        for (Mascot mascot : manager.getMascots()){
            imageSets.add(mascot.getImageSet());
            shimejisName.add(mascot.toString());
            ids.add(mascot.getId());
            Configuration configuration = VShimejiApplication.getInstance().getConfiguration(mascot.getImageSet());

            mascotList.add(new MascotData(mascot.getId(), mascot.getImageSet(), mascot.toString(), configuration.getBehaviorNames()));
        }

        for (String set : imageSets){
            Configuration configuration = VShimejiApplication.getInstance().getConfiguration(set);
            allowedBehaviors.put(set, configuration.getBehaviorNames());
        }
    }

    public ArrayList<MascotData> getMascotList() {
        return mascotList;
    }

    public void setMascotList(ArrayList<MascotData> mascotList) {
        this.mascotList = mascotList;
    }

    public Set<String> getImageSets() {
        return imageSets;
    }

    public void setImageSets(Set<String> imageSets) {
        this.imageSets = imageSets;
    }

    public ArrayList<String> getShimejisName() {
        return shimejisName;
    }

    public void setShimejisName(ArrayList<String> shimejisName) {
        this.shimejisName = shimejisName;
    }

    public ArrayList<Integer> getIds() {
        return ids;
    }

    public void setIds(ArrayList<Integer> ids) {
        this.ids = ids;
    }
}
