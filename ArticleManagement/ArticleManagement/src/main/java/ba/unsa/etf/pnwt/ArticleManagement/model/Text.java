package ba.unsa.etf.pnwt.ArticleManagement.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;

@Entity
@Table(name = "text_sections")
public class Text {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "VARCHAR(64)")
    private String id;

    @Column(nullable = false)
    //@NotBlank(message = "Article must have text!")
    private String content;

    @OneToOne(mappedBy = "text")
    private Article article;

    public Text() {}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
