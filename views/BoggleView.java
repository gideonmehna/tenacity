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

    VBox instructionsBox;
    Stage stage;
    Button startButton,endButton, enterButton;
    ArrayList<Button> gridButtons;
    BoggleStats gameStats;

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


         BackgroundImage myBI = new BackgroundImage(new Image("images/Bubbles PPT.png", 32, 32, false, true),
                 BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                 BackgroundSize.DEFAULT);
         //then you set to your node
         Group root = new Group();

         Scene back = new Scene(root);

    //     back.setBackground(new Background(myBI));
         // draw the boggle box
         borderPane = new BorderPane();
         borderPane.setStyle("-fx-background-image: url(\"images/Bubbles.png\");");
         ToolBar toolbar = new ToolBar();
         HBox statusbar = new HBox();
    //     HBox boggleGrid = new HBox();
         HBox gameStart = new HBox();

         borderPane.setTop(toolbar);

//         HBox controls = new HBox(20, startButton);
//         controls.setPadding(new Insets(20, 20, 20, 20));
//         controls.setAlignment(Pos.CENTER);
//
//         borderPane.setBottom(controls);

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

        String instructions = "The Boggle board contains a grid of letters that are randomly placed. \n";
        instructions += "We're both going to try to find words in this grid by joining the letters. \n";
        instructions += "You can form a word by connecting adjoining letters on the grid. \n";
        instructions += "Two letters adjoin if they are next to each other horizontally, \n";
        instructions += "vertically, or diagonally. The words you find must be at least 4 letters long, \n";
        instructions += "and you can't use a letter twice in any single word. Your points \n";
        instructions += "will be based on word length: a 4-letter word is worth 1 point, 5-letter \n";
        instructions += "words earn 2 points, and so on. After you find as many words as you can, \n";
        instructions += "I will find all the remaining words. \n";
        instructions += "Hit the Next button below or hit return when you're ready... \n";

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


//            if (text == "5" || text == "4") size = Integer.parseInt( text);
//            else message.setText("Please type either 5 or 4.");
//            if ( (size > 0)&& (size < 9) )  giveThirdInstructions();
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
            };

//            gridSize.setOnAction( gridSize.getOnAction());
//            if ( (size > 0)&& (size < 9) )  giveThirdInstructions();
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
        random.setPrefSize(50, 50);
        random.setFont(new Font(12));
        random.setStyle("-fx-background-color: #10871b; -fx-text-fill: white;");

        Button usersLetters = new Button("Provide Your Own");
        usersLetters.setId("usersLetters");
        usersLetters.setPrefSize(50, 50);
        usersLetters.setFont(new Font(12));
        usersLetters.setStyle("-fx-background-color: #10871b; -fx-text-fill: white;");

        startButton = new Button("Start");
        startButton.setId("Start");
        startButton.setPrefSize(150, 50);
        startButton.setFont(new Font(12));
        startButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        HBox buttonBox = new HBox();

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

            instructionsBox.getChildren().remove(buttonBox);
            instructionsBox.getChildren().addAll(randomLetters);
        });


        random.setOnAction(e ->{
            instructionsBox.getChildren().remove(instructionsText);
            message.setText("Ready to Start");
            instructionsBox.getChildren().addAll(message, startButton);
        });
        randomLetters.setOnAction(e-> {
            s[0] = randomLetters.getText();
            s[0] = s[0].toUpperCase().strip();
            if (s[0].length() < size * size) message.setText("You have entered few letters, please enter "+ size * size +" letters");
            else if (s[0].length() > size * size) {
                s[0] = s[0].substring(0, size * size);
            }
            if (s[0].length() == size * size) {
                message.setText("Ready to Start");
                instructionsBox.getChildren().addAll(message, startButton);
            }
        });
        startButton.setOnAction(e -> {

            if(s[0].isEmpty())launchBoard();
            else launchBoard(s[0]);
//             this.model.playGame();
            borderPane.requestFocus();
        });


    }
    /**
     * launches the Gridpane with the Boggle Grid once the user has gone through the instructions
     *
     * This displays and handles the events of the whole Boggle Game
     */
    private void launchBoard(){
        // strip the scene off the hbox wiht he start and add the grid paen instead.
        grid = populateGridPane(size);
        grid.setAlignment(Pos.CENTER);
        Node boggleGrid = grid;

        instructionsBox.getChildren().clear();
        // remove instruction box from border pane
        instructionsBox.getChildren().add(boggleGrid);
        instructionsBox.setAlignment(Pos.CENTER);
//        borderPane.setCenter(boggleGrid);
        launchTextInputField();
    }
    /**
     * launches the Gridpane with the Boggle Grid once the user has gone through the instructions
     *
     * This displays and handles the events of the whole Boggle Game
     */
    private void launchBoard(String s){
        // strip the scene off the hbox wiht he start and add the grid paen instead.
        instructionsBox.getChildren().clear();
        grid = populateGridPane(size, s);
        grid.setAlignment(Pos.CENTER);
        Node boggleGrid = grid;


        instructionsBox.getChildren().addAll(boggleGrid);
        instructionsBox.setAlignment(Pos.CENTER);
//        borderPane.setCenter(boggleGrid);
        launchTextInputField();

        // I need a clear button

        // I need an enter button, which also clears the word chosen, this button also evaluates the word input.

        // i also need to display the words the user has currently chosen


    }
    /**
     * launches the Buttons that go with the Boggle Grid.
     *
     * this enables the user t
     */
    private void launchButtonControls(){

    }
    /**
     * launches the Text Input Field when the user starts playing the game.
     *
     * this enables a user to type in their word choices.
     */
    private void launchTextInputField(){
        TextField textInputField = new TextField();
        textInputField.setAlignment(Pos.CENTER);
        textInputField.setId("boggleWordInput");
        textInputField.setLayoutY(30);
        textInputField.setLayoutX(100);

        Label enterText = new Label("Enter a word from the grid:");

        HBox textInput = new HBox(enterText, textInputField);
        borderPane.setBottom(textInput);

    }

    /**
     * Generates the gridpane depending on the user input.
     *
     * This displays and handles the events of the whole Boggle Game
     */
    private GridPane populateGridPane(int rSize, String userString ){
        final String[] inputWord = {new String()};
        modelBoggleGrid = new BoggleGrid(rSize);
        modelBoggleGrid.initalizeBoard(userString);
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
                    System.out.println(inputWord[0]);
                });
                grid.add(newButton, row, col);
            }
        }

        return grid;

    }
    /**
     * Generates the gridpane depending on the user input.
     *
     * This displays and handles the events of the whole Boggle Game
     */
     private GridPane populateGridPane(int rSize ){
         final String[] inputWord = {new String()};
        modelBoggleGrid = new BoggleGrid(rSize);
        String randomlyGeneratedString = this.model.randomizeLetters(rSize);
        modelBoggleGrid.initalizeBoard(randomlyGeneratedString);
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

                     System.out.println(inputWord[0]);

//                     newButton.setOnMouseClicked();
                 });
                 grid.add(newButton, row, col);
             }
         }

         return grid;

     }
    /**
     * Verifies the words chosen by the game player.
     *
     *
     */
    private void evaluatePlayerWord(){
        //
    }

    /**
     * Generates last instructions
     *
     * This asks the user to play again or not.
     */
    private void giveEndRoundInstructions(){
        throw new UnsupportedOperationException();
    }




}
