package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        PersonContainsKeywordsPredicate firstPredicate = new PersonContainsKeywordsPredicate("first", true);
        PersonContainsKeywordsPredicate secondPredicate = new PersonContainsKeywordsPredicate("second", true);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy = new PersonContainsKeywordsPredicate("first", true);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different search phrase -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsPhrase_returnsTrue() {
        // Full name match
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("Alex Yeoh", true);
        assertTrue(predicate.test(new PersonBuilder().withName("Alex Yeoh").build()));

        // Partial name match (substring)
        predicate = new PersonContainsKeywordsPredicate("Alex", true);
        assertTrue(predicate.test(new PersonBuilder().withName("Alex Yeoh").build()));

        // Case-insensitive match
        predicate = new PersonContainsKeywordsPredicate("aLex", true);
        assertTrue(predicate.test(new PersonBuilder().withName("Alex Yeoh").build()));
    }

    @Test
    public void test_phoneContainsPhrase_returnsTrue() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("12345", true);
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));
    }

    @Test
    public void test_facebookContainsPhrase_returnsTrue() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("alice.pau", true);
        assertTrue(predicate.test(new PersonBuilder().withFacebook("alice.pauline").build()));
    }

    @Test
    public void test_addressContainsPhrase_returnsTrue() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("Main Street", true);
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Main Street").build()));
    }

    @Test
    public void test_tagContainsPhrase_returnsTrue() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("friend", true);
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));
    }

    @Test
    public void test_noFieldContainsPhrase_returnsFalse() {
        // Empty search phrase
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("", true);
        assertFalse(predicate.test(new PersonBuilder().withName("Alex Yeoh").build()));

        // Non-matching phrase
        predicate = new PersonContainsKeywordsPredicate("Zebra", true);
        assertFalse(predicate.test(new PersonBuilder().withName("Alex Yeoh").withPhone("12345")
                .withFacebook("alice.pauline").withAddress("Main Street").build()));
    }

    @Test
    public void test_specificSearch_returnsTrue() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("find n/Alice",
                false);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        predicate = new PersonContainsKeywordsPredicate("find t/friends",
                false);
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));
    }

    @Test
    public void test_specificSearch_returnsFalse() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("find n/Alice",
                false);
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").withPhone("91234567").build()));
    }

    @Test
    public void testSpecificSearch_withMultiplePhrases_returnsTrue() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("find n/Alice t/friends",
                false);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withTags("friends").build()));
    }

    @Test
    public void testSpecificSearch_withMultiplePhrases_returnsFalse() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("find n/Alice t/friends",
                false);
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").withTags("friends").build()));
    }

    @Test
    public void toStringMethod() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("testing phrase", true);

        String expected = PersonContainsKeywordsPredicate.class.getCanonicalName()
                + "{searchPhrase=testing phrase}";
        assertEquals(expected, predicate.toString());
    }
}
