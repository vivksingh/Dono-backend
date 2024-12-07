package com.example.backend.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.Model.Items;
import com.example.backend.Repository.ItemsRepo;

@Service
public class ItemService {
    
    @Autowired
    private ItemsRepo itemsRepo;
    
    public List<Items> getItems() {
        return itemsRepo.findAll();
    }

    public Items getItemById(int id) {
        return itemsRepo.findById(id);
    }

    public Items addItem(Items item, MultipartFile imageFile) throws Exception {
        
        item.setImage(imageFile.getBytes());
        item.setImageName(imageFile.getOriginalFilename());
        item.setImageType(imageFile.getContentType());
        item.setClaimed(false);
        return itemsRepo.save(item);
    }

    public void claimItem(int id) throws Exception{
        Items item = getItemById(id);
        if(item != null){
            item.setClaimed(true);
            itemsRepo.save(item);
        }
    }
    
    public void deleteItem(int id) {
        itemsRepo.deleteById(id);
    }

    public List<Items> getNonClaimedItems() {
        return itemsRepo.findByIsClaimedFalse();
    }
}
