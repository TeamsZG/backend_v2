package services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import teamszg.initialisation_project.security.JwtService;

import javax.crypto.SecretKey;
import java.lang.reflect.Field;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setup() throws Exception {
        jwtService = new JwtService();

        // Prépare une clé HS256 et une expiration courte
        SecretKey key = Keys.hmacShaKeyFor("this_is_a_very_long_test_key_for_hs256_32bytes_min!!".getBytes());

        Field fKey = JwtService.class.getDeclaredField("key");
        fKey.setAccessible(true);
        fKey.set(jwtService, key);

        Field fExp = JwtService.class.getDeclaredField("expirationMs");
        fExp.setAccessible(true);
        fExp.set(jwtService, 60_000L); // 1 minute
    }

    @Test
    void generate_and_parse_ok() {
        String token = jwtService.generateToken(99L, "foo@bar.com");
        assertNotNull(token);

        Claims claims = jwtService.parseClaims(token);
        assertEquals("foo@bar.com", claims.getSubject());
        assertEquals(99, ((Number) claims.get("uid")).intValue());

        Date exp = claims.getExpiration();
        assertTrue(exp.after(new Date()));
    }

    @Test
    void generate_and_parse_token() {
        JwtService jwt = new JwtService();

        String token = jwt.generateToken(123L, "user@example.com");
        assertNotNull(token);
        assertTrue(token.length() > 10);

        Claims claims = jwt.parseClaims(token);
        assertEquals("user@example.com", claims.getSubject());
        assertEquals(123, ((Number)claims.get("uid")).longValue());

        assertNotNull(claims.getExpiration());
        assertTrue(claims.getExpiration().getTime() > System.currentTimeMillis());
    }


}
