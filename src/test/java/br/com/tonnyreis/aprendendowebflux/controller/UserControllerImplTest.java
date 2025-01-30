package br.com.tonnyreis.aprendendowebflux.controller;

import br.com.tonnyreis.aprendendowebflux.entity.User;
import br.com.tonnyreis.aprendendowebflux.mapper.UserMapper;
import br.com.tonnyreis.aprendendowebflux.model.request.UserRequest;
import br.com.tonnyreis.aprendendowebflux.model.response.UserResponse;
import br.com.tonnyreis.aprendendowebflux.service.UserService;
import com.mongodb.reactivestreams.client.MongoClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
class UserControllerImplTest {

    @MockBean
    private UserService service;

    @MockBean
    private UserMapper mapper;

    @MockBean
    private MongoClient mongoClient;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Testando save com sucesso")
    void testSaveSuccess() {
        final var request = new UserRequest("name", "test@mail.com", "123");

        when(service.save(ArgumentMatchers.any(UserRequest.class))).thenReturn(Mono.just(User.builder().build()));

        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus()
                .isCreated();

        Mockito.verify(service, times(1)).save(any(UserRequest.class));
    }

    @Test
    @DisplayName("Testando save com badRequest")
    void testSaveBadRequest() {
        final var request = new UserRequest(" name", "test@mail.com", "123");

        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.path").isEqualTo("/users")
                .jsonPath("$.status").isEqualTo(HttpStatus.BAD_REQUEST.value())
                .jsonPath("$.error").isEqualTo("Validation error")
                .jsonPath("$.message").isEqualTo("Error on validation attributes")
                .jsonPath("$.errors[0].fieldName").isEqualTo("name")
                .jsonPath("$.errors[0].message").isEqualTo("campo não deve ter espaços em branco no início e no final");

    }

    @Test
    @DisplayName("testando findById success")
    void testFindByIdSuccess() {

        final var id = "12345";
        final var userResponse = new UserResponse(id, "name", "test@mail.com", "123");

        when(service.findById(ArgumentMatchers.any(String.class))).thenReturn(Mono.just(User.builder().build()));
        when(mapper.toResponse(any(User.class))).thenReturn(userResponse);

        webTestClient.get().uri("/users/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.name").isEqualTo("name")
                .jsonPath("$.email").isEqualTo("test@mail.com")
                .jsonPath("$.password").isEqualTo("123");
    }

//    @Test
    void findAll() {
    }

//    @Test
    void update() {
    }

//    @Test
    void delete() {
    }
}