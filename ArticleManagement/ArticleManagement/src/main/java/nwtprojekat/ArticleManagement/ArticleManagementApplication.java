package nwtprojekat.ArticleManagement;

import nwtprojekat.ArticleManagement.model.Article;
import nwtprojekat.ArticleManagement.model.Image;
import nwtprojekat.ArticleManagement.model.Video;
import nwtprojekat.ArticleManagement.model.Section;
import nwtprojekat.ArticleManagement.model.Text;
import nwtprojekat.ArticleManagement.repository.ArticleRepository;
import nwtprojekat.ArticleManagement.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class ArticleManagementApplication implements CommandLineRunner {

	@Autowired
	private ArticleRepository articleRepo;

	@Autowired
	private SectionRepository sectionRepo;

	@Override
	public void run(String... args) throws Exception {
		Article article = new Article();
		article.setAuthor("Neko Nekic");
		article.setTitle("Ovo je NWT projekat.");

		Text textSection = new Text();
		textSection.setContent("Ovo je tekst.");

		Video videoSection = new Video();
		videoSection.setVideoUrl("Ovo je video.");

		Image imageSection = new Image();
		imageSection.setImageUrl("Ovo je slika.");

		List<Section> sections = new ArrayList<>();
		sections.add(textSection);
		sections.add(videoSection);
		sections.add(imageSection);

		articleRepo.save(article);

		sections.forEach((Section s) -> {
			s.setArticle(article);
			sectionRepo.save(s);
		});

		//article.setSections(sections);
		// article se prvo spasi
		// a nakon toga sections
	}

	public static void main(String[] args) {
		SpringApplication.run(ArticleManagementApplication.class, args);
	}

}
