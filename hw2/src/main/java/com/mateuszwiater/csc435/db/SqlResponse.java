package com.mateuszwiater.csc435.db;

import com.mateuszwiater.csc435.util.Response;

import java.util.List;

public class SqlResponse extends Response<SqlStatus, List<List<String>>> {

    public SqlResponse(SqlStatus status, List<List<String>> lists, String message) {
        super(status, lists, message);
    }
}
