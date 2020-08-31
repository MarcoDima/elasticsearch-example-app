package de.evoila.elasticsearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(indexName = "blog", type = "article")
public class Article {

    @Id
    private String id = UUID.randomUUID().toString();

    private String title;

    private String releaseYear;

    private String genre;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Author> authors;

    public List<Author> getAuthors() {
        return authors;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleasseYear(String releasseYear) {
        this.releaseYear = releasseYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Article() { }

    public Article(String title, String genre, String releaseYear, List<Author> authors) {
        setTitle(title);
        setGenre(genre);
        setReleasseYear(releaseYear);
        setAuthors(new ArrayList<>());
    }

    public String getId() {
        return id;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}