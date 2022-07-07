package com.gor.socialmediarest.dto;

import java.util.List;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long id;

    private String name;
    
    private String username;
    
    private List<PostDto> posts;
}
