import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class Handler extends JPanel implements ActionListener {
    LinkedList<GameObject> object = new LinkedList<GameObject>();

    public void tick(){
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            tempObject.tick();
        }
    }

    public void render(Graphics g){
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            tempObject.render(g);
        }
    }

    public void addObject(GameObject object){
        this.object.add(object);
    }

    public void removeObject(GameObject object){
        this.object.remove(object);
    }

    public LinkedList<GameObject> getListWithoutEnemies(LinkedList<GameObject> oldList){
        LinkedList<GameObject> newList = new LinkedList<GameObject>();
        for (int i = 0; i < oldList.size(); i++) {
            GameObject tempObject = this.object.get(i);
            if (tempObject.getId() == ID.Chansey || tempObject.getId() == ID.Player || tempObject.getId() == ID.Food) {
                newList.add(tempObject);
            }
        }
        return newList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
