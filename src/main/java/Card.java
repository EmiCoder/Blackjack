import javafx.scene.Parent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;


public class Card extends Parent {

    public final Suit suit;
    public final Rank rank;
    public final int value;

    public Card (Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        this.value = rank.value;

        Rectangle rectangle = new Rectangle(80, 100);
        rectangle.setArcHeight(20);
        rectangle.setArcWidth(20);
        rectangle.setFill(Color.WHITE);
        Text text = new Text(toString());
        text.setWrappingWidth(70);
        getChildren().add(new StackPane(rectangle, text));
    }

    enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    };

    enum Rank {
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7),
        EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10),
        ACE(11);

        final int value;
        private Rank (int value) {
            this.value = value;
        }
    };

    @Override
    public String toString() {
        return rank.toString() + "of " + suit.toString();
    }


}
