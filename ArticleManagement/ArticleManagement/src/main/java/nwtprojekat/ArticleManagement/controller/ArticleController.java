package nwtprojekat.ArticleManagement.controller;

//import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import nwtprojekat.ArticleManagement.exception.ArticleNotFoundException;
import nwtprojekat.ArticleManagement.model.Article;
import nwtprojekat.ArticleManagement.repository.ArticleRepository;
import nwtprojekat.ArticleManagement.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/articles")
@RefreshScope
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private RestTemplate restTemplate;
    @PostMapping("/add")
    public ResponseEntity<?> createArticle(@RequestBody @Valid Article article, BindingResult bindingResult) {
        try {
            String url = "http://appointmentservice/psychologists/find/" + article.getAuthor();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.ALL));
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response1 = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if(response1.getStatusCode().is2xxSuccessful()) {
                if (bindingResult.hasErrors()) {
                    FieldError titleError = bindingResult.getFieldError("title");
                    if (titleError != null) {
                        Map<String, String> errorResponse = new HashMap<>();
                        errorResponse.put("error", "validation");
                        errorResponse.put("message", titleError.getDefaultMessage());
                        return ResponseEntity.badRequest().body(errorResponse);
                    }
                    return ResponseEntity.badRequest().body("Invalid data: " + bindingResult.getAllErrors());
                }

                Article savedArticle = articleService.createArticle(article);
                Map<String, String> response = new HashMap<>();
                response.put("message", "Article added successfully");
                return ResponseEntity.ok(response);
            } else {
                return response1;
            }
        } catch (HttpClientErrorException.NotFound ex) {
            // Handle 404 Not Found error
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    // OK
    @GetMapping("/all")
    public ResponseEntity<?> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        List<Map<String, Object>> articlesWithPsychologist = new ArrayList<>();

        if (articles.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            for (Article article : articles) {
                try {
                    String url = "http://appointmentservice/psychologists/findUserPsychologist/" + article.getAuthor();
                    HttpHeaders headers = new HttpHeaders();
                    headers.setAccept(Collections.singletonList(MediaType.ALL));
                    HttpEntity<?> entity = new HttpEntity<>(headers);
                    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

                    if (response.getStatusCode().is2xxSuccessful()) {
                        String psychologistDetails = response.getBody();
                        Map<String, Object> articleWithPsychologist = new HashMap<>();
                        articleWithPsychologist.put("article", article);
                        articleWithPsychologist.put("psychologistDetails", psychologistDetails);
                        articlesWithPsychologist.add(articleWithPsychologist);
                    } else {
                        System.out.println("Failed to fetch psychologist info for article: " + article.getId());
                    }
                } catch (Exception e) {
                    System.out.println("An error occurred while fetching psychologist info for article: " + article.getId());
                    e.printStackTrace();
                }
            }
        }
        return ResponseEntity.ok(articlesWithPsychologist);
    }

//    // OK
//    @GetMapping("/{id}")
//    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
//        Article article = articleService.getArticleById(id);
//        return ResponseEntity.ok(article);
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<?> updateArticle(@PathVariable Long id, @Valid @RequestBody Article updatedArticle, BindingResult bindingResult) {
//        if (!articleService.existsArticleById(id)) {
//            throw new ArticleNotFoundException(id);
//        }
//
//        if (bindingResult.hasErrors()) {
//            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
//        }
//
//        updatedArticle.setId(id);
//        Article savedArticle = articleService.updateArticle(updatedArticle);
//        return ResponseEntity.ok(savedArticle);
//    }
//
//    @DeleteMapping("/remove/{id}")
//    public ResponseEntity<?> deleteArticle(@PathVariable Long id) {
//        articleService.deleteArticleById(id);
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "Article with ID " + id + " has been deleted successfully");
//        return ResponseEntity.ok(response);
//    }
//
//    @ExceptionHandler(ArticleNotFoundException.class)
//    public ResponseEntity<?> handleArticleNotFoundException(ArticleNotFoundException e) {
//        Map<String, String> errorResponse = new HashMap<>();
//        errorResponse.put("error", e.getError());
//        errorResponse.put("message", e.getMessage());
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
//    }
//    @GetMapping("/byAuthor/{authorName}")
//    public ResponseEntity<?> getArticlesByAuthor(@PathVariable String authorName) {
//        List<Article> articles = articleService.findArticlesByAuthor(authorName);
//        Map<String, Object> response = new HashMap<>();
//        if (articles.isEmpty()) {
//            response.put("message", "No articles found for author: " + authorName);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//        }
//        response.put("articles", articles);
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/byKeyword/{keyword}")
//    public ResponseEntity<?> getArticlesByKeyword(@PathVariable String keyword) {
//        List<Article> articles = articleService.findArticlesByKeyword(keyword);
//        Map<String, Object> response = new HashMap<>();
//        if (articles.isEmpty()) {
//            response.put("message", "No articles found containing keyword: " + keyword);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//        }
//        response.put("articles", articles);
//        return ResponseEntity.ok(response);
//    }
}

