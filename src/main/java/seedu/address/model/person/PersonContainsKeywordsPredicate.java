package seedu.address.model.person;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FACEBOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTAGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;

/**
 * Tests that a {@code Person}'s fields contain the given search phrase as a case-insensitive substring.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    /**
     * Specifies the type of search to perform.
     */
    public enum SearchType {
        NAME, ADDRESS, PHONE, FACEBOOK, TAG, INSTAGRAM, REMARK
    }
    private final String searchPhrase;
    private final boolean isGeneralSearch;

    /** Constructor for General Search */
    public PersonContainsKeywordsPredicate(String searchPhrase, boolean isGeneralSearch) {
        this.searchPhrase = searchPhrase;
        this.isGeneralSearch = isGeneralSearch;
    }

    @Override
    public boolean test(Person person) {
        if (isGeneralSearch) {
            return testGeneral(person);
        }
        return testSpecific(person);
    }


    private boolean testGeneral(Person person) {
        if (searchPhrase.isEmpty()) {
            return false;
        }
        String lowerPhrase = searchPhrase.toLowerCase();
        return person.getName().fullName.toLowerCase().contains(lowerPhrase)
                || person.getPhone().map(p -> p.value.toLowerCase().contains(lowerPhrase)).orElse(false)
                || person.getFacebook().map(fb -> fb.value.toLowerCase().contains(lowerPhrase)).orElse(false)
                || person.getInstagram().map(ig -> ig.value.toLowerCase().contains(lowerPhrase)).orElse(false)
                || person.getAddress().map(a -> a.value.toLowerCase().contains(lowerPhrase)).orElse(false)
                || person.getRemark().map(r -> r.value.toLowerCase().contains(lowerPhrase)).orElse(false)
                || person.getTags().stream()
                .anyMatch(tag -> tag.tagName.toLowerCase().contains(lowerPhrase));
    }

    private boolean testSpecific(Person customer) {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                searchPhrase, PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_FACEBOOK,
                PREFIX_TAG, PREFIX_INSTAGRAM, PREFIX_REMARK);

        List<Predicate<Person>> predicateList = new ArrayList<>();

        argMultimap.getValue(PREFIX_NAME).ifPresent(name ->
                predicateList.add(person -> person.getName().fullName.toLowerCase().contains(name.toLowerCase()))
        );

        argMultimap.getValue(PREFIX_ADDRESS).ifPresent(address ->
                predicateList.add(person ->
                        person.getAddress().toString().toLowerCase().contains(address.toLowerCase()))
        );

        argMultimap.getValue(PREFIX_PHONE).ifPresent(phone ->
                predicateList.add(person -> person.getPhone().toString().toLowerCase().contains(phone.toLowerCase()))
        );

        argMultimap.getValue(PREFIX_TAG).ifPresent(tags ->
                predicateList.add(person -> person.getTags().stream()
                        .anyMatch(tag -> tag.toString().toLowerCase().contains(tags.toLowerCase()))
                ));

        argMultimap.getValue(PREFIX_FACEBOOK).ifPresent(fb ->
                predicateList.add(person -> person.getFacebook().toString().toLowerCase().contains(fb.toLowerCase()))
        );

        argMultimap.getValue(PREFIX_INSTAGRAM).ifPresent(ig ->
                predicateList.add(person -> person.getInstagram().toString().toLowerCase().contains(ig.toLowerCase()))
        );

        argMultimap.getValue(PREFIX_REMARK).ifPresent(remark ->
                predicateList.add(person -> person.getRemark().toString().toLowerCase().contains(remark.toLowerCase()))
        );

        if (predicateList.isEmpty()) {
            return false;
        }

        return predicateList.stream().allMatch(p -> p.test(customer));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonContainsKeywordsPredicate)) {
            return false;
        }

        PersonContainsKeywordsPredicate otherPredicate = (PersonContainsKeywordsPredicate) other;
        return searchPhrase.equals(otherPredicate.searchPhrase);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("searchPhrase", searchPhrase).toString();
    }
}
