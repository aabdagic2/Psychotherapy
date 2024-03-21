package nwtprojekat.ArticleManagement.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "sections")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "section_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="article_id")

    private Article article;

    public Section() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
