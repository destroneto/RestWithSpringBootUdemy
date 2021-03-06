package br.com.erudio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.converter.DozerConverter;
import br.com.erudio.converter.custom.PersonConverter;
import br.com.erudio.data.model.Person;
import br.com.erudio.data.vo.PersonVO;
import br.com.erudio.data.vo.v2.PersonVOv2;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired PersonRepository repository;

	@Autowired PersonConverter personConverter;

	public PersonVO create(PersonVO person) {
		var entity = DozerConverter.parseObject(person, Person.class); 
		return DozerConverter.parseObject(repository.save(entity), PersonVO.class);
	}

	public PersonVOv2 createv2(PersonVOv2 person) {
		var entity = personConverter.convertVOToEntity(person); 
		return personConverter.convertEntityToVO(repository.save(entity));
	}

	public List<PersonVO> findAll() {
		return DozerConverter.parseListObjects(repository.findAll(), PersonVO.class);
	}

	public PersonVO findById(Long id) {		
		return DozerConverter.parseObject(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found")), PersonVO.class);
	}

	public PersonVO update(PersonVO person) {
		var entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());

		return DozerConverter.parseObject(repository.save(entity), PersonVO.class);
	}

	public void delete(Long id) {
		Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found"));
		repository.delete(entity);
	}

	
	
	
}
