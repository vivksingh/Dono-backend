package com.example.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.Model.Items;

public interface ItemsRepo extends JpaRepository<Items, Integer>{
    Items findById(int id);
    List<Items> findByIsClaimedFalse();
}
