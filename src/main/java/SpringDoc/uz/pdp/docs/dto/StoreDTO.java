package SpringDoc.uz.pdp.docs.dto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
@ToString
@Schema(name = "DTO",description = "dto for creating an object (store) with specific fields")
public class StoreDTO {

    private String name;
    private String email;
    private int employeeCount;
    private String desc;
}
