package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.address.logic.commands.ViewOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.Status;

/**
 * Parses input arguments and creates a new ViewOrderCommand object
 */
public class ViewOrderCommandParser implements Parser<ViewOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewOrderCommand
     * and returns a ViewOrderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ViewOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String statusInput = args.trim().toUpperCase();

        if (statusInput.equals("ALL")) {
            return new ViewOrderCommand((Status)null);
        }

        if (!Status.isValidStatus(statusInput)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewOrderCommand.MESSAGE_USAGE));
        }
        return new ViewOrderCommand(new Status(statusInput));
    }
}
