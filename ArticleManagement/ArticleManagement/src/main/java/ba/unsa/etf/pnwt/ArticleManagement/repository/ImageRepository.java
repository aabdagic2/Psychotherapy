package ba.unsa.etf.pnwt.ArticleManagement.repository;

import ba.unsa.etf.pnwt.ArticleManagement.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
}
