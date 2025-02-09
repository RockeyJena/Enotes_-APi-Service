package com.Enotes_Api_Service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotesDto {
    private Integer id;
    private String title;
    private String description;
    private CategoryDto category;
    private Integer createdBy;
    private Integer updatedBy;
    private Date createdOn;
    private Date updatedOn;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static   class CategoryDto {
        private Integer id;
        private String name;
    }


}