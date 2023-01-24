package com.solvd.onlinestore.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Information about program issues")
public class ResponseDto {

    @Schema(description = "List of exceptions that were identified during the program")
    private List<String> errors;

}
