package com.wl.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <>
 *
 * @author wulei
 * @create 2019/5/18 0018 10:31
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -7079670280795838762L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String password;
    private int age;
}
