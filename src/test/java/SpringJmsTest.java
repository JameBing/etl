import com.wangjunneil.schedule.activemq.TopicMessageConsumer;
import com.wangjunneil.schedule.activemq.TopicMessageProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.Destination;

/**
 * Created by admin on 2016-12-12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class SpringJmsTest {

    @Autowired
    @Qualifier("topicProvider")
    private TopicMessageProducer topicMessageProducer;

    @Autowired
    @Qualifier("topicDestination")
    private Destination topicDestination;

@Test
    public void testTopic(){
        topicMessageProducer.sendMessage(topicDestination,"1112");
    }
}
