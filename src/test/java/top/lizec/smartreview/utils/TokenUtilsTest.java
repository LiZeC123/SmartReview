package top.lizec.smartreview.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import top.lizec.smartreview.entity.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TokenUtilsTest {
    private static User user;


    @BeforeAll
    static void createUser() {
        user = new User();
        user.setUsername("Test UserName");
        user.setEmail("test@example.com");
        user.setRoles("ROLE_USER");
    }

    @Test
    void createToken() {
        String token = TokenUtils.createToken(user);
        assertNotNull(token);
        System.out.println(token);
    }

    @Test
    void validationToken() {
        String token = TokenUtils.createToken(user);
        token = token.replace("Bearer ", "");
        User tokenUser = TokenUtils.validationToken(token);
        assertNotNull(tokenUser);
        System.out.println(tokenUser);
        assertEquals(user.getUsername(), tokenUser.getUsername());
        assertEquals(user.getEmail(), tokenUser.getEmail());
        assertEquals(user.getRoles(), tokenUser.getRoles());
    }
}