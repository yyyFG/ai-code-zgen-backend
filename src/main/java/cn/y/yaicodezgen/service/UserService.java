package cn.y.yaicodezgen.service;

import cn.y.yaicodezgen.model.vo.LoginUserVO;
import com.mybatisflex.core.service.IService;
import cn.y.yaicodezgen.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;

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
     * @param userPassword  密码
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

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


    /**
     * 获取当前登录用户
     *
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 获取当前登录用户 (脱敏)
     *
     * @return
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);
}
