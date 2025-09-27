package cn.y.yaicodezgen.service;

import com.mybatisflex.core.service.IService;
import cn.y.yaicodezgen.model.entity.User;

/**
 * 用户 服务层。
 *
 * @author <a href="https://www.yaicode.com">程序员yyy</a>
 */
public interface UserService extends IService<User> {

    /**
     * 用户登录
     *
     * @param userAccount  用户名
     * @param password  密码
     * @return 脱敏后的用户信息
     */
    long userLogin(String userAccount, String password);

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 密码加密
     * @param userPassword
     * @return
     */
    String getEncryptPassword(String userPassword);
}
