package boggle;

import java.util.HashSet;
import java.util.Set;

/**
 * The BoggleStats class for the first Assignment in CSC207, Fall 2022
 * The BoggleStats will contain statsitics related to game play Boggle 
 */
public class BoggleStats {

    /**
     * set of words the player finds in a given round 
     */  
    private Set<String> playerWords = new HashSet<String>();  
    /**
     * set of words the computer finds in a given round 
     */  
    private Set<String> computerWords = new HashSet<String>();  
    /**
     * the player's score for the current round
     */  
    private int pScore; 
    /**
     * the computer's score for the current round
     */  
    private int cScore; 
    /**
     * the player's total score across every round
     */  
    private int pScoreTotal; 
    /**
     * the computer's total score across every round
     */  
    private int cScoreTotal; 
    /**
     * the average number of words, per round, found by the player
     */  
    private double pAverageWords; 
    /**
     * the average number of words, per round, found by the computer
     */  
    private double cAverageWords; 
    /**
     * the current round being played
     */  
    private int round; 

    /**
     * enumarable types of players (human or computer)
     */  
    public enum Player {
        Human("Human"),
        Computer("Computer");
        private final String player;
        Player(final String player) {
            this.player = player;
        }
    }

    /* BoggleStats constructor
     * ----------------------
     * Sets round, totals and averages to 0.
     * Initializes word lists (which are sets) for computer and human players.
     */
    public BoggleStats() {
        this.round = 0;
        this.cAverageWords = 0;
        this.pAverageWords = 0;
        this.cScoreTotal = 0;
        this.pScoreTotal = 0;
        this.cScore = 0;
        this.pScore = 0;
    }

    /* 
     * Add a word to a given player's word list for the current round.
     * You will also want to increment the player's score, as words are added.
     *
     * @param word     The word to be added to the list
     * @param player  The player to whom the word was awarded
     */
    public void addWord(String word, Player player) {
        if (player.player == "Human" ){
            this.playerWords.add(word);
            pScore ++;
        } else if (player.player == "Computer") {
            this.computerWords.add(word);
            cScore ++;
        }
    }

    /* 
     * End a given round.
     * This will clear out the human and computer word lists, so we can begin again.
     * The function will also update each player's total scores, average scores, and
     * reset the current scores for each player to zero.
     * Finally, increment the current round number by 1.
     */
    public void endRound() {
        // End a given round.

        // This will clear out the human and computer word lists, so we can begin again.
        this.playerWords.clear();
        this.computerWords.clear();
        // The function will also update each player's total scores, average scores.
        this.cScoreTotal += this.cScore;
        this.pScoreTotal += this.pScore;
        // The function will also reset the current scores for each player to zero.
        this.cScore = 0;
        this.pScore = 0;
        // Finally, increment the current round number by 1.
        this.round += 1;
//        this.cAverageWords = 0;
//        this.pAverageWords = 0;
    }

    /* 
     * Summarize one round of boggle.  Print out:
     * The words each player found this round.
     * Each number of words each player found this round.
     * Each player's score this round.
     */
    public void summarizeRound() {
        String cWordsFound = new String();
        int cNumWordsFound = 0;
        String pWordsFound = new String();
        int pNumWordsFound = 0;
        for(String s: this.computerWords) {
            cWordsFound = cWordsFound + ", " + s;
            cNumWordsFound += 1;
        }
        cWordsFound = cWordsFound.substring(2);
        for(String d: this.playerWords) {
            pWordsFound = pWordsFound + ", " + d;
            pNumWordsFound += 1;
        }
        pWordsFound = pWordsFound.substring(2);
        System.out.println("ROUND "+this.round+" SUMMARY");

        System.out.println("The Computer Players Score: " + this.cScore
        );
        System.out.println( "The Computer player found: " + cNumWordsFound + " words."
        );
        System.out.println( "The following are the words found by the computer player: " + cWordsFound
        );

        System.out.println("The Human Players Score: " + this.pScore
        );
        System.out.println("The Human player found: " + pNumWordsFound + " words."

        );
        System.out.println( "The following are the words found by the human player: " + pWordsFound
        );
    }

    /* 
     * Summarize the entire boggle game.  Print out:
     * The total number of rounds played.
     * The total score for either player.
     * The average number of words found by each player per round.
     */
    public void summarizeGame() {
        System.out.println("GAME SUMMARY:"
        );
        this.pAverageWords = this.pScoreTotal / this.round;
        this.cAverageWords = this.cScoreTotal / this.round;
        System.out.println("TOTAL NUMBER OF ROUNDS: " + this.round + " round(s)/n"
        );
        System.out.println(
                "TOTAL COMPUTER PLAYER SCORE: " + this.cScoreTotal
        );
        System.out.println(
                "TOTAL HUMAN PLAYER SCORE: " + this.pScoreTotal
        );
        System.out.println(
                "AVERAGE NUMBER OF WORDS FOUND BY COMPUTER PLAYER: " + this.cAverageWords
        );
        System.out.println(
                "AVERAGE NUMBER OF WORDS FOUND BY HUMAN PLAYER: " + this.pAverageWords
        );
    }

    /* 
     * @return Set<String> The player's word list
     */
    public Set<String> getPlayerWords() {
        return this.playerWords;
    }

    /*
     * @return int The number of rounds played
     */
    public int getRound() { return this.round; }

    /*
    * @return int The current player score
    */
    public int getScore() {
        return this.pScore;
    }

}
