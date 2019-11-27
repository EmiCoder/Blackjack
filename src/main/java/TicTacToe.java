import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
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
import javafx.util.Duration;
import java.util.*;


public class TicTacToe extends Application {

    public static Image backgroundImage = new Image("backgroundAngryBirds.png");
    protected static ImageView playerImage = new ImageView("red.png");

    private static TicTacToeButton [][] buttons = new TicTacToeButton[3][3];

    private static Button button;

    protected static Text messageRound= new Text();
    protected static Text messagePlayerPoints = new Text();
    protected static Text messageComputerPoints= new Text();

    protected static StackPane stackPaneRound= new StackPane();
    protected static StackPane stackPanePlayerPoints = new StackPane();
    protected static StackPane stackPaneComputerPoints = new StackPane();
    protected static StackPane playerFinalStackPane = new StackPane();
    protected static StackPane computerFinalStackPane = new StackPane();
    protected static StackPane gameFinalStackPane = new StackPane();

    protected static GridPane grid;

    protected static ColumnConstraints column;
    protected static RowConstraints row;

    private static final int startOfRows = 1;
    private static final int endOfRows = 4;
    private static final int startOfColumns = 2;
    private static final int endOfColumns = 5;
    protected static final int suggestedAmountOfRounds = 5;

    protected static int roundsCounter = 1;
    protected static int amountOfRounds = 1;
    protected static int playerPoints= 0;
    protected static int computerPoints= 0;

    protected static SimpleBooleanProperty playable = new SimpleBooleanProperty(false);

    protected static SimpleBooleanProperty playerMoveExecuted= new SimpleBooleanProperty(false);
    private static SimpleBooleanProperty expectedPlayerMoveState= new SimpleBooleanProperty(true);
    private static BooleanProperty completedProperty = new SimpleBooleanProperty();

    private static int rowOfClickedButton;
    private static int columnOfClickedButton;

    protected static boolean playerWinner = false;
    protected static boolean computerWinner = false;

    protected static Stage genearalStage;

    protected static boolean playerHasMoved = false;



    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws InterruptedException{
        startGame(primaryStage);
    }

