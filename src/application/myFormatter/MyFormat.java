package application.myFormatter;

import dictionary.Dictionary;
import dictionary.Vocabulary;

public class MyFormat implements MyFormatter {
    @Override
    public String format(Object obj) {
        StringBuilder output = new StringBuilder();
        Dictionary dictionary = new Dictionary();

        for (Vocabulary vocab: dictionary.getVocabularies()) {
            output.append(vocab.getWord()).append("\n");
            output.append(vocab.getMean()).append("\n");
            output.append(vocab.getPartOfSpeech().getName()).append("\n");
            output.append(vocab.getExample()).append("\n");
        }

        return output.toString();
    }
}
