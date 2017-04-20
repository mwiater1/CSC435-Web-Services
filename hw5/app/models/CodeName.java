package models;

import com.google.gson.annotations.Expose;

abstract class CodeName {
    @Expose
    final String name, code;

    public CodeName(final String name, final String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
