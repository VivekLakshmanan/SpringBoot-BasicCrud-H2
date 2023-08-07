package com.example.VivekCrud.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.VivekCrud.model.Book;
import com.example.VivekCrud.repository.BookRepo;

@RestController
public class BookController {

	@Autowired
	BookRepo repo;

	@GetMapping("/getAllBooks")
	public ResponseEntity<List<Book>> getAllBooks() {
		try {

			List<Book> bookList = new ArrayList<Book>();
			repo.findAll().forEach(bookList::add);

			if (bookList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(bookList,HttpStatus.OK);

		} catch (Exception E) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/getBookById/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable Long id) {
		
	  Optional<Book> bookdData = repo.findById(id);
	  
	  if(bookdData.isPresent()) {
		  return new ResponseEntity<Book>(bookdData.get(), HttpStatus.OK);
	  }
	  
	  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@PostMapping("/addBook")
	public ResponseEntity<Book> addBook(@RequestBody Book book) {
		Book bookObj = repo.save(book);
		
		return new ResponseEntity<>(bookObj,HttpStatus.OK);

	}

	@PostMapping("/updateBookById/{id}")
	public ResponseEntity<Book> updateBookById(@PathVariable Long id, @RequestBody Book newBookDetails) {
		
		Optional<Book> oldBookData = repo.findById(id);
		if(oldBookData.isPresent())
		{
			Book updatedBookData = oldBookData.get();
			updatedBookData.setTitle(newBookDetails.getTitle());
			updatedBookData.setAuthor(newBookDetails.getAuthor());
			
			Book book = repo.save(updatedBookData);
			
			return new ResponseEntity<>(book,HttpStatus.OK);
			
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	

	@DeleteMapping("/deleteBookById/{id}")
	public ResponseEntity<HttpStatus> deleteBookById(@PathVariable Long id) {
		repo.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
