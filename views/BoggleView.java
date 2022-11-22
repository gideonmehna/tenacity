package views;

import boggle.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
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

    HBox instructionsBox;
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
         startButton = new Button("Start");
         startButton.setId("Start");
         startButton.setPrefSize(150, 50);
         startButton.setFont(new Font(12));
         startButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

         HBox controls = new HBox(20, startButton);
         controls.setPadding(new Insets(20, 20, 20, 20));
         controls.setAlignment(Pos.CENTER);

    //    Node appContent = new Node() {
    //        @Override
    //        public void toFront() {
    //            super.toFront();
    //        }
    //    };
         borderPane.setTop(toolbar);


         borderPane.setBottom(controls);

         // place the start and stop buttons at the top

         // draw the dummy boxes. focus on the one for input and the enter button.

         // draw the game box. it will first display instructions. then it will display the grid.

         // create the grid.

         // collect the words.

         startButton.setOnAction(e -> {

             launchBoard();
//             this.model.playGame();
             borderPane.requestFocus();
         });

         var scene = new Scene(borderPane, 1200, 600);
         this.stage.setScene(scene);
         this.stage.show();
     }



    /**
     * launches the Gridpane with the Boggle Grid once the user has gone through the instructions
     *
     * This displays and handles the events of the whole Boggle Game
     */
    private void launchBoard(){
        // strip the scene off the hbox wiht he start and add the grid paen instead.
        grid = populateGridPane();
        Node boggleGrid = grid;
        borderPane.setCenter(boggleGrid);
//        gridpaneEvent();
    }
    /**
     *  Give the User Instructions
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

        HBox instructionsBox = new HBox( instructionsText, next);
        borderPane.setCenter(instructionsBox);

    }

    private void giveSecondInstructions() {
        instructionsBox.getChildren().clear();

        String instruction = "Enter 5 to play on a big (5x5) grid; 4 to play on a small (4x4) one:";

        instructionsText.setText(instruction);

        TextField gridSize = new TextField();
        instructionsBox.getChildren().addAll(instructionsBox, gridSize);
        // Figure out how to ask the question for string characters or for using random strings.

        // call the launch grid funciton. 


    }

    /**
     * Generates the gridpane depending on the user input.
     *
     * This displays and handles the events of the whole Boggle Game
     */
     private GridPane populateGridPane( ){
         final String[] inputWord = {new String()};
        modelBoggleGrid = new BoggleGrid(5);
        modelBoggleGrid.initalizeBoard("PSERPIYIEBRSNATUREMODAECH");
         char[][] board = modelBoggleGrid.getBoard();
         int size = modelBoggleGrid.numRows();
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




}
