package nwtprojekat.ArticleManagement.repository;

import nwtprojekat.ArticleManagement.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {


}
