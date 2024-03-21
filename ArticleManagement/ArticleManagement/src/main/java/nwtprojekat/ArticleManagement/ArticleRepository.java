package nwtprojekat.ArticleManagement;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import nwtprojekat.ArticleManagement.Models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Integer> {

}
