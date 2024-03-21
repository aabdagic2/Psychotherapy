package nwtprojekat.ArticleManagement;

import nwtprojekat.ArticleManagement.Models.Article;
import nwtprojekat.ArticleManagement.Models.Image;
import nwtprojekat.ArticleManagement.Models.Video;
import nwtprojekat.ArticleManagement.Models.Section;
import nwtprojekat.ArticleManagement.Models.Text;
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
		article.setTitle("Ovo je NWT projekta!");
		Text textSection = new Text();
		textSection.setContent("TEXT TEXT");

		Video videoSection = new Video();
		videoSection.setVideoUrl("URL URL");

		Image imageSection = new Image();
		imageSection.setImageUrl("Ovdje treba ici url!");

		List<Section> sections = new ArrayList<>();
		sections.add(textSection);
		sections.add(videoSection);
		sections.add(imageSection);

		sections.forEach((Section s) -> {
			s.setArticle(article);
		});

		article.setSections(sections);
		articleRepo.save(article);
		
	}

	public static void main(String[] args) {
		SpringApplication.run(ArticleManagementApplication.class, args);
	}

}
