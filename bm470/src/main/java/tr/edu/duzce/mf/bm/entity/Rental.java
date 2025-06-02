package tr.edu.duzce.mf.bm.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
@Entity
@Getter
@Setter
@Table(name = "RentalRecord")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_tckn", referencedColumnName = "TCKN", nullable = false)
    private Person user;

    @Column(name = "book_title")
    private String title;

    @Column(name = "book_authors")
    private String author;

    @Column(name = "rental_date")
    private LocalDate rentalDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "returned")
    private boolean returned;

    public boolean isOverdue() {
        return !returned && rentalDate != null &&
                ChronoUnit.DAYS.between(rentalDate, LocalDate.now()) > 15;
    }
}

