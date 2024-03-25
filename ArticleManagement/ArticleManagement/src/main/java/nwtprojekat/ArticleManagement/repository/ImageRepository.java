package nwtprojekat.ArticleManagement.repository;

import nwtprojekat.ArticleManagement.model.Article;
import nwtprojekat.ArticleManagement.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
