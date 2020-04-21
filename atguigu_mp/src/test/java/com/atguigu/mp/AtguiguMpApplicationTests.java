package com.atguigu.mp;

import com.atguigu.mp.entity.User;
import com.atguigu.mp.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AtguiguMpApplicationTests {

    @Autowired
    private UserMapper userMapper;

	@Test
	public void contextLoads() {

	    //通过Mapper查询用户列表
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.err::println);

    }

    /**
     * MP - insert语句操作
     */
    @Test
    public void insertMpTest(){
        //怎么就帮我们自动生成主键了？
        User user = new User();
        user.setName("xiaohei");
        user.setAge(4);
        user.setEmail("xiaohei@hl.com");
//        user.setCreateTime(new Date());
//        user.setUpdateTime(new Date());
        //插入操作
        userMapper.insert(user);

        System.err.println("插入后的Id："+user.getId());
    }


    //有问题吗？
    // 在插入的时候如果有时间插入那么怎么办？
    //例如：在添加的时候，会有一个create_time 和 update_time 时间类型的数据必须添加的；
    //每一次添加和修改都需要进行时间的设置；
    // 怎么解决这种方式，在添加和修改的时候不需要我们手动设置就可以帮我们自动补全添加进数据库？

    /**
     * MP - update语句
     */
    @Test
    public void updateMpTest(){

        User user = userMapper.selectById(1201702324763619329L);
        user.setName("xiaoxiao");
        userMapper.updateById(user);
        /**
         * 找问题：
         * 1、补全已经完成了；
         * 2、当用户量多的情况下、并发操作，进行修改一条记录？容易出现卡顿或者数据不准确；
         * 悲观锁：
         *      如果是悲观锁，数据库认为是悲观的，在每一次操作都需要加锁；
         *      优势：安全，数据准确；
         *      缺点：效率慢，如果并发操作那么肯定卡顿；
         * 乐观锁：
         *      每一次操作数据库的是都认为是合法的，乐观的所以不加锁；
         *      优势：效率高；
         *      缺点：不安全，数据不准确；
         *  怎么做才能保证：1、有效率；2、安全；
         *  version：加版本号；
         *  根据这个版本号来修改或者查询等操作；
         *  id  name    age     email   version     ***
         *  1   xiaosan  12     ***      1
         *  select * from user where id  =1   A用户
         *  select * from user where id = 1   B 用户
         *  怎么写SQL，使B用户修改完这条记录后A用户不能修改；
         *  B 用户 ： update set user.*** user.version = user.version + 1 where id = 1 and version = 1
         *  A 用户 ： pdate set user.*** user.version = user.version + 1 where id = 1 and version = 1  （×）
         */
    }

    /**
     * MP == update 乐观锁失败测试
     */
    @Test
    public void updateVersionTest(){
        //1、A用户获取出用户数据
        User userA = userMapper.selectById(2L);
        //2、B用户获取出用户数据
        User userB = userMapper.selectById(2L);
        //3、B用户先进行了 修改
        userB.setName("daliu");
        userMapper.updateById(userB);

        //4、A用户再进行修改
        userA.setName("daqi");
        userMapper.updateById(userA);
    }

    /**
     * MP - select 操作
     */
    @Test
    public void selectMpTest(){
        //1、根据Id查询的
        User user = userMapper.selectById(3L);
        System.err.println(user);
        //2、根据多个IDS查询的
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(4L);
        List<User> users = userMapper.selectBatchIds(ids);
        users.forEach(System.err::println);
        //3、根据map查询的
        Map<String, Object> map = new HashMap<>();
        //map中的Key是数据表字段名
        map.put("name", "xiaowu");
        map.put("age", 14);
        List<User> userList = userMapper.selectByMap(map);
        userList.forEach(System.err::println);
    }

    @Test
    public void selectByPage(){
        //4、分页查询的
        //参数说明：1、当前页数；2、每页显示的记录数
        Page<User> page = new Page<User>(3, 3);
        userMapper.selectPage(page, null);//不同返回值了；在被拦截执行分页操作的时候，返回的数据自动封装到Page对象中了
        List<User> userList = page.getRecords();
        System.err.println("查询的集合数据：");
        userList.forEach(System.err::println);
        System.err.println("总页数：" + page.getPages());
        System.err.println("总记录数：" + page.getTotal());
        System.err.println("当前页数：" + page.getCurrent());
        System.err.println("每页显示记录数L：" + page.getSize());
        System.err.println("是否有上一页：" + page.hasPrevious());
        System.err.println("是否有下一页：" + page.hasNext());

    }

    @Test
    public void deleteById(){

        /**
         * 多个Id删除
         * Map条件删除的
         */

        userMapper.deleteById(2L);
        /**
         * 找问题：这里面的删除都是物理删除；
         * 真的删除，数据库就真的没有了！
         * 如果在企业中物理删除了怎么办？
         * 不要说话，跑；
         * 再企业中会这样做吗？
         * 逻辑删除：修改了这条记录的状态！！！
         * 需要配置删除插件：目的是在进行删除操作的时候，其实SQL语句进行修改操作
         * 1、数据库加字段deleted
         * 2、实体类加属性deleted
         * 3、Config配置类中加插件
         * 4、在properties文件中声明删除和不删除的状态值
         */
    }

    /**
     * Wrapper条件查询
     * 1、根据名称中模糊查询xiao查询出来
     * 2、根据age大于等于14查询出来
     * 3、根据时间查询出来
     * 4、根据id = 3L查询出来
     */
    @Test
    public void wrapperTest(){ //【重点】
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //wrapper.eq("id", 3L);
        // like = %***%
        // likeRight = LIKE 'xiao%'
        // likeLeft = LIKE '%xiao'
        wrapper.like("name", "xiao");
        wrapper.ge("age", 14);
        List<User> userList = userMapper.selectList(wrapper);
        userList.forEach(System.err::println);
    }


}

