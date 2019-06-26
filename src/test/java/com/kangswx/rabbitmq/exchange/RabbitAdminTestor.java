package com.kangswx.rabbitmq.exchange;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class RabbitAdminTestor {

    @Resource(name = "rabbitAdmin")
    private RabbitAdmin rabbitAdmin;
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void creatExchangeTest(){
        //创建交换机，无则创建有则跳过
        rabbitAdmin.declareExchange(new FanoutExchange("test.exchange.fanout", true, false));
        rabbitAdmin.declareExchange(new DirectExchange("test.exchange.direct", true, false));
        rabbitAdmin.declareExchange(new TopicExchange("test.exchange.topic", true, false));
    }

    @Test
    public void createQueuetestor(){
        //创建队列
        rabbitAdmin.declareQueue(new Queue("test.queue"));
        //绑定队列
        rabbitAdmin.declareBinding(new Binding("test.queue", Binding.DestinationType.QUEUE, "test.exchange.topic", "#", new HashMap<String, Object>()));
        //发送消息
        rabbitTemplate.convertAndSend("test.exchange.topic", "hello", "abc123");
    }

    @Test
    public void deleteTest(){
        rabbitAdmin.deleteQueue("test.queue");
        rabbitAdmin.deleteExchange("test.exchange.fanout");
        rabbitAdmin.deleteExchange("test.exchange.direct");
        rabbitAdmin.deleteExchange("test.exchange.topic");
    }

}
