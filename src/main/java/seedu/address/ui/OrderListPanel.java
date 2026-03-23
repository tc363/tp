package seedu.address.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.order.Order;

/**
 * Panel containing the list of orders.
 */
public class OrderListPanel extends UiPart<Region> {
    private static final String FXML = "OrderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(OrderListPanel.class);

    @FXML
    private ListView<Order> orderListView;

    /**
     * Creates an {@code OrderListPanel} with the given {@code ObservableList} of orders.
     * Orders are displayed sorted by delivery time, with the latest order first.
     */
    public OrderListPanel(ObservableList<Order> orderList) {
        super(FXML);

        SortedList<Order> sortedList = new SortedList<>(orderList);
        sortedList.setComparator(getOrderComparator());

        orderListView.setItems(sortedList);
        orderListView.setCellFactory(listView -> new OrderListViewCell());
    }

    /**
     * Returns a comparator that sorts orders by delivery time in descending order (latest first).
     */
    static Comparator<Order> getOrderComparator() {
        return Comparator.comparing(
                order -> order.getDeliveryTime().value,
                Comparator.reverseOrder()
        );
    }

    /**
     * Custom {@code ListCell} that displays the graphics of an {@code Order} using an {@code OrderCard}.
     */
    class OrderListViewCell extends ListCell<Order> {

        OrderListViewCell() {
        }

        @Override
        protected void updateItem(Order order, boolean empty) {
            super.updateItem(order, empty);

            if (empty || order == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new OrderCard(order, getIndex() + 1).getRoot());
            }
        }
    }

}
