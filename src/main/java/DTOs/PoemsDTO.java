package DTOs;

import entities.Poem;

public class PoemsDTO {
    private Long id;
    private String title;
    private String author;
    private String content;

    public PoemsDTO(Poem poem) {
        this.id = poem.getId();
        this.title = poem.getTitle();
        this.author = poem.getAuthor();
        this.content = poem.getContent();
    }
    public Poem toEntity() {
        Poem poem = new Poem();
        poem.setId(this.id);
        poem.setTitle(this.title);
        poem.setAuthor(this.author);
        poem.setContent(this.content);
        return poem;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
