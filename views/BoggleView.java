package views;

import boggle.BoggleGame;
import boggle.BoggleGrid;
import boggle.BoggleStats;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.Objects;



/**
 * Boggle View
 *
 * This is The class that displays boggle.
 */
public class BoggleView {
    /**
     * Boggle Model class
     */
    BoggleGame model;
    /**
     * Boggle Grid
     */
    BoggleGrid modelBoggleGrid;
    /**
     * Boggle Statistics
     */
    BoggleStats gameStats;
    /**
     * JavaFX BorderPane where the game is played.
     */
    BorderPane borderPane;
    /**
     * Grid Pane that holds the Boggle GRid
     */
    GridPane grid;
    /**
     * Instructions are displayed in this JavaFX Text
     */
    Text instructionsText;
    /**
     * The Instructions and Boggle Game are displayed in this Box
     */
    VBox instructionsBox; // this holds the instructions and the game
    /**
     * The main stage of the game. JavaFX stage or window
     */
    Stage stage;
    /**
     * Buttons to start, end, enter and create a new game
     */
    Button startButton,endButton, enterButton, newGame;
    /**
     * Grid size (4 -9). The grid is always a square therefore the size is only an integer.
     * GridButtonSize is the size of the grid buttons
     * Font is the font for the buttons
     *
     */
    int size, gridButtonSize = 50, font = 12;
    /**
     * The word the user is typing or selecting from the grid
     */
    final String[] inputWord = {new String()}; // The word the user is typing
    /**
     * JavaFX Text to display the word the user is typing
     */
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
        borderPane.setStyle("-fx-background-image: url(\"https://wallpaperset.com/w/full/4/9/8/141069.jpg\");");

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
        instructionsText.setX(100); //changing the size of the instructions box
        instructionsText.setY(100); //changing the size of the instructions box
        instructionsText.setFont(new Font(this.font));

        Button next = new Button("Next");
        next.setId("Next");
        next.setPrefSize(50, 50);
        next.setFont(new Font(12));
        next.setStyle("-fx-background-color: #10871b; -fx-text-fill: white; ");

        next.setOnAction(e -> {
            giveSecondInstructions();
        });

