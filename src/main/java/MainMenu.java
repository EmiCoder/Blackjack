
import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

import java.util.stream.IntStream;

public class MainMenu {

    public MainMenu() {
        MenuBar menuBar = new MenuBar();
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
                new CharactersList().getList().stream().forEach(e -> {
                                                MenuItem item = new MenuItem(e.getName());
                                                item.setGraphic(new ImageView(e.getImage()));
                                                item.setOnAction(event -> {
                                                    if (TicTacToe.playable.getValue()) {
                                                        TicTacToe.playerImage = new ImageView(e.getImage());
                                                    }
                                                });
                                                chooseCharacter.getItems().add(item);
                                            });


        Menu rounds = new Menu("ROUNDS");
                rounds.setGraphic(new ImageView("roundS.png"));
                IntStream.range(0,TicTacToe.suggestedAmountOfRounds).forEach(e -> {
                                            MenuItem item = new MenuItem(String.valueOf(e + 1));
                                            item.setGraphic(new ImageView("roundItem.png"));
                                            item.setOnAction(event -> {
                                                if (TicTacToe.playable.getValue()) {
                                                    TicTacToe.amountOfRounds = e + 1;
                                                }
                                            });
                                            rounds.getItems().add(item);
                                        });

        Menu restart = new Menu("RESTART");
                restart.setGraphic(new ImageView("restart.png"));
                MenuItem restartItem = new MenuItem("Restart");
                restartItem.setGraphic(new ImageView("restartItem.png"));
                restartItem.setOnAction(event -> {

                    if (TicTacToe.playerWinner) {
                        TicTacToe.grid.getChildren().remove(TicTacToe.playerFinalStackPane);
                    } else if (TicTacToe.computerWinner) {
                        TicTacToe.grid.getChildren().remove(TicTacToe.computerFinalStackPane);
                    } else {
                        TicTacToe.grid.getChildren().remove(TicTacToe.gameFinalStackPane);
                    }

                    TicTacToe.messagePlayerPoints.setText("Player: " + String.valueOf(TicTacToe.playerPoints));
                    TicTacToe.messageComputerPoints.setText("Computer: " + String.valueOf(TicTacToe.computerPoints));
                    TicTacToe.messageRound.setText("Round: " + String.valueOf(TicTacToe.amountOfRounds));

                    TicTacToe.resetTheButtons();

                    TicTacToe.playable.set(true);
                    TicTacToe.playerMoveExecuted.setValue(false);
                    TicTacToe.playerImage = new ImageView("red.png");
                    TicTacToe.playerWinner = false;
                    TicTacToe.computerWinner = false;
                    TicTacToe.prepareRoundStack();
                    TicTacToe.preparePlayerAndComputerPointsStack();

                    TicTacToe.startGame(TicTacToe.genearalStage);
                });
                restart.getItems().add(restartItem);

                menuBar.getMenus().addAll(play, chooseCharacter, rounds, restart);
                TicTacToe.grid.getChildren().add(menuBar);
    }

}
