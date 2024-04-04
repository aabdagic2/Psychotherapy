package nwtprojekat.ArticleManagement.repository;

import nwtprojekat.ArticleManagement.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByAuthor(String authorName);
}
