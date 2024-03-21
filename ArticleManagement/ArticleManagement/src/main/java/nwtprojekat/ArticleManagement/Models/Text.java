package nwtprojekat.ArticleManagement.Models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import nwtprojekat.ArticleManagement.Models.Section;

@Entity
@Table(name = "text_sections")
@DiscriminatorValue("text")
public class Text extends Section {
    @Column(nullable = false)
    private String content;

    public Text() {}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
