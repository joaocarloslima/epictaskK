package br.com.fiap.epictaskk.task;

import br.com.fiap.epictaskk.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Task {

    @Id
    UUID id = UUID.randomUUID();

    @NotBlank(message = "campo obrigatório")
    String title;

    @Size(min = 10)
    String description;

    @Min(1)
    int score;

    @Min(0) @Max(100)
    int status;

    @ManyToOne
    User user;

}
