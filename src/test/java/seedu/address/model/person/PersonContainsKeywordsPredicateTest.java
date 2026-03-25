package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        PersonContainsKeywordsPredicate firstPredicate = new PersonContainsKeywordsPredicate("first");
        PersonContainsKeywordsPredicate secondPredicate = new PersonContainsKeywordsPredicate("second");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy = new PersonContainsKeywordsPredicate("first");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different search phrase -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // same phrase but general vs field-specific search -> not equal
        PersonContainsKeywordsPredicate generalAlice = new PersonContainsKeywordsPredicate("Alice");
        PersonContainsKeywordsPredicate nameAlice = new PersonContainsKeywordsPredicate("Alice",
                PersonContainsKeywordsPredicate.SearchType.NAME);
        assertFalse(generalAlice.equals(nameAlice));
    }

    @Test
    public void nullSearchPhrase_constructorThrows() {
        assertThrows(NullPointerException.class, () -> new PersonContainsKeywordsPredicate(null));
    }

    @Test
    public void nullSearchType_specificConstructorThrows() {
        assertThrows(NullPointerException.class, () -> new PersonContainsKeywordsPredicate("x", null));
    }

    @Test
    public void nullPerson_testThrows() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("any");
        assertThrows(NullPointerException.class, () -> predicate.test(null));
    }

    @Test
    public void test_nameContainsPhrase_returnsTrue() {
        // Full name match
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("Alex Yeoh");
        assertTrue(predicate.test(new PersonBuilder().withName("Alex Yeoh").build()));

        // Partial name match (substring)
        predicate = new PersonContainsKeywordsPredicate("Alex");
        assertTrue(predicate.test(new PersonBuilder().withName("Alex Yeoh").build()));

        // Case-insensitive match
        predicate = new PersonContainsKeywordsPredicate("aLex");
        assertTrue(predicate.test(new PersonBuilder().withName("Alex Yeoh").build()));
    }

    @Test
    public void test_phoneContainsPhrase_returnsTrue() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("12345");
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));
    }

    @Test
    public void test_facebookContainsPhrase_returnsTrue() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("alice.pau");
        assertTrue(predicate.test(new PersonBuilder().withFacebook("alice.pauline").build()));
    }

    @Test
    public void test_addressContainsPhrase_returnsTrue() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("Main Street");
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Main Street").build()));
    }

    @Test
    public void test_tagContainsPhrase_returnsTrue() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("friend");
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));
    }

    @Test
    public void test_noFieldContainsPhrase_returnsFalse() {
        // Empty search phrase
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("");
        assertFalse(predicate.test(new PersonBuilder().withName("Alex Yeoh").build()));

        // Non-matching phrase
        predicate = new PersonContainsKeywordsPredicate("Zebra");
        assertFalse(predicate.test(new PersonBuilder().withName("Alex Yeoh").withPhone("12345")
                .withFacebook("alice.pauline").withAddress("Main Street").build()));
    }

    @Test
    public void test_specificSearch_returnsTrue() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("Alice",
                PersonContainsKeywordsPredicate.SearchType.NAME);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        predicate = new PersonContainsKeywordsPredicate("friends",
                PersonContainsKeywordsPredicate.SearchType.TAG);
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));
    }

    @Test
    public void test_specificSearch_returnsFalse() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("Alice",
                PersonContainsKeywordsPredicate.SearchType.PHONE);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("91234567").build()));
    }

    @Test
    public void toStringMethod() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("testing phrase");

        String expected = PersonContainsKeywordsPredicate.class.getCanonicalName()
                + "{searchPhrase=testing phrase, generalSearch=true, searchType=null}";
        assertEquals(expected, predicate.toString());
    }
}
