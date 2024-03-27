package nwtprojekat.ArticleManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import nwtprojekat.ArticleManagement.controller.ArticleController;
import nwtprojekat.ArticleManagement.model.Article;
import nwtprojekat.ArticleManagement.model.Image;
import nwtprojekat.ArticleManagement.model.Text;
import nwtprojekat.ArticleManagement.model.Video;
import nwtprojekat.ArticleManagement.repository.ArticleRepository;
import nwtprojekat.ArticleManagement.repository.ImageRepository;
import nwtprojekat.ArticleManagement.repository.TextRepository;
import nwtprojekat.ArticleManagement.repository.VideoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private TextRepository textRepository;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ArticleController articleController;

    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new ParameterNamesModule());
    }

    @BeforeEach
    public void initialData() {
        Article article = new Article();
        article.setAuthor("Author 1");
        article.setTitle("Title 1");

        Text textSection = new Text();
        textSection.setContent("Text 1");

        Video videoSection = new Video();
        videoSection.setVideoUrl("Video 1");

        Image imageSection = new Image();
        imageSection.setImageUrl("Image 1");

        article.setText(textSection);
        article.setImage(imageSection);
        article.setVideo(videoSection);

        articleRepository.save(article);

        textSection.setArticle(article);
        textRepository.save(textSection);
        videoSection.setArticle(article);
        videoRepository.save(videoSection);
        imageSection.setArticle(article);
        imageRepository.save(imageSection);
    }
    @Test
    public void testGetAllArticles() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/articles/all"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        var articles = objectMapper.convertValue(objectMapper.readTree(content), Article[].class);

        Assertions.assertEquals(1, articles.length);
    }

    /*@Test
    public void testGetArticleByID() throws Exception {
        var allArticles = articleRepository.findAll();
        var id = allArticles.stream().findFirst().get().getId();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/articles/{id}",id))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        var article = objectMapper.convertValue(objectMapper.readTree(content), Article.class);

        Assertions.assertNotNull(article);
    }*/

    @Test
    public void addNewArticle() throws Exception {
        Text textSection = new Text();
        textSection.setContent("Text 2");
        Video videoSection = new Video();
        videoSection.setVideoUrl("Video 2");
        Image imageSection = new Image();
        imageSection.setImageUrl("Image 2");
        Article newArticle = new Article("Title 2", "Author 2", textSection, videoSection, imageSection);

        mockMvc.perform(MockMvcRequestBuilders.post("/articles/add")
                        .content(asJsonString(newArticle))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/articles/all"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Article[] articles = objectMapper.readValue(content, Article[].class);

        boolean articleFound = false;
        for (Article article : articles) {
            if (article.getTitle().equals("Title 2")) {
                articleFound = true;
                break;
            }
        }
        Assertions.assertTrue(articleFound, "New article not found among retrieved articles");
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

