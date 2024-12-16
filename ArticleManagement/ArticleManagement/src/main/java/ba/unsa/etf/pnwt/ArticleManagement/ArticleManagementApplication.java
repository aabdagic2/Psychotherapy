package ba.unsa.etf.pnwt.ArticleManagement;

import ba.unsa.etf.pnwt.ArticleManagement.model.Article;
import ba.unsa.etf.pnwt.ArticleManagement.model.Text;
import ba.unsa.etf.pnwt.ArticleManagement.model.Video;
import ba.unsa.etf.pnwt.ArticleManagement.repository.ArticleRepository;
import ba.unsa.etf.pnwt.ArticleManagement.repository.ImageRepository;
import ba.unsa.etf.pnwt.ArticleManagement.repository.TextRepository;
import ba.unsa.etf.pnwt.ArticleManagement.repository.VideoRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import ba.unsa.etf.pnwt.ArticleManagement.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
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
		// Kreiranje članka sa fiksnim ID-om i podacima
		Article article = new Article();
		article.setId("1"); // Fiksni ID za članak
		article.setAuthor("59a8bf4f-1414-4a1e-b184-a370bd61313c"); // Postojeći userId
		article.setTitle("Važnost mentalnog zdravlja");

		// Tekstualni sadržaj članka
		Text textSection = new Text();
		textSection.setContent("Mentalno zdravlje je jednako važno kao i fizičko zdravlje. "
				+ "Redovno razgovaranje sa stručnjacima, kao i briga o sebi kroz meditaciju, "
				+ "fizičku aktivnost i kvalitetan san može značajno unaprijediti kvalitet života.");

		// Video sadržaj članka
		Video videoSection = new Video();
		videoSection.setVideoUrl("https://www.youtube.com/watch?v=abc123test"); // Testni video link

		// Slikovni sadržaj članka
		Image imageSection = new Image();
		imageSection.setImageUrl("https://example.com/mental-health.jpg"); // Testni link za sliku

		// Postavljanje sadržaja članka
		article.setText(textSection);
		article.setImage(imageSection);
		article.setVideo(videoSection);

		// Spremanje članka u bazu
		articleRepo.save(article);

		// Povezivanje sadržaja sa člankom
		textSection.setArticle(article);
		textRepo.save(textSection);

		videoSection.setArticle(article);
		videoRepo.save(videoSection);

		imageSection.setArticle(article);
		imageRepo.save(imageSection);

		System.out.println("Testni članak sa slikom, videom i tekstom uspješno dodat u bazu!");
	}


	public static void main(String[] args) {
		SpringApplication.run(ArticleManagementApplication.class, args);
	}

}
