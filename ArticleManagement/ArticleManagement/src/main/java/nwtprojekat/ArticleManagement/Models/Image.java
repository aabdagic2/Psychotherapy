package nwtprojekat.ArticleManagement.Models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import nwtprojekat.ArticleManagement.Models.Section;

@Entity
@Table(name = "image_sections")
@DiscriminatorValue("image")
public class Image extends Section {
    @Column(nullable = false)
    private String imageUrl;

    public Image() {}

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
