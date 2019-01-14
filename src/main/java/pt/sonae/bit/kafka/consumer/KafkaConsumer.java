package pt.sonae.bit.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//import io.opentracing.Tracer;

/**
 * @author Marco Emidio
 */
@Service
public class KafkaConsumer {
	
	private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumer.class);
	
	//@Autowired
    //private Tracer tracer;
	
    @KafkaListener(topics = "queuing_messages_events")
    public void receive(@Payload String message) {
        LOG.info("received message='{}'", message);
        //LOG.info("span propagated? '{}'", tracer.activeSpan().getBaggageItem("sonae.mid"));
		
		//tracer.activeSpan().setTag("sonae.consumer.mid", tracer.activeSpan().getBaggageItem("sonae.mid"));
    }

}
