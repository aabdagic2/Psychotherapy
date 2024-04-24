//package nwtprojekat.ArticleManagement;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
//import jakarta.transaction.Transactional;
//import nwtprojekat.ArticleManagement.config.JPAConfig;
//import nwtprojekat.ArticleManagement.model.Article;
//import nwtprojekat.ArticleManagement.model.Image;
//import nwtprojekat.ArticleManagement.model.Text;
//import nwtprojekat.ArticleManagement.model.Video;
//import nwtprojekat.ArticleManagement.repository.ArticleRepository;
//import nwtprojekat.ArticleManagement.repository.ImageRepository;
//import nwtprojekat.ArticleManagement.repository.TextRepository;
//import nwtprojekat.ArticleManagement.repository.VideoRepository;
//import nwtprojekat.ArticleManagement.service.ArticleService;
//import org.junit.jupiter.api.*;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.*;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.client.MockRestServiceServer;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
//import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest(classes = {ArticleManagementApplication.class, JPAConfig.class})
//@Transactional
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//public class ArticleControllerAdvancedTest {
//
//    @Mock
//    private RestTemplate restTemplate;
//
//    @InjectMocks
//    private ArticleService articleService = new ArticleService();
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    private static ObjectMapper objectMapper;
//    @Mock
//    private Article article;
//
//    @BeforeAll
//    public static void setUp() {
//        objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.registerModule(new ParameterNamesModule());
//    }
//
//
//    @Test
//    public void testGetAllArticles() throws Exception {
//        List<Article> articles = new ArrayList<>();
//        articles.add(new Article("Title 42", "author1", new Text(), new Video(), new Image()));
//
//        when(articleService.getAllArticles()).thenReturn(articles);
//
//        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
//        mockServer.expect(requestTo("http://appointmentservice/psychologists/findUserPsychologist/author1"))
//                .andRespond(withSuccess("{ \"name\": \"Psychologist Name\" }", MediaType.APPLICATION_JSON));
//
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/articles/all"))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String content = result.getResponse().getContentAsString();
//        List<Map<String, Object>> articlesWithPsychologist = objectMapper.readValue(content, new TypeReference<List<Map<String, Object>>>() {});
//        assertEquals(1, articlesWithPsychologist.size());
//
//        Map<String, Object> articleWithPsychologist = articlesWithPsychologist.get(0);
//        assertNotNull(articleWithPsychologist.get("article"));
//        assertNotNull(articleWithPsychologist.get("psychologistDetails"));
//    }
//
//    private static String asJsonString(final Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//}
