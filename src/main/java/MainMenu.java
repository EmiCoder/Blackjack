import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainMenu {

    public MainMenu() {
        MenuBar menuBar=new MenuBar();
        menuBar.setMinWidth(600);
        menuBar.setMinHeight(50);
        menuBar.setTranslateX(-5);
        menuBar.setTranslateY(-20);
        menuBar.setStyle("-fx-background-color: #8B0000");
        menuBar.setPadding(new Insets(15));

        Menu play = new Menu("PLAY");
                play.setGraphic(new ImageView("playButton.png"));
                MenuItem start = new MenuItem("Start");
                start.setGraphic(new ImageView("playItem.png"));
                play.getItems().add(start);
                start.setOnAction(event -> {
                    TicTacToe.playable.set(true);
                });


        Menu chooseCharacter = new Menu("CHARACTERS");
                chooseCharacter.setGraphic(new ImageView("chuckS2.png"));

                CharactersList list = new CharactersList();
                for (Character character : list.getList()) {
                    MenuItem item = new MenuItem(character.getName());
                    item.setGraphic(new ImageView(character.getImage()));
                    item.setOnAction(event -> {
                        if (TicTacToe.playable.getValue()) {
                            TicTacToe.playerImage = new ImageView(character.getImage());
                        }
                    });
                    chooseCharacter.getItems().add(item);
                }

        Menu rounds = new Menu("ROUNDS");
                rounds.setGraphic(new ImageView("roundS.png"));
                for (int i = 0; i < 5; i++) {
                    int tmp = i;
                    MenuItem item = new MenuItem(String.valueOf(i + 1));
                    item.setGraphic(new ImageView("roundItem.png"));
                    item.setOnAction(event -> {
                        if (TicTacToe.playable.getValue()) {
                            TicTacToe.amountOfRounds = tmp + 1;
                        }
                    });
                    rounds.getItems().add(item);
                }

        Menu restart = new Menu("RESTART");
        restart.setGraphic(new ImageView("restart.png"));
        MenuItem restartItem = new MenuItem("Restart");
        restartItem.setGraphic(new ImageView("restartItem.png"));
        restartItem.setOnAction(event -> {

            TicTacToe.roundsCounter = 1;
            TicTacToe.amountOfRounds = 1;
            TicTacToe.playerPoints= 0;
            TicTacToe.computerPoints= 0;

            TicTacToe.resetTheGame();
            TicTacToe.resetTheButtons();
            TicTacToe.preparePlayerAndComputerPointsStack();

            TicTacToe.startGame(TicTacToe.genearalStage);
        });
        restart.getItems().add(restartItem);

        menuBar.getMenus().addAll(play, chooseCharacter, rounds, restart);
        TicTacToe.grid.getChildren().add(menuBar);
    }


}
