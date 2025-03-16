package dev.babal.dispatcherservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
public class DispatcherFunctionsStreamIntegrationTests {

    @Autowired
    private InputDestination input;
    @Autowired
    private OutputDestination output;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void whenOrderAcceptedThenDispatched() {
        long orderId = 121;
        Message<OrderAcceptMessage> inputMessage = MessageBuilder.withPayload(new OrderAcceptMessage(orderId))
            .build();
        Message<OrderDispatchedMessage> expectedOutput = MessageBuilder.withPayload(new OrderDispatchedMessage(orderId))
            .build();

        this.input.send(inputMessage);
        Assertions.assertEquals(objectMapper.readValue(objectMapper.writeValueAsString(inputMessage.getPayload()), OrderDispatchedMessage.class), expectedOutput.getPayload());
    }
}
