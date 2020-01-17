package test;

import dao.UserDao;
import domain.QueryVo;
import domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import org.apache.ibatis.io.Resources;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class MybatisTest {

    private InputStream in;
    private SqlSession sqlSession;
    private UserDao userDao;
    @Before   //用于在测试方法执行之前执行
    public void init()throws Exception{
        //1、读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2、创建SqlSessionFactory工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3、使用工厂生产SqlSession对象
        sqlSession = factory.openSession();
        //4、使用SqlSession创建Dao接口的代理对象
        userDao = sqlSession.getMapper(UserDao.class);
    }

    @After   //用于在测试方法执行之后执行
    public void destory()throws Exception{
        //提交事务
        sqlSession.commit();
        //6、释放资源
        sqlSession.close();
        in.close();
    }
    /**
     * 测试查询所有
     * @throws Exception
     */
    @Test
    public void findAll()throws Exception{
        //5、使用代理对象执行方法
        List<User> users = userDao.findAll();
        for (User user: users) {
            System.out.println(user);
        }
    }

    /**
     * 测试保存操作
     */
    @Test
    public void testSave(){
        User user = new User();
        user.setUsername("马超");
        user.setAddress("西凉");
        user.setSex("男");
        user.setBirthday(new Date());

        //执行保存方法
        userDao.saveUser(user);
        System.out.println("新插入数据id为："+user.getId());
    }
    /**
     * 测试更新操作
     */
    @Test
    public void testUpdate(){
        User user = new User();
        user.setId(6);
        user.setUsername("黄混");
        user.setAddress("河南省南阳市");
        user.setSex("男");
        user.setBirthday(new Date());

        //执行保存方法
        userDao.updateUser(user);
    }
    /**
     * 测试删除操作
     */
    @Test
    public void testDelete(){
        //执行删除方法
        userDao.deleteUser(6);
    }
    /**
     * 测试根据id查询单个用户信息操作
     */
    @Test
    public void findOne(){
        User user = userDao.findById(1);
        System.out.println(user);
    }
    //测试根据username模糊查询用户信息
    @Test
    public void findByName(){
        List<User> users = userDao.findByName("%小%");
//        List<User> users = userDao.findByName("小");
        for (User user: users ) {
            System.out.println(user);
        }
    }
    //测试使用QueryVo作为查询条件
    @Test
    public void findTotal(){
        System.out.println("总记录数为:"+userDao.findTotal());
    }
    //测试根据username模糊查询用户信息
    @Test
    public void findByVo(){
        QueryVo vo = new QueryVo();
        User user = new User();
        user.setUsername("小%");
        vo.setUser(user);
        List<User> users = userDao.findUserByVo(vo);
        for (User u: users ) {
            System.out.println(u);
        }
    }
}
