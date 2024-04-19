package nwtprojekat.ArticleManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "article_id", columnDefinition = "VARCHAR(64)")
    private String id;

    @Column(nullable = false)
    @NotBlank(message = "The title must not be empty!")
    private String title;

    @Column(nullable = false)
    private String author;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "text_id", referencedColumnName = "id")
    private Text text;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "video_id", referencedColumnName = "id")
    private Video video;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    public Article(String title, String author, Text text, Video video, Image image) {
        this.title = title;
        this.author = author;
        this.text = text;
        this.video = video;
        this.image = image;
    }

    public Article() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Text getText() {
        return text;
    }

    public Video getVideo() {
        return video;
    }

    public Image getImage() {
        return image;
    }
}
