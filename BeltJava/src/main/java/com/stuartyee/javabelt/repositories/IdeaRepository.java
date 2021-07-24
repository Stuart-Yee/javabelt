package com.stuartyee.javabelt.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.stuartyee.javabelt.models.Idea;

public interface IdeaRepository extends CrudRepository<Idea, Long> {
	
	public List<Idea> findAllByOrderByNumberOfLikesAsc();
	public List<Idea> findAllByOrderByNumberOfLikesDesc();
	public List<Idea> findAll();
	public Optional<Idea> findById(Long id);
	

}
