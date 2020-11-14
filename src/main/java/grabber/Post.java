package grabber;

import java.time.LocalDateTime;
import java.util.Objects;

public class Post {
    private String id;
    private String link;
    private String textLink;
    private String description;
    private LocalDateTime createDate;

    public Post(String id, String link, String textLink, String description, LocalDateTime createDate) {
        this.id = id;
        this.link = link;
        this.textLink = textLink;
        this.description = description;
        this.createDate = createDate;
    }

    public Post() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getTextLink() {
        return textLink;
    }

    public void setTextLink(String textLink) {
        this.textLink = textLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return Objects.equals(id, post.id) && Objects.equals(link, post.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, link);
    }

    @Override
    public String toString() {
        return "Post{"
                +
                "id='"
                + id
                + '\''
                +
                ", link='"
                + link
                + '\''
                +
                ", textLink='"
                + textLink
                + '\''
                +
                ", description='"
                + description
                + '\''
                +
                ", createDate="
                + createDate
                +
                '}';
    }
}
