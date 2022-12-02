package boggle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.TreeSet;

/**
 * The Dictionary class for the first Assignment in CSC207, Fall 2022
 * The Dictionary will contain lists of words that are acceptable for Boggle 
 */
public class OnlineDictionary {

    /**
     * set of legal words for Boggle
     *  we will load the words depeding on the users preferences.
     *  but once a word is loaded, we want to temporariyl keep the word until the round is ended.
     */
    private TreeSet<String> fetchedWords;
    private String word;

    /**
     * Class constructor
     *
     * @param word the file containing a list of legal words.
     */
    public OnlineDictionary(String word) {
        String line = "";
        int wordcount = 0;
        this.fetchedWords = new TreeSet<String>();

        System.out.println("Initialized " + wordcount + " words in the Dictionary.");;
    }

    /* 
     * Checks to see if a provided word is in the dictionary.
     *
     * @param word  The word to check
     * @return  A boolean indicating if the word has been found
     */
    public boolean containsWord(String word) {
        // looping through the Treeset
        for(String s: this.fetchedWords){
            // Checking if the word equals a word s in the treeset
            if (Objects.equals(word.toLowerCase(), s)){
                return true;
            }
        }
        return false;
    }

    /* 
     * Checks to see if a provided string is a prefix of any word in the dictionary.
     *
     * @param str  The string to check
     * @return  A boolean indicating if the string has been found as a prefix
     */
    public boolean isPrefix(String str) {
        // looping through the Treeset
        for(String s: this.fetchedWords){
            int x = str.length();
            if (s.length() >= x) {

                if (s.substring(0, x).equals(str.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

}
