package SpringDoc.uz.pdp.docs.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ParameterObject// checks for validity in the  swagger ui not at Intellij IDEA
@Schema(name = "Store Entity",description = "Store  entity detailed information (description)")
public class Store {

    @Id
    @Min(1)
    @NotNull
    @Parameter(description = "Store Identifier", required = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 120)
    @Parameter(description = "Store Name", required = true)
    private String name;

    @NotBlank
    @Size(min = 9, max = 250)
    @Parameter( description = "Store Email", required = true)
    private String email;

    @NotNull
    @Min(1)
    @Parameter(description = "Store EmployeeCount", required = true)
    private int employeeCount;

    @Parameter(description = "Store Description", required = false)
    private String desc;
}
