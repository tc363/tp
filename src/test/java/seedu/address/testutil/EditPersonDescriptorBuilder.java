package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Facebook;
import seedu.address.model.person.Instagram;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        person.getPhone().ifPresent(descriptor::setPhone);
        person.getFacebook().ifPresent(descriptor::setFacebook);
        person.getInstagram().ifPresent(descriptor::setInstagram);
        person.getAddress().ifPresent(descriptor::setAddress);
        person.getRemark().ifPresent(descriptor::setRemark);
        descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /** Clears the {@code Phone} in the {@code EditPersonDescriptor} that we are building. */
    public EditPersonDescriptorBuilder clearPhone() {
        descriptor.clearPhone();
        return this;
    }

    /**
     * Sets the {@code Facebook} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withFacebook(String facebook) {
        descriptor.setFacebook(new Facebook(facebook));
        return this;
    }

    /** Clears the {@code Facebook} in the {@code EditPersonDescriptor} that we are building. */
    public EditPersonDescriptorBuilder clearFacebook() {
        descriptor.clearFacebook();
        return this;
    }

    /**
     * Sets the {@code Instagram} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withInstagram(String instagram) {
        descriptor.setInstagram(new Instagram(instagram));
        return this;
    }

    /** Clears the {@code Instagram} in the {@code EditPersonDescriptor} that we are building. */
    public EditPersonDescriptorBuilder clearInstagram() {
        descriptor.clearInstagram();
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /** Clears the {@code Address} in the {@code EditPersonDescriptor} that we are building. */
    public EditPersonDescriptorBuilder clearAddress() {
        descriptor.clearAddress();
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withRemark(String remark) {
        descriptor.setRemark(new Remark(remark));
        return this;
    }

    /** Clears the {@code Remark} in the {@code EditPersonDescriptor} that we are building. */
    public EditPersonDescriptorBuilder clearRemark() {
        descriptor.clearRemark();
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
