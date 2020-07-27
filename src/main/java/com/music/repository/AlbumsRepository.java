package com.music.repository;

import com.music.model.Albums;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface  AlbumsRepository extends MongoRepository<Albums, String> {
}
