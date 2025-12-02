package ufrn.imd.operation_server.events.publisher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;
import ufrn.imd.operation_server.events.publisher.ReportEventListener;
import ufrn.imd.operation_server.events.saga.ReportEvent;

@Configuration
public class ReportEventListenerConfig {

    @Bean
    public ReportEventListener reportEventListener(){
        var sink = Sinks.many().unicast().<ReportEvent>onBackpressureBuffer();
        var flux = sink.asFlux();
        return new ReportEventListenerImpl(sink, flux);
    }
}
