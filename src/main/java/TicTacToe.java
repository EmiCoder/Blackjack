
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;



public class TicTacToe extends Application {

    private Image backgroundImage = new Image("backgroundAngryBirds.png");

    private ImageView playerImage;
    private ImageView computerImage = new ImageView("pig.png");

    private TicTacToeButton [][] buttons = new TicTacToeButton[3][3];

    private Button button;

    GridPane grid;

    private ColumnConstraints column;
    private RowConstraints row;

    private final int startOfRows = 1;
    private final int endOfRows = 4;
    private final int startOfColumns = 2;
    private final int endOfColumns = 5;

    private int counter = 0;
    private int amountOfRoundsToWin= 1;
    private int amountOfWonPlayerRounds = 0;
    private int amountOfWonComputerRounds = 0;
    private boolean playerFirst = true;
    private SimpleBooleanProperty playable = new SimpleBooleanProperty(false);


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(this.backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        grid = new GridPane();
        grid.setBackground(background);

//        grid.setGridLinesVisible(true);
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setPadding(new Insets(5,5,5,5));

        for (int i = 0; i < 7; i++) {
            column= new ColumnConstraints();
            column.setPercentWidth(15);
            row = new RowConstraints();
            row.setPercentHeight(15);
            grid.getColumnConstraints().add(i, column);
            grid.getRowConstraints().add(i, row);
        }

//        for (int i = startOfRows; i < endOfRows; i++) {
//                for (int j = startOfColumns; j < endOfColumns; j++) {
//                    button = new TicTacToeButton();
//                    button.setPrefSize(70, 70);
//                    button.setStyle("-fx-background-color: #FFC0CB");
//                    button.setOnAction(event -> {
//                        TicTacToeButton button = (TicTacToeButton) event.getSource();
//                        if (counter % 2 == 0) {
//                            button.setGraphic(new ImageView(playerImage.getImage()));
//                            button.changeStateWithPlayerMove();
//                            counter++;
//                        } else {
//                            button.setGraphic(new ImageView(computerImage.getImage()));
//                            button.changeStateWithComputerMove();
//                            counter++;
//                        }
//
//                    });
//
//
//                        GridPane.setConstraints(button,j, i);
//                        GridPane.setHalignment(button, HPos.CENTER);
//                        grid.getChildren().add(button);
//
//                        buttons[i - 1][j - 2] = (TicTacToeButton) button;
//                }
//        }

        for (int i = startOfRows; i < endOfRows; i++) {
            for (int j=startOfColumns; j < endOfColumns; j++) {
                button=new TicTacToeButton();
                button.setPrefSize(70, 70);
                button.setStyle("-fx-background-color: #FFC0CB");
                button.setOnAction(event -> {
                    TicTacToeButton button=(TicTacToeButton) event.getSource();
                    if (counter % 2 == 0) {
                        button.setGraphic(new ImageView(playerImage.getImage()));
                        button.changeStateWithPlayerMove();
                        if (checkTheResult(3)) {
                            amountOfWonPlayerRounds++;
                            System.out.println("player won " + amountOfWonPlayerRounds);

                            if (amountOfWonPlayerRounds == amountOfRoundsToWin) {
                                System.out.println("You won!!!");
                            } else {
                                amountOfWonPlayerRounds=0;
                            }
                        }
                    } else {
                        button.setGraphic(new ImageView(computerImage.getImage()));
                        button.changeStateWithComputerMove();
                        if (checkTheResult(-3)) {
                            amountOfWonComputerRounds++;
                            System.out.println("computer won " + amountOfWonComputerRounds);

                            if (amountOfWonComputerRounds == amountOfRoundsToWin) {
                                System.out.println("computer won!!!");
                            } else {
                                amountOfWonComputerRounds=0;
                            }
                        }
                    }
                    counter++;
                });
            }}

                MenuBar menuBar=new MenuBar();
                menuBar.setMinWidth(600);
                menuBar.setMinHeight(50);
                menuBar.setTranslateX(-5);
                menuBar.setTranslateY(-5);
                menuBar.setStyle("-fx-background-color: c70029; -fx-text-fill: #FFFFFF");
                menuBar.setPadding(new Insets(15));


                Menu menu1=new Menu("Choose your character");
                MenuItem menuItem1=new MenuItem("Red");
                menuItem1.setGraphic(new ImageView("red.png"));
                MenuItem menuItem2=new MenuItem("Matilda");
                menuItem2.setGraphic(new ImageView("matildaS.png"));
                MenuItem menuItem3=new MenuItem("Bomb");
                menuItem3.setGraphic(new ImageView("bomgS.png"));
                MenuItem menuItem4=new MenuItem("Chuck");
                menuItem4.setGraphic(new ImageView("chuckS.png"));

                menuItem1.setOnAction(e -> {
                    if (playable.getValue()) {
                        playerImage=new ImageView("red.png");
                    }
                });
                menuItem2.setOnAction(e -> {
                    if (playable.getValue()) {
                        playerImage=new ImageView("matildaS.png");
                    }
                });
                menuItem3.setOnAction(e -> {
                    if (playable.getValue()) {
                        playerImage=new ImageView("bomgS.png");
                    }
                });
                menuItem4.setOnAction(e -> {
                    if (playable.getValue()) {
                        playerImage=new ImageView("chuckS.png");
                    }
                });
                menu1.getItems().addAll(menuItem1, menuItem2, menuItem3, menuItem4);


                Menu menu2=new Menu("How many rounds?");
                MenuItem item1=new MenuItem("1");
                MenuItem item2=new MenuItem("2");
                MenuItem item3=new MenuItem("3");
                MenuItem item4=new MenuItem("4");
                MenuItem item5=new MenuItem("5");
                item1.setOnAction(event -> {
                    if (playable.getValue()) {
                        amountOfRoundsToWin=1;
                    }
                });
                item2.setOnAction(event -> {
                    if (playable.getValue()) {
                        amountOfRoundsToWin=2;
                    }
                });
                item3.setOnAction(event -> {
                    if (playable.getValue()) {
                        amountOfRoundsToWin=3;
                    }
                });
                item4.setOnAction(event -> {
                    if (playable.getValue()) {
                        amountOfRoundsToWin=4;
                    }
                });
                item5.setOnAction(event -> {
                    if (playable.getValue()) {
                        amountOfRoundsToWin=5;
                    }
                });
                menu2.getItems().addAll(item1, item2, item3, item4, item5);


                Menu menu3=new Menu("Who starts?");
                MenuItem itemOne=new MenuItem("Player");
                MenuItem itemTwo=new MenuItem("Computer");
                itemTwo.setOnAction(event -> playerFirst=false);
                menu3.getItems().addAll(itemOne, itemTwo);

                Menu menu4=new Menu("Play");
                MenuItem i1=new MenuItem("Start");
                MenuItem i2=new MenuItem("New Game");
                i1.setOnAction(event -> playable.set(true));
                i2.setOnAction(event -> {
                    resetTheGame();
                });
                menu4.getItems().addAll(i1, i2);

                menuBar.getMenus().addAll(menu1, menu2, menu3, menu4);

                VBox vBox=new VBox(menuBar);

                grid.getChildren().add(vBox);

                Scene scene=new Scene(grid, 600, 600);
                primaryStage.setTitle("Tic Tac Toe");
                primaryStage.setScene(scene);
                primaryStage.show();
            }





