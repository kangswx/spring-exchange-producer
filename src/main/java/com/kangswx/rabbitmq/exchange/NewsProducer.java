package com.kangswx.rabbitmq.exchange;

import com.kangswx.rabbitmq.domain.News;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

public class NewsProducer {

    private RabbitTemplate rabbitTemplate = null;

    public RabbitTemplate getRabbitTemplate() {
        return rabbitTemplate;
    }

    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNews(String routingKey, News news){
        //convertAndSend用于向exchange发送数据
        //第一个参数是routingKey,第二个参数是传送的对象，可以是字符串,byte数组或者任何实现了序列化接口的对象
        rabbitTemplate.convertAndSend(routingKey, news);
        System.out.println("新闻发送成功");
    }

    public static void main(String[] args) {
        //手动初始化Spring的IOC容器
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        //IOC容器中获取对象
        NewsProducer newsProducer = (NewsProducer) ctx.getBean("newsProducer");

        newsProducer.sendNews("us.20190101", new News("新华社", "特朗普又又又退群了", new Date(), "国际新闻内容"));
        newsProducer.sendNews("china.20190101", new News("凤凰TV", "XXX企业荣登世界500强", new Date(), "国内新闻内容"));
    }

}
