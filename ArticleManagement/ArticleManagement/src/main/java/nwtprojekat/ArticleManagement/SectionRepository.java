package nwtprojekat.ArticleManagement;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import nwtprojekat.ArticleManagement.Models.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section,Integer> {


}
