package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import teamszg.initialisation_project.InitialisationProjectApplication;
import teamszg.initialisation_project.controllers.AuthController;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.repositories.IPersonRepository;
import teamszg.initialisation_project.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthController.class)
@ContextConfiguration(classes = InitialisationProjectApplication.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerWebTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean IPersonRepository personRepository;
    @MockBean PasswordEncoder passwordEncoder;
    @MockBean JwtService jwtService;

    @Test
    void login_success_returnsToken() throws Exception {
        Person p = new Person();
        p.setId(42L);
        p.setEmail("user@test.com");
        p.setPasswordHash("$2a$10$hash"); // simulé

        when(personRepository.findByEmail("user@test.com")).thenReturn(Optional.of(p));
        when(passwordEncoder.matches("secret", "$2a$10$hash")).thenReturn(true);
        when(jwtService.generateToken(42L, "user@test.com")).thenReturn("jwt-token");

        Map<String, String> body = Map.of("email", "user@test.com", "password", "secret");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token"))
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.expiresIn").value(24*60*60));
    }

    @Test
    void login_badPassword_401() throws Exception {
        Person p = new Person();
        p.setId(1L);
        p.setEmail("x@y.z");
        p.setPasswordHash("hash");

        when(personRepository.findByEmail("x@y.z")).thenReturn(Optional.of(p));
        when(passwordEncoder.matches("wrong", "hash")).thenReturn(false);

        Map<String, String> body = Map.of("email", "x@y.z", "password", "wrong");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isUnauthorized());
    }
}
