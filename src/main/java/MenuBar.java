import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

public class MenuBar {


    public MenuBar() {

        javafx.scene.control.MenuBar menuBar = new javafx.scene.control.MenuBar();
        menuBar.setMinWidth(350);
        menuBar.setTranslateX(115);
        menuBar.setTranslateY(215);
        menuBar.setStyle("-fx-background-color: #00FFFF;");
        menuBar.setPadding(new Insets(5));


        Menu menu1 = new Menu("Choose your character");

        MenuItem menuItem1 = new MenuItem("Red");
        menuItem1.setGraphic(new ImageView("red.png"));

        MenuItem menuItem2 = new MenuItem("King Pig");
        menuItem2.setGraphic(new ImageView("pig.png"));

        menuItem1.setOnAction(e -> {
            System.out.println("Menu Item 1 Selected");
        });
        menuItem2.setOnAction(e -> {
            System.out.println("Menu Item 2 Selected");
        });
        menu1.getItems().addAll(menuItem1, menuItem2);


        Menu menu2 = new Menu("How many rounds?");
        Menu menu3 = new Menu("Who starts?");

        menu1.setOnShowing(e -> {
            System.out.println("Showing Menu 1");
        });

        menu1.setOnShown  (e -> { System.out.println("Shown Menu 1"); });
        menu1.setOnHiding (e -> { System.out.println("Hiding Menu 1"); });
        menu1.setOnHidden (e -> { System.out.println("Hidden Menu 1"); });

        menuBar.getMenus().addAll(menu1, menu2, menu3);

    }

}
