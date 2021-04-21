package com.basejava.webapp.model;

import java.util.List;
import java.util.Objects;

public class ResumePeriodSection extends Section{
    private final List<ResumePeriod> periods;

    public ResumePeriodSection(List<ResumePeriod> periods) {
        this.periods = periods;
    }

    public void addPeriod(ResumePeriod period) {
        periods.add(period);
    }

    public void deletePeriod(int index) {
        periods.remove(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResumePeriodSection that = (ResumePeriodSection) o;
        return periods.equals(that.periods);
    }

    @Override
    public String toString() {
        return periods.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(periods);
    }

    public List<ResumePeriod> getPeriods() {
        return periods;
    }

}
