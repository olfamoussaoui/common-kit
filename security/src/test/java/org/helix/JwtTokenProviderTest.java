package org.helix;

import org.helix.configuration.JwtTokenProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        JwtTokenProperties properties = new JwtTokenProperties();
        properties.setSecret("thisIsASecretKeyForTestingPurposesOnly1234567890");
        properties.setExpirationMs(3600000); // 1 hour

        jwtTokenProvider = new JwtTokenProvider(properties);
        jwtTokenProvider.init();
    }

    @Test
    void generateTokenAndValidateShouldBeValid() {
        String token = jwtTokenProvider.generateToken("user1", List.of("USER"), Map.of("customClaim", "customValue"));

        assertNotNull(token);
        assertTrue(jwtTokenProvider.validateToken(token));

        String subject = jwtTokenProvider.getSubject(token);
        assertEquals("user1", subject);

        List<String> roles = jwtTokenProvider.getRoles(token);
        assertEquals(List.of("USER"), roles);
    }

    @Test
    void validateToken_withInvalidTokenShouldReturnFalse() {
        String invalidToken = "invalid.jwt.token";

        assertFalse(jwtTokenProvider.validateToken(invalidToken));
    }

    @Test
    void getRoles_whenNoRolesClaimShouldReturnEmptyList() {
        String token = jwtTokenProvider.generateToken("user2", List.of(), Map.of());

        List<String> roles = jwtTokenProvider.getRoles(token);
        assertTrue(roles.isEmpty());
    }
}
