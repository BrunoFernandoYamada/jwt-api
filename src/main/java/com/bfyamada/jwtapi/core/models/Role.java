package com.bfyamada.jwtapi.core.models;

import com.bfyamada.jwtapi.core.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "roles")
public class Role {

    @Id
    private String id;
    private ERole name;

    public Role(ERole eRole){
        this.name = eRole;
    }
}
