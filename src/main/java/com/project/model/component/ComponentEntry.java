package com.project.model.component;

import javax.persistence.*;
import java.util.Objects;

@Deprecated
@Entity
@Table(schema = "APP", name = "COMPONENT_ENTRY")
public class ComponentEntry {
    private int id;

    @GeneratedValue
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String jsonScript;

    @Basic
    @Column(name = "JSON_STRING")
    public String getJsonScript() {
        return jsonScript;
    }

    public void setJsonScript(String jsonScript) {
        this.jsonScript = jsonScript;
    }

    private boolean valid;

    @Basic
    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentEntry that = (ComponentEntry) o;
        return id == that.id &&
                valid == that.valid &&
                Objects.equals(jsonScript, that.jsonScript);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, jsonScript, valid);
    }

    @Override
    public String toString() {
        return "ComponentEntry{" +
                "id=" + id +
                ", jsonScript='" + jsonScript + '\'' +
                ", valid=" + valid +
                '}';
    }
}
