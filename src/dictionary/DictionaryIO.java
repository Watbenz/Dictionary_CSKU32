package dictionary;

import application.myFormatter.MyFormat;
import application.myFormatter.MyFormatter;

import java.io.*;
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

        assert jarFile != null;                                         // Check jarFile is not null
        String pathJar = jarFile.getParent() + fileSep;                 // Get path of jar file

        this.workDir = new File(pathJar + "dictionaryData");
        workDir.mkdir();                                                // Making new directory
        this.dicFile = new File(workDir.getPath() + fileSep + "dictionary.txt");

        if (dicFile.createNewFile()) {                                  // if file is not exist
            try {
                System.out.println("Created");
                dictionaryInit();
                updateFile();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        else {
            readDictionary();
        }
    }

    private void dictionaryInit() throws IllegalAccessException {
        dictionary.addVocab(new Vocabulary(
                "void",
                "a large hole or empty space",
                PartOfSpeech.NOUN,
                "She stood at the edge of the chasm and stared into the void. " +
                        "Before Einstein, space was regarded as a formless void."
        ));

        dictionary.addVocab(new Vocabulary(
                "avoid",
                "to prevent something from happening or to not allow yourself to do something",
                PartOfSpeech.VERB,
                "The report studiously avoided any mention of the controversial plan."
        ));

        dictionary.addVocab(new Vocabulary(
                "null",
                "having a value of zero",
                PartOfSpeech.ADJECTIVE,
                "Browser performance was improved by analysing failed searches which return null set results."
        ));

        dictionary.addVocab(new Vocabulary(
                "instant",
                "happening immediately, without any delay",
                PartOfSpeech.ADJECTIVE,
                "This type of account offers you instant access to your money. " +
                        "Contrary to expectations, the film was an instant success."
        ));

        dictionary.addVocab(new Vocabulary(
                "constant",
                "staying the same, or not getting less or more",
                PartOfSpeech.ADJECTIVE,
                "The fridge keeps food at a constant temperature."
        ));
    }

    private void readDictionary() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dicFile));
            String line;
            while (!(line = reader.readLine()).equals("")) {
                String word = line;
                String mean = reader.readLine();
                String input = reader.readLine();
                PartOfSpeech speech = PartOfSpeech.parseSpeech(input);
                String example = reader.readLine();

                dictionary.addVocab(new Vocabulary(word, mean, speech, example));
            }
            reader.close();
        } catch (IOException | IllegalAccessException e) {
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
