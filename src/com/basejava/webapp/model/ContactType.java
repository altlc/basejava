package com.basejava.webapp.model;

public enum ContactType {
    MOBILE_PHONE("Мобильный тел."),
    HOME_PHONE("Домашний тел."),
    SKYPE("Skype"),
    EMAIL("E-Mail"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STATCKOVERFLOW("Профиль Stackoverflow"),
    HOME_PAGE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
