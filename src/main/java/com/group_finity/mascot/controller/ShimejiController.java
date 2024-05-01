package com.group_finity.mascot.controller;

import com.group_finity.mascot.Manager;
import com.group_finity.mascot.Mascot;
import com.group_finity.mascot.VShimejiApplication;
import com.group_finity.mascot.behavior.Behavior;
import com.group_finity.mascot.exception.BehaviorInstantiationException;
import com.group_finity.mascot.exception.CantBeAliveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


//Esse funciona
@RestController
@RequestMapping("shimeji")
public class ShimejiController {
    private Manager manager = Manager.getInstance();

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<String> setACommand(@RequestBody ShimejiCommand data) {
        manager.setBehaviorAPI(data.getBehaviour(), data.getMascotImageSet(), data.getMascotId());
        System.out.println(data.getMascotImageSet());

        return ResponseEntity.ok("Sucess");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<AvaliableCommandsAndShimejis> getAllowedCommandsAndShimejis(){

        return ResponseEntity.ok(new AvaliableCommandsAndShimejis());
    }
}
