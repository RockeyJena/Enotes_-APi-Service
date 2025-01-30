package com.Enotes_Api_Service.Entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;


@Data
@MappedSuperclass
public class BaseModel {
    private Boolean isActive;
    private Boolean isDeleted;
    private  Integer createdBy;
    private Integer updatedBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;
}
