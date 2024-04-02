package nwtprojekat.ArticleManagement.controller;

//import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import nwtprojekat.ArticleManagement.exception.ArticleNotFoundException;
import nwtprojekat.ArticleManagement.model.Article;
import nwtprojekat.ArticleManagement.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

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

        Article savedArticle = articleRepository.save(article);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Article added successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Article>> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        if (articles.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(articles);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        return optionalArticle.map(ResponseEntity::ok)
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable Long id, @Valid @RequestBody Article updatedArticle, BindingResult bindingResult) {
        if (!articleRepository.existsById(id)) {
            throw new ArticleNotFoundException(id);
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        updatedArticle.setId(id);
        Article savedArticle = articleRepository.save(updatedArticle);
        return ResponseEntity.ok(savedArticle);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id) {
        if (!articleRepository.existsById(id)) {
            throw new ArticleNotFoundException(id);
        }
        articleRepository.deleteById(id);
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
}

