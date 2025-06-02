package tr.edu.duzce.mf.bm.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "Favorite")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_TCKN", referencedColumnName = "TCKN", nullable = false)
    private Person user;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

}