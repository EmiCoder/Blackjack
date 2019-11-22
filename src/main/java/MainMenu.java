import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

public class MainMenu {

    public MainMenu() {
        MenuBar menuBar=new MenuBar();
        menuBar.setMinWidth(600);
        menuBar.setMinHeight(50);
        menuBar.setTranslateX(-5);
        menuBar.setTranslateY(-20);
        menuBar.setStyle("-fx-background-color: #8B0000; -fx-text-fill: #FFFFFF");
        menuBar.setPadding(new Insets(15));

        Menu play = new Menu("PLAY");
                play.setGraphic(new ImageView("playButton.png"));
                MenuItem start = new MenuItem("Start");
                start.setGraphic(new ImageView("playItem.png"));
                start.setOnAction(event -> {
                    TicTacToe.playable.set(true);
                });
                play.getItems().add(start);

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
        restartItem.setOnAction(event -> TicTacToe.resetTheGame());
        restart.getItems().add(restartItem);

        menuBar.getMenus().addAll(play, chooseCharacter, rounds, restart);
        TicTacToe.grid.getChildren().add(menuBar);
    }
}
