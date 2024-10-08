package co.mercadolibre.SocialMeli.dto.request;

import co.mercadolibre.SocialMeli.dto.ProductDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostRequestDTO {

    @JsonProperty("user_id")
    @NotNull(message = "El id no puede estar vacio.")
    @Min(value = 1, message = "El id debe ser mayor a cero")
    private int userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    @NotNull(message = "El campo \"date\" no puede estar vacío.")
    @PastOrPresent(message = "No se puede colocar una fecha futura.")
    private LocalDate date;

    @JsonProperty("product")
    @Valid
    @NotNull(message = "El campo \"product\" no puede estar vacío.")
    private ProductDTO product;

    @NotNull(message = "El campo \"category\" no puede estar vacío.")
    private int category;

    @NotNull(message = "El campo \"price\" no puede estar vacio.")
    @Max(value = 10000000, message = "El precio máximo por producto es de 10.000.000")
    private double price;
}
