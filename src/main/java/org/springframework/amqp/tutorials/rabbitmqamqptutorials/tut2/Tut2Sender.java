package org.springframework.amqp.tutorials.rabbitmqamqptutorials.tut2;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.concurrent.atomic.AtomicInteger;

public class Tut2Sender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;

    AtomicInteger count=new AtomicInteger(0);
    AtomicInteger dots =new AtomicInteger(0);

    @Scheduled(fixedDelay = 1000,initialDelay = 200)
    public void send()
    {
        StringBuilder builder = new StringBuilder("Hello");

        if(dots.incrementAndGet()==3)
        {
            dots.set(1);
        }

        for(int i=0; i< dots.get(); i++)
        {
            builder.append('.');
        }

        builder.append(count.incrementAndGet());
        String message=builder.toString();
        template.convertAndSend(queue.getName(),message);
        System.out.println(" [x] sent '"+message+"'");

    }
}
