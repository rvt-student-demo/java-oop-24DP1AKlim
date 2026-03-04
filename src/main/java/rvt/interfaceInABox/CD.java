package rvt.interfaceInABox;

public class CD implements Packable {
    public String artist;
    public String name;
    public int publicationYear;

    public CD(String artist, String name, int publicationYear) {
        this.artist = artist;
        this.name = name;
        this.publicationYear = publicationYear;
    }

    public double weight() {
        return 0.1;
    }

    public String toString() {
        return artist + ": " + name + " (" + publicationYear + ")";
    }
}
