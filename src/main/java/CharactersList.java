import java.util.ArrayList;
import java.util.List;

public class CharactersList {

    private List<Character> list;

    public CharactersList() {
        this.list = prepareList();
    }

    public List<Character> getList() {
        return list;
    }

    private static List<Character> prepareList() {
        List<Character> list = new ArrayList<>();
        list.add(new Character("Red", "red.png"));
        list.add(new Character("Matilda", "matildaS.png"));
        list.add(new Character("Bomb", "bombS2.png"));
        list.add(new Character("Chuck", "chuckS2.png"));
        return list;
    }
}


