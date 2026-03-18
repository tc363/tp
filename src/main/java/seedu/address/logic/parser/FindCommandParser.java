package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_TAG);
        boolean hasPrefix = argMultimap.containsPrefix(PREFIX_NAME, PREFIX_PHONE,
                PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_TAG);
        if (!hasPrefix) {
            return new FindCommand(new PersonContainsKeywordsPredicate(trimmedArgs));
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            return new FindCommand(new PersonContainsKeywordsPredicate(
                    getNonEmptyValue(argMultimap, PREFIX_NAME),
                    PersonContainsKeywordsPredicate.SearchType.NAME));
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            return new FindCommand(new PersonContainsKeywordsPredicate(
                    getNonEmptyValue(argMultimap, PREFIX_PHONE),
                    PersonContainsKeywordsPredicate.SearchType.PHONE));
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            return new FindCommand(new PersonContainsKeywordsPredicate(
                    getNonEmptyValue(argMultimap, PREFIX_EMAIL),
                    PersonContainsKeywordsPredicate.SearchType.EMAIL));
        }

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            return new FindCommand(new PersonContainsKeywordsPredicate(
                    getNonEmptyValue(argMultimap, PREFIX_ADDRESS),
                    PersonContainsKeywordsPredicate.SearchType.ADDRESS));
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            return new FindCommand(new PersonContainsKeywordsPredicate(
                    getNonEmptyValue(argMultimap, PREFIX_TAG),
                    PersonContainsKeywordsPredicate.SearchType.TAG));
        }
        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    /**
     * Helper function to make sure the input value is non-empty.
     */
    private String getNonEmptyValue(ArgumentMultimap argMultimap, Prefix prefix) throws ParseException {
        String value = argMultimap.getValue(prefix).get().trim();
        if (value.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        return value;
    }
}
