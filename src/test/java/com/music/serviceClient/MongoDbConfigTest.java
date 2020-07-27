package com.music.serviceClient;

import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.UnknownHostException;

@Configuration
@EnableMongoRepositories(basePackages = {"com.music.repository"})
public class MongoDbConfigTest {

    @Autowired
    private MongodExecutable mongodExecutable;
    private String ip = "localhost";
    private int port = 27017;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() throws IOException {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory(), mongoConverter());
    }


    @Bean
    public MongodExecutable mongodExecutable() throws IOException, UnknownHostException {
        IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
                .net(new Net(ip, port, Network.localhostIsIPv6()))
                .build();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        MongodExecutable mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();
        return mongodExecutable;
    }

    @Bean
    public MongoDbServiceClient mongoDbServiceClient() {
        return new MongoDbServiceClient();
    }

    @PreDestroy
    void clean() {
        mongodExecutable.stop();
    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {

        final MongoClient build = new MongoClient(ip, port);
        return new SimpleMongoDbFactory(build, "test");
    }

//    @Bean
//    public CustomConversions customConversions() {
//        List<Converter<?, ?>> converters = new ArrayList<Converter<?, ?>>();
//        converters.add(new DateTimeWriterConverter());
//        converters.add(new DateTimeReadingConverter());
//        converters.add(new LocalDateReadingConverter());
//        converters.add(new LocalDateWriterConverter());
//        return new CustomConversions(converters);
//    }

    @Bean
    public MappingMongoConverter mongoConverter() throws Exception {
        MongoMappingContext mappingContext = new MongoMappingContext();
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
        MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mappingContext);
        //  mongoConverter.setCustomConversions(customConversions());
        mongoConverter.afterPropertiesSet();
        return mongoConverter;
    }

}

