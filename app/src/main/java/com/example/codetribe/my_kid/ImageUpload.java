package com.example.codetribe.my_kid;

/**
 * Created by CodeTribe on 9/2/2017.
 */

public class ImageUpload {
    private String name;
    private String personsUploaded;
    private String uri;

    public ImageUpload(String name, String uri, String personsUploaded){
        this.name = name;
        this.uri = uri;
        this.personsUploaded = personsUploaded;
    }

    public ImageUpload() {
        this.name = name;
        this.uri = uri;
        this.personsUploaded = personsUploaded;
    }


    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public String getPersonsUploaded() {
        return personsUploaded;
    }

}
