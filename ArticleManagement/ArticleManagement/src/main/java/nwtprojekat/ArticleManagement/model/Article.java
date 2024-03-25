package nwtprojekat.ArticleManagement.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "articles")
public class Article {
    @Id
    @Column(name = "article_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @OneToMany(mappedBy = "article")
    private List<Section> sections;

    public Article() {}
    public Article(Long id, String title, String author, List<Section> sections) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.sections = sections;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}
