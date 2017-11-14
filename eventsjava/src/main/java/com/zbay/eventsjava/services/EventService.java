package com.zbay.eventsjava.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.zbay.eventsjava.models.Event;
import com.zbay.eventsjava.models.Message;
import com.zbay.eventsjava.models.User;
import com.zbay.eventsjava.repositories.EventRepository;
import com.zbay.eventsjava.repositories.MessageRepository;
import com.zbay.eventsjava.repositories.UserRepository;

@Service
public class EventService {
	private EventRepository eventRepository;
	private UserRepository userRepository;
	private MessageRepository messageRepository;
	
	public EventService(EventRepository eventRepository, UserRepository userRepository, MessageRepository messageRepository) {
		this.eventRepository = eventRepository;
		this.userRepository = userRepository;
		this.messageRepository = messageRepository;
	}
	
	public List<Event> getAll(){
		return this.eventRepository.findAll();
	}
	
	/*public List<Event> getEventsInState(String state){
		return this.eventRepository.findEventsInState(state);
	}
	
	public List<Event> getEventsExcludingState(String state){
		return this.eventRepository.findEventsInState(state);
	}*/
	
	public void deleteEvent(Long id) {
		this.eventRepository.delete(id);
	}
	
	public void saveEvent(User user, Event event) {
		event.setHost(user);
		List<User> joiners = new ArrayList<>();
		joiners.add(user);
		event.setJoiners(joiners);
		this.eventRepository.save(event);
	}
	
	public void editEvent(Long id, Event changedEvent) {
		Event original = this.eventRepository.findOne(id);
		original.setName(changedEvent.getName());
		original.setDate(changedEvent.getDate());
		original.setCity(changedEvent.getCity());
		original.setState(changedEvent.getState());
		this.eventRepository.save(original);
	}
	
	public void joinEvent(User user, Long eventID) {
		List<Event> joinedEvents = user.getJoinedEvents();
		Event event = this.eventRepository.findOne(eventID);
		if(!joinedEvents.contains(event)) {
			joinedEvents.add(event);
			user.setJoinedEvents(joinedEvents);
			this.userRepository.save(user);
		}
	}
	
	public void leaveEvent(User user, Long eventID) {
		List<Event> joinedEvents = user.getJoinedEvents();
		Event event = this.eventRepository.findOne(eventID);
		int indexOfEvent = joinedEvents.indexOf(event);
		if(indexOfEvent > -1 && !event.getHost().equals(user)) { // if the event is in the arraylist and the user isn't the host
			joinedEvents.remove(indexOfEvent);
		}
		user.setJoinedEvents(joinedEvents);
		this.userRepository.save(user);	
	}
	
	public Event getEventById(Long eventID) {
		return this.eventRepository.findOne(eventID);
	}
	
	public void addMessage(Message message, Long eventID, User user) {
		Event event = this.eventRepository.findOne(eventID);
		message.setEvent(event);
		message.setAuthor(user);
		this.messageRepository.save(message);
	}
}
