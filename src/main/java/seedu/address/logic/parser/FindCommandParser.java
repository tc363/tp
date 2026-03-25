package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FACEBOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTAGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
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

        // --- Part A: General Search ---
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_FACEBOOK,
                PREFIX_TAG, PREFIX_INSTAGRAM, PREFIX_REMARK);
        boolean hasPrefix = argMultimap.containsPrefix(PREFIX_NAME, PREFIX_PHONE,
                PREFIX_ADDRESS, PREFIX_FACEBOOK, PREFIX_TAG, PREFIX_INSTAGRAM, PREFIX_REMARK);
        if (!hasPrefix) {
            return new FindCommand(new PersonContainsKeywordsPredicate(trimmedArgs, true));
        }

        // --- Part B: Specific Search (Prefixes) ---
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE,
                PREFIX_ADDRESS, PREFIX_FACEBOOK, PREFIX_TAG, PREFIX_INSTAGRAM, PREFIX_REMARK);
        Prefix[] prefixesToToValidate = {
                PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_TAG,
                PREFIX_FACEBOOK, PREFIX_INSTAGRAM, PREFIX_REMARK
        };

        for (Prefix prefix : prefixesToToValidate) {
            if (argMultimap.getValue(prefix).isPresent()) {
                String value = argMultimap.getValue(prefix).get().trim();
                if (value.isEmpty()) {
                    throw new ParseException("Search value for " + prefix.getPrefix() + " cannot be empty");
                }
            }
        }

        return new FindCommand(new PersonContainsKeywordsPredicate(args, false));
    }

}
