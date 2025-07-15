package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private int role;
    private String avatar;
    private Integer assignedStaffId;
    
    // Constructor để tương thích với code cũ (không có assignedStaffId)
    public User(int id, String username, String password, String firstName, String lastName, String email, String phone, int role, String avatar) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.avatar = avatar;
    }
}
