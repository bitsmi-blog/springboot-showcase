package com.bitsmi.springbootshowcase.utils;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Tag("ManualTest")
public class KafkaTests
{
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTests.class);

    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    public void kafkaTest1() throws Exception
    {
        var messageTemplate = """
                    {
                        "oneName": "name_%1$s",
                        "oneValue": "value_%1$s",
                        "fail": %2$b
                    }
                """;

//        for(int i=0; i<100; i++) {
//            var fail = i<25 || i>50;
//            var message = messageTemplate.formatted(i, fail);
        var message = messageTemplate.formatted(0, true);
        var record = new ProducerRecord<String, Object>("test-topic1", message);
        record.headers().add(new RecordHeader("__TypeId__", "sampleOne".getBytes(StandardCharsets.UTF_8)));
        LOGGER.info("Sending message: {}", message);
        kafkaTemplate.send(record).get();
//        }
    }

    /*---------------------------*
     * SETUP AND HELPERS
     *---------------------------*/
    @BeforeEach
    public void setUp()
    {
        var configProps = new HashMap<String, Object>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        var kafkaProducerFactory = new DefaultKafkaProducerFactory<String, Object>(configProps);
        this.kafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory);
    }
}
