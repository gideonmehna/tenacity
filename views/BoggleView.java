package views;

import boggle.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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


    Stage stage;
    Button startButton,endButton, enterButton;
    ArrayList<Button> gridButtons;
    BoggleStats gameStats;

    public BoggleView(BoggleGame game, Stage stage) throws Exception {
        this.stage = stage;
        this.model = game;
        initUI();
    }

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
         borderPane.setStyle("-fx-background-image: url(\"images/Bubbles PPT.png\");");
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
    private void launchBoard(){
        // strip the scene off the hbox wiht he start and add the grid paen instead.
        Node boggleGrid = populateGridPane();
        borderPane.setCenter(boggleGrid);
    }
     private GridPane populateGridPane( ){
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
                 grid.add(newButton, row, col);
//                 gridButtons.add(newButton);
    //
             }
         }
    //     grid.add(new Button(), 1, 0); // column=1 row=0
    //     grid.add(new Label(), 2, 0);  // column=2 row=0
         return grid;

     }


}
