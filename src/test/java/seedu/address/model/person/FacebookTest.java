package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FacebookTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Facebook(null));
    }

    @Test
    public void constructor_invalidFacebook_throwsIllegalArgumentException() {
        String invalidFacebook = "";
        assertThrows(IllegalArgumentException.class, () -> new Facebook(invalidFacebook));
    }

    @Test
    public void constructor_validFacebookWithAtPrefix_stripsAtForStorage() {
        Facebook facebook = new Facebook("@john.smith55");

        assertEquals("john.smith55", facebook.toString());
    }

    @Test
    public void getDisplayValue_returnsAtPrefixedHandle() {
        Facebook facebook = new Facebook("sarah.eats");
        assertEquals("@sarah.eats", facebook.getDisplayValue());
    }

    @Test
    public void constructor_validFacebookWithWhitespace_trimsInput() {
        Instagram facebookWithoutAtPrefix = new Instagram("  john.smith55  ");
        assertEquals("john.smith55", facebookWithoutAtPrefix.toString());

        Facebook facebookWithAtPrefix = new Facebook("  @john.smith55  ");
        assertEquals("john.smith55", facebookWithAtPrefix.toString());
    }

    @Test
    public void isValidFacebook() {
        // null facebook
        assertThrows(NullPointerException.class, () -> Facebook.isValidFacebook(null));

        // invalid facebook usernames
        assertFalse(Facebook.isValidFacebook("")); // empty string
        assertFalse(Facebook.isValidFacebook("    ")); // spaces only
        assertFalse(Facebook.isValidFacebook("john")); // less than 5 chars
        assertFalse(Facebook.isValidFacebook(".john55")); // starts with period
        assertFalse(Facebook.isValidFacebook("john55.")); // ends with period
        assertFalse(Facebook.isValidFacebook("john..smith55")); // consecutive periods
        assertFalse(Facebook.isValidFacebook("john_smith55")); // underscore not allowed
        assertFalse(Facebook.isValidFacebook("john-smith55")); // hyphen not allowed
        assertFalse(Facebook.isValidFacebook("john smith55")); // spaces not allowed

        // valid facebook usernames
        assertTrue(Facebook.isValidFacebook("john5")); // minimum length
        assertTrue(Facebook.isValidFacebook("johnsmith55"));
        assertTrue(Facebook.isValidFacebook("@johnsmith55"));
        assertTrue(Facebook.isValidFacebook("john.smith55"));
        assertTrue(Facebook.isValidFacebook("john.smith.55"));
        assertTrue(Facebook.isValidFacebook("@john.smith.55"));
        assertTrue(Facebook.isValidFacebook("A1b2c3"));
        assertTrue(Facebook.isValidFacebook("abcde12345abcde12345abcde12345abcde12345abcde12345")); // 50 chars
    }

    @Test
    public void equals() {
        Facebook facebook = new Facebook("john.smith55");

        // same values -> returns true
        assertTrue(facebook.equals(new Facebook("john.smith55")));

        // same values with @ prefix normalization -> returns true
        assertTrue(facebook.equals(new Facebook("@john.smith55")));

        // same object -> returns true
        assertTrue(facebook.equals(facebook));

        // null -> returns false
        assertFalse(facebook.equals(null));

        // different types -> returns false
        assertFalse(facebook.equals(5.0f));

        // different letter case -> returns true
        assertTrue(facebook.equals(new Facebook("John.Smith55")));

        // different punctuation (periods absent) -> returns true
        assertTrue(facebook.equals(new Facebook("johnsmith55")));

        // different case and punctuation (multiple periods) -> returns true
        assertTrue(facebook.equals(new Facebook("J.o.h.n.S.m.i.t.h.5.5")));
    }
}

