package dictionary;

public enum PartOfSpeech {
    Noun("Noun"),
    Pronoun("Pronoun"),
    Verb("Verb"),
    Adverb("Adverb"),
    Adjective("Adjective"),
    Conjunction("Conjunction"),
    Preposition("Preposition"),
    Interjection("Interjection"),
    Undefined("Undefined");

    private String name;

    PartOfSpeech(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
