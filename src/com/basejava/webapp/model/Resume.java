package com.basejava.webapp.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {
    // Unique identifier
    private final String uuid;
    private final String fullName;

    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void addContact(ContactType contactType, String contact) {
        contacts.put(contactType, contact);
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public void addSection(SectionType sectionType, AbstractSection section) {
        sections.put(sectionType, section);
    }

    public AbstractSection getSection(SectionType type) {
        return sections.get(type);
    }

    @Override
    public int compareTo(Resume o) {
        int compareResult = fullName.compareTo(o.fullName);
        //Если fullName разные, вернем результат сравнения, если одинакаовые сравним uuid
        return compareResult == 0 ? uuid.compareTo(o.uuid) : compareResult;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) && fullName.equals(resume.fullName) && contacts.equals(resume.contacts) && sections.equals(resume.sections);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append(uuid).append("\n").append("\033[1m").append(fullName).append("\033[0m").append("\n\n");
        contacts.forEach((k, v) -> result.append(k.getTitle()).append(": ").append(v).append("\n"));
        result.append("\n");
        sections.forEach((k, v) -> result.append("\033[1m").append(k.getTitle()).append("\n\n").append("\033[0m").append(v).append("\n"));

        return result.toString();
    }
}