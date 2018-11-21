package dictionary;

import java.util.ArrayList;

public class Dictionary {
    private ArrayList<Vocabulary> vocabularies;

    public Dictionary() {
        this.vocabularies = new ArrayList<>();
    }

    public void addVocab(Vocabulary vocab) throws IllegalAccessException {
        if (vocab == null) {
            throw new IllegalAccessException("There is no input");
        }
        for (Vocabulary each: vocabularies) {
            if (each.getWord().equals(vocab.getWord())) {
                throw new IllegalAccessException(vocab.getWord() + " is already have in this dictionary");
            }
        }
        vocabularies.add(vocab);
    }

    public void deleteVocab(String word) throws NoSuchFieldException, IllegalAccessException {
        if (word == null) {
            throw new IllegalAccessException("There is no input");
        }
        for (Vocabulary each: vocabularies) {
            if (each.getWord().equals(word)) {
                vocabularies.remove(each);
                return;
            }
        }
        throw new NoSuchFieldException("There is no \"" + word + "\" in this dictionary.");
    }

    public String findMeaning(String word) throws NoSuchFieldException {
        for (Vocabulary each: vocabularies) {
            if (each.getWord().equals(word)) {
                return each.getMean();
            }
        }
        throw new NoSuchFieldException("There is no \"" + word + "\" in this dictionary.");
    }

    public ArrayList<Vocabulary> getVocabularies() {
        return vocabularies;
    }
}
