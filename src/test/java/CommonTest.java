import com.alibaba.fastjson.JSON;
import com.mod.loan.model.Merchant;
import com.mod.loan.service.biz.BizMerchantService;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;


/**
 * @ author liujianjian
 * @ date 2019/5/15 13:56
 */
public class CommonTest extends BaseSpringBootJunitTest {

    @Resource
    BizMerchantService bizMerchantService;

    @Test
    public void t() {
        List<Merchant> list =
                bizMerchantService.queryNormalAll();
        System.out.println(JSON.toJSONString(list));
    }

}
