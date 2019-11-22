
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class TicTacToe extends Application {

    private Image backgroundImage = new Image("backgroundAngryBirds.png");
    protected static ImageView playerImage = new ImageView("red.png");

    private TicTacToeButton [][] buttons = new TicTacToeButton[3][3];

    private Button button;

    private Text messageRound= new Text();
    private Text messagePlayerPoints = new Text();
    private Text messageComputerPlayerPoints = new Text();

    private StackPane stackPaneRound= new StackPane();
    private StackPane stackPanePlayerPoints = new StackPane();
    private StackPane stackPaneComputerPoints = new StackPane();
    private StackPane playerFinalStackPane = new StackPane();
    private StackPane computerFinalStackPane = new StackPane();

    protected static GridPane grid;

    private ColumnConstraints column;
    private RowConstraints row;

    private final int startOfRows = 1;
    private final int endOfRows = 4;
    private final int startOfColumns = 2;
    private final int endOfColumns = 5;

    private int roundsCounter = 1;
    protected static int amountOfRounds = 1;
    private int playerPoints= 0;
    private int computerPoints= 0;

    protected static SimpleBooleanProperty playable = new SimpleBooleanProperty(false);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(setScene(), 600, 600));
        primaryStage.setTitle("Bird VS Bird");
        primaryStage.show();
 }

    private GridPane setScene() {
        grid = prepareGrid();
        fillTheGridWithRowsAndColumn();
        setButtonsGrid();
        prepareMenuBar();
        prepareRoundStack();
        preparePlayerAndComputerPointsStack();

//        grid.setGridLinesVisible(true);
        return grid;
    }

    private GridPane prepareGrid() {
        GridPane gp = new GridPane();
        gp.setVgap(5);
        gp.setHgap(5);
        gp.setPadding(new Insets(5,5,5,5));
        gp.setBackground(setStageBackground());
        return gp;
    }

    private void fillTheGridWithRowsAndColumn() {
        for (int i = 0; i < 7; i++) {
            column= new ColumnConstraints();
            column.setPercentWidth(15);
            row = new RowConstraints();
            row.setPercentHeight(15);
            grid.getColumnConstraints().add(i, column);
            grid.getRowConstraints().add(i, row);
        }
    }

    private Background setStageBackground() {
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(this.backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        return new Background(backgroundImage);
    }

    private void prepareMenuBar() {
        MainMenu mainMenu = new MainMenu();
    }

    private void prepareRoundStack() {
        stackPaneRound.setTranslateY(70);
        messageRound.setText("Round " + roundsCounter);
        messageRound.setTextAlignment(TextAlignment.CENTER);
        messageRound.setFill(Color.WHITE);

        Rectangle rectangle = new Rectangle(145, 40);
        rectangle.setFill(Color.DARKRED);
        rectangle.setTranslateX(10);
        stackPaneRound.getChildren().addAll(rectangle, messageRound);

        grid.getChildren().add(stackPaneRound);
    }

    private void preparePlayerAndComputerPointsStack() {
        stackPanePlayerPoints.setTranslateY(70);
        stackPanePlayerPoints.setTranslateX(435);

        messagePlayerPoints.setText("Player points: " + String.valueOf(playerPoints));
        messagePlayerPoints.setFill(Color.WHITE);

        Rectangle playerRectangle = new Rectangle(145, 40);
        playerRectangle.setFill(Color.DARKRED);
        stackPanePlayerPoints.getChildren().addAll(playerRectangle, messagePlayerPoints);

        stackPaneComputerPoints.setTranslateY(125);
        stackPaneComputerPoints.setTranslateX(435);

        messageComputerPlayerPoints.setText("Computer points: " + String.valueOf(computerPoints));
        messageComputerPlayerPoints.setFill(Color.WHITE);

        Rectangle computerRectangle = new Rectangle(145, 40);
        computerRectangle.setFill(Color.DARKRED);

        stackPaneComputerPoints.getChildren().addAll(computerRectangle, messageComputerPlayerPoints);
        grid.getChildren().addAll(stackPanePlayerPoints, stackPaneComputerPoints);
    }

    protected static void resetTheGame() {
        playable.set(false);
    }

    private void resetTheButtons() {
        ObservableList<Node> childrens = grid.getChildren();
        for (Node node : childrens) {
            if (node instanceof TicTacToeButton) {
                ((TicTacToeButton) node).setState(0);
                ((TicTacToeButton) node).setGraphic(null);
            }
        }
        messageRound.setText("Round: " + roundsCounter);
    }

    private void setButtonsGrid() {
        for (int i = startOfRows; i < endOfRows; i++) {
            for (int j = startOfColumns; j < endOfColumns; j++) {
                button = new TicTacToeButton();
                button.setPrefSize(70, 70);
                button.setStyle("-fx-background-color: #FFC0CB");
                button.setOnAction(event -> {
                    if (playable.getValue()) {
                            TicTacToeButton button = (TicTacToeButton) event.getSource();
                            button.setGraphic(new ImageView(playerImage.getImage()));
                            button.changeStateWithPlayerMove();
                            if (checkTheResult(3)) {
                                playerWon();
                            }  else if (filledBoard()) {
                                roundsCounter++;
                                resetTheButtons();
                            } else {
                                setTheComputerMove();
                                if (checkTheResult(-3)) {
                                    computerWon();
                                }
                            }
                        }
                });

                GridPane.setConstraints(button,j, i);
                GridPane.setHalignment(button, HPos.CENTER);
                grid.getChildren().add(button);

                buttons[i - 1][j - 2] = (TicTacToeButton) button;
            }
        }
    }

    private void playerWon() {
            playerPoints++;
            messagePlayerPoints.setText("Player points: " +String.valueOf(playerPoints));
            if (roundsCounter == amountOfRounds && playerPoints > computerPoints) {
                playerFinal();
                resetTheGame();
            } else if (roundsCounter == amountOfRounds && playerPoints == computerPoints) {
                System.out.println("Nobody won :/");
                resetTheGame();
            } else {
                roundsCounter++;
                resetTheButtons();
            }
        }

    private void playerFinal() {
        playerFinalStackPane.setTranslateY(300);
        playerFinalStackPane.setTranslateX(30);

        ImageView winnerImage = new ImageView("winner.png");

        AudioClip sound = new AudioClip(this.getClass().getResource("winningSound.mp3").toString());
        sound.play();

        playerFinalStackPane.getChildren().add(winnerImage);
        grid.getChildren().add(playerFinalStackPane);
    }

    private void computerWon() {
        computerPoints++;
        messageComputerPlayerPoints.setText("Computer points: " + String.valueOf(computerPoints));
        if (roundsCounter == amountOfRounds && playerPoints < computerPoints) {
            computerFinal();
            resetTheGame();
        } else if (roundsCounter == amountOfRounds && playerPoints == computerPoints) {
            System.out.println("Nobody won :/");
            resetTheGame();
        } else {
            roundsCounter++;
            resetTheButtons();
        }
    }

    private void computerFinal() {
        computerFinalStackPane.setTranslateY(300);
        computerFinalStackPane.setTranslateX(30);

        ImageView winnerImage = new ImageView("winnerComputer.png");

        AudioClip sound = new AudioClip(this.getClass().getResource("booSound.mp3").toString());
        sound.play();

        computerFinalStackPane.getChildren().add(winnerImage);
        grid.getChildren().add(computerFinalStackPane);
    }

    private void setTheComputerMove() {
        List<TicTacToeButton> list = new ArrayList<>();

        for (Node node : grid.getChildren()) {
            if (node instanceof TicTacToeButton) {
                if (((TicTacToeButton) node).getValue() == 0) {
                    list.add((TicTacToeButton)node);
                }
            }
        }
        Random nodeGenerator = new Random();
        TicTacToeButton button = list.get(nodeGenerator.nextInt(list.size()));
        button.setGraphic(new ImageView("pig.png"));
        button.changeStateWithComputerMove();
    }

    private boolean filledBoard() {
        ObservableList<Node> childrens = grid.getChildren();
        for (Node node : childrens) {
            if (node instanceof TicTacToeButton) {
                if (((TicTacToeButton) node).getValue() == 0) {
                    return false;
                }
            }
        }
        return true;
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
