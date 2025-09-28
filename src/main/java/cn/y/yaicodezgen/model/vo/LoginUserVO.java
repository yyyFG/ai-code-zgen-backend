package cn.y.yaicodezgen.model.vo;


import com.mybatisflex.annotation.Column;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoginUserVO implements Serializable {

    private static final long serialVersionUID = -2879850034997483906L;

    private Long id;

    private String userAccount;

    private String userPassword;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin
     */
    private String userRole;
}
