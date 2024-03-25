package nwtprojekat.ArticleManagement.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;

@Entity
@Table(name = "video_sections")
@DiscriminatorValue("video")
public class Video extends Section {
    @Column(nullable = false)
    private String videoUrl;

    public Video() {}

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}