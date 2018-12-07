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

    public static PartOfSpeech parseSpeech(String string) {
        switch (string) {
            case "NOUN":
                return PartOfSpeech.NOUN;
            case "PRONOUN":
                return PartOfSpeech.PRONOUN;
            case "VERB":
                return PartOfSpeech.VERB;
            case "ADVERB":
                return PartOfSpeech.ADVERB;
            case "ADJECTIVE":
                return PartOfSpeech.ADJECTIVE;
            case "CONJUNCTION":
                return PartOfSpeech.CONJUNCTION;
            case "PREPOSITION":
                return PartOfSpeech.PREPOSITION;
            case "INTERJECTION":
                return PartOfSpeech.INTERJECTION;
        }
        return PartOfSpeech.UNDEFINED;
    }
}
