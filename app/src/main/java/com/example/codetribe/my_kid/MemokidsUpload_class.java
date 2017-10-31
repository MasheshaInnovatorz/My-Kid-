package com.example.codetribe.my_kid;

/**
 * Created by CodeTribe on 9/2/2017.
 */


public class MemokidsUpload_class {
    public String name;
    public String uri;
    public String personUploaded;


    public MemokidsUpload_class(String name, String uri, String personUploaded) {
        this.name = name;
        this.uri = uri;
        this.personUploaded = personUploaded;

    }


    public MemokidsUpload_class() {
        this.name = name;
        this.uri = uri;
        this.personUploaded = personUploaded;

    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public String getPersonUploaded() {
        return personUploaded;
    }

}
