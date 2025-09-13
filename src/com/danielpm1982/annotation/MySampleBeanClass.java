package com.danielpm1982.annotation;
import java.util.Objects;
import java.util.UUID;

public class MySampleBeanClass {
    private final UUID id;
    @VisibleField
    private String name;
    @VisibleField
    private String address;
    @VisibleField
    private String phone;
    public MySampleBeanClass(String name, String address, String phone) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
    public UUID getId() {
        return id;
    }
    @VisibleMethod
    public String getName() {
        return name;
    }
    @VisibleMethod
    public void setName(String name) {
        this.name = name;
    }
    @VisibleMethod
    public String getAddress() {
        return address;
    }
    @VisibleMethod
    public void setAddress(String address) {
        this.address = address;
    }
    @VisibleMethod
    public String getPhone() {
        return phone;
    }
    @VisibleMethod
    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MySampleBeanClass that = (MySampleBeanClass) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getAddress(), that.getAddress()) && Objects.equals(getPhone(), that.getPhone());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAddress(), getPhone());
    }
    @Override
    public String toString() {
        return "MySampleBeanClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

/*
This is a sample bean class that will have its instance metadata analysed by the MyAnnotation class, using the
Reflection API. No real business modeling here, just a mockup bean, with fields (attributes) and properties (methods).
Two custom-created Annotations are used here, @VisibleField and @VisibleMethod. These two simple annotations are
markers to the fields and methods of this bean class, and will be used to filter the visible and not visible fields
and methods at the MyAnnotation class. Much more distinct logic operations can be performed using annotations along
with classes, fields, properties, methods, arguments and returns. For that, see Java documentation.
*/
