package com.gor.socialmediarest.db.entities;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "posts")
@EntityListeners(AuditingEntityListener.class)
public class PostEntity {
    @Id
    @GeneratedValue
    private Long id;
    
    private String text;

    public PostEntity(String text) {
        this.text = text;
    }
    
    private Long likesCount;
    
    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity author;
    
    @CreatedDate
    private LocalDate createdDate;

    @LastModifiedDate
    private LocalDate lastModifiedDate;
}
