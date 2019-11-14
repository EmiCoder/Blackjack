import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class TicTacToe extends Application {

    private Image backgroundImage = new Image("BackgroundTicTacToe.png");
    private Image circleImage =new Image("circle.png");

    private Button [][] buttons = new Button[3][3];

    private Button button;

    private ColumnConstraints column;
    private RowConstraints row;

    private final int startOfRows = 3;
    private final int endOfRows = 6;
    private final int startOfColumns = 2;
    private final int endOfColumns = 5;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(this.backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        GridPane grid = new GridPane();
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

        for (int i = startOfRows; i < endOfRows; i++) {
                for (int j = startOfColumns; j < endOfColumns; j++) {
                    button = new Button();
                    button.setPrefSize(70, 70);
                    button.setStyle("-fx-background-color: #FFC0CB");
                    button.setOnAction(event -> {
                        Button button = (Button) event.getSource();
                        button.setGraphic(new ImageView(circleImage));
                    });
                    GridPane.setConstraints(button,j, i);
                    GridPane.setHalignment(button, HPos.CENTER);
                    grid.getChildren().add(button);
                }
        }

        Scene scene = new Scene(grid,600, 600);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
