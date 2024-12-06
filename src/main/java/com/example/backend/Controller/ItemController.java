package com.example.backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.Model.Items;
import com.example.backend.Service.ItemService;

@RestController
@CrossOrigin
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/")
    public String isWorking() {
        return "Everything is working! :)";
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Items>> getItems() {
        return new ResponseEntity<>(itemService.getItems(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Items> getItemById(@PathVariable int id){
        Items item = itemService.getItemById(id);
        if(item != null) return new ResponseEntity<>(item, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestPart Items item, @RequestPart("imageFile") MultipartFile imageFile){
        try {
            Items prod = itemService.addItem(item, imageFile);
            return new ResponseEntity<>(prod, HttpStatus.CREATED);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
   
}