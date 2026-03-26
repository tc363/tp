package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FACEBOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTAGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");

        person.getPhone().ifPresent(p ->
                sb.append(PREFIX_PHONE + p.value + " ")
        );

        person.getFacebook().ifPresent(fb ->
                sb.append(PREFIX_FACEBOOK + fb.value + " ")
        );

        person.getInstagram().ifPresent(ig ->
                sb.append(PREFIX_INSTAGRAM + ig.value + " ")
        );

        person.getAddress().ifPresent(a ->
                sb.append(PREFIX_ADDRESS + a.value + " ")
        );

        person.getRemark().ifPresent(r ->
                sb.append(PREFIX_REMARK + r.value + " ")
        );

        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        appendOptionalEditField(sb, PREFIX_PHONE.toString(), descriptor.isPhoneEdited(),
                descriptor.getPhone().map(phone -> phone.value).orElse(null));
        appendOptionalEditField(sb, PREFIX_FACEBOOK.toString(), descriptor.isFacebookEdited(),
                descriptor.getFacebook().map(facebook -> facebook.value).orElse(null));
        appendOptionalEditField(sb, PREFIX_INSTAGRAM.toString(), descriptor.isInstagramEdited(),
                descriptor.getInstagram().map(instagram -> instagram.value).orElse(null));
        appendOptionalEditField(sb, PREFIX_ADDRESS.toString(), descriptor.isAddressEdited(),
                descriptor.getAddress().map(address -> address.value).orElse(null));
        appendOptionalEditField(sb, PREFIX_REMARK.toString(), descriptor.isRemarkEdited(),
                descriptor.getRemark().map(remark -> remark.value).orElse(null));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }

    /** Appends either a set value (prefix+value) or a clear marker (bare prefix) for edited optional fields. */
    private static void appendOptionalEditField(StringBuilder sb, String prefix, boolean isEdited, String value) {
        if (!isEdited) {
            return;
        }
        sb.append(prefix);
        if (value != null) {
            sb.append(value);
        }
        sb.append(" ");
    }
}
