package com.example.can.ports.inbound;

import com.example.can.command.ProductCommand;
import com.example.can.query.ProductQuery;

public abstract class ProductApplicationService<R> implements CommandDispatcher<R, ProductCommand>, QueryDispatcher<R, ProductQuery> {

    @Override
    public R dispatch(ProductCommand command) {
        return switch (command) {
            case ProductCommand.Create c -> this.handle(c);
            case ProductCommand.IncreaseUnitInStock c -> this.handle(c);
            case ProductCommand.DecreaseUnitInStock  c -> this.handle(c);
            case ProductCommand.Close c -> this.handle(c);
            case ProductCommand.Replay c -> this.handle(c);
            case ProductCommand.ChangeStatus c -> this.handle(c);
            default -> throw new IllegalStateException("Unexpected value: " + command);
        };
    }

    @Override
    public R dispatch(ProductQuery query) {
        return switch (query) {
            case ProductQuery.ById q -> this.handle(q);
            default -> throw new IllegalStateException("Unexpected value: " + query);
        };
    }

    protected abstract R handle(ProductCommand.Create create);
    protected abstract R handle(ProductCommand.IncreaseUnitInStock increaseUnitInStock);
    protected abstract R handle(ProductCommand.DecreaseUnitInStock decreaseUnitInStock);
    protected abstract R handle(ProductCommand.Close close);
    protected abstract R handle(ProductQuery.ById query);
    protected abstract R handle(ProductCommand.Replay replay);
    protected abstract R handle(ProductCommand.ChangeStatus changeStatus);
}
