package nwtprojekat.ArticleManagement.controller;

import nwtprojekat.ArticleManagement.model.Article;
import nwtprojekat.ArticleManagement.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        // Logika za kreiranje članka
        Article savedArticle = articleRepository.save(article);
        return ResponseEntity.ok(savedArticle);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        // Logika za dohvatanje članka po ID-u
        Optional<Article> optionalArticle = articleRepository.findById(id);
        return optionalArticle.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Article updatedArticle) {
        // Logika za ažuriranje članka
        if (!articleRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedArticle.setId(id); // Postavljanje ID-a članka
        Article savedArticle = articleRepository.save(updatedArticle);
        return ResponseEntity.ok(savedArticle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        // Logika za brisanje članka
        if (!articleRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        articleRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

