package br.com.tonnyreis.aprendendowebflux.repository;

import br.com.tonnyreis.aprendendowebflux.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final ReactiveMongoTemplate mongoTemplate;

    public Mono<User> save(final User user){
        return mongoTemplate.save(user);
    }

    public Mono<User> findById(String id) {
        return mongoTemplate.findById(id, User.class);
    }

    public Flux<User> findAll() {
        return mongoTemplate.findAll(User.class);
    }

    public Mono<User> findAndRemove(String id) {
        var query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findAndRemove(query, User.class);
    }
}
