import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class fogIndex {
    public static void main(String[] args) {
        // Read file in
        String filePath = "/file.txt";
        try {
            // Read file contents into text String
            String text = readFile(filePath);

            // Calculate fog index
            double fogIndex = calculateFogIndex(text);
            System.out.println("Fog index: " + fogIndex);
            
        } catch (Exception e) {
            System.out.println("Error reading file");
        }
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
        }

        // Return text as string
        return text.toString();
    }

    // Count number of sentences in the text.
    public static int countSentences(String text) {
        // Split text into sentences using regex to split on ., !, ?
        String[] sentences = text.split("[.!?]");

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


}
