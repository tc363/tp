package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.order.Order;

/**
 * An UI component that displays information of an {@code Order}.
 */
public class OrderCard extends UiPart<Region> {

    private static final String FXML = "OrderListCard.fxml";

    public final Order order;

    @FXML
    private HBox cardPane;
    @FXML
    private Label item;
    @FXML
    private Label address;
    @FXML
    private Label date;
    @FXML
    private Label status;

    /**
     * Creates an {@code OrderCard} with the given {@code Order} and index to display.
     */
    public OrderCard(Order order, int displayedIndex) {
        super(FXML);
        this.order = order;

        item.setText("Order: " + order.getItem().value + " (x" + order.getQuantity().value + ")");

        address.setText("Address: " + order.getAddress().value);

        date.setText("Date: " + order.getDeliveryTime().value);

        status.setText("Status: " + order.getStatus().value);
    }
}