        instructionsBox = new VBox( instructionsText, next);
        instructionsBox.setPadding(new Insets(20, 20, 20, 20));
        instructionsBox.setSpacing(10);
        instructionsBox.setAlignment(Pos.CENTER);
        instructionsBox.setStyle("-fx-background-color: rgb(225,225,255,0.52);");
        instructionsBox.setMaxSize(500, 500);
        borderPane.setCenter(instructionsBox);

    }
    /**
     *  Gives the Second User Instructions. Asks for the grid size.
     *
     *
     */
    private void giveSecondInstructions() {
        instructionsBox.getChildren().clear();

        String instruction = "Enter a number between 4 - 9 inclusive. \ne.g enter 5 to play on a (5x5) grid;";

        instructionsText.setText(instruction);


        TextField gridSize = new TextField();
        gridSize.setAlignment(Pos.CENTER);
        gridSize.setMaxSize(100, 100);

        Label message = new Label();
        message.setFont(new Font(this.font));
        message.setStyle("-fx-background-color: #10871b; -fx-text-fill: red;");

        Button next = new Button("Next");
        next.setId("Next");
        next.setPrefSize(50, 50);
        next.setFont(new Font(this.font));
        next.setStyle("-fx-background-color: #10871b; -fx-text-fill: white;");
        next.setPadding(new Insets(5));

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
        random.setFont(new Font(this.font));
        random.setStyle("-fx-background-color: #10871b; -fx-text-fill: white;");
        random.setPadding(new Insets(5, 10, 5, 5));

        Button usersLetters = new Button("Provide Your Own");
        usersLetters.setId("usersLetters");
        usersLetters.setPrefSize(200, 50);
        usersLetters.setFont(new Font(this.font));
        usersLetters.setStyle("-fx-background-color: #10871b; -fx-text-fill: white;");
        random.setPadding(new Insets(5, 5, 5, 10));

        startButton = new Button("Start");
        startButton.setId("Start");
        startButton.setPrefSize(150, 50);
        startButton.setFont(new Font(this.font));
        startButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(5);

        if (size == 4 || size == 5){
            instruction = "Click one of the buttons below to either randomly assign letters or to provide your own";
            buttonBox.getChildren().addAll(random, usersLetters);


        }
        else if( (6 <= size) && (size <= 9)  ) {
            instruction = "Randomly assign Letters for your board.";
            VBox messageRandomLetters = new VBox(message, randomLetters);
            messageRandomLetters.setAlignment(Pos.CENTER);
            buttonBox.getChildren().addAll(messageRandomLetters);

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
            message.setStyle("-fx-background-color: rgb(0,0,0,0.5); -fx-text-fill: red;");
            if (!s[0].matches("^[A-Z]*$")) {
                message.setText("Please only enter alphabet charaters from A - Z");
                randomLetters.setText("");
            }
            else if (s[0].length() < size * size) {
                message.setText("You have entered few letters, please enter "+ size * size +" letters");
            }

            else if (s[0].length() > size * size) {
                s[0] = s[0].substring(0, size * size);
            }
            if (s[0].length() == size * size) {
                message.setText("Ready to Start");
                message.setStyle("-fx-background-color: rgb(255,255,255,0); -fx-text-fill: black;");
                instructionsBox.getChildren().clear();
                instructionsBox.getChildren().addAll(message, startButton);
            }
        });
        startButton.setOnAction(e -> {
            instructionsBox.getChildren().clear();
            message.setText("Loading Game ...");
            instructionsBox.getChildren().addAll(message);

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
        // Clear all the instructions in the box
        instructionsBox.getChildren().clear();
        // Prepare the gridButtons array
//        gridButtons = new ArrayList<>();
        // prepare the variables to be used
        wordInput = new Text();
        wordInput.setText(inputWord[0]);
        wordInput.setX(200);
        wordInput.setY(100);

        // Get the grid , add the string
        grid = populateGridPane(size, s);
        grid.setAlignment(Pos.CENTER);
        Node boggleGrid = grid;

        // Add the grid
        instructionsBox.getChildren().addAll(boggleGrid);
        launchControls();
        instructionsBox.setAlignment(Pos.CENTER);
        instructionsBox.setSpacing(10);

    }
    /**
     * launches the Buttons that go with the Boggle Grid.
     *
     * this enables the user t
     */
    private void launchControls(){
        // text input field
        TextField textInputField = new TextField();
        textInputField.setAlignment(Pos.CENTER);
        textInputField.setId("boggleWordInput");
        textInputField.setMaxSize(150, 150);
        textInputField.setLayoutY(30);
        textInputField.setLayoutX(100);
        textInputField.setText(inputWord[0]);

        Label enterText = new Label("Enter a word from the grid:");

        VBox textInput = new VBox(enterText, textInputField);
        textInput.setAlignment(Pos.CENTER);
        textInput.setSpacing(5);

        Button clear = new Button("Clear Text");
        clear.setId("Start");
        clear.setPrefSize(150, 50);
        clear.setFont(new Font(this.font));
        clear.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
//        mainButtons.add(clear);

        endButton = new Button("End Round");
        endButton.setId("end");
        endButton.setPrefSize(150, 50);
        endButton.setFont(new Font(this.font));
        endButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
//        mainButtons.add(endButton);

        enterButton = new Button("Enter");
        enterButton.setId("enter");
        enterButton.setPrefSize(150, 50);
        enterButton.setFont(new Font(this.font));
        enterButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
//        mainButtons.add(enterButton);

//        wordInput.setText(inputWord[0]);

        HBox controls = new HBox(clear, wordInput, enterButton);
        controls.setAlignment(Pos.CENTER);
        controls.setSpacing(5);
        VBox vControls = new VBox(controls, textInput);
        vControls.setAlignment(Pos.CENTER);
        vControls.setSpacing(5);
        instructionsBox.getChildren().addAll(vControls, endButton);


        clear.setOnAction(e -> {
            // CLear button colors.
//            clearButtons(gridButtons);
            inputWord[0] = "";
            wordInput.setText(inputWord[0]);
            textInputField.setText(inputWord[0]);
        });
        textInputField.setOnAction(e -> {
            enterButton.fire();
        });
        enterButton.setOnAction(e -> {
            if (inputWord[0].isEmpty()) inputWord[0] = textInputField.getText();
            wordInput.setText(inputWord[0]);
//            HBox box1 = new HBox(5);
//            VBox box2 = new VBox(5);
//            HBox box3 = new HBox(10);
//            Label wordCheck = new Label();
//            if (this.model.evaluateWord(inputWord[0]) == 2){
//                wordCheck.getStyleClass().add("bg-4");
//                wordCheck.setText("Correct! " + inputWord[0]);
//                SVGPath githubIcon = new SVGPath();
//                githubIcon.setContent("m36.097 739.31 20-30c16.511 12.907 17.767 19.639 24.949 30.909 36.804-72.31 " +
//                        "74.954-104.96 128.57-144.29-51.91 53.35-83.23 89.32-130 198.58-16.193-26.29-27.333-53." +
//                        "62-43.523-55.2z");
//                githubIcon.setFill(Color.web("#81c483"));
//                box2.getChildren().addAll(wordCheck, githubIcon);
//                box2.setAlignment(Pos.CENTER);
//            }else if (this.model.evaluateWord(inputWord[0]) == 1){
//                wordCheck.getStyleClass().add("bg-3");
//                wordCheck.setText("This word has already been inputted");
//                SVGPath githubIcon = new SVGPath();
//                githubIcon.setContent("M42.42,12.401c0.774-0.774,0.774-2.028,0-2.802L38.401,5.58c-0.774-0.774-2.028-" +
//                        "0.774-2.802,0	L24,17.179L12.401,5.58c-0.774-0.774-2.028-0.774-2.802,0L5.58,9.599c-0.774," +
//                        "0.774-0.774,2.028,0,2.802L17.179,24L5.58,35.599	c-0.774,0.774-0.774,2.028,0,2.802l4.019," +
//                        "4.019c0.774,0.774,2.028,0.774,2.802,0L42.42,12.401z M24,30.821L35.599,42.42c0.774,0.774," +
//                        "2.028,0.774,2.802,0l4.019-4.019    c0.774-0.774,0.774-2.028,0-2.802L30.821,24L24,30.821z");
//                githubIcon.setFill(Color.web("#a8142e"));
//                githubIcon.autosize();
//                box2.getChildren().addAll(wordCheck, githubIcon);
//                box2.setAlignment(Pos.CENTER);
//            }else if (this.model.evaluateWord(inputWord[0]) == 0){
//                wordCheck.getStyleClass().add("bg-2");
//                wordCheck.setText("Incorrect word :(");
//                SVGPath githubIcon = new SVGPath();
//                githubIcon.setContent("M42.42,12.401c0.774-0.774,0.774-2.028,0-2.802L38.401,5.58c-0.774-0.774-2.028-" +
//                        "0.774-2.802,0	L24,17.179L12.401,5.58c-0.774-0.774-2.028-0.774-2.802,0L5.58,9.599c-0.774," +
//                        "0.774-0.774,2.028,0,2.802L17.179,24L5.58,35.599	c-0.774,0.774-0.774,2.028,0,2.802l4.019," +
//                        "4.019c0.774,0.774,2.028,0.774,2.802,0L42.42,12.401z M24,30.821L35.599,42.42c0.774,0.774," +
//                        "2.028,0.774,2.802,0l4.019-4.019    c0.774-0.774,0.774-2.028,0-2.802L30.821,24L24,30.821z");
//                githubIcon.setFill(Color.web("#a8142e"));
//                githubIcon.autosize();
//                box2.getChildren().addAll(wordCheck, githubIcon);
//                box2.setAlignment(Pos.CENTER);
//            }
//            Label wordCount = new Label("Words left: " +
//                    (this.model.getAllWords().length - this.model.getGameStats().getScore()));
//            box1.getChildren().add(wordCount);
//            box1.setAlignment(Pos.TOP_CENTER);
//            String wordFound = "Words Found: ";
//            for (String word: this.model.getGameStats().getPlayerWords()){
//                wordFound +=  "\n" + word ;
//            }
//            Label wordsFound = new Label(wordFound);
//            box3.getChildren().add(wordsFound);
//            box3.setAlignment(Pos.BOTTOM_CENTER);
//            VBox finalBox = new VBox(20, box1, box2, box3);
//            borderPane.setRight(finalBox);
//            finalBox.setAlignment(Pos.CENTER);
//            finalBox.setStyle("-fx-background-color: rgb(225,225,255,0.52);");
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
//        System.out.println(board);

        GridPane grid = new GridPane();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                String s = String.valueOf(board[row][col]);
                Button newButton = new Button(s);
                newButton.setId(s);
                newButton.setPrefSize(this.gridButtonSize, this.gridButtonSize);
                newButton.setFont(new Font(this.font));
                newButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
//                newButton.setStyle("-fx-background-color: " + this.buttonBackgroundColor + "; -fx-text-fill:" + this.buttonTextColor +";");
//               
                newButton.setOnMouseEntered(e->{
                    newButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
                });
                newButton.setOnMouseExited(e->{
//                    if (!gridButtons.contains(newButton)) newButton.setStyle("-fx-background-color: " + this.buttonBackgroundColor + "; -fx-text-fill:" + this.buttonTextColor +";");
//               
                });


                newButton.setOnAction(e -> {
//                    if (!gridButtons.contains(newButton)) {
//                        gridButtons.add(newButton);
//                        newButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
//                        inputWord[0] += newButton.getId();
//                        wordInput.setText(inputWord[0]);
//                        System.out.println(inputWord[0]);
//                    }
                    // colors
                    // see if i can limit people from clicing buttons that are not adjancet
                    // if i click a button that is already inside the grid buttons, remove it and also remove the string.
                });
//                mainButtons.add(newButton);
                grid.add(newButton, row, col);
            }
        }

//        makeSpecialButtons();
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
        summary.setTextAlignment(TextAlignment.CENTER);
        summary.setWrappingWidth(500);


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
        chooseRoundButtons.setAlignment(Pos.CENTER);
        chooseRoundButtons.setSpacing(5);

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
