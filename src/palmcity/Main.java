package palmcity;

/**---------------------------------------------
 *
 *    @author: Enes Keles
 *    @description: Initial screen of Palm City
 *
 *---------------------------------------------*/

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application
{
    private Image logoImage;
    private Label logoHolder;
    private Scene mainScene;
    private Button startButton, wikiButton;
    private Stage window;


    @Override
    public void start(Stage primaryStage) throws Exception
    {
        window = primaryStage ;
        window.setResizable(false);
        window.setOnCloseRequest(exitGame -> {System.exit(0);});

        startButton = new Button("Start!");
        startButton.setMaxWidth(135);
        startButton.setMaxHeight(80);


        wikiButton = new Button("Wiki");
        wikiButton.setMaxWidth(135);
        wikiButton.setMaxHeight(80);

        startButton.setOnAction( startTheGame ->
        {
            new Game(window);
        });

        logoImage = new Image(getClass().getResourceAsStream("images/logo.PNG"));
        logoHolder = new Label("",new ImageView(logoImage));

        //welcomeLabel = new Label("");

        VBox mainLayout = new VBox(70);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(logoHolder, startButton);

        mainScene = new Scene(mainLayout,300, 500);
        mainScene.getStylesheets().add("palmcity/images/Theme.css");

        window.setTitle("Palm City");
        window.setScene(mainScene);
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
