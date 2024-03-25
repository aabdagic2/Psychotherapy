package nwtprojekat.ArticleManagement.repository;

import nwtprojekat.ArticleManagement.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Integer> {

}
