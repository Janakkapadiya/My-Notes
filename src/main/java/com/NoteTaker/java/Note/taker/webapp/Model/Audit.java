package com.NoteTaker.java.Note.taker.webapp.Model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Audit {

    @Temporal(TemporalType.DATE)
    @CreatedDate
    @Column(name = "create_date",nullable = false,updatable = false)
    private Date createDate;

    @Temporal(TemporalType.DATE)
    @LastModifiedDate
    @Column(name = "last_modified_date",nullable = false)
    private Date updateDate;
}
