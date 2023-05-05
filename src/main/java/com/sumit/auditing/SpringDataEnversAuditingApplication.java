package com.sumit.auditing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class SpringDataEnversAuditingApplication {

	@Autowired
	private BookRepo bookRepo;
	
	@PostMapping("/saveBook")
	public Book saveBook(@RequestBody Book book) {
		return bookRepo.save(book);
	}
	
	@PutMapping("/update/{id}/{pages}")
	public String updateBook(@PathVariable int id, @PathVariable int pages) {
		Book book=bookRepo.findById(id).get();
		
		book.setPages(pages);
		bookRepo.save(book);
		
		return "Book updated....";
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteBook(@PathVariable int id) {
		bookRepo.deleteById(id);
		return "book deleted";
	}
	
	@GetMapping("/getinfo/{id}")
	public void getInfo(@PathVariable int id) {
		System.out.println(bookRepo.findLastChangeRevision(id));
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringDataEnversAuditingApplication.class, args);
	}

}
