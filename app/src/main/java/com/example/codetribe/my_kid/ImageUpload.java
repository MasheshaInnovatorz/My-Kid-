package com.example.codetribe.my_kid;

/**
 * Created by CodeTribe on 9/2/2017.
 */

public class ImageUpload {
    public String name;
    public String uri;
    public String personUploaded;

    public ImageUpload(String name, String uri,String personUploaded) {
        this.name = name;
        this.uri = uri;
        this.personUploaded = personUploaded;
    }

    public ImageUpload() {
        this.name = name;
        this.uri = uri;
        this.personUploaded = personUploaded;
    }

    public String getPersonUploaded() {
        return personUploaded;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }


}
