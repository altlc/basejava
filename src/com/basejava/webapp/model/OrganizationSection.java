package com.basejava.webapp.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationSection extends Section {
    private static final long serialVersionUID = 1L;
    private List<Organisation> organisationList;

    public OrganizationSection() {
    }

    public OrganizationSection(List<Organisation> organisations) {
        this.organisationList = organisations;
    }

    public void addOrganisation(Organisation organisation) {
        organisationList.add(organisation);
    }

    public List<Organisation> getOrganisationsList() {
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
        for (Organisation organisation : organisationList) {
            result.append(organisation.toString()).append("\n");
        }
        return result.toString();
    }

}
