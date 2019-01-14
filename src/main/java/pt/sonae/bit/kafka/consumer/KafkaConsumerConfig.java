package pt.sonae.bit.kafka.consumer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

//import io.opentracing.Tracer;
//import io.opentracing.contrib.kafka.spring.TracingConsumerFactory;


/**
 * @author Marco Emidio
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

	private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerConfig.class);
	
	@Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
	
	@Value("${spring.kafka.consumer.auto-offset-reset}")
	private String autoOffsetResetConfig;
	
	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;
	
	//@Autowired
	//private Tracer tracer;
	
	@Bean
    public Map<String, Object> kafkaConsumerConfigs() {
		
		LOG.debug("Loading kafka consumer configs...");
		
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetResetConfig);
        
        return props;
    }
	
	// Decorate ConsumerFactory with TracingConsumerFactory
	@Bean
	public ConsumerFactory<Integer, String> kafkaConsumerFactory() {
		return new DefaultKafkaConsumerFactory<>(kafkaConsumerConfigs());
	}
	
	// Use decorated ConsumerFactory in KafkaListenerContainerFactory
	@Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaConsumerFactory());
        return factory;
    }
	
	
	
	
}
