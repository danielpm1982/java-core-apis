package com.danielpm1982.reflection;
import java.util.Objects;
import java.util.UUID;

public class MySampleBeanClass {
    private final UUID id;
    private String name;
    public MySampleBeanClass(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }
    public UUID getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MySampleBeanClass that = (MySampleBeanClass) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
    @Override
    public String toString() {
        return "SampleBeanClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

/*
This is a sample bean class that will have its instance metadata analysed by the Explorer class,
using the Reflection API. No real business modeling here, just a mockup bean, with fields
(attributes) and properties (methods).
*/
