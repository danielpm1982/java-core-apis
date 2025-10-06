package com.danielpm1982.networking.webservice02;

public record MyAddress(String zipCode, String street, String number, String complement, String neighbourhood,
                        String city, String stateCode,String state, String country){
    public MyAddress() {
        this("","","","","","","","","");
    }
    public boolean isEmpty(){
        return this.zipCode.compareTo("")==0;
    }
}

/*
This is a record (immutable) structure with only immutable type fields (String). If no arg is passed at instantiation, the
base constructor initializes all field values as "", or empty. A custom method isEmpty() is implemented to return if a specific
instance, of such a record, is empty or not, checking if the zipCode is empty or not.
*/
