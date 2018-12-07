package dictionary;

import application.myFormatter.MyFormat;
import application.myFormatter.MyFormatter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;

public class DictionaryIO {
    private File workDir;
    private File dicFile;
    private Dictionary dictionary;

    public DictionaryIO(Dictionary dictionary) {
        this.dictionary = dictionary;

        try {
            initFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initFile() throws IOException {
        File jarFile = null;

        try {
            jarFile = new File(DictionaryIO.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String fileSep = System.getProperty("file.separator");

        assert jarFile != null;

        String filePath = jarFile.getParent() + fileSep + "dictionaryData";
        this.workDir = new File(filePath);
        this.workDir.mkdir();
        this.dicFile = new File(workDir.getPath() + fileSep + "dictionary.txt");

        if (this.dicFile.createNewFile()) {
            try {
                dictionaryInit();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            updateFile();
        } else {
            readDictionary();
        }

    }

    private void dictionaryInit() throws IllegalAccessException {
        this.dictionary.addVocab(new Vocabulary("void", "a large hole or empty space", PartOfSpeech.NOUN, "She stood at the edge of the chasm and stared into the void. Before Einstein, space was regarded as a formless void."));
        this.dictionary.addVocab(new Vocabulary("avoid", "to prevent something from happening or to not allow yourself to do something", PartOfSpeech.VERB, "The report studiously avoided any mention of the controversial plan."));
        this.dictionary.addVocab(new Vocabulary("null", "having a value of zero", PartOfSpeech.ADJECTIVE, "Browser performance was improved by analysing failed searches which return null set results."));
        this.dictionary.addVocab(new Vocabulary("instant", "happening immediately, without any delay", PartOfSpeech.ADJECTIVE, "This type of account offers you instant access to your money. Contrary to expectations, the film was an instant success."));
        this.dictionary.addVocab(new Vocabulary("constant", "staying the same, or not getting less or more", PartOfSpeech.ADJECTIVE, "The fridge keeps food at a constant temperature."));
    }

    private void readDictionary() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dicFile));

            String line;
            while(!(line = reader.readLine()).equals("")) {
                String mean = reader.readLine();
                PartOfSpeech speech = PartOfSpeech.parseSpeech(reader.readLine());
                String example = reader.readLine();
                this.dictionary.addVocab(new Vocabulary(line, mean, speech, example));
            }

            reader.close();
        } catch (IllegalAccessException | IOException e) {
            e.printStackTrace();
        }

    }

    public void updateFile() {
        try {
            MyFormatter formatter = new MyFormat();
            PrintWriter writer = new PrintWriter(new FileWriter(dicFile));
            writer.println(formatter.format(dictionary));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}