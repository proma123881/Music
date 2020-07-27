package com.music.config;

import com.music.repository.AlbumRepository;
import com.music.repository.ArtistRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/** Mongo db configuration class.
 * @author Proma Chowdhury
 * @version 1.0
 */
@Configuration
@EnableMongoRepositories(basePackageClasses = { ArtistRepository.class , AlbumRepository.class})
public class MongoConfig {


    }
