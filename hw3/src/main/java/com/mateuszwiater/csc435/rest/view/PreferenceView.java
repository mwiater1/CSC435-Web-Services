package com.mateuszwiater.csc435.rest.view;

import com.mateuszwiater.csc435.rest.util.Response.Status;

import java.util.ArrayList;

public class PreferenceView extends ArticlesView {

    public static String postView(final Status status, final String message) {
        return getView(status, message, new ArrayList<>());
    }
}
