package nwtprojekat.ArticleManagement;

import nwtprojekat.ArticleManagement.model.Article;
import nwtprojekat.ArticleManagement.model.Image;
import nwtprojekat.ArticleManagement.model.Video;
import nwtprojekat.ArticleManagement.model.Text;
import nwtprojekat.ArticleManagement.repository.ArticleRepository;
import nwtprojekat.ArticleManagement.repository.ImageRepository;
import nwtprojekat.ArticleManagement.repository.TextRepository;
import nwtprojekat.ArticleManagement.repository.VideoRepository;
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
	private TextRepository textRepo;

	@Autowired
	private VideoRepository videoRepo;

	@Autowired
	private ImageRepository imageRepo;

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

		article.setText(textSection);
		article.setImage(imageSection);
		article.setVideo(videoSection);

		articleRepo.save(article);

		textSection.setArticle(article);
		textRepo.save(textSection);
		videoSection.setArticle(article);
		videoRepo.save(videoSection);
		imageSection.setArticle(article);
		imageRepo.save(imageSection);

	}

	public static void main(String[] args) {
		SpringApplication.run(ArticleManagementApplication.class, args);
	}

}
