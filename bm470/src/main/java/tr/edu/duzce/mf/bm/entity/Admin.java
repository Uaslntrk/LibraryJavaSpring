package tr.edu.duzce.mf.bm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Admin")
public class Admin {

    @Id
    @Size(min = 11, max = 11, message = "TCKN must be 11 digits")
    @Column(name = "TCKN")
    private String tckn;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;
}
