package com.mateuszwiater.csc435.rest.view;

import com.mateuszwiater.csc435.model.Source;
import com.mateuszwiater.csc435.rest.util.Response;
import com.mateuszwiater.csc435.rest.util.Response.Status;

import java.util.List;

public class SourcesView {

    public static String getView(final Status status, final String message, final List<Source> sources) {
        return new SourceResponse(status, message, sources).toJson();
    }

    private static class SourceResponse extends Response {
        final List<Source> sources;

        SourceResponse(final Status status, final String message, final List<Source> sources) {
            super(status, message);
            this.sources = sources;
        }
    }
}
