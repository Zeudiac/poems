package entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Poem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;
    @Column(length = 1000)
    private String content;
    private String title;

    public Poem(Long id, String title, String author, String content) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
    }

    public Poem() {
    }

}