package com.example.VivekCrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.VivekCrud.model.Book;

@Repository
public interface BookRepo extends JpaRepository<Book,Long > {

}
