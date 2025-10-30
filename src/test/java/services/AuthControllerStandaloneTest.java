// src/test/java/controllers/AuthControllerStandaloneTest.java
package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;
import teamszg.initialisation_project.controllers.AuthController;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.repositories.IPersonRepository;
import teamszg.initialisation_project.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthControllerStandaloneTest {

    private MockMvc mvc;
    private IPersonRepository personRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    @BeforeEach
    void setup() {
        personRepository = Mockito.mock(IPersonRepository.class);
        passwordEncoder  = Mockito.mock(PasswordEncoder.class);
        jwtService       = Mockito.mock(JwtService.class);

        AuthController controller = new AuthController(personRepository, passwordEncoder, jwtService);

        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(new ObjectMapper()))
                .build();
    }

    @Test
    void login_ok() throws Exception {
        Person p = new Person();
        p.setId(42L);
        p.setEmail("u@test.com");
        p.setPasswordHash("$2a$xx"); // valeur quelconque

        when(personRepository.findByEmail("u@test.com")).thenReturn(Optional.of(p));
        when(passwordEncoder.matches("secret", "$2a$xx")).thenReturn(true);
        when(jwtService.generateToken(42L, "u@test.com")).thenReturn("token-xyz");

        mvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content("{\"email\":\"u@test.com\",\"password\":\"secret\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token-xyz"))
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.expiresIn").value(24*60*60));
    }

    @Test
    void login_bad_password_401() throws Exception {
        Person p = new Person();
        p.setId(1L);
        p.setEmail("x@test.com");
        p.setPasswordHash("hash");

        when(personRepository.findByEmail("x@test.com")).thenReturn(Optional.of(p));
        when(passwordEncoder.matches("wrong", "hash")).thenReturn(false);

        mvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content("{\"email\":\"x@test.com\",\"password\":\"wrong\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void login_email_not_found_401() throws Exception {
        when(personRepository.findByEmail("nobody@test.com")).thenReturn(Optional.empty());

        mvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content("{\"email\":\"nobody@test.com\",\"password\":\"x\"}"))
                .andExpect(status().isUnauthorized());
    }
}
