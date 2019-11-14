import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;



class blackjack extends Application {

    private Deck deck = new Deck();
    private Hand dealer, player;
    private Text message = new Text();

    private SimpleBooleanProperty playable = new SimpleBooleanProperty(false);

    private HBox dealerCardes = new HBox(20);
    private HBox playerCards = new HBox(20);

    private Parent createContent() {
        dealer = new Hand(dealerCardes.getChildren());
        player = new Hand(playerCards.getChildren());

        Pane root = new Pane();
        root.setPrefSize(800, 600);

        Region background = new Region();
        background.setPrefSize(800, 600);
        background.setStyle("-fx-background-color: rgba(0,0,0,1)");

        HBox rootLayout = new HBox(5);
        rootLayout.setPadding(new Insets(5,5,5,5));

        Rectangle rectangleLeft = new Rectangle(550, 560);
        rectangleLeft.setArcWidth(50);
        rectangleLeft.setArcHeight(50);
        rectangleLeft.setFill(Color.GREEN);

        Rectangle rectangleRight = new Rectangle(230, 560);
        rectangleRight.setArcWidth(50);
        rectangleRight.setArcHeight(50);
        rectangleRight.setFill(Color.ORANGE);

        StackPane leftStack = new StackPane();
        VBox leftBox = new VBox(50);
        leftBox.setAlignment(Pos.CENTER);
        Text dealerScore = new Text("Dealer: ");
        Text playerScore = new Text("Player: ");

        leftBox.getChildren().addAll(dealerScore, playerScore, dealerCardes, playerCards, message);
        leftStack.getChildren().addAll(rectangleLeft, leftBox);

        StackPane rightStack = new StackPane();
        VBox rightBox = new VBox(20);
        rightBox.setAlignment(Pos.CENTER);

        final TextField bet = new TextField("BET");
        bet.setDisable(true);
        bet.setMaxWidth(50);
        Text money = new Text("MONEY");
        Button btnPlay = new Button("PLAY");
        Button btnHit = new Button("HIT");
        Button btnStand = new Button("STAND");

        HBox buttonsBox = new HBox(15);
        buttonsBox.setAlignment(Pos.CENTER);
        btnHit.setDisable(true);
        btnStand.setDisable(true);
        buttonsBox.getChildren().addAll(btnHit, btnStand);
        rightBox.getChildren().addAll(bet, btnPlay, money, buttonsBox);
        rightStack.getChildren().addAll(rectangleRight, rightBox);
        rootLayout.getChildren().addAll(leftStack, rightStack);
        root.getChildren().addAll(background, rootLayout);

        btnPlay.disableProperty().bind(playable);
        btnHit.disableProperty().bind(playable.not());
        btnStand.disableProperty().bind(playable.not());

        playerScore.textProperty().bind(new SimpleStringProperty("Player: ").concat(player.integerProperty().asString()));
        dealerScore.textProperty().bind(new SimpleStringProperty("Dealer: ").concat(dealer.integerProperty().asString()));

        player.integerProperty().addListener((obs, old, newValues) -> {
            if (newValues.intValue() >= 21) {
                endGame();
            }
        });

        dealer.integerProperty().addListener((obs, old, newValues) -> {
            if (newValues.intValue() >= 21) {
                endGame();
            }
        });

        btnPlay.setOnAction(event -> {
            startNewGame();
        });

        btnHit.setOnAction(event -> {
            player.takeCard(deck.drawCard());
        });

        btnStand.setOnAction(event -> {
            while (dealer.integerProperty().get() < 17) {
                dealer.takeCard(deck.drawCard());
            }
            endGame();
        });
        return root;
    }

    private void startNewGame() {
        playable.set(true);
        message.setText("");

        deck.refill();
        dealer.reset();
        player.reset();

        dealer.takeCard(deck.drawCard());
        dealer.takeCard(deck.drawCard());
        player.takeCard(deck.drawCard());
        player.takeCard(deck.drawCard());
    }

    private void endGame() {
        playable.set(false);

        int dealerValue = dealer.integerProperty().get();
        int playerValue = player.integerProperty().get();
        String winner = "Exceptional " + dealerValue + " : " + playerValue;

        if (dealerValue == 21 || playerValue > 21 || dealerValue == playerValue || (dealerValue < 21 && dealerValue > playerValue)) {
            winner = "DEALER";
        } else if (playerValue == 21 || dealerValue > 21 || playerValue > dealerValue) {
            winner = "PLAYER";
        }

        message.setText(winner + " WON");
    }

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Blackjack");
        primaryStage.show();
    }
}


