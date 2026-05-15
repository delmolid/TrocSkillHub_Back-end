package RNCP.TrocSkillHub.Users;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import jakarta.servlet.http.Cookie;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UsersApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private Cookie jwtCookie;

    @BeforeEach
    void setUp() throws Exception {
    
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "nom": "Dupont",
                      "prenom": "Jean",
                      "email": "jean.dupont@test.fr",
                      "password": "Password1!",
                      "city": "Paris",
                      "country": "France"
                    }
                    """));

        MvcResult loginResult = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "email": "jean.dupont@test.fr",
                      "password": "Password1!"
                    }
                    """))
                .andExpect(status().isOk())
                .andReturn();

        jwtCookie = loginResult.getResponse().getCookie("jwt");
    }

    @Test
    void getAllUsers_withJwt_returnsUsersWithNames() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/users").cookie(jwtCookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.email == 'jean.dupont@test.fr')].firstName").value("Jean"))
                .andExpect(jsonPath("$[?(@.email == 'jean.dupont@test.fr')].lastName").value("Dupont"))
                .andReturn();
    
       
        System.out.println("Réponse GET /api/users :\n" + result.getResponse().getContentAsString());
    }

    @Test
    void getAllUsers_withoutJwt_returns403() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isForbidden());
            
                
    }
}
