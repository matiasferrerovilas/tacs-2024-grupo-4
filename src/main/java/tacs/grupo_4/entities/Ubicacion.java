package tacs.grupo_4.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Ubicacion {    // Estadio X, Teatro Y
    @Id
    private UUID id;
    private String nombre;
    private Long capacidad;
    private String direccion;
}
