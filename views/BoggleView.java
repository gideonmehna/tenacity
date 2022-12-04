package views;

import boggle.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;



/**
 * Boggle View
 *
 * This is The class that displays boggle.
 */
public class BoggleView {
    BoggleGame model;
    BoggleGrid modelBoggleGrid;
    BorderPane borderPane;
    GridPane grid;
    Text instructionsText;
    int size;
    VBox instructionsBox; // this holds the instructions and the game
    Stage stage;
    Button startButton,endButton, enterButton, newGame;
    ArrayList<Button> gridButtons;
    BoggleStats gameStats;
    final String[] inputWord = {new String()}; // The word the user is typing
    Text wordInput ; // display the word the user is typing

    /**
     * BoggleView Class constructor
     * It also runs the initUI() function that displays the game.
     *
     * @param game, this is the game model
     * @param stage, this is the window
     *
     *
     */
    public BoggleView(BoggleGame game, Stage stage) throws Exception {
        this.stage = stage;
        this.model = game;
        initUI();
    }
    /**
     * initUI() Initializes the GUI of BoggleGame
     *
     * This displays and handles the events of the whole Boggle Game
     */
     public void initUI() {
         // set the stage
         this.stage.setTitle("TenaCity Boggle");

         borderPane = new BorderPane();
         borderPane.setStyle("-fx-background-image: url(\"images/Bubbles.png\");");
         giveFirstInstructions();

         var scene = new Scene(borderPane, 1200, 600);
         this.stage.setScene(scene);
         this.stage.show();
     }




    /**
     *  Gives the first User Instructions. Talks about the main game functions.
     *
     *
     */
    private void giveFirstInstructions() {

        String instructions = this.model.loadInstructions();

        instructionsText = new Text();
        instructionsText.setText(instructions);
        instructionsText.setX(50);
        instructionsText.setY(50);
        instructionsText.setFont(new Font(12));

        Button next = new Button("Next");
        next.setId("Next");
        next.setPrefSize(50, 50);
        next.setFont(new Font(12));
        next.setStyle("-fx-background-color: #10871b; -fx-text-fill: white;");

        next.setOnAction(e -> {
            giveSecondInstructions();
        });

        instructionsBox = new VBox( instructionsText, next);
        instructionsBox.setPadding(new Insets(20, 20, 20, 20));
        instructionsBox.setAlignment(Pos.CENTER);
        borderPane.setCenter(instructionsBox);

    }

    /**
     *  Gives the Second User Instructions. Asks for the grid size.
     *
     *
     */
    private void giveSecondInstructions() {
        instructionsBox.getChildren().clear();

        String instruction = "Enter 5 to play on a big (5x5) grid; 4 to play on a small (4x4) one:";

        instructionsText.setText(instruction);

        TextField gridSize = new TextField();
        gridSize.setAlignment(Pos.CENTER);
        gridSize.setMaxSize(100, 100);

        Label message = new Label();
        message.setFont(new Font(12));
        message.setStyle("-fx-background-color: #10871b; -fx-text-fill: red;");

        Button next = new Button("Next");
        next.setId("Next");
        next.setPrefSize(50, 50);
        next.setFont(new Font(12));
        next.setStyle("-fx-background-color: #10871b; -fx-text-fill: white;");

        gridSize.setOnAction(e -> {
            next.fire();
        });


        next.setOnAction(e -> {
            String text = gridSize.getText();
            if(text.matches("[4-9]")){
                System.out.println(text + " passes the regex");
                size = Integer.parseInt( text);
                message.setText("Great choice!");
                giveThirdInstructions();
            }
            else
            {System.out.println("No, this is not regex");
                message.setText("Please type either 5 or 4.");
            }
        });
        instructionsBox.getChildren().addAll(instructionsText, message, gridSize, next);

    }
    /**
     *  Gives the Third User Instructions.
     *  Asks the user to provide a string of characters to be used for the game or to let the computer generate
     *
     *
     */
    private void giveThirdInstructions() {
        instructionsBox.getChildren().clear();
        final String[] s = {new String()};
        String instruction = new String();
        Label message = new Label();


        TextField randomLetters = new TextField();
        randomLetters.setAlignment(Pos.CENTER);
        randomLetters.setMaxSize(100, 100);

        Button random = new Button("Randomly Assign Letters");
        random.setId("random");
        random.setPrefSize(200, 50);
        random.setFont(new Font(12));
        random.setStyle("-fx-background-color: #10871b; -fx-text-fill: white;");

        Button usersLetters = new Button("Provide Your Own");
        usersLetters.setId("usersLetters");
        usersLetters.setPrefSize(200, 50);
        usersLetters.setFont(new Font(12));
        usersLetters.setStyle("-fx-background-color: #10871b; -fx-text-fill: white;");

        startButton = new Button("Start");
        startButton.setId("Start");
        startButton.setPrefSize(150, 50);
        startButton.setFont(new Font(12));
        startButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);

