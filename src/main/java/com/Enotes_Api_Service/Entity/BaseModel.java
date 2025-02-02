package com.Enotes_Api_Service.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;


@Data
@MappedSuperclass
public abstract class BaseModel {
@CreatedBy
@Column(updatable = false)
    private  Integer createdBy;
  @CreatedDate
  @Column(updatable = false)
  private Date createdOn;
  @LastModifiedBy
  @Column(insertable = false)
    private Integer updatedBy;
    @LastModifiedDate
    @Column(insertable = false)
    private Date updatedOn;
}
