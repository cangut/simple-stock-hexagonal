package com.example.can.controller;

import com.example.can.command.ProductCommand;
import com.example.can.ports.inbound.ProductApplicationService;
import com.example.can.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class ReplayEventsController {

    private final ProductApplicationService<ProductResponse> productApplicationService;

    @PostMapping("/replay")
    public void replayEvents() {
        var replay = ProductCommand.Replay.builder().build();
        productApplicationService.dispatch(replay);
    }
}
