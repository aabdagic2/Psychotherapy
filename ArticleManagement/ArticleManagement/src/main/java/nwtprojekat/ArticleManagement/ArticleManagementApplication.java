package nwtprojekat.ArticleManagement;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
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
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
// @EnableEurekaClient
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
		// prvi clanak
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

		// drugi clanak
		Article article2 = new Article();
		article2.setAuthor("Drugi Autor");
		article2.setTitle("Drugi clanak.");

		Text textSection2 = new Text();
		textSection2.setContent("Jos jedan tekst.");

		Video videoSection2 = new Video();
		videoSection2.setVideoUrl("Jos jedan video.");

		Image imageSection2 = new Image();
		imageSection2.setImageUrl("Jos jedna slika.");

		article2.setText(textSection2);
		article2.setImage(imageSection2);
		article2.setVideo(videoSection2);

		articleRepo.save(article2);

		textSection2.setArticle(article2);
		textRepo.save(textSection2);
		videoSection2.setArticle(article2);
		videoRepo.save(videoSection2);
		imageSection2.setArticle(article2);
		imageRepo.save(imageSection2);

		// treći clanak
		Article article3 = new Article();
		article3.setAuthor("Treci Autor");
		article3.setTitle("Treci clanak.");

		Text textSection3 = new Text();
		textSection3.setContent("Tekst treceg clanka.");

		Video videoSection3 = new Video();
		videoSection3.setVideoUrl("Video treceg clanka.");

		Image imageSection3 = new Image();
		imageSection3.setImageUrl("Slika treceg clanka.");

		article3.setText(textSection3);
		article3.setImage(imageSection3);
		article3.setVideo(videoSection3);

		articleRepo.save(article3);

		textSection3.setArticle(article3);
		textRepo.save(textSection3);
		videoSection3.setArticle(article3);
		videoRepo.save(videoSection3);
		imageSection3.setArticle(article3);
		imageRepo.save(imageSection3);

	}

	public static void main(String[] args) {
		SpringApplication.run(ArticleManagementApplication.class, args);
	}

}
