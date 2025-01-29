package com.Enotes_Api_Service.Entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.util.Date;


@Data
@MappedSuperclass
public class BaseModel {
    private Boolean isActive;
    private Boolean isDeleted;
    private  Integer createdBy;
    private Integer updatedBy;
    private Date createdOn;
    private Integer updatedOn;
}