    protected static void startGame(Stage stage) {
        try {
            completedProperty.bind(playerMoveExecuted.isEqualTo(expectedPlayerMoveState));
            completedProperty.addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    for (Node node : grid.getChildren()) {
                        if (node instanceof TicTacToeButton) {
                            if (grid.getRowIndex(node) == rowOfClickedButton && grid.getColumnIndex(node) == columnOfClickedButton) {
                                ((TicTacToeButton) node).setGraphic(new ImageView(playerImage.getImage()));
                                ((TicTacToeButton) node).changeStateWithPlayerMove();
                                if (checkTheResult(3)) {
                                    playerWon();
                                    if (playerWinner) {
                                        break;
                                    }
                                } else if (filledBoard() && roundsCounter != amountOfRounds) {
                                    roundsCounter++;
                                    resetTheButtons();
                                } else if (filledBoard() && roundsCounter == amountOfRounds) {
                                    gameFinal();
                                    resetTheGame();
                                } else {
                                    PauseTransition wait = new PauseTransition(Duration.seconds(1));
                                    wait.setOnFinished((e) -> {
                                        wait.playFromStart();
                                        setTheComputerMove();
                                        if (checkTheResult(-3)) {
                                            computerWon();
                                        }
                                        wait.stop();
                                    });
                                    wait.play();
                                }
                            }
                        }
                    }
                    playerMoveExecuted.setValue(false);
                }
            });



            stage.setScene(new Scene(setScene(), 600, 600));
            stage.setTitle("Bird VS Bird");
            genearalStage = stage;
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static GridPane setScene() {
        grid = prepareGrid();
        fillTheGridWithRowsAndColumn();
        setButtonsGrid();
        prepareMenuBar();
        prepareRoundStack();
        preparePlayerAndComputerPointsStack();
        return grid;
    }

    protected static GridPane prepareGrid() {
        GridPane gp = new GridPane();
        gp.setVgap(5);
        gp.setHgap(5);
        gp.setPadding(new Insets(5,5,5,5));
        gp.setBackground(setStageBackground());
        return gp;
    }

    protected static void fillTheGridWithRowsAndColumn() {
        for (int i = 0; i < 7; i++) {
            column= new ColumnConstraints();
            column.setPercentWidth(15);
            row = new RowConstraints();
            row.setPercentHeight(15);
            grid.getColumnConstraints().add(i, column);
            grid.getRowConstraints().add(i, row);
        }
    }

    protected  static Background setStageBackground() {
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(TicTacToe.backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        return new Background(backgroundImage);
    }

    private static void prepareMenuBar() {
        MainMenu mainMenu = new MainMenu();
    }

    protected static void prepareRoundStack() {
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

    protected static void preparePlayerAndComputerPointsStack() {
        stackPanePlayerPoints.setTranslateY(70);
        stackPanePlayerPoints.setTranslateX(435);

        messagePlayerPoints.setText("Player points: " + String.valueOf(playerPoints));
        messagePlayerPoints.setFill(Color.WHITE);

        Rectangle playerRectangle = new Rectangle(145, 40);
        playerRectangle.setFill(Color.DARKRED);
        stackPanePlayerPoints.getChildren().addAll(playerRectangle, messagePlayerPoints);

        stackPaneComputerPoints.setTranslateY(125);
        stackPaneComputerPoints.setTranslateX(435);

        messageComputerPoints.setText("Computer points: " + String.valueOf(computerPoints));
        messageComputerPoints.setFill(Color.WHITE);

        Rectangle computerRectangle = new Rectangle(145, 40);
        computerRectangle.setFill(Color.DARKRED);

        stackPaneComputerPoints.getChildren().addAll(computerRectangle, messageComputerPoints);
        grid.getChildren().addAll(stackPanePlayerPoints, stackPaneComputerPoints);
    }

    protected static void resetTheGame() {
        playable.set(false);
        playerMoveExecuted.setValue(false);
    }

    protected static void resetTheButtons() {
        ObservableList<Node> childrens = grid.getChildren();
        for (Node node : childrens) {
            if (node instanceof TicTacToeButton) {
                ((TicTacToeButton) node).setState(0);
                ((TicTacToeButton) node).setGraphic(null);
            }
        }
        messageRound.setText("Round: " + roundsCounter);
    }

    protected  static void setButtonsGrid() {
        for (int i = startOfRows; i < endOfRows; i++) {
            for (int j = startOfColumns; j < endOfColumns; j++) {
                button = new TicTacToeButton();
                button.setPrefSize(70, 70);
                button.setStyle("-fx-background-color: #FFC0CB");
                button.setOnAction(event -> {
                    if (playable.getValue() && playerHasMoved) {
                        rowOfClickedButton = GridPane.getRowIndex((TicTacToeButton)event.getSource());
                        columnOfClickedButton = GridPane.getColumnIndex((TicTacToeButton)event.getSource());
                        playerMoveExecuted.set(true);
                        playerHasMoved = false;
                    }
                });

                GridPane.setConstraints(button,j, i);
                GridPane.setHalignment(button, HPos.CENTER);
                grid.getChildren().add(button);

                buttons[i - 1][j - 2] = (TicTacToeButton) button;
            }
        }
    }

    private static void playerWon() {
        playerPoints++;
        messagePlayerPoints.setText("Player points: " +String.valueOf(playerPoints));
        if (roundsCounter == amountOfRounds && playerPoints > computerPoints) {
            playerWinner = true;
            resetTheGame();
            playerMoveExecuted.setValue(false);
            playable.setValue(false);
            playerFinal();
        } else if (roundsCounter == amountOfRounds && playerPoints == computerPoints) {
            gameFinal();
            resetTheGame();
        } else {
            roundsCounter++;
            resetTheButtons();
        }
    }

    private static void playerFinal() {
        playerFinalStackPane.setTranslateY(300);
        playerFinalStackPane.setTranslateX(30);

        ImageView winnerImage = new ImageView("winner.png");

        AudioClip sound = new AudioClip(TicTacToe.class.getClassLoader().getResource("winningSound.mp3").toString());
        sound.play();

        playerFinalStackPane.getChildren().add(winnerImage);
        grid.getChildren().add(playerFinalStackPane);
        setNewSettings();
    }

    private static void computerWon() {
        computerPoints++;
        messageComputerPoints.setText("Computer points: " + String.valueOf(computerPoints));
        if (roundsCounter == amountOfRounds && playerPoints < computerPoints) {
            resetTheGame();
            computerFinal();
            playerMoveExecuted.setValue(false);
            playable.setValue(false);
        } else if (roundsCounter == amountOfRounds && playerPoints == computerPoints) {
            gameFinal();
            resetTheGame();
        } else {
            roundsCounter++;
            resetTheButtons();
        }
    }

    private static void computerFinal() {

        computerWinner = true;

        computerFinalStackPane.setTranslateY(300);
        computerFinalStackPane.setTranslateX(30);

        ImageView winnerImage = new ImageView("winnerComputer.png");
        AudioClip sound = new AudioClip(TicTacToe.class.getClassLoader().getResource("booSound.mp3").toString());

        sound.play();

        computerFinalStackPane.getChildren().add(winnerImage);
        grid.getChildren().add(computerFinalStackPane);
        setNewSettings();
    }

    private static void gameFinal() {

        gameFinalStackPane.setTranslateY(300);
        gameFinalStackPane.setTranslateX(30);

        ImageView tryAgainImage = new ImageView("tryAgain.png");

        AudioClip sound = new AudioClip(TicTacToe.class.getClassLoader().getResource("winningSound.mp3").toString());
        sound.play();

        gameFinalStackPane.getChildren().add(tryAgainImage);
        grid.getChildren().add(gameFinalStackPane);
        setNewSettings();
    }

    private static void setNewSettings() {
        TicTacToe.playerPoints = 0;
        TicTacToe.computerPoints = 0;
        TicTacToe.roundsCounter = 1;
        TicTacToe.amountOfRounds = 1;
    }

    private static void setTheComputerMove() {
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
        playerHasMoved = true;
    }

    private static boolean filledBoard() {
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

    public static boolean checkTheResult(int expectedResult) {

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