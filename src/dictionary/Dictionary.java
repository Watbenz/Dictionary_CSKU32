package dictionary;

import application.MyFormatter;

import java.util.ArrayList;

public class Dictionary {
    private ArrayList<Vocabulary> vocabularies;

    public Dictionary() {
        this.vocabularies = new ArrayList<>();
    }

    public void addVocab(Vocabulary vocab) throws IllegalAccessException {
        if (vocab.getWord().equals("")) {
            throw new IllegalAccessException("There is no word input");
        }
        if (vocab.getMean().equals("")) {
            throw new IllegalAccessException("There is no mean input");
        }
        for (Vocabulary each: vocabularies) {
            if (each.getWord().equals(vocab.getWord())) {
                throw new IllegalAccessException(vocab.getWord() + " is already have in this dictionary");
            }
        }
        vocabularies.add(vocab);
    }

    public void deleteVocab(String word) throws NoSuchFieldException, IllegalAccessException {
        if (word.equals("")) {
            throw new IllegalAccessException("There is no word input");
        }
        for (Vocabulary each: vocabularies) {
            if (each.getWord().equals(word)) {
                vocabularies.remove(each);
                return;
            }
        }
        throw new NoSuchFieldException("There is no \"" + word + "\" in this dictionary.");
    }

    public Vocabulary findVocab(String word) throws NoSuchFieldException {
        for (Vocabulary each: vocabularies) {
            if (each.getWord().equals(word)) {
                return each;
            }
        }
        throw new NoSuchFieldException("There is no \"" + word + "\" in this dictionary.");
    }

    public void editVocab(String word, Vocabulary editedVocab) throws NoSuchFieldException {
        if (editedVocab.getWord().equals("")) {
            throw new NoSuchFieldException("There is no word input");
        }
        if (editedVocab.getMean().equals("")) {
            throw new NoSuchFieldException("There is no mean input");
        }
        for (int i=0; i<vocabularies.size(); i++) {
            Vocabulary each = vocabularies.get(i);
            if (word.equals(each.getWord())) {
                vocabularies.remove(i);
                vocabularies.add(i, editedVocab);
                return;
            }
        }
        throw new NoSuchFieldException("There is no \"" + word + "\" in this dictionary.");
    }

    public String format(MyFormatter format) {
        return format.format(this);
    }

    public ArrayList<Vocabulary> getVocabularies() {
        return vocabularies;
    }
}
