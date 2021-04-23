package com.basejava.webapp.model;

import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private final List<Experience> periods;

    public OrganizationSection(List<Experience> periods) {
        this.periods = periods;
    }


    public void addPeriod(Experience period) {
        periods.add(period);
    }
    public List<Experience> getPeriods() {
        return periods;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Experience period : periods) {
            result.append(period.toString()).append("\n");
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(periods);
    }


}
