package com.zbay.eventsjava.repositories;

import org.springframework.data.repository.CrudRepository;

import com.zbay.eventsjava.models.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {

}
