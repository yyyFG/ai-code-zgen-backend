package cn.y.yaicodezgen.service.impl;

import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.y.yaicodezgen.common.ResultUtils;
import cn.y.yaicodezgen.exception.BusinessException;
import cn.y.yaicodezgen.exception.ErrorCode;
import cn.y.yaicodezgen.model.enums.UserRoleEnum;
import cn.y.yaicodezgen.model.vo.LoginUserVO;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.y.yaicodezgen.model.entity.User;
import cn.y.yaicodezgen.mapper.UserMapper;
import cn.y.yaicodezgen.service.UserService;
import io.netty.util.internal.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import static cn.y.yaicodezgen.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户 服务层实现。
 *
 * @author <a href="https://www.yaicode.com">程序员yyy</a>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>  implements UserService{

    @Override
    public LoginUserVO userLogin(String userAccount, String password, HttpServletRequest request) {
        // 校验
        if (StrUtil.hasBlank(userAccount, password)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 5){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (password.length() < 8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 判断用户是否存在
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userAccount", userAccount);
        long count = this.count(queryWrapper);
        if (count < 1){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }
        // 验证密码
        User user = this.getOne(queryWrapper);
        String encryptPassword = getEncryptPassword(password);
        if (!user.getUserPassword().equals(encryptPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }

        LoginUserVO loginUserVO = new LoginUserVO();
        loginUserVO.setId(user.getId());
        loginUserVO.setUserAccount(user.getUserAccount());
        loginUserVO.setUserName(user.getUserName());
        loginUserVO.setUserRole(user.getUserRole());
        loginUserVO.setUserAvatar(user.getUserAvatar());

        return loginUserVO;
    }

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StrUtil.hasBlank(userAccount, userPassword, checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 5){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if(!userPassword.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }

        // 2. 检查是否重复
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userAccount", userAccount);
        long count = this.count(queryWrapper);
        if (count > 0){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户已存在");
        }

        // 3. 密码加密
        String encryptPassword = getEncryptPassword(userPassword);

        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserName("无名");
        user.setUserPassword(encryptPassword);
        user.setUserRole(UserRoleEnum.USER.getValue());
        boolean save = this.save(user);
        if (!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库异常");
        }

        return user.getId();
    }

    /**
     * 获取加密密码
     * @param userPassword
     * @return
     */
    @Override
    public String getEncryptPassword(String userPassword) {
        //
        final String SALT = "yyy";
        return DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (user == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return user;
    }


    /**
     * 获取当前登录用户
     * @param request
     * @return
     */
    @Override
    public User getLoginUserVO(HttpServletRequest request) {
        // 判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        // 从缓存或者数据库查询
        long userId = currentUser.getId();
        currentUser = this.getById(userId);
        if (currentUser == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        LoginUserVO loginUserVO = new LoginUserVO();
        loginUserVO.setId(currentUser.getId());
        loginUserVO.setUserAccount(currentUser.getUserAccount());
        loginUserVO.setUserName(currentUser.getUserName());
        loginUserVO.setUserRole(currentUser.getUserRole());

        return currentUser;
    }
}
