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

    @GetMapping("/AllNotClaimed")
    public ResponseEntity<List<Items>> getNonClaimedItems() {
        return new ResponseEntity<>(itemService.getNonClaimedItems(), HttpStatus.OK);
    }    

    @GetMapping("/{id}")
    public ResponseEntity<Items> getItemById(@PathVariable int id){
        Items item = itemService.getItemById(id);
        if(item != null) return new ResponseEntity<>(item, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestPart Items item, @RequestPart(value = "imageFile", required = false) MultipartFile imageFile){
        try {
            System.out.println(item);
            Items prod = itemService.addItem(item, imageFile);
            return new ResponseEntity<>(prod, HttpStatus.CREATED);
        } catch(Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/claim/{id}")
    public ResponseEntity<?> claimItem(@PathVariable int id){
        try{
            itemService.claimItem(id);
        }
        catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>("Item claimed", HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable int id) {
        try {
            itemService.deleteItem(id);
            return new ResponseEntity<>("Item deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting item: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
