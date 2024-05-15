package ba.unsa.etf.pnwt.ArticleManagement;

import ba.unsa.etf.pnwt.ArticleManagement.model.Article;
import ba.unsa.etf.pnwt.ArticleManagement.model.Text;
import ba.unsa.etf.pnwt.ArticleManagement.model.Video;
import ba.unsa.etf.pnwt.ArticleManagement.repository.ImageRepository;
import ba.unsa.etf.pnwt.ArticleManagement.repository.TextRepository;
import ba.unsa.etf.pnwt.ArticleManagement.repository.VideoRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import ba.unsa.etf.pnwt.ArticleManagement.model.Image;
import ba.unsa.etf.pnwt.ArticleManagement.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
		article.setId("aaaaaaaa");
		article.setAuthor("9d0d4a57-8623-4686-9d2f-06c2640c78c8"); // dodati istog autora
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
