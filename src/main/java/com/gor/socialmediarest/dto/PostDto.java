package com.gor.socialmediarest.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    
    private String text;
    
    private Long likesCount;
    
    private LocalDate createdDate;

    private LocalDate lastModifiedDate;
}
