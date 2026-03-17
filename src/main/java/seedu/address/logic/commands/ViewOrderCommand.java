package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.order.OrderList;
import seedu.address.model.order.Status;

/**
 * Command to view orders by status (PREPARING/READY/DELIVERED/CANCELLED/ALL)
 */
public class ViewOrderCommand extends Command {
    public static final String COMMAND_WORD = "view-o";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View orders by status.\n"
            + "Parameters: STATUS (PREPARING/READY/DELIVERED/CANCELLED/ALL)\n"
            + "Example: " + COMMAND_WORD + " PREPARING";

    private final Status status;

    public ViewOrderCommand(Status status) {
        this.status = status;
    }

    @Override
    public CommandResult execute(Model model) {
        OrderList orders;
        String resultMessage;

        if (status == null) {
            orders = model.getAllOrders();
            resultMessage = "=== ALL ORDERS ===\n";
        } else if (status.equals(new Status("PREPARING"))) {
            orders = model.getOrdersByStatus(new Status("PREPARING"));
            resultMessage = "=== PREPARING ORDERS ===\n";
        } else if (status.equals(new Status("READY"))) {
            orders = model.getOrdersByStatus(new Status("READY"));
            resultMessage = "=== READY ORDERS ===\n";
        } else if (status.equals(new Status("DELIVERED"))) {
            orders = model.getOrdersByStatus(new Status("DELIVERED"));
            resultMessage = "=== DELIVERED ORDERS ===\n";
        } else if (status.equals(new Status("CANCELLED"))) {
            orders = model.getOrdersByStatus(new Status("CANCELLED"));
            resultMessage = "=== CANCELLED ORDERS ===\n";
        } else {
            return new CommandResult("Invalid status.\n" + MESSAGE_USAGE);
        }

        if (orders.isEmpty()) {
            return new CommandResult(resultMessage + "No orders found.");
        }

        return new CommandResult(resultMessage + orders);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ViewOrderCommand)) {
            return false;
        }
        ViewOrderCommand e = (ViewOrderCommand) other;
        if (status == null && e.status == null) {
            return true;
        }
        if (status == null || e.status == null) {
            return false;
        }
        return status.equals(e.status);
    }
}
