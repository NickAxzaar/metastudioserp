package com.metastudios.mserp.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private Boolean isActive;
    private RoleDTO role;

    @Data
    public static class RoleDTO {
        private String name;
    }
}