package tr.edu.duzce.mf.bm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "Person")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {

    @Id
    @Size(min = 11, max = 11, message = "TCKN must be 11 digits")
    @Column(name = "TCKN")
    private String tckn;

    @Size(min = 3, message = "Name must be greater than 3 chars")
    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Size(min = 3, message = "Name must be greater than 3 chars")
    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Size(min = 3, message = "Gender must be greater than 3 chars")
    @Column(name = "gender", nullable = false)
    private String gender;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dateOfBirth", nullable = false)
    private Date birthday;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "is_banned")
    private boolean banned = false;
}
