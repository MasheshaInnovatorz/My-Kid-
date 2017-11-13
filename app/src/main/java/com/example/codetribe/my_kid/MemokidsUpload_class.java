package com.example.codetribe.my_kid;

/**
 * Created by CodeTribe on 9/2/2017.
 */


public class MemokidsUpload_class {
    public String name;
    public String uri;
    public String personUploaded;
    public long timeUploaded;


    public MemokidsUpload_class( String name, String uri, String personUploaded, long timeUploaded) {

        this.name = name;
        this.uri = uri;
        this.personUploaded = personUploaded;
        this.timeUploaded =timeUploaded;

    }


    public MemokidsUpload_class() {

        this.name = name;
        this.uri = uri;
        this.personUploaded = personUploaded;
        this.timeUploaded = timeUploaded;

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

    public long getTimeUploaded() {
        return timeUploaded;
    }
}
