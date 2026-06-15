package com.todoapp20.TodoApplication;

import com.todoapp20.TodoApplication.Controller.WebController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class WebControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private WebController webController;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(webController).build();
    }

    @Test
    void initiateLogin_ShouldSetSessionAttributeAndRedirect() throws Exception {
        mockMvc.perform(get("/auth/google/login"))
                .andExpect(request().sessionAttribute("AUTH_INTENT", "LOGIN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/oauth2/authorization/google"));
    }

    @Test
    void initiateRegister_ShouldSetSessionAttributeAndRedirect() throws Exception {
        mockMvc.perform(get("/auth/google/register"))
                .andExpect(request().sessionAttribute("AUTH_INTENT", "REGISTER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/oauth2/authorization/google"));
    }

    @Test
    void dashboard_ShouldReturnIndexView() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}