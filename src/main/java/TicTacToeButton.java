import javafx.scene.control.Button;

public class TicTacToeButton extends Button {

   private int value;

    public TicTacToeButton() {
        setState(0);
    }

    public void setState(int state) {
        this.value = state;
    }

    public void changeStateWithPlayerMove() {
        if (this.value == 0) {
            this.setState(1);
        } else {
            System.out.println("illegal player move");
        }
    }

    public void changeStateWithComputerMove() {
        if (this.value == 0) {
            this.setState(-1);
        } else {
            System.out.println("illegal computer move");
        }
    }

    public int getValue() {
        return value;
    }
}
