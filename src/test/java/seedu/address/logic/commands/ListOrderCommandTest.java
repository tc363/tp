package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showOrderAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalOrders.getTypicalAddressbook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Test cases for AddressBook order retrieval methods
 */
public class ListOrderCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressbook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListOrderCommand(), model, ListOrderCommand.MESSAGE_SUCCESS, expectedModel);
        assertEquals(expectedModel.getFilteredOrderList().size(), model.getFilteredOrderList().size());
        assertEquals(expectedModel.getFilteredOrderList(), model.getFilteredOrderList());
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);
        Model expectedModelAfterCommand = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(new ListOrderCommand(), model,
                ListOrderCommand.MESSAGE_SUCCESS, expectedModelAfterCommand);
        assertEquals(expectedModelAfterCommand.getAddressBook().getOrderList(),
                model.getFilteredOrderList());
    }
}


