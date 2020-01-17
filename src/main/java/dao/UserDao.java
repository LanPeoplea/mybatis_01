package dao;

import domain.User;

import java.util.List;

public interface UserDao {

     //查询所有用户
    List<User> findAll();

     //保存用户
    void saveUser(User user);

     //更新用户信息
    void updateUser(User user);

     //删除用户
    void deleteUser(Integer userId);

    //根据id查询用户信息
    User findById(Integer userId);

    //根据username查询用户信息
    List<User> findByName(String userName);

    //查询总用户数
    int findTotal();
}
