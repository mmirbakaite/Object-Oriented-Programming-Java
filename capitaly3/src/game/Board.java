package game;

import players.Player;
import java.util.*;

public class Board {
    public List<Field> fields;

    /**
     * Construct Board is responsible for determing the number of fields on the board.
     * @param numberOfFields
     */
    public Board(int numberOfFields) {
        fields = new ArrayList<>(numberOfFields);
    }

    /**
     * Method addField is responsible for adding fields on the board.
     * @param field
     */

    public void addField(Field field) {
        fields.add(field);
    }

    /**
     * Method getField is responsible for accesing fields on the board.
     * @param index
     * @return
     */
    public Field getField(int index) {
        return fields.get(index % fields.size());
    }

    /**
     * Method releaseProperties is responsible for realeasing all the properties from the owner.
     * @param owner
     */
    public void releaseProperties(Player owner) {
        for (Field field : fields) {
            if (field instanceof PropertyField) {
                PropertyField property = (PropertyField) field;
                if (property.getOwner() == owner) {
                    property.release();
                }
            }
        }
    }
}
