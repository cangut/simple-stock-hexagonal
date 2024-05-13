package com.example.can.adapter.inbound;

import com.example.can.command.ProductCommand;
import com.example.can.event.ProductEvent;
import com.example.can.ports.inbound.BackOfficeEventConsumer;
import com.example.can.ports.inbound.ProductApplicationService;
import com.example.can.valueobject.ProductId;
import com.example.can.valueobject.ProductState;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackOfficeEventConsumerImpl implements BackOfficeEventConsumer<Acknowledgment> {

    private final ProductApplicationService productApplicationService;

    @Override
    @KafkaListener(topics = "StatusChanged", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(@Payload ProductEvent.StatusChanged statusChanged, Acknowledgment acknowledgment) {
        var changeStatus = mapToChangeStatus(statusChanged);
        productApplicationService.dispatch(changeStatus);
        acknowledgment.acknowledge();
    }

    private ProductCommand.ChangeStatus mapToChangeStatus(ProductEvent.StatusChanged statusChanged) {
        return ProductCommand.ChangeStatus.builder()
                .state(ProductState.valueOf(statusChanged.productState()))
                .productId(new ProductId(statusChanged.productId()))
                .createdAt(statusChanged.createdAt())
                .build();
    }
}
