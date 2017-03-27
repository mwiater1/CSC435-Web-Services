package com.mateuszwiater.csc435.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mateuszwiater.csc435.db.DatabaseConnector;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class History {
    private boolean isNew;
    private final LocalDateTime requestTime;
    private final Map<String, String> parameters;
    private final String apiKey, endpoint, requestType;

    public History(final String apiKey, final String endpoint, final String requestType, final LocalDateTime requestTime, final Map<String, String> parameters) {
        this.isNew = true;
        this.apiKey = apiKey;
        this.endpoint = endpoint;
        this.parameters = parameters;
        this.requestTime = requestTime;
        this.requestType = requestType;
    }

    public void save() throws SQLException {
        if (isNew) {
            Gson g = new Gson();
            final String query = String.format("INSERT INTO HISTORY (APIKEY,ENDPOINT,REQUESTTYPE,REQUESTTIME,PARAMETERS) VALUES ('%s','%s','%s','%s','%s');",
                    apiKey, endpoint, requestType, g.toJson(requestTime), g.toJson(parameters));
            DatabaseConnector.runQuery(query);
        }
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getRequestType() {
        return requestType;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public static List<History> getHistory(final String apiKey) throws SQLException {
        final String query = String.format("SELECT APIKEY, ENDPOINT, REQUESTTYPE, REQUESTTIME, PARAMETERS FROM HISTORY WHERE APIKEY = '%s'", apiKey);

        Optional<List<List<String>>> res = DatabaseConnector.runQuery(query);

        final List<History> histories = new ArrayList<>();

        final Gson g = new Gson();
        final Type time = new TypeToken<LocalDateTime>(){}.getType();
        final Type map = new TypeToken<Map<String, String>>(){}.getType();
        res.ifPresent(l -> l.forEach(r -> histories.add(new History(r.get(0), r.get(1), r.get(2), g.fromJson(r.get(3), time), g.fromJson(r.get(4), map)))));
        histories.forEach(h -> h.isNew = false);

        return histories;
    }
}
