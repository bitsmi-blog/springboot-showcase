package com.bitsmi.springbootshowcase.sampleapps.kafka.messaging.actuator;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeClusterOptions;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnEnabledHealthIndicator("kafka")
public class KafkaHealthIndicator extends AbstractHealthIndicator
{
    private final AdminClient kafkaAdminClient;

    public KafkaHealthIndicator(KafkaAdmin kafkaAdmin)
    {
        this.kafkaAdminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());
    }

    @Override
    protected void doHealthCheck(Builder builder) throws Exception
    {
        final DescribeClusterOptions options = new DescribeClusterOptions().timeoutMs(1000);

        DescribeClusterResult clusterDescription = kafkaAdminClient.describeCluster(options);

        // When Kafka is not connected future.get() throws an exception which in turn sets the indicator DOWN.
        clusterDescription.clusterId().get();

        builder.up().build();

        // Alternatively directly use data from future in health detail.
        builder.up()
                .withDetail("clusterId", clusterDescription.clusterId().get())
                .withDetail("nodeCount", clusterDescription.nodes().get().size())
                .build();
    }
}
