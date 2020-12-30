package top.lizec.smartreview.entity;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String roles;
    private boolean enable;
}
