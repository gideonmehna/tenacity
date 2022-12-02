package boggle;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import views.BoggleView;

import java.io.*;
import java.util.Objects;

public class BoogleLoad {


    private BoggleView view;
    public BoggleGame jl;
    public BoggleSave save;
    private Label selectBoardLabel;
    private Button selectBoardButton;
    private ListView<String> boardsList;



    public BoogleLoad (BoggleView view) {


        this.view = view;
        this.jl = view.getModel();

        selectBoardLabel = new Label(String.format("Currently playing: Default Board"));
        boardsList = new ListView<>(); //list of tetris.boards

        final Stage dialog = new Stage(); //dialogue box
        dialog.initModality(Modality.APPLICATION_MODAL);
        //dialog.initOwner(tetrisView.stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setStyle("-fx-background-color: #121212;");

        selectBoardLabel.setId("CurrentBoard"); // DO NOT MODIFY ID

        boardsList.setId("BoardsList");  // DO NOT MODIFY ID
        boardsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        getFiles(boardsList); //get files for file selector

        selectBoardButton = new Button("Change board");
        selectBoardButton.setId("ChangeBoard"); // DO NOT MODIFY ID

        //on selection, do something
        selectBoardButton.setOnAction(e -> {
            try {
                selectBoard(selectBoardLabel, boardsList);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        VBox selectBoardBox = new VBox(10, selectBoardLabel, boardsList, selectBoardButton);

        // Default styles which can be modified
        boardsList.setPrefHeight(100);

        selectBoardLabel.setStyle("-fx-text-fill: #e8e6e3");
        selectBoardLabel.setFont(new Font(16));

        selectBoardButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        selectBoardButton.setPrefSize(200, 50);
        selectBoardButton.setFont(new Font(16));

        selectBoardBox.setAlignment(Pos.CENTER);

        dialogVbox.getChildren().add(selectBoardBox);
        Scene dialogScene = new Scene(dialogVbox, 400, 400);
        dialog.setScene(dialogScene);
        dialog.show();
//        dialog.setOnCloseRequest(event -> {
//            tetrisView.paused = false;
//        });
    }

    /**
     * Populate the listView with all the .SER files in the boards directory
     *
     * @param listView ListView to update
     * @return the index in the listView of Stater.ser
     */
    private void getFiles(ListView<String> listView) {


        String directory =  "boards";
        File directoryFile = new File(directory);

        if(!directoryFile.isDirectory()) {
           throw new IllegalArgumentException("path must be a directory");
        }

        for(File serFile : Objects.requireNonNull(directoryFile.listFiles())) {
            if (!serFile.isDirectory() && serFile.getName().contains(".ser")) listView.getItems().add(serFile.getName());
        }
        boardsList = listView;

    }

    /**
     * Select and load the board file selected in the boardsList and update selectBoardLabel with the name of the new Board file
     *
     * @param selectBoardLabel a message Label to update which board is currently selected
     * @param boardsList a ListView populated with tetris.boards to load
     */
     void selectBoard(Label selectBoardLabel, ListView<String> boardsList) throws IOException {

        String selectedBoard;
        selectedBoard = boardsList.getSelectionModel().getSelectedItem();
        if (selectedBoard == null) throw new IOException();
        selectBoardLabel.setText(selectedBoard);
        this.jl = loadBoard("boards/"+ selectedBoard);
        view.changeboradandstats(jl);
    }

    /**
     * Load the board from a file
     *
     * @param boardFile file to load
     * @return loaded Tetris Model
     */
    public BoggleGame loadBoard(String boardFile) throws IOException {
        System.out.println("boardFile: " + boardFile);

        // Reading the object from a file
        FileInputStream file = null;
        ObjectInputStream in = null;
        try {
            file = new FileInputStream(boardFile);
            in = new ObjectInputStream(file);
            return (BoggleGame) in.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            in.close();
            file.close();
        }
    }
}
