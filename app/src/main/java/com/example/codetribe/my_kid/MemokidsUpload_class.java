package com.example.codetribe.my_kid;

/**
 * Created by CodeTribe on 9/2/2017.
 */

<<<<<<< HEAD:app/src/main/java/com/example/codetribe/my_kid/ImageUpload.java
public class ImageUpload {
    private String name;
    private String personsUploaded;
    private String uri;

    public ImageUpload(String name, String uri, String personsUploaded){
        this.name = name;
        this.uri = uri;
        this.personsUploaded = personsUploaded;
=======
public class MemokidsUpload_class {
    public String name;
    public String uri;
    public String personUploaded;

    public MemokidsUpload_class(String name, String uri, String personUploaded) {
        this.name = name;
        this.uri = uri;
        this.personUploaded = personUploaded;
>>>>>>> 8e30d93c600b1fbb2293eb17d630bb1d26dff173:app/src/main/java/com/example/codetribe/my_kid/MemokidsUpload_class.java
    }

    public MemokidsUpload_class() {
        this.name = name;
        this.uri = uri;
<<<<<<< HEAD:app/src/main/java/com/example/codetribe/my_kid/ImageUpload.java
        this.personsUploaded = personsUploaded;
=======
        this.personUploaded = personUploaded;
    }

    public String getPersonUploaded() {
        return personUploaded;
>>>>>>> 8e30d93c600b1fbb2293eb17d630bb1d26dff173:app/src/main/java/com/example/codetribe/my_kid/MemokidsUpload_class.java
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
