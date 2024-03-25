package nwtprojekat.ArticleManagement.repository;

import nwtprojekat.ArticleManagement.model.Article;
import nwtprojekat.ArticleManagement.model.Text;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextRepository extends JpaRepository<Text, Long> {
}
