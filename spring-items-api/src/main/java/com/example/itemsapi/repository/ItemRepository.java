package com.example.itemsapi.repository;

import com.example.itemsapi.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    // JpaRepository provides findAll(), findById(), save(), delete(), etc. out of the box
}
