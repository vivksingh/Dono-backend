package com.example.backend.Service;

import java.sql.Blob;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.backend.Model.Users;
import com.example.backend.Repository.UsersRepo;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    
    @Autowired
    private UsersRepo repo;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Transactional
    public Users findByUsername(String username){
        return repo.findByUsername(username);
    }

    public boolean existsByUsername(String username){
        return repo.existsByUsername(username);
    }

    @Transactional // Ensures the method runs within a transaction
    public Users registerUser(Users user, MultipartFile profile_pic) {
        try {
            byte[] imageBytes = profile_pic.getBytes();
            Blob imageBlob = new SerialBlob(imageBytes); // Convert byte[] to Blob
            user.setImage(imageBlob);

            user.setImageName(profile_pic.getOriginalFilename());
            user.setImageType(profile_pic.getContentType());
            user.setPassword(encoder.encode(user.getPassword()));
            user.setDonated(0);
            user.setReceived(0);
            user.setBlessings(0);

            return repo.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error during user registration: " + e.getMessage(), e);
        }
    }

    public List<Users> getAllUsers(){
        return repo.findAll();
    }
}
