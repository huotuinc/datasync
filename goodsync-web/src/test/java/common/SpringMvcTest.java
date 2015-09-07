package common;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * Created by liual on 2015-09-07.
 */
public class SpringMvcTest {
    @Autowired
    protected WebApplicationContext context;
    @Autowired
    protected ServletContext servletContext;
    @Autowired
    protected MockHttpServletRequest request;
    @Autowired
    protected MockHttpSession mockHttpSession;

    protected MockMvc mockMvc;

    @Before
    public void createMockMVC() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = ((DefaultMockMvcBuilder) MockMvcBuilders.webAppContextSetup(this.context)).build();
    }
}
