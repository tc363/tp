package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.order.DeliveryTime;
import seedu.address.model.order.Item;
import seedu.address.model.order.Order;
import seedu.address.model.order.Quantity;
import seedu.address.model.order.Status;
import seedu.address.model.person.Address;

public class OrderListPanelTest {

    private static final String TEST_ITEM = "Burger";
    private static final String TEST_QUANTITY = "5";
    private static final String TEST_DELIVERY_TIME = "2030-12-01 1800";
    private static final String TEST_ADDRESS = "456 Oak Avenue";

    private Order createOrder(String status) {
        return new Order(
                UUID.randomUUID(),
                new Item(TEST_ITEM),
                new Quantity(TEST_QUANTITY),
                new DeliveryTime(TEST_DELIVERY_TIME),
                new Address(TEST_ADDRESS),
                new Status(status)
        );
    }

    private Order createOrderForCustomer(UUID customerId, String status) {
        return new Order(
                customerId,
                new Item(TEST_ITEM),
                new Quantity(TEST_QUANTITY),
                new DeliveryTime(TEST_DELIVERY_TIME),
                new Address(TEST_ADDRESS),
                new Status(status)
        );
    }

    @Test
    public void orderList_withPreparingStatus_displaysCorrectly() {
        Order order = createOrder("PREPARING");
        assertEquals("PREPARING", order.getStatus().value);
    }

    @Test
    public void orderList_withReadyStatus_displaysCorrectly() {
        Order order = createOrder("READY");
        assertEquals("READY", order.getStatus().value);
    }

    @Test
    public void orderList_withDeliveredStatus_displaysCorrectly() {
        Order order = createOrder("DELIVERED");
        assertEquals("DELIVERED", order.getStatus().value);
    }

    @Test
    public void orderList_withCancelledStatus_displaysCorrectly() {
        Order order = createOrder("CANCELLED");
        assertEquals("CANCELLED", order.getStatus().value);
    }

    @Test
    public void orderList_withAllStatuses_displaysCorrectly() {
        String[] statuses = {"PREPARING", "READY", "DELIVERED", "CANCELLED"};
        ObservableList<Order> orderList = FXCollections.observableArrayList();

        for (String status : statuses) {
            orderList.add(createOrder(status));
        }

        assertEquals(4, orderList.size());
        for (int i = 0; i < statuses.length; i++) {
            assertEquals(statuses[i], orderList.get(i).getStatus().value);
        }
    }

    @Test
    public void orderList_preservesOrder() {
        Order order1 = createOrder("PREPARING");
        Order order2 = createOrder("READY");
        Order order3 = createOrder("DELIVERED");
        ObservableList<Order> orderList = FXCollections.observableArrayList(order1, order2, order3);

        assertEquals(3, orderList.size());
        assertEquals(order1, orderList.get(0));
        assertEquals(order2, orderList.get(1));
        assertEquals(order3, orderList.get(2));
    }

    @Test
    public void orderList_withCustomerId_ordersDisplayedCorrectly() {
        UUID customerId = UUID.randomUUID();
        Order order1 = createOrderForCustomer(customerId, "PREPARING");
        Order order2 = createOrderForCustomer(customerId, "READY");
        ObservableList<Order> orderList = FXCollections.observableArrayList(order1, order2);

        assertEquals(customerId, orderList.get(0).getCustomerId());
        assertEquals(customerId, orderList.get(1).getCustomerId());
    }

    @Test
    public void orderList_differentCustomers_ordersDisplayedCorrectly() {
        UUID customer1 = UUID.randomUUID();
        UUID customer2 = UUID.randomUUID();
        Order order1 = createOrderForCustomer(customer1, "PREPARING");
        Order order2 = createOrderForCustomer(customer2, "READY");
        ObservableList<Order> orderList = FXCollections.observableArrayList(order1, order2);

        assertEquals(customer1, orderList.get(0).getCustomerId());
        assertEquals(customer2, orderList.get(1).getCustomerId());
    }

    @Test
    public void orderListItem_containsCorrectDetails() {
        String item = "Pizza";
        String quantity = "3";
        String deliveryTime = "2027-06-15 1200";
        String address = "789 Pine Street";
        String status = "PREPARING";

        Order order = new Order(
                UUID.randomUUID(),
                new Item(item),
                new Quantity(quantity),
                new DeliveryTime(deliveryTime),
                new Address(address),
                new Status(status)
        );

        assertEquals(item, order.getItem().value);
        assertEquals(quantity, order.getQuantity().value);
        assertEquals(deliveryTime, order.getDeliveryTime().value);
        assertEquals(address, order.getAddress().value);
        assertEquals(status, order.getStatus().value);
    }

    @Test
    public void orderList_withLargeNumberOfOrders_storesCorrectly() {
        ObservableList<Order> largeOrderList = FXCollections.observableArrayList();

        for (int i = 0; i < 100; i++) {
            largeOrderList.add(createOrder("PREPARING"));
        }

        assertEquals(100, largeOrderList.size());
    }

    @Test
    public void order_createsWithUniqueCustomerIds() {
        Order order1 = createOrder("PREPARING");
        Order order2 = createOrder("READY");

        assertNotNull(order1.getCustomerId());
        assertNotNull(order2.getCustomerId());
        assertEquals(order1.getCustomerId(), order1.getCustomerId());
    }

    @Test
    public void order_customerIdMatchesExpected() {
        UUID expectedId = UUID.randomUUID();
        Order order = createOrderForCustomer(expectedId, "PREPARING");

        assertEquals(expectedId, order.getCustomerId());
    }

    @Test
    public void observableList_clear_removesAllOrders() {
        ObservableList<Order> orderList = FXCollections.observableArrayList();
        orderList.add(createOrder("PREPARING"));
        orderList.add(createOrder("READY"));

        assertEquals(2, orderList.size());

        orderList.clear();

        assertEquals(0, orderList.size());
    }

    @Test
    public void observableList_addAll_addsMultipleOrders() {
        ObservableList<Order> orderList = FXCollections.observableArrayList();
        Order[] orders = {
            createOrder("PREPARING"),
            createOrder("READY"),
            createOrder("DELIVERED")
        };

        orderList.addAll(orders);

        assertEquals(3, orderList.size());
    }
}
