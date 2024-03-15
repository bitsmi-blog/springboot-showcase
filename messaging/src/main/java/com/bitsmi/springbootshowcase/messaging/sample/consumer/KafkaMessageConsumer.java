package com.bitsmi.springbootshowcase.messaging.sample.consumer;

import com.bitsmi.springbootshowcase.messaging.sample.message.SampleOneMessage;
import com.bitsmi.springbootshowcase.messaging.sample.message.SampleTwoMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@KafkaListener(id = "KafkaMessageConsumer", topics = "test-topic1", groupId = "test-group1")
public class KafkaMessageConsumer
{
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaMessageConsumer.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaHandler
    @Transactional(rollbackFor = Exception.class)
    public void processSampleOneMessage(SampleOneMessage message) throws Exception
    {
        LOGGER.info("Received sampleOne message: {}", message);
        kafkaTemplate.send("test-topic1", new SampleTwoMessage(message.oneName(), message.oneValue()));

        Thread.sleep(10_000);

        if(message.fail()) {
            throw new Exception("sampleOne message failed");
        }
    }

    @KafkaHandler
    @SendTo("test-topic2")
    // Transactional because of the reply
    @Transactional(rollbackFor = Exception.class)
    public SampleTwoMessage processSampleTwoMessage(SampleTwoMessage message)
    {
        LOGGER.info("Received sampleTwo message: {}", message);
        return message;
    }

    @KafkaHandler(isDefault = true)
    public void processUnknown(Object message) {
        LOGGER.info("Received unknown message: {}", message);
    }
}
