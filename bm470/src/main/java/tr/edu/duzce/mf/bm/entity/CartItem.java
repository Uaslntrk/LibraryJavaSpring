package tr.edu.duzce.mf.bm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Getter
@Setter
@Table(name = "CartItem")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Person ile ManyToOne ilişkisi, foreign key sütunu person_tckn olarak belirleniyor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_tckn", referencedColumnName = "TCKN", nullable = false)
    private Person person;

    @Column(name = "book_title")
    private String title;

    @Column(name = "book_authors")
    private String author;

    @Column(name="added_date")
    private Date addedDate;

    @Column(name = "quantity")
    private int quantity = 1;
}
