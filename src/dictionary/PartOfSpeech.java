package dictionary;

public enum PartOfSpeech {
    NOUN("NOUN"),
    PRONOUN("PRONOUN"),
    VERB("VERB"),
    ADVERB("ADVERB"),
    ADJECTIVE("ADJECTIVE"),
    CONJUNCTION("CONJUNCTION"),
    PREPOSITION("PREPOSITION"),
    INTERJECTION("INTERJECTION"),
    UNDEFINED("UNDEFINED");

    private String name;

    PartOfSpeech(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
