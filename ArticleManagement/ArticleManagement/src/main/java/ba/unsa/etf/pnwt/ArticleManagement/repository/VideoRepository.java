package ba.unsa.etf.pnwt.ArticleManagement.repository;

import ba.unsa.etf.pnwt.ArticleManagement.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {
}
