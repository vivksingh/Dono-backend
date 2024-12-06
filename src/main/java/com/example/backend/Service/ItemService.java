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
        return itemsRepo.findById(id).orElse(null);
    }

    public Items addItem(Items item, MultipartFile imageFile) throws Exception {
        item.setImage(imageFile.getBytes());
        item.setImageName(imageFile.getOriginalFilename());
        item.setImageType(imageFile.getContentType());
        item.setClaimed(false);
        return itemsRepo.save(item);
    }
    

}
