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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Objects;


/*

 A list of elements (nodes /objects )
Every time a function loads in Boggle View, collect the elements into this array, and create another function
that updates the font for all the elements in this array. THis function runs whenever you click fontUp or fontdown buttons.
Remember to clear the list at the end of the function
2. wherever there is font size, change it to this.font
* */


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
    int size, gridButtonSize = 50, font = 12;
    VBox instructionsBox; // this holds the instructions and the game
    Stage stage;
    Button startButton,endButton, enterButton, newGame;
    ArrayList<Node> mainButtons = new ArrayList<>();
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
        instructionsText.setX(100); //changing the size of the instructions box
        instructionsText.setY(100); //changing the size of the instructions box
        instructionsText.setFont(new Font(this.font));



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
        message.setFont(new Font(this.font));
        message.setStyle("-fx-background-color: #10871b; -fx-text-fill: red;");

        Button next = new Button("Next");
        next.setId("Next");
        next.setPrefSize(50, 50);
        next.setFont(new Font(this.font));
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
        random.setFont(new Font(this.font));
        random.setStyle("-fx-background-color: #10871b; -fx-text-fill: white;");

        Button usersLetters = new Button("Provide Your Own");
        usersLetters.setId("usersLetters");
        usersLetters.setPrefSize(200, 50);
        usersLetters.setFont(new Font(this.font));
        usersLetters.setStyle("-fx-background-color: #10871b; -fx-text-fill: white;");

        startButton = new Button("Start");
        startButton.setId("Start");
        startButton.setPrefSize(150, 50);
        startButton.setFont(new Font(this.font));
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
        // Clear all the instructions in the box
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
        clear.setFont(new Font(this.font));
        clear.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        mainButtons.add(clear);

        endButton = new Button("End Round");
        endButton.setId("end");
        endButton.setPrefSize(150, 50);
        endButton.setFont(new Font(this.font));
        endButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        mainButtons.add(endButton);

        enterButton = new Button("Enter");
        enterButton.setId("enter");
        enterButton.setPrefSize(150, 50);
        enterButton.setFont(new Font(this.font));
        enterButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        mainButtons.add(enterButton);

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
            this.model.evaluateWord(inputWord[0]);
            clear.fire();
        });
        endButton.setOnAction(e -> {
            giveEndRoundInstructions();
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
                newButton.setPrefSize(this.gridButtonSize, this.gridButtonSize);
                newButton.setFont(new Font(this.font));
                newButton.setStyle("-fx-background-color: #10871b; -fx-text-fill: white;");

                newButton.setOnAction(e -> {

                    inputWord[0] += newButton.getId();
                    wordInput.setText(inputWord[0]);
                    System.out.println(inputWord[0]);
                });
                mainButtons.add(newButton);
                grid.add(newButton, row, col);
            }
        }

        makeSpecialButtons();
        return grid;
    }



    /*
        Creates special buttons on the grid view for accessibility purposes.
     */

    private void makeSpecialButtons() {

        // increase font button
        Button fontUp = new Button("Increase Font");
        fontUp.setId("Increase Font");
        fontUp.setPrefSize(100,50);
        fontUp.setFont(new Font(this.font));
        fontUp.setStyle("-fx-background-color: #10871b; -fx-text-fill: white;");

        borderPane.setLeft(fontUp);

        fontUp.setOnAction(e -> {
            updateFont("increase");
        });

        mainButtons.add(fontUp);

        // decrease font button
        Button fontDown = new Button("Decrease Font");
        fontDown.setId("Decrease Font");
        fontDown.setPrefSize(100,50);
        fontDown.setFont(new Font(this.font));
        fontDown.setStyle("-fx-background-color: #10871b; -fx-text-fill: white;");

        borderPane.setRight(fontDown);

        fontDown.setOnAction(e -> {
           updateFont("decrease");
        });

        mainButtons.add(fontDown);

        // black on white setting
        Button blackOnWhite = new Button("Black & White");
        blackOnWhite.setId("Black & White");
        blackOnWhite.setPrefSize(100, 50);
        blackOnWhite.setFont(new Font(this.font));
        blackOnWhite.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #112e51;");

        // borderPane.setTop(blackOnWhite);

        blackOnWhite.setOnAction(e -> {
            updateColours("BOW");
        });

        //white on black setting
        Button whiteOnBlack = new Button("White & Black");
        whiteOnBlack.setId("White & Black");
        whiteOnBlack.setPrefSize(100, 50);
        whiteOnBlack.setFont(new Font(this.font));
        whiteOnBlack.setStyle("-fx-background-color: #212121; -fx-text-fill: #ffffff;");

        // borderPane.setTop(whiteOnBlack);

        whiteOnBlack.setOnAction(e -> {
            updateColours("WOB");
        });

        //blue on white
        Button blueOnWhite = new Button("Blue & White");
        blueOnWhite.setId("Blue & White");
        blueOnWhite.setPrefSize(100, 50);
        blueOnWhite.setFont(new Font(this.font));
        blueOnWhite.setStyle("-fx-background-colour: #ffffff; -fx-text-fill: #0071bc;");

        // borderPane.setCenter(blueOnWhite);

        blueOnWhite.setOnAction(e -> {
            updateColours("BLOW");
        });

        //green on white - default
        Button whiteOnGreen = new Button("Green & White");
        whiteOnGreen.setId("Green & White");
        whiteOnGreen.setId("Green & White");
        whiteOnGreen.setPrefSize(100, 50);
        whiteOnGreen.setFont(new Font(this.font));
        whiteOnGreen.setStyle("-fx-background-color: #10871b; -fx-text-fill: white;");

        // borderPane.setBottom(whiteOnGreen);

        whiteOnGreen.setOnAction(e -> {
            updateColours("WOG");
        });

        //make gridpane of buttons and position it at the top
        FlowPane colorSettings = new FlowPane();
        colorSettings.getChildren().addAll(whiteOnGreen, blackOnWhite, whiteOnBlack, blueOnWhite);
        colorSettings.setAlignment(Pos.BOTTOM_CENTER);
        borderPane.setBottom(colorSettings);
    }


    /*
     * Updates the colour scheme of the grid.
     */

    private void updateColours(String colour){
        if (Objects.equals(colour, "BOW")){
            // change the buttons on the grid to white and the text to black
            for (Node node: mainButtons) {
                if (node instanceof Button button) {
                    button.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #112e51;");
                }
            }
        } else if (colour == "WOB") {
            // change the buttons on the grid to black and the text to white
            for (Node node: mainButtons) {
                if (node instanceof Button button){
                    button.setStyle("-fx-background-color: #212121; -fx-text-fill: #ffffff;");
                }
            }
        } else if (colour == "BLOW") {
            // change the buttons on the grid to white and the text to blue
            for (Node node: mainButtons){
                if (node instanceof Button button) {
                    button.setStyle("-fx-background-colour: #ffffff; -fx-text-fill: #0071bc;");
                }
            }
        } else {
            // change the buttons on the grid to green and the text to white
            for (Node node : mainButtons) {
                if (node instanceof Button button) {
                    button.setStyle("-fx-background-color: #10871b; -fx-text-fill: white;");
                }
            }
        }
    }

    /*
      * Updates the font of the letters on the grid.
     */

    private void updateFont(String action){
        if (Objects.equals(action, "increase")){
            if(!(this.font >= 24)) {
                this.font++;
            }
            if(!(this.gridButtonSize >= 85)){
                this.gridButtonSize += 5;
            }
            for (Node node: grid.getChildren()) {
                if (node instanceof Button nb){
                    nb.setFont(new Font(this.font));
                    nb.setPrefSize(this.gridButtonSize, this.gridButtonSize);
                }
            }
        }
        else {
            if(!(this.font <= 8)) {
                this.font--;
            }
            if(!(this.gridButtonSize <= 35)){
                this.gridButtonSize -= 5;
            }

            for (Node node: grid.getChildren()) {
                if (node instanceof Button nb){
                    nb.setFont(new Font(this.font));
                    nb.setPrefSize(this.gridButtonSize, this.gridButtonSize);
                }
            }
        }
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