    private void resetTheGame() {
        playable.set(false);
    }

    private void resetTheButtons() {
        ObservableList<Node> childrens = grid.getChildren();
        for (Node node : childrens) {
            if (node instanceof TicTacToeButton) {
                ((TicTacToeButton) node).setState(0);
                ((TicTacToeButton) node).setGraphic(null);
                counter = 0;
                System.out.println("counter ot" + counter);
            }
        }
    }

    public boolean checkTheResult(int expectedResult) {

            for (int i = 0; i < buttons.length; i++) {
                int tmpResult = 0;
                for (int j = 0; j < buttons.length; j++) {
                    tmpResult += buttons[i][j].getValue();
                }
                if (tmpResult == expectedResult) {
                    return true;
                }
            }
            for (int i = 0; i < buttons.length; i++) {
                int tmpResult = 0;
                for (int j = 0; j < buttons.length; j++) {
                    tmpResult += buttons[j][i].getValue();
                }
                if (tmpResult == expectedResult) {
                    return true;
                }
            }
            if (buttons[0][0].getValue() + buttons[1][1].getValue() +buttons[2][2].getValue() == expectedResult) {
                return true;
            }
            if (buttons[0][2].getValue() + buttons[1][1].getValue() +buttons[2][0].getValue() == expectedResult) {
                return true;
            }
            return false;
    }


}
