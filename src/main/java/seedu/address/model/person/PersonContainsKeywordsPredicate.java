package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

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
    private final SearchType searchType;

    /** Constructor for General Search */
    public PersonContainsKeywordsPredicate(String searchPhrase) {
        requireNonNull(searchPhrase, "Search phrase cannot be null");
        this.searchPhrase = searchPhrase;
        this.isGeneralSearch = true;
        this.searchType = null;
    }

    /** Constructor for Specific Search */
    public PersonContainsKeywordsPredicate(String searchPhrase, SearchType searchType) {
        requireNonNull(searchPhrase, "Search phrase cannot be null");
        requireNonNull(searchType, "Search type cannot be null for field-specific search");
        this.searchPhrase = searchPhrase;
        this.isGeneralSearch = false;
        this.searchType = searchType;
    }

    @Override
    public boolean test(Person person) {
        requireNonNull(person, "Person cannot be null");
        if (isGeneralSearch) {
            return testGeneral(person);
        }
        assert searchType != null : "Specific search must have a non-null search type";
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

    private boolean testSpecific(Person person) {
        if (searchPhrase.isEmpty()) {
            return false;
        }
        String lowerPhrase = searchPhrase.toLowerCase();
        switch (searchType) {
        case NAME:
            return person.getName().toString().toLowerCase().contains(lowerPhrase);
        case ADDRESS:
            return person.getAddress()
                    .map(address -> address.toString().toLowerCase().contains(lowerPhrase))
                    .orElse(false);
        case PHONE:
            return person.getPhone()
                .map(phone -> phone.toString().toLowerCase().contains(lowerPhrase))
                .orElse(false);
        case FACEBOOK:
            return person.getFacebook()
                    .map(facebook -> facebook.toString().toLowerCase().contains(lowerPhrase))
                    .orElse(false);
        case TAG:
            return person.getTags().stream()
                    .anyMatch(tag -> tag.toString().toLowerCase().contains(lowerPhrase));
        case INSTAGRAM:
            return person.getInstagram()
                    .map(ig -> ig.toString().toLowerCase().contains(lowerPhrase))
                    .orElse(false);
        case REMARK:
            return person.getRemark()
                    .map(remark -> remark.toString().toLowerCase().contains(lowerPhrase))
                    .orElse(false);
        default:
            throw new AssertionError("Unhandled search type: " + searchType);
        }
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
        return searchPhrase.equals(otherPredicate.searchPhrase)
                && isGeneralSearch == otherPredicate.isGeneralSearch
                && Objects.equals(searchType, otherPredicate.searchType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(searchPhrase, isGeneralSearch, searchType);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("searchPhrase", searchPhrase)
                .add("generalSearch", isGeneralSearch)
                .add("searchType", searchType)
                .toString();
    }
}