        if (size == 4 || size == 5){
            instruction = "Click one of the buttons below to either randomly assign letters or to provide your own";
            buttonBox.getChildren().addAll(random, usersLetters);


        }
        else if( (6 < size) && (size <= 9)  ) {
            instruction = "Randomly assign Letters for your board.";
            buttonBox.getChildren().addAll(randomLetters);

        }

        instructionsText.setText(instruction);

        instructionsBox.getChildren().addAll(instructionsText, buttonBox);

        usersLetters.setOnAction(e -> {
            buttonBox.getChildren().clear();
            instructionsBox.getChildren().remove(buttonBox);
            instructionsBox.getChildren().addAll(randomLetters);
        });


        random.setOnAction(e ->{
            instructionsBox.getChildren().remove(instructionsText);
            message.setText("Ready to Start");
            instructionsBox.getChildren().clear();
            instructionsBox.getChildren().addAll(message, startButton);
        });
        randomLetters.setOnAction(e-> {
            s[0] = randomLetters.getText();
            s[0] = s[0].toUpperCase().strip();// mke sure there are no numbers
            if (s[0].length() < size * size) message.setText("You have entered few letters, please enter "+ size * size +" letters");
            else if (s[0].length() > size * size) {
                s[0] = s[0].substring(0, size * size);
            }
            if (s[0].length() == size * size) {
                message.setText("Ready to Start");
                instructionsBox.getChildren().clear();
                instructionsBox.getChildren().addAll(message, startButton);
            }
        });
        startButton.setOnAction(e -> {
            if(s[0].isEmpty()){
                String randomlyGeneratedString = this.model.randomizeLetters(size);
                s[0] = randomlyGeneratedString;
            }
            launchBoard(s[0]);
            borderPane.requestFocus();
        });


    }

    /**
     * launches the Gridpane with the Boggle Grid once the user has gone through the instructions
     *
     * This displays and handles the events of the whole Boggle Game
     */
    private void launchBoard(String s){
        // Clear all the instrucitons in the box
        instructionsBox.getChildren().clear();
        // prepare the variables to be used
        wordInput = new Text();
        wordInput.setText(inputWord[0]);

         // Get the grid , add the string
        grid = populateGridPane(size, s);
        grid.setAlignment(Pos.CENTER);
        Node boggleGrid = grid;

        // Add the grid
        instructionsBox.getChildren().addAll(boggleGrid);
        launchControls();
        instructionsBox.setAlignment(Pos.CENTER);

    }
    /**
     * launches the Buttons that go with the Boggle Grid.
     *
     * this enables the user t
     */
    private void launchControls(){
        TextField textInputField = new TextField();
        textInputField.setAlignment(Pos.CENTER);
        textInputField.setId("boggleWordInput");
        textInputField.setLayoutY(30);
        textInputField.setLayoutX(100);
        textInputField.setText(inputWord[0]);

        Label enterText = new Label("Enter a word from the grid:");

        VBox textInput = new VBox(enterText, textInputField);

        Button clear = new Button("Clear Text");
        clear.setId("Start");
        clear.setPrefSize(150, 50);
        clear.setFont(new Font(12));
        clear.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        endButton = new Button("End Round");
        endButton.setId("end");
        endButton.setPrefSize(150, 50);
        endButton.setFont(new Font(12));
        endButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        enterButton = new Button("Enter");
        enterButton.setId("enter");
        enterButton.setPrefSize(150, 50);
        enterButton.setFont(new Font(12));
        enterButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

//        wordInput.setText(inputWord[0]);

        HBox controls = new HBox(clear, wordInput, enterButton);
        VBox vControls = new VBox(controls, textInput);
        instructionsBox.getChildren().addAll(vControls, endButton);

        clear.setOnAction(e -> {
            inputWord[0] = "";
            wordInput.setText(inputWord[0]);
        });
        textInputField.setOnAction(e -> {
            enterButton.fire();
        });
        enterButton.setOnAction(e -> {
            if (inputWord[0].isEmpty()) inputWord[0] = textInputField.getText();
            wordInput.setText(inputWord[0]);
            HBox box1 = new HBox(5);
            VBox box2 = new VBox(5);
            HBox box3 = new HBox(10);
            Label wordCheck = new Label();
            if (this.model.evaluateWord(inputWord[0]) == 2){
                wordCheck.getStyleClass().add("bg-4");
                wordCheck.setText("Correct! " + inputWord[0]);
                SVGPath githubIcon = new SVGPath();
                githubIcon.setContent("m36.097 739.31 20-30c16.511 12.907 17.767 19.639 24.949 30.909 36.804-72.31 " +
                        "74.954-104.96 128.57-144.29-51.91 53.35-83.23 89.32-130 198.58-16.193-26.29-27.333-53." +
                        "62-43.523-55.2z");
                githubIcon.setFill(Color.web("#81c483"));
                box2.getChildren().addAll(wordCheck, githubIcon);
            }else if (this.model.evaluateWord(inputWord[0]) == 1){
                wordCheck.getStyleClass().add("bg-3");
                wordCheck.setText("This word has already been inputted");
                SVGPath githubIcon = new SVGPath();
                githubIcon.setContent("M42.42,12.401c0.774-0.774,0.774-2.028,0-2.802L38.401,5.58c-0.774-0.774-2.028-" +
                        "0.774-2.802,0	L24,17.179L12.401,5.58c-0.774-0.774-2.028-0.774-2.802,0L5.58,9.599c-0.774," +
                        "0.774-0.774,2.028,0,2.802L17.179,24L5.58,35.599	c-0.774,0.774-0.774,2.028,0,2.802l4.019," +
                        "4.019c0.774,0.774,2.028,0.774,2.802,0L42.42,12.401z M24,30.821L35.599,42.42c0.774,0.774," +
                        "2.028,0.774,2.802,0l4.019-4.019    c0.774-0.774,0.774-2.028,0-2.802L30.821,24L24,30.821z");
                githubIcon.setFill(Color.web("#a8142e"));
                githubIcon.autosize();
                box2.getChildren().addAll(wordCheck, githubIcon);
            }else if (this.model.evaluateWord(inputWord[0]) == 0){
                wordCheck.getStyleClass().add("bg-2");
                wordCheck.setText("Incorrect word :(");
                SVGPath githubIcon = new SVGPath();
                githubIcon.setContent("M42.42,12.401c0.774-0.774,0.774-2.028,0-2.802L38.401,5.58c-0.774-0.774-2.028-" +
                        "0.774-2.802,0	L24,17.179L12.401,5.58c-0.774-0.774-2.028-0.774-2.802,0L5.58,9.599c-0.774," +
                        "0.774-0.774,2.028,0,2.802L17.179,24L5.58,35.599	c-0.774,0.774-0.774,2.028,0,2.802l4.019," +
                        "4.019c0.774,0.774,2.028,0.774,2.802,0L42.42,12.401z M24,30.821L35.599,42.42c0.774,0.774," +
                        "2.028,0.774,2.802,0l4.019-4.019    c0.774-0.774,0.774-2.028,0-2.802L30.821,24L24,30.821z");
                githubIcon.setFill(Color.web("#a8142e"));
                githubIcon.autosize();
                box2.getChildren().addAll(wordCheck, githubIcon);
            }
            Label wordCount = new Label("Words left: " +
                    (this.model.getAllWords().length - this.model.getGameStats().getScore()));
            box1.getChildren().add(wordCount);
            String wordFound = "Words Found: ";
            for (String word: this.model.getGameStats().getPlayerWords()){
                wordFound +=  "\n" + word ;
            }
            Label wordsFound = new Label(wordFound);
            box3.getChildren().add(wordsFound);
            VBox finalBox = new VBox(20, box1, box2, box3);
            borderPane.setRight(finalBox);
            finalBox.setAlignment(Pos.CENTER);
            clear.fire();
        });
        endButton.setOnAction(e -> {
            giveEndRoundInstructions();
            borderPane.setRight(null);
        });


    }


    /**
     * Generates the gridpane depending on the user input.
     *
     * This displays and handles the events of the whole Boggle Game
     */
    private GridPane populateGridPane(int rSize, String userString ){

        modelBoggleGrid = new BoggleGrid(rSize);
//        modelBoggleGrid.initalizeBoard(userString);
        // launching a round
        this.model.playRound(size, userString, modelBoggleGrid);
        char[][] board = modelBoggleGrid.getBoard();
        int size = modelBoggleGrid.numRows();
        System.out.println(board);

        GridPane grid = new GridPane();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                String s = String.valueOf(board[row][col]);
                Button newButton = new Button(s);
                newButton.setId(s);
                newButton.setPrefSize(50, 50);
                newButton.setFont(new Font(12));
                newButton.setStyle("-fx-background-color: #10871b; -fx-text-fill: white;");

                newButton.setOnAction(e -> {

                    inputWord[0] += newButton.getId();
                    wordInput.setText(inputWord[0]);
                    System.out.println(inputWord[0]);
                });
                grid.add(newButton, row, col);
            }
        }

        return grid;

    }

    /**
     * Generates last instructions
     *
     * This asks the user to play again or not.
     */
    private void giveEndRoundInstructions(){
        instructionsBox.getChildren().clear();
        // give the game summary
        String gameSummary = this.model.endRound();
        Text summary = new Text(gameSummary);
        summary.setX(50);
        summary.setY(50);
        summary.setFont(new Font(12));

        // ask if they want to play agian
        Label anotherRound = new Label("Do you want to play another round?");

        Button playAgain = new Button("Play Again");
        playAgain.setId("playagain");
        playAgain.setPrefSize(150, 50);
        playAgain.setFont(new Font(12));
        playAgain.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        Button end = new Button("End");
        end.setId("end");
        end.setPrefSize(150, 50);
        end.setFont(new Font(12));
        end.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        HBox chooseRoundButtons = new HBox(playAgain, end);

        instructionsBox.getChildren().addAll(summary, anotherRound,chooseRoundButtons);
        // if they want to play again, well and good.
        playAgain.setOnAction(e -> {
            giveSecondInstructions();
        });
        newGame = new Button("New Game");
        newGame.setId("new");
        newGame.setPrefSize(150, 50);
        newGame.setFont(new Font(12));
        newGame.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        end.setOnAction(e -> {
            instructionsBox.getChildren().clear();
            summary.setText("The game has ended, Good bye! \n You can also start a new game.");
            instructionsBox.getChildren().addAll(summary, newGame);

        });

        newGame.setOnAction(e -> {
            instructionsBox.getChildren().clear();
            giveFirstInstructions();
        });

    }




}
