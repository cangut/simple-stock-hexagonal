package com.example.can.ports.inbound;

import com.example.can.command.ProductCommand;

public interface CommandDispatcher<R, T extends ProductCommand> {
    R dispatch (T command);
}
