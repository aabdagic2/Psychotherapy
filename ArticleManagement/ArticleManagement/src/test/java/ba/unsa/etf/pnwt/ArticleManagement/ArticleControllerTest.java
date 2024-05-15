package ba.unsa.etf.pnwt.ArticleManagement;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ba.unsa.etf.pnwt.ArticleManagement.model.Video;
import ba.unsa.etf.pnwt.ArticleManagement.repository.ImageRepository;
import ba.unsa.etf.pnwt.ArticleManagement.repository.TextRepository;
import ba.unsa.etf.pnwt.ArticleManagement.repository.VideoRepository;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import jakarta.transaction.Transactional;
import ba.unsa.etf.pnwt.ArticleManagement.config.JPAConfig;
import ba.unsa.etf.pnwt.ArticleManagement.model.Article;
import ba.unsa.etf.pnwt.ArticleManagement.model.Image;
import ba.unsa.etf.pnwt.ArticleManagement.model.Text;
import ba.unsa.etf.pnwt.ArticleManagement.repository.ArticleRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

//@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest(classes = {ArticleManagementApplication.class, JPAConfig.class})
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TextRepository textRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private ImageRepository imageRepository;

    private static ObjectMapper objectMapper;
    @Mock
    private Article article;

    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new ParameterNamesModule());
    }

    @BeforeEach
    public void initialData() {
        article = new Article();
        article.setAuthor("aaaaaaaaa-bbbbbbbb-cccc-ddddd-eeeeeee");
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

    @AfterEach
    public void deleteData() {
        articleRepository.deleteAll();
        textRepository.deleteAll();
        imageRepository.deleteAll();
        videoRepository.deleteAll();
    }

    // GET - metoda dohvatanja samo clanaka, bez autora (psihologa)
    @Test
    public void testGetAllArticlesWithoutAuthors() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/articles/allArticles"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        var articles = objectMapper.readValue(content, Article[].class);

        for (Article articleN : articles) {
            System.out.println(articleN.getId() + articleN.getTitle());
        }

        Assertions.assertEquals(1, articles.length);
    }

    // GET - uspjesno dohvatanje clanaka koji u naslovu imaju keyword
    @Test
    public void testGetAllArticlesWithKeyword() throws Exception {
        var allArticles = articleRepository.findAll();
        String keyword = "Title";

        var foundArticle = allArticles.stream()
                .filter(a -> a.getTitle().contains(keyword))
                .findFirst()
                .orElse(null);

        String foundTitle = foundArticle.getTitle();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/articles/byKeyword/{keyword}", keyword))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Assertions.assertTrue(foundTitle.contains(keyword));
    }


    // GET - uspjesno; nema clanaka koji u naslovu imaju keyword
    @Test
    public void testGetArticlesWithoutKeyword() throws Exception {
        var allArticles = articleRepository.findAll();
        String keyword = "nesto";

        var foundArticle = allArticles.stream()
                .filter(a -> a.getTitle().contains(keyword))
                .findFirst()
                .orElse(null);

        assertNull(foundArticle);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/articles/byKeyword/{keyword}", keyword))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("No articles found containing keyword: " + keyword));
    }

    // POST - uspjesno dodavanje
    @Test
    public void testAddNewArticleWithoutAuthor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/articles/addNew")
                        .content(asJsonString(article))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/articles/allArticles"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Article[] articles = objectMapper.readValue(content, Article[].class);

        boolean articleFound = false;
        for (Article article : articles) {
            if (article.getTitle().equals("Title 1")) {
                articleFound = true;
                break;
            }
        }
        Assertions.assertTrue(articleFound, "New article not found among retrieved articles");
    }

    // POST - neuspjesno dodavanje; izuzetak bacen (naslov prazan)
    @Test
    void testAddNewArticleWithoutAuthorWithEmptyTitle() throws Exception {
        Article article = new Article();
        article.setTitle("");

        mockMvc.perform(MockMvcRequestBuilders.post("/articles/addNew")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(article)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("validation"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("The title must not be empty!"));
    }

    // DELETE - uspjesno brisanje
    @Test
    public void testDeleteExistingArticle() throws Exception {
        var allArticles = articleRepository.findAll();

        String id = allArticles.stream()
                .filter(a -> a.getTitle() == "Title 1")
                .findFirst()
                .get()
                .getId();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/articles/remove/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
               .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertTrue(content.contains("Article with ID " + id + " has been deleted successfully"));
    }

    // DELETE - pogresan id; neuspjesno brisanje
    @Test
    public void testDeleteNonExistentArticle() throws Exception {
        String id = "abcdefghijk";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                        delete("/articles/remove/{id}", id))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertTrue(content.contains("Article with ID " + id + " not found!"));
    }

    // PUT - uspjesna izmjena
    @Test
    public void testSuccessfulUpdateArticle() throws Exception {
        var allArticles = articleRepository.findAll();
        var foundArticle = allArticles.stream().filter(a -> a.getTitle().equals("Title 1")).findFirst().get();

        foundArticle.setTitle("Neki novi testni naslov");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/articles/update/{id}", foundArticle.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(foundArticle)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        var updatedArticle = objectMapper.convertValue(objectMapper.readTree(content), Article.class);

        Assertions.assertEquals("Neki novi testni naslov", updatedArticle.getTitle());
    }

    // PUT - neuspjesna izmjena; pogresan naslov
    @Test
    public void testUpdateArticleWithEmptyTitle() throws Exception {
        var allArticles = articleRepository.findAll();
        String id = allArticles.stream()
                .filter(a -> a.getTitle() == "Title 1")
                .findFirst()
                .get()
                .getId();

        Article updatedArticle = new Article("", "A-B-C", new Text(), new Video(), new Image());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/articles/update/{id}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updatedArticle)))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertTrue(content.contains("The title must not be empty!"));
    }

    // PUT - neuspjesna izmjena; ID ne postoji
    @Test
    public void testUpdateNonExistentArticle() throws Exception {
        String id = "abcdefghijk";
        Article updatedArticle = new Article("", "A-B-C", new Text(), new Video(), new Image());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/articles/update/{id}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updatedArticle)))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertTrue(content.contains("Article with ID " + id + " not found!"));
    }


//    @Test
//    public void testGetArticlesAndAuthors() throws Exception {
//
//    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

