package com.danielpm1982.optional;
import java.util.Objects;
import java.util.UUID;

public record MyItemRecord(UUID id, String name) {
    public MyItemRecord{
        Objects.requireNonNull(name, "The 'name' of this object must not be null !");
        id = UUID.randomUUID();
    }
    public MyItemRecord(String name){
        this(null, name);
    }
    @Override
    public String toString() {
        return "MyItemRecord instance: id="+id+" , name="+name;
    }
}

/*
This is a simple record that instantiates sample objects. Here we have two overloaded Constructors, one is the principal
Constructor of the record itself, receiving both "id" and "name" values, while the other is a custom canonical Constructor
that receives only the "name" value. The latter delegates the instantiation to the principal Constructor, passing null as
the id and the "name" value received. The principal record Constructor, either being called directly by the user or by the
overloaded custom Constructor, will set (or reset) the "id" value with a random UUID, and check that the "name" value
is not null. If the "name" value is not null, and after it sets (or resets) the "id" with the random UUID value, it is
then returned the full MyItemRecord instance with both fields set ("id" and "name"). Although the user is able to call the
principal Constructor of this record, he should actually always call the overloaded one, that only receives the "name"
argument, as any UUID passed by the user will actually be reset internally by another random UUID. Differently from a
traditional class Constructor, all instance variables must be declared at the principal record Constructor beforehand,
including the fields whose values will only be set internally and not by the user of the record, as in the case of the "id".
But, at least, we can overload that principal record Constructor with custom Constructors that receive only the arguments
expected to be set by the user. This avoids the user have to call the principal record Constructor passing null or useless
values as arguments, for example, in the case of "id" here, as any value passed for such fields will be reset internally.
*/
