package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalOrders.ORDER_A;
import static seedu.address.testutil.TypicalOrders.ORDER_B;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.OrderContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;

public class FindOrderCommandTest {

    @Test
    public void execute_itemKeyword_filtersOrderList() {
        Model model = new ModelManager(
                new AddressBookBuilder().withOrder(ORDER_A).withOrder(ORDER_B).build(),
                new UserPrefs());
        Model expectedModel = new ModelManager(
                new AddressBookBuilder().withOrder(ORDER_A).withOrder(ORDER_B).build(),
                new UserPrefs());

        FindOrderCommand command = new FindOrderCommand(
                new OrderContainsKeywordsPredicate(OrderContainsKeywordsPredicate.SearchType.ITEM, "Pizza"));
        command.execute(model);

        assertEquals(1, model.getFilteredOrderList().size());
        assertEquals(2, expectedModel.getFilteredOrderList().size());
    }

    @Test
    public void execute_addressKeyword_filtersOrderList() {
        Model model = new ModelManager(
                new AddressBookBuilder().withOrder(ORDER_A).withOrder(ORDER_B).build(),
                new UserPrefs());

        FindOrderCommand command = new FindOrderCommand(
                new OrderContainsKeywordsPredicate(OrderContainsKeywordsPredicate.SearchType.ADDRESS, "Amy"));
        command.execute(model);

        assertEquals(1, model.getFilteredOrderList().size());
    }

    @Test
    public void execute_customerId_filtersOrderList() {
        Model model = new ModelManager(
                new AddressBookBuilder().withOrder(ORDER_A).withOrder(ORDER_B).build(),
                new UserPrefs());

        FindOrderCommand command = new FindOrderCommand(
                new OrderContainsKeywordsPredicate(OrderContainsKeywordsPredicate.SearchType.CUSTOMER, "1"));
        command.execute(model);

        assertEquals(1, model.getFilteredOrderList().size());
    }

    @Test
    public void equals_samePredicate_returnsTrue() {
        OrderContainsKeywordsPredicate predicate = new OrderContainsKeywordsPredicate(
                OrderContainsKeywordsPredicate.SearchType.ITEM, "pizza");
        FindOrderCommand command1 = new FindOrderCommand(predicate);
        FindOrderCommand command2 = new FindOrderCommand(predicate);
        assertTrue(command1.equals(command2));
    }

    @Test
    public void equals_differentPredicate_returnsFalse() {
        OrderContainsKeywordsPredicate predicate1 = new OrderContainsKeywordsPredicate(
                OrderContainsKeywordsPredicate.SearchType.ITEM, "pizza");
        OrderContainsKeywordsPredicate predicate2 = new OrderContainsKeywordsPredicate(
                OrderContainsKeywordsPredicate.SearchType.ADDRESS, "Jurong");
        FindOrderCommand command1 = new FindOrderCommand(predicate1);
        FindOrderCommand command2 = new FindOrderCommand(predicate2);
        assertFalse(command1.equals(command2));
    }

    @Test
    public void toString_returnsCorrectFormat() {
        OrderContainsKeywordsPredicate predicate = new OrderContainsKeywordsPredicate(
                OrderContainsKeywordsPredicate.SearchType.ITEM, "pizza");
        FindOrderCommand command = new FindOrderCommand(predicate);
        String expected = FindOrderCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, command.toString());
    }
}
