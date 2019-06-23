import com.mod.loan.common.enums.UserOriginEnum;
import com.mod.loan.mapper.UserMapper;
import com.mod.loan.model.User;
import org.junit.Test;

import javax.annotation.Resource;


/**
 * @ author liujianjian
 * @ date 2019/5/15 13:56
 */
public class CommonTest extends BaseSpringBootJunitTest {

    @Resource
    UserMapper userMapper;

    @Test
    public void t() {
        User u = new User();
        u.setUserName("t");
        u.setMobileNo("t");
        u.setSource(UserOriginEnum.RZ.getCodeInt());
        userMapper.insertSelective(u);
    }

}
