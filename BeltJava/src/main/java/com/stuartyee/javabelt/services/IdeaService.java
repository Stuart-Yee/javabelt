package com.stuartyee.javabelt.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stuartyee.javabelt.models.Idea;
import com.stuartyee.javabelt.models.User;
import com.stuartyee.javabelt.repositories.IdeaRepository;
import com.stuartyee.javabelt.repositories.UserRepository;

@Service
public class IdeaService {
	
	IdeaRepository iRepo;
	UserRepository uRepo;
	
	
	
	public IdeaService(IdeaRepository iRepo, UserRepository uRepo) {
		this.iRepo = iRepo;
		this.uRepo = uRepo;
	}



	public List<Idea> findAllIdeas() {
		return iRepo.findAll();
		
	}
	
	public List<Idea> findIdeasByLeastLikes(){
		return iRepo.findAllByOrderByNumberOfLikesAsc();
	}
	
	public List<Idea> findIdeasByMostLikes(){
		return iRepo.findAllByOrderByNumberOfLikesDesc();
	}
	
	public Idea findIdeaById(Long id) {
		if (iRepo.existsById(id)) {
			return iRepo.findById(id).get();
		} else return null;
	}
	
	public void createIdea(User user, Idea idea) {
		idea.setCreator(user);
		user.getIdeas().add(idea);
		iRepo.save(idea);
		uRepo.save(user);
	}
	
	public void like(User user, Idea idea) {
		if(!idea.getLikers().contains(user)) {
			idea.getLikers().add(user);
			idea.setNumberOfLikes(idea.getLikers().size());
			iRepo.save(idea);
			uRepo.save(user);
			System.out.println("User " + user.getName() + "is liking this");
		} else {
			System.out.println("already liked");
		}
	}
	
	public void unlike(User user, Idea idea) {
		if(idea.getLikers().contains(user)) {
			System.out.println(idea.getNumberOfLikes());
			idea.getLikers().remove(user);
			idea.setNumberOfLikes(idea.getLikers().size());
			System.out.println(idea.getNumberOfLikes());
			user.getLikedIdeas().remove(idea);
			iRepo.save(idea);
			uRepo.save(user);
		}
	}
	
	public void editIdea(Idea idea) {
		iRepo.save(idea);
	}
	
	public void deleteIdea(Idea idea) {
		iRepo.delete(idea);
	}

}
