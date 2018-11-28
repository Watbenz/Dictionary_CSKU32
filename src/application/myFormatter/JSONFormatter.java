package application.myFormatter;

import dictionary.Dictionary;
import dictionary.Vocabulary;

public class JSONFormatter implements MyFormatter {
    @Override
    public String format(Object obj) {
        StringBuilder output = new StringBuilder();
        Dictionary dictionary = (Dictionary) obj;
        output.append("[\n");

        for (Vocabulary vocab: dictionary.getVocabularies()) {
            output.append("\t{\n");
            output.append("\t\tvocab: ").append(vocab.getWord()).append("\",\n");             // vocab: "iterator",
            output.append("\t\tpartOfSpeech: ").append(vocab.getPartOfSpeech().getName()).append("\",\n");       // partOfSpeech: "VERB",
            output.append("\t\tmeaning: ").append(vocab.getMean()).append("\",\n");           // meaning: "To say, or do again; repeat",
            output.append("\t\texample: ").append(vocab.getExample()).append("\",\n");        // example: "She kept reiterating her request."

            output.append("\t},\n");
        }

        if (!dictionary.getVocabularies().isEmpty())                                    // If dictionary is not empty
            output.delete(output.length()-2, output.length()-1);                        // Delete last ,

        output.append("]\n");
        return output.toString();
    }
}
