import com.wangjunneil.schedule.utility.StringUtil;

import java.util.Date;

/**
 * Created by Administrator on 2016-11-15.
 */
public class OtherTest {


    public static void main(String[] args) {
        String aa = "body={\"errno\":0,\"error\":\"success\"}&cmd=resp.order.status.push&encrypt=&secret=c46f122d7658454b&source=31485&ticket=BB38B6E0-0F77-48A9-B808-0F912A1044C9&timestamp=1502089955&version=3";
        System.out.print(StringUtil.getMD5(aa));
    }

}
