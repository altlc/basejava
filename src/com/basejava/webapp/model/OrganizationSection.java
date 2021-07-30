package com.basejava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationSection extends Section {
    private static final long serialVersionUID = 1L;
    private List<Organization> organisationList;

    public OrganizationSection() {
    }

    public OrganizationSection(List<Organization> organisations) {
        this.organisationList = organisations;
    }

    public OrganizationSection(Organization... organisations) {
        this(Arrays.asList(organisations));
    }

    public void addOrganization(Organization organisation) {
        organisationList.add(organisation);
    }

    public List<Organization> getOrganizationsList() {
        return organisationList;
    }

    @Override
    public int hashCode() {
        return Objects.hash(organisationList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return organisationList.equals(that.organisationList);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Organization organisation : organisationList) {
            result.append(organisation.toString()).append("\n");
        }
        return result.toString();
    }

}
