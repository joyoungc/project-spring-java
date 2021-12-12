package io.joyoungc.data.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/***
 * Created by Aiden Jeong on 2021.09.22
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditEntity {

    @CreatedBy
//    @Column(columnDefinition = "varchar(20) comment '생성자'")
    protected String createdBy;

    @CreatedDate
//    @Column(columnDefinition = "datetime comment '생성일'")
    protected LocalDateTime createdDate;

    @LastModifiedBy
//    @Column(columnDefinition = "varchar(20) comment '수정자'")
    protected String modifiedBy;

    @LastModifiedDate
//    @Column(columnDefinition = "datetime comment '수정일'")
    protected LocalDateTime modifiedDate;
}
