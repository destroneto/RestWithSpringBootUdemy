package br.com.erudio.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@Autowired
	private PagedResourcesAssembler<BooksVO> assembler;

	@ApiOperation(value = "Find all people from database")
	@GetMapping(produces = {"application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {

		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "title"));

		Page<BooksVO> books = services.findAll(pageable);
		books.stream().forEach(b -> b.add(linkTo(methodOn(BooksController.class).findById(b.getKey())).withSelfRel()));
		return new ResponseEntity<>(assembler.toResource(books), HttpStatus.OK);
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
