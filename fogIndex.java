import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class fogIndex {
    public static void main(String[] args) {
        // Check if at least one command line argument is given
        if (args.length < 1) {
            System.out.println("Please provide a file path as a command line argument");
            return;
        }

        // Read file path from command line argument (only first arg)
        String filePath = args[0];

        // Read file contents into text String
        String text = readFile("docs/" + filePath);

        // If file is empty, print error message and return
        if (text.equals("")) {
            System.out.println("File is empty");
            return;
        }

        // Calculate fog index
        int countWords = countWords(text);
        int countSentences = countSentences(text);
        int countComplexWords = countComplexWords(text);
        double fogIndex = calculateFogIndex(countWords, countSentences, countComplexWords);

        // Print results
        System.out.println("\nAmount of Words: " + countWords);
        System.out.println("Amount of Sentences: " + countSentences);
        System.out.println("Amount of Complex Words: " + countComplexWords);
        System.out.println("\nFog Index: " + String.format("%.3f", fogIndex) + "\n");
    }

    // Read file and return text as a string
    private static String readFile(String filePath) {
        // Create string builder to store text
        StringBuilder text = new StringBuilder();

        // Try to read file
        try (Scanner scanner = new Scanner(new File(filePath))) {
            // Read file line by line
            while (scanner.hasNextLine()) {
                // Append line to text
                text.append(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);  
        }

        // Return text as string
        return text.toString();
    }
    
    // // Count number of words in the text.
    public static int countWords(String text){
        String[] words = text.split("\\s+");
        int wordCount = words.length;
        
        return wordCount;
    }

    // Count number of sentences in the text.
    public static int countSentences(String text) {
        // Split text into sentences using regex to split on ., :, !, ?
        String[] sentences = text.split("[:.!?]");

        // Return number of sentences
        return sentences.length;
    }

    // Simplified version of counting syllabes of a word
    public static int countSyllables(String word) {
        // Count number of vowels = rough estimate of syllables
        int count = word.length() - word.replaceAll("[aeiouyAEIOUY]", "").length();
        if (word.endsWith("e"))
            count--;
        if (word.endsWith("es") || word.endsWith("ed")) {
            count--;
        }

        // Return number of syllables
        return Math.max(count, 1);
    }

    // Count number of complex words.
    // Complex words = words with more than 2 syllables
    public static int countComplexWords(String text) {
        // Split text into words
        String[] words = text.split(" ");
        int count = 0;
        
        // Iterate through words and count complex words
        for (String word : words) {
            if (countSyllables(word) > 2) {
                count++;
            }
        }

        return count;
    }
    
    // Calculate Gunning Fog Index
    public static double calculateFogIndex(int totalWords, int totalSentences, int numComplexWords){
        double wordsToSentencesRatio = (double)totalWords / (double)totalSentences;
        double complexToWordsRatio = (double)numComplexWords / totalWords;
        double ratio = 100 * complexToWordsRatio;
        double total = wordsToSentencesRatio + ratio;

        double result = 0.4 * total;
        return result;
    }


}
