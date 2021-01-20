package br.com.erudio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.converter.DozerConverter;
import br.com.erudio.data.model.Books;
import br.com.erudio.data.vo.v1.BooksVO;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.repository.BooksRepository;

@Service
public class BooksService {

	@Autowired
	BooksRepository repository;

	public BooksVO create(BooksVO Books) {
		var entity = DozerConverter.parseObject(Books, Books.class); 
		return DozerConverter.parseObject(repository.save(entity), BooksVO.class);
	}

	public List<BooksVO> findAll() {
		return DozerConverter.parseListObjects(repository.findAll(), BooksVO.class);
	}

	public BooksVO findById(Long id) {		
		return DozerConverter.parseObject(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found")), BooksVO.class);
	}

	public BooksVO update(BooksVO Books) {
		var entity = repository.findById(Books.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found"));

		entity.setAuthor(Books.getAuthor());
		entity.setLaunchDate(Books.getLaunchDate());
		entity.setPrice(Books.getPrice());
		entity.setTitle(Books.getTitle());

		return DozerConverter.parseObject(repository.save(entity), BooksVO.class);
	}

	public void delete(Long id) {
		Books entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found"));
		repository.delete(entity);
	}

	
	
	
}
