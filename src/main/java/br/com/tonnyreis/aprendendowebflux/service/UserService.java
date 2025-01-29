package br.com.tonnyreis.aprendendowebflux.service;

import br.com.tonnyreis.aprendendowebflux.entity.User;
import br.com.tonnyreis.aprendendowebflux.mapper.UserMapper;
import br.com.tonnyreis.aprendendowebflux.model.request.UserRequest;
import br.com.tonnyreis.aprendendowebflux.repository.UserRepository;
import br.com.tonnyreis.aprendendowebflux.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@Service
public class UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    @Autowired
    public UserService(UserRepository repository, @Qualifier("userMapperImpl") UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Mono<User> save(final UserRequest request) {
        return repository.save(mapper.toEntity(request));
    }

    public Mono<User> findById(final String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ObjectNotFoundException(
                                format("Object not found. Id: %s, Type: %s", id, User.class.getSimpleName()))
                        )
                );
    }

    public Flux<User> findAll() {
        return repository.findAll();
    }

    public Mono<User> update(final String id, final UserRequest request) {
        return findById(id)
                .map(entity -> mapper.toEntity(request, entity))
                .flatMap(repository::save);
    }
}
