package com.Enotes_Api_Service.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Integer id;
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;
    @Size(min = 1, max = 100, message = "Description must be between 1 and 100 characters")
    private String description;
    @NotNull
    private Boolean isActive;
    private  Integer createdBy;
    private Integer updatedBy;
    private Date createdOn;
    private Date updatedOn;
}
