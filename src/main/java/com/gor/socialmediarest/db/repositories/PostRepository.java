package com.gor.socialmediarest.db.repositories;

import com.gor.socialmediarest.db.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    
}
