package ba.unsa.etf.pnwt.ArticleManagement.repository;

import ba.unsa.etf.pnwt.ArticleManagement.model.Text;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextRepository extends JpaRepository<Text, String> {
}
