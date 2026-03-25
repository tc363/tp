package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_2031;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ITEM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_DESC_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_DESC_PIZZA;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_3;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_5;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_PREPARING;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_READY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_2031;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_PIZZA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_5;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_PREPARING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_READY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditOrderCommand;
import seedu.address.logic.commands.EditOrderCommand.EditOrderDescriptor;
import seedu.address.model.order.Item;
import seedu.address.model.order.Quantity;
import seedu.address.model.order.Status;
import seedu.address.model.person.Address;
import seedu.address.testutil.EditOrderDescriptorBuilder;

public class EditOrderCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditOrderCommand.MESSAGE_USAGE);

    private EditOrderCommandParser parser = new EditOrderCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_ITEM_PIZZA, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditOrderCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + ITEM_DESC_PIZZA, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + ITEM_DESC_PIZZA, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 z/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_ITEM_DESC, Item.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_QUANTITY_DESC, Quantity.MESSAGE_CONSTRAINTS);
        // Invalid datetime produces format constraint first, since it doesn't match regex
        assertParseFailure(parser, "1" + INVALID_DATETIME_DESC,
                seedu.address.model.order.DeliveryTime.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_STATUS_DESC, Status.MESSAGE_CONSTRAINTS);

        // invalid item followed by valid quantity
        assertParseFailure(parser, "1" + INVALID_ITEM_DESC + QUANTITY_DESC_3, Item.MESSAGE_CONSTRAINTS);

        // valid item followed by invalid quantity
        assertParseFailure(parser, "1" + ITEM_DESC_PIZZA + INVALID_QUANTITY_DESC, Quantity.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ORDER;
        String userInput = targetIndex.getOneBased() + ITEM_DESC_BURGER + QUANTITY_DESC_5
                + DATETIME_DESC_2031 + ADDRESS_DESC_AMY + STATUS_DESC_READY;

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withItem(VALID_ITEM_BURGER)
                .withQuantity(VALID_QUANTITY_5)
                .withDeliveryTime(VALID_DATETIME_2031)
                .withAddress(VALID_ADDRESS_AMY)
                .withStatus(VALID_STATUS_READY)
                .build();
        EditOrderCommand expectedCommand = new EditOrderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + ITEM_DESC_BURGER + QUANTITY_DESC_5;

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withItem(VALID_ITEM_BURGER)
                .withQuantity(VALID_QUANTITY_5)
                .build();
        EditOrderCommand expectedCommand = new EditOrderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // item
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + ITEM_DESC_PIZZA;
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withItem(VALID_ITEM_PIZZA).build();
        EditOrderCommand expectedCommand = new EditOrderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // quantity
        userInput = targetIndex.getOneBased() + QUANTITY_DESC_3;
        descriptor = new EditOrderDescriptorBuilder().withQuantity(VALID_QUANTITY_3).build();
        expectedCommand = new EditOrderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // delivery time
        userInput = targetIndex.getOneBased() + DATETIME_DESC_2031;
        descriptor = new EditOrderDescriptorBuilder().withDeliveryTime(VALID_DATETIME_2031).build();
        expectedCommand = new EditOrderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditOrderDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditOrderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // status
        userInput = targetIndex.getOneBased() + STATUS_DESC_PREPARING;
        descriptor = new EditOrderDescriptorBuilder().withStatus(VALID_STATUS_PREPARING).build();
        expectedCommand = new EditOrderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // invalid followed by valid
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + INVALID_ITEM_DESC + ITEM_DESC_BURGER;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ITEM));

        // valid followed by invalid
        userInput = targetIndex.getOneBased() + ITEM_DESC_BURGER + INVALID_ITEM_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ITEM));

        // multiple valid fields repeated
        userInput = targetIndex.getOneBased() + ITEM_DESC_PIZZA + QUANTITY_DESC_3
                + ADDRESS_DESC_AMY + STATUS_DESC_PREPARING
                + ITEM_DESC_BURGER + QUANTITY_DESC_5 + ADDRESS_DESC_BOB + STATUS_DESC_READY;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ITEM, PREFIX_QUANTITY,
                        PREFIX_ADDRESS, PREFIX_STATUS));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_ITEM_DESC + INVALID_QUANTITY_DESC
                + INVALID_ITEM_DESC + INVALID_QUANTITY_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ITEM, PREFIX_QUANTITY));
    }

    @Test
    public void parse_invalidAddressValue_failure() {
        // empty address (PREFIX_ADDRESS with no value)
        assertParseFailure(parser, "1 a/", Address.MESSAGE_CONSTRAINTS);
    }
}
