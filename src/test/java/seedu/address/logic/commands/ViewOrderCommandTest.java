package seedu.address.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.model.AddressBook;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderList;;
import seedu.address.model.order.Status;
import seedu.address.model.person.Person;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.PersonBuilder;
import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.TypicalOrders.ORDER_A;
import static seedu.address.testutil.TypicalOrders.ORDER_B;
import static seedu.address.testutil.TypicalOrders.ORDER_C;
import static seedu.address.testutil.TypicalOrders.ORDER_D;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

/**
 * Test cases for AddressBook order retrieval methods
 */
public class ViewOrderCommandTest {

    private AddressBook addressBook;
    private Person person1;
    private Person person2;
    private Order order1;
    private Order order2;
    private Order order3;
    private Order order4;

    @BeforeEach
    public void setUp() {
        addressBook = new AddressBook();

        // Create test persons
        person1 = new PersonBuilder(ALICE).build();
        person2 = new PersonBuilder(BENSON).build();

        addressBook.addPerson(person1);
        addressBook.addPerson(person2);

        // Create test orders
        order1 = new OrderBuilder(ORDER_A).build();
        order2 = new OrderBuilder(ORDER_B).build();
        order3 = new OrderBuilder(ORDER_C).build();
        order4 = new OrderBuilder(ORDER_D).build();
    }

    // ===== Tests for getOrdersByStatus() =====

    @Test
    public void getOrdersByStatus_withPreparingOrders_returnsPreparingOrders() {
            System.out.println("\n=== Test: getOrdersByStatus_withPreparingOrders ===");

            // Add orders to person1
            person1.getOrders().add(order1);  // PREPARING
            System.out.println("Added order1 to person1");

            person1.getOrders().add(order3);  // READY
            System.out.println("Added order2 to person1");

            person2.getOrders().add(order2);  // PREPARING
            System.out.println("Added order4 to person2");

            System.out.println("Person1 orders: " + person1.getOrders());
            System.out.println("Person2 orders: " + person2.getOrders());

            OrderList result = addressBook.getOrdersByStatus(new Status("PREPARING"));

            System.out.println("Result size: " + result.size());
            System.out.println("Result orders: " + result);
            System.out.println("Contains order1: " + result.contains(order1));
            System.out.println("Contains order4: " + result.contains(order4));

            assertEquals(2, result.size());
            assertTrue(result.contains(order1));
            assertTrue(result.contains(order2));

    }

    @Test
    public void getOrdersByStatus_withReadyOrders_returnsReadyOrders() {
        person1.getOrders().add(order1);  // PREPARING
        person2.getOrders().add(order4);  // READY
        person2.getOrders().add(order2);  // DELIVERED

        OrderList result = addressBook.getOrdersByStatus(new Status("READY"));

        assertEquals(1, result.size());
        assertTrue(result.contains(order4));
    }

    @Test
    public void getOrdersByStatus_withDeliveredOrders_returnsDeliveredOrders() {
        person1.getOrders().add(order1);  // PREPARING
        person1.getOrders().add(order3);  // DELIVERED

        OrderList result = addressBook.getOrdersByStatus(new Status("DELIVERED"));

        assertEquals(1, result.size());
        assertTrue(result.contains(order3));
    }

    @Test
    public void getOrdersByStatus_noMatchingOrders_returnsEmptyList() {
        person1.getOrders().add(order1);  // PREPARING
        person2.getOrders().add(order2);  // READY

        OrderList result = addressBook.getOrdersByStatus(new Status("CANCELLED"));

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
    }

    @Test
    public void getOrdersByStatus_withNullOrderList_skipsPersonWithoutOrders() {
        // person1 has null orders (not initialized)
        // person2 has orders
        person2.getOrders().add(order2);  // PREPARING

        OrderList result = addressBook.getOrdersByStatus(new Status("PREPARING"));

        assertEquals(1, result.size());
        assertTrue(result.contains(order2));
    }


    // ===== Tests for getAllOrders() =====

    @Test
    public void getAllOrders_withMultipleOrders_returnsAllOrders() {
        person1.getOrders().add(order1);
        person1.getOrders().add(order3);
        person2.getOrders().add(order2);
        person2.getOrders().add(order4);

        OrderList result = addressBook.getAllOrders();

        assertEquals(4, result.size());
        assertTrue(result.contains(order1));
        assertTrue(result.contains(order2));
        assertTrue(result.contains(order3));
        assertTrue(result.contains(order4));
    }

    @Test
    public void getAllOrders_noOrders_returnsEmptyList() {
        OrderList result = addressBook.getAllOrders();

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
    }

    @Test
    public void getAllOrders_withNullOrderLists_skipsPersonsWithoutOrders() {
        // person1 has null orders
        // person2 has orders
        person2.getOrders().add(order4);
        person2.getOrders().add(order2);
        // person3 has null orders

        OrderList result = addressBook.getAllOrders();

        assertEquals(2, result.size());
        assertTrue(result.contains(order4));
        assertTrue(result.contains(order2));
    }

    @Test
    public void getAllOrders_allPersonsHaveNullOrders_returnsEmptyList() {
        // All persons have null orders (default state)

        OrderList result = addressBook.getAllOrders();

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
    }
}


