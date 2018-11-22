package dictionary;

public class Vocabulary {
    private String word;
    private String mean;
    private PartOfSpeech partOfSpeech;
    private String example;

    public Vocabulary(String word, String mean, PartOfSpeech partOfSpeech, String example) {
        this.word = word;
        this.mean = mean;
        this.partOfSpeech = partOfSpeech;
        this.example = example;
    }

    public String getWord() {
        return word;
    }

    public String getMean() {
        return mean;
    }

    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    public String getExample() {
        return example;
    }
}
