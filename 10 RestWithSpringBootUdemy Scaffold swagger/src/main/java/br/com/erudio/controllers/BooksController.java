package br.com.erudio.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.v1.BooksVO;
import br.com.erudio.services.BooksService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "BooksEndpoint")
@RestController
@RequestMapping("/api/books/v1")
public class BooksController {

	@Autowired
	BooksService services;

	@ApiOperation(value = "Find all people from database")
	@GetMapping(produces = {"application/json", "application/xml", "application/x-yaml" })
	public List<BooksVO> findAll() {
		var books = services.findAll();
		books.stream().forEach(b -> b.add(linkTo(methodOn(BooksController.class).findById(b.getKey())).withSelfRel()));
		return books;
	}

	@ApiOperation(value = "Find one book by identifier")
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public BooksVO findById(@PathVariable("id") Long id) {
		var BooksVO = services.findById(id);
		BooksVO.add(linkTo(methodOn(BooksController.class).findById(id)).withSelfRel());
		return BooksVO;
	}

	@ApiOperation(value = "Save a book")
	@PostMapping(produces = { "application/json", "application/xml","application/x-yaml" },
			consumes = { "application/json", "application/xml","application/x-yaml" })
	public BooksVO create(@RequestBody BooksVO book) {
		var bookVO = services.create(book);
		bookVO.add(linkTo(methodOn(BooksController.class).findById(bookVO.getKey())).withSelfRel());
		return bookVO;
	}

	@ApiOperation(value = "Update book's datas")
	@PutMapping(produces = { "application/json", "application/xml", "application/x-yaml" },
			consumes = { "application/json", "application/xml", "application/x-yaml" })
	public BooksVO update(@RequestBody BooksVO book) {
		var bookVO = services.update(book);
		bookVO.add(linkTo(methodOn(BooksController.class).findById(bookVO.getKey())).withSelfRel());
		return bookVO;
	}

	@ApiOperation(value = "Remove a book by identifier")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		services.delete(id);
		return ResponseEntity.ok().build();
	}
}
