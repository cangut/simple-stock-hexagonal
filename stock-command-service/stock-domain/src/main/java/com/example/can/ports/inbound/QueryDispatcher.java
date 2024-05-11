package com.example.can.ports.inbound;

import com.example.can.query.ProductQuery;

public interface QueryDispatcher <R, T extends ProductQuery>{
    R dispatch (T query);
}
