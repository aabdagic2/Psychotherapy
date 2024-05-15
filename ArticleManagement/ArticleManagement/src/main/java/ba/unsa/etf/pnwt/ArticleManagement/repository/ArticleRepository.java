package ba.unsa.etf.pnwt.ArticleManagement.repository;

import ba.unsa.etf.pnwt.ArticleManagement.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, String> {
    List<Article> findByAuthor(String authorName);

    @Query("SELECT a FROM Article a WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Article> findByKeywordInTitle(@Param("keyword") String keyword);
}
