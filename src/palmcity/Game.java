package palmcity;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by Enes on 16.05.2017.
 */
public class Game
{
    private GameLogic myGame;
    private GridPane turnGrid;
    private GridPane grid;
    private GridPane powerGrid;
    private HBox hbox;
    private VBox vbox1, vbox2, vbox3;
    private Label[][] labels;
    private Image snowy, foggy, rainy, sunny, monsterImage, idleImage, bossImage;
    private Scene gameScene;
    private Label monsterLabel, riskTableLabel, turnTableLabel;
    private Button nextTurnButton, prevTurnButton, displayMonsterButton;


    public Game(Stage window)
    {
        nextTurnButton = new Button("Next Turn");
        nextTurnButton.setOnAction( nextTurn->
        {
            myGame.nextTurn();
            fillGrid();
            fillTurnGrid();
            displayMonster();
        });

        prevTurnButton = new Button("Prev Turn");
        prevTurnButton.setOnAction( prevTurn->
        {
            myGame.prevTurn();
            fillGrid();
            fillTurnGrid();

            if ( myGame.getMonster() != null)
                displayMonster();
        });

        displayMonsterButton = new Button("Monster Info");
        displayMonsterButton.setOnAction( showMonster->
        {
            if ( myGame.getMonster() != null)
                displayMonster();
        });
        displayMonsterButton.setFont(new Font(displayMonsterButton.getFont().getName(), 11));

        nextTurnButton.setMaxWidth(80);
        nextTurnButton.setMaxHeight(135);

        prevTurnButton.setMaxWidth(80);
        prevTurnButton.setMaxHeight(135);

        displayMonsterButton.setMaxWidth(80);
        displayMonsterButton.setMaxHeight(135);

        labels = new Label[16][16];

        monsterImage = new Image(getClass().getResourceAsStream("images/monster.png"));

        monsterLabel = new Label("",new ImageView(monsterImage));
        monsterLabel.setStyle("-fx-border-color:orangered");

        riskTableLabel = new Label("Risk Table");
        turnTableLabel = new Label("Current Turn");

        myGame = new GameLogic();

        snowy = new Image(getClass().getResourceAsStream("images/snowy.png"));
        foggy = new Image(getClass().getResourceAsStream("images/foggy.png"));
        rainy = new Image(getClass().getResourceAsStream("images/rainy.png"));
        sunny = new Image(getClass().getResourceAsStream("images/sunny.png"));

        idleImage = new  Image(getClass().getResourceAsStream("images/idle.png"));
        bossImage = new  Image(getClass().getResourceAsStream("images/boss.jpg"));

        initializeLabelMatrix();

        grid = new GridPane();
        grid.setVgap(0);
        grid.setHgap(0);
        grid.setAlignment(Pos.CENTER);

        powerGrid = new GridPane();
        powerGrid.setVgap(2);
        powerGrid.setHgap(2);
        powerGrid.setAlignment(Pos.CENTER);

        turnGrid = new GridPane();
        turnGrid.setVgap(2);
        turnGrid.setHgap(2);
        turnGrid.setAlignment(Pos.CENTER);

        fillGrid();

        fillPowerGrid();

        fillTurnGrid();

        vbox1 = new VBox(20);
        vbox1.setAlignment(Pos.CENTER);
        vbox1.getChildren().addAll(riskTableLabel, powerGrid);

        vbox2 = new VBox(20);
        vbox2.setAlignment(Pos.CENTER);
        vbox2.getChildren().addAll(turnTableLabel, turnGrid);

        vbox3 = new VBox(75);
        vbox3.setAlignment(Pos.CENTER);
        vbox3.getChildren().addAll(vbox1, vbox2, nextTurnButton, prevTurnButton, displayMonsterButton);

        hbox = new HBox(20);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.getChildren().addAll(grid, vbox3);

        gameScene = new Scene(hbox, 740, 650);
        gameScene.getStylesheets().add("palmcity/images/Theme.css");

        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case RIGHT: nextTurnButton.fire(); break;
                    case LEFT: prevTurnButton.fire(); break;
                }
            }
        });

        window.setScene(gameScene);
        window.show();

        if ( myGame.getMonster() != null)
            displayMonster();
    }

    /**----------------------------
     *  fills the turn grid (2x2)
     *---------------------------*/
    private void fillTurnGrid()
    {
        turnGrid.getChildren().clear();

        for (int i = 0; i < 2; ++i)
            for (int j = 0; j < 2; ++j)
            {
                Label temp = new Label("");
                temp.setMinHeight(15);
                temp.setMinWidth(15);

                if (myGame.getTurn() == 1 && i == 0 && j == 0)
                    temp.setStyle("-fx-background-color: darkblue");

                else if  (myGame.getTurn() == 2 && i == 1 && j == 0)
                    temp.setStyle("-fx-background-color: darkblue");

                else if  (myGame.getTurn() == 3 && i == 1 && j == 1)
                    temp.setStyle("-fx-background-color: darkblue");

                else if  (myGame.getTurn() == 4 && i == 0 && j == 1)
                    temp.setStyle("-fx-background-color: darkblue");

                else
                    temp.setStyle("-fx-background-color: cadetblue");

                turnGrid.add(temp,i, j);
            }
    }

    /**--------------------------------------------------------------------
     *  initializes risk table that appears on the right side of the screen
     *--------------------------------------------------------------------*/
    private void fillPowerGrid()
    {
        for (int i = 0; i < 4; ++i)
            for (int j = 0; j < 4; ++j)
            {
                Label temp = new Label("");
                temp.setMinHeight(15);
                temp.setMinWidth(15);
                switch( myGame.getMap()[i][j].y)
                {
                    case 1: temp.setStyle("-fx-background-color: lawngreen;");
                        break;

                    case 2: temp.setStyle("-fx-background-color: yellowgreen;");
                        break;

                    case 3: temp.setStyle("-fx-background-color: yellow;");
                        break;

                    case 4: temp.setStyle("-fx-background-color: red;");
                        break;
                }

                powerGrid.add(temp, i, j);
            }
    }

    /**---------------------------------------------------
     * every single turn, initializes the big grid
     * that appears on the left side of the screen
     *---------------------------------------------------*/
    private void fillGrid()
    {
        grid.getChildren().clear();

        // dummy values
        int mX = -1234;
        int mY = -23432;

        if ( myGame.getMonster() != null)
        {
            mX = myGame.getMonster().getPosition().x;
            mY = myGame.getMonster().getPosition().y;
        }

        int turn = myGame.getTurn();

        for ( int i = 0; i < 16; ++i)
            for ( int j = 0; j < 16; ++j)
            {
                if ( turn == 1 && i == mX && j == mY)
                    grid.add(monsterLabel, i + i/8, j + j/8);

                else if ( turn == 2 && i-8 == mX && j == mY )
                    grid.add(monsterLabel, i + i/8, j + j/8);

                else if ( turn == 3 && i-8 == mX &&  j-8  == mY )
                    grid.add(monsterLabel, i + i/8, j + j/8);

                else if ( turn == 4 && i == mX && j-8 == mY )
                    grid.add(monsterLabel, i + i/8, j + j/8);

                else grid.add(labels[i][j], i + i/8, j + j/8);
            }

        for ( int i = 0; i < 16; ++i)
        {
            grid.add(new Label("", new ImageView(idleImage)), i + i/8, 8);
            grid.add(new Label("", new ImageView(idleImage)), 8, i + i/8);
        }

        grid.add(new Label("", new ImageView(bossImage)), 8, 8);
    }

    /**-----------------------------------------------------
     *  initializes the label matrix which contains weather conditions
     *  according to newly created random map
     **----------------------------------------------------*/
    private void initializeLabelMatrix()
    {
        for ( int i = 0; i < 16; ++i)
            for ( int j = 0; j < 16; ++j) {

                switch (myGame.getMap()[i / 4][j / 4].x) {
                    case 0:
                        labels[i][j] = new Label("", new ImageView(snowy));
                        break;
                    case 1:
                        labels[i][j] = new Label("", new ImageView(foggy));
                        break;
                    case 2:
                        labels[i][j] = new Label("", new ImageView(rainy));
                        break;
                    case 3:
                        labels[i][j] = new Label("", new ImageView(sunny));
                        break;
                }
                labels[i][j].setStyle("-fx-border-color:darkblue;");
            }
    }

    /**
     *  displays the monster which shows up at that turn
     *  if no monster shows up, does nothing
     **/
    private void displayMonster()
    {
        if ( myGame.getMonster() == null)
            return ;

        Stage monsterStage;
        Scene monsterScene;
        VBox monsterVBox;
        TextArea name, description;
        Label monsterImageHolder;

        monsterStage = new Stage();

        monsterVBox = new VBox(10);

        name = new TextArea(myGame.getMonster().getName());
        name.setEditable(false);

        description = new TextArea( "Strength: "  + myGame.getMonster().getStrength());
        description.setEditable(false);

        monsterImageHolder = new Label("", new ImageView(myGame.getMonster().getImage()));


        Timeline timeline;
        timeline = new Timeline( new KeyFrame(Duration.seconds(1), e ->
        {
        })) ;
        timeline.setCycleCount(2);
        timeline.play() ;

        timeline.setOnFinished( closeTheWindow ->
        {
            monsterStage.close();
        });

        monsterVBox.getChildren().addAll( name, monsterImageHolder, description);
        monsterVBox.getStylesheets().add("palmcity/images/Theme.css");


        monsterStage.centerOnScreen();
        monsterScene = new Scene(monsterVBox, 150, 300);
        monsterStage.setScene(monsterScene);
        monsterStage.showAndWait();

    }


}
