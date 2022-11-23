import boggle.BoggleGame;
import views.BoggleView;

import javafx.application.Application;
import javafx.stage.Stage;
/**
 * The Main class for the first Assignment in CSC207, Fall 2022
 */
public class BoggleApp extends Application {
    /**
    * Main method. 
    * @param args command line arguments.
    **/
    public static void main(String[] args) {
        launch(args);


    }
    /**
     * Start method.  Control of application flow is dictated by JavaFX framework
     *
     * @param stage stage upon which to load GUI elements
     */
    @Override
    public void start(Stage stage) throws Exception {
        BoggleGame b = new BoggleGame();
        BoggleView v = new BoggleView(b, stage);
    }
}
/*

P S E R P
I Y I E B
R S N A T
U R E M O
D A E C H

PSERPIYIEBRSNATUREMODAECH

 */