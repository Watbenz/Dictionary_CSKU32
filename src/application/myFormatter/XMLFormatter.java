package application.myFormatter;

import dictionary.Dictionary;
import dictionary.Vocabulary;

public class XMLFormatter implements MyFormatter {
    @Override
    public String format(Object obj) {
        StringBuilder output = new StringBuilder();
        Dictionary dictionary = (Dictionary) obj;

        output.append("<Dictionary>\n");                                                            // <Dictionary>

        for (Vocabulary vocab: dictionary.getVocabularies()) {
            output.append("\t<Vocab word=\"").append(vocab.getWord()).append("\">\n");              // <Vocab word="word">
            output.append("\t\t<PartOfSpeech>").append(vocab.getPartOfSpeech().getName()).append("</PartOfSpeech>\n");  // <PartOfSpeech>NOUN</PartOfSpeech>
            output.append("\t\t<Meaning>").append(vocab.getMean()).append("</Meaning>\n");          // <Meaning>To say, or do again; repeat</Meaning>
            output.append("\t\t<Example>").append(vocab.getExample()).append("</Example>\n");       // <Example>She kept reiterating her request.</Example>
            output.append("\t</Vocab>\n");                                                          // </Vocab>
        }

        output.append("</Dictionary>\n");                                                           // </Dictionary>
        return output.toString();
    }
}
