package nwtprojekat.ArticleManagement.controller;

//import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import nwtprojekat.ArticleManagement.exception.ArticleNotFoundException;
import nwtprojekat.ArticleManagement.model.Article;
import nwtprojekat.ArticleManagement.repository.ArticleRepository;
import nwtprojekat.ArticleManagement.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/articles")
@RefreshScope
public class ArticleController {


    @Autowired
    private ArticleService articleService;

    @PostMapping("/add")
    public ResponseEntity<?> createArticle(@RequestBody @Valid Article article, BindingResult bindingResult) {
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
    }

    // OK
    @GetMapping("/all")
    public ResponseEntity<List<Article>> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        if (articles.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(articles);
        }
    }

    // OK
    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        Article article = articleService.getArticleById(id);
        return ResponseEntity.ok(article);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable Long id, @Valid @RequestBody Article updatedArticle, BindingResult bindingResult) {
        if (!articleService.existsArticleById(id)) {
            throw new ArticleNotFoundException(id);
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        updatedArticle.setId(id);
        Article savedArticle = articleService.updateArticle(updatedArticle);
        return ResponseEntity.ok(savedArticle);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticleById(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Article with ID " + id + " has been deleted successfully");
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<?> handleArticleNotFoundException(ArticleNotFoundException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", e.getError());
        errorResponse.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @GetMapping("/byAuthor/{authorName}")
    public ResponseEntity<?> getArticlesByAuthor(@PathVariable String authorName) {
        List<Article> articles = articleService.findArticlesByAuthor(authorName);
        Map<String, Object> response = new HashMap<>();
        if (articles.isEmpty()) {
            response.put("message", "No articles found for author: " + authorName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response.put("articles", articles);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/byKeyword/{keyword}")
    public ResponseEntity<?> getArticlesByKeyword(@PathVariable String keyword) {
        List<Article> articles = articleService.findArticlesByKeyword(keyword);
        Map<String, Object> response = new HashMap<>();
        if (articles.isEmpty()) {
            response.put("message", "No articles found containing keyword: " + keyword);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response.put("articles", articles);
        return ResponseEntity.ok(response);
    }
}

