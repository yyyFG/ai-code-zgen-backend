package cn.y.yaicodezgen.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class user implements Serializable {

    private static final long serialVersionUID = 3206971031461066260L;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 校验密码
     */
    private String checkPassword;
}
