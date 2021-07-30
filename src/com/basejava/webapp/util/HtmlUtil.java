package com.basejava.webapp.util;

import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Organization;

public class HtmlUtil {
    public static String contactToLink(ContactType type, String contact) {
        switch (type) {
            case EMAIL:
                return "mailto:" + contact;
            case SKYPE:
                return "skype:" + contact;
            case HOME_PHONE:
            case MOBILE_PHONE:
                return "tel:" + contact;
            case GITHUB:
            case STATCKOVERFLOW:
            case LINKEDIN:
            case HOME_PAGE:
            default:
                return contact;
        }
    }

    public static String formatDate(Organization.Stage stage) {
        return DateUtil.format(stage.getStartDate()) + " - " + DateUtil.format(stage.getEndDate());
    }
}
