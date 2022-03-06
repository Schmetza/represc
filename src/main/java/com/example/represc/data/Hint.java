package com.example.represc.data;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;


/**
 * @author Schmetz Arnaud
 * @version 1.0
 *
 * @overview A Hint is a hint (very helpful I know). It may or may not be tied to one or multiple Physical Element(s)
 * {@link PhysicalElement}.
 *
 * @specfield name: String // The name of the Countdown as a String. Must be neither, null, empty, nor blank.
 * @specfield duration: Duration // The duration of the Countdown. Must be not null and positive.
 *
 * // facultative
 * @specfield description: String // The description of the Hint as a String.
 * @specfield position: Point2D // The position of the hint, relative to the map. {@link com.example.represc.gui.GameMap}
 * x and y must be positive, but both can exceed the actual size of the GameMap, as it would grow itself (todo).
 * @specfield physicalElements: ArrayList of UUIDs // The list of all the UUIDs of the Physical Elements which are linked
 * to the hint.
 *
 * @invariant {@code duration} must be positive.
 * @invariant {@code name != null && !name.isEmpty() && !name.isBlank()}
 * @invariant {@code position.getX() >= 0 && position.getY() >= 0}
 * @invariant  if physicalElement != null : (every UUID of physicalElement must be different from null && must be the
 * UUID of a Physical Element of the escape Game).
 * @invariant {@code if (physicalElements != null) {!physicalElements.isEmpty()} }
 */
public class Hint {
    private String name;
    private String description; //facultative
    private Point2D position; //facultative
    private ArrayList<UUID> physicalElements; //facultative


    /**
     * @precondition {@code name != null && !name.isEmpty() && !name.isBlank()}
     * @postcondition Initialises this with the given name.
     */
    public Hint(String name){
        assert name != null : "Hint: Tries to create a hint with a name being a null string";
        assert !name.isEmpty() : "Hint: trying to create a hint with an empty String name";
        assert !name.isBlank() : "Hint: trying to create a hint with a blank String name";

        this.name = name;
    }

    /**
     * @return Returns the name of the Hint as a string.
     * **/
    public String getName() {
        return name;
    }
    /**
     * @precondition {@code name != null && !name.isEmpty() && !name.isBlank()}
     * @postcondition Sets the name of the Hint to be the String {@code name}.
     */
    public void setName(String name) {
        assert name != null : "setName: Tries to change the Hint's name to a null string";
        assert !name.isEmpty() : "setName: trying to change the Hint's name to an empty String name";
        assert !name.isBlank() : "setName: trying to change the Hint's name to a blank String name";

        this.name = name;
    }


    /**
     * @return Returns an Optional Object containing :
     * the description of the Hint as a String if {@code description} is not null
     * OR
     * being empty if {@code description} is null.
     */
    public Optional<String> getDescription(){
        if (description != null){
            return Optional.of(description);
        }
        return Optional.empty();
    }
    /**
     * @precondition {@code description != null}
     * @postcondition Sets the description of the PHint to be the String {@code description}.
     */
    public void setDescription(String description) {
        assert description != null : "setDescription: trying to set the description of the Hint, but parameter description " +
                "is null.";
        this.description = description;
    }
    /**
     * @effects Sets the description of the Hint to {@code null}.
     * **/
    public void deleteDescription(){
        description = null;
    }

    /**
     * @return Returns an Optional Object containing :
     * the position of the Hint as a Position if {@code position} is not null
     * OR
     * being empty if {@code position} is null.
     */
    public Optional<Point2D> getPosition(){
        if(position == null){
            return Optional.empty();
        }
        return Optional.of(position);
    }
    /**
     * @precondition {@code position.getX() >= 0 && position.getY() >=0}
     * @postcondition Sets the position of the Hint to be the Point2D {@code position}.
     */
    public void setPosition(Point2D position){
        assert position.getX() >= 0 && position.getY() >=0: "setPosition: Trying to set the position of an hint with a " +
                "negative x and/or y value.";

        this.position = position;
    }
    /**
     * @effects Sets the position of the Hint to {@code null}.
     * **/
    public void deletePosition(){
        position = null;
    }

    /**
     * @effects Adds the UUID {@code toAdd} of a Physical Element, into {@code physicalElements}.
     */
    public void addPhysicalElement(UUID toAdd){
        assert toAdd != null : "addPhysicalElement: Tries to add a Physical Element to a Problem with a null UUID";
        assert EscapeGame.getEscapeGame().containsPhysicalElement(toAdd): "addPhysicalElement: Tries to add a Physical " +
                "Element to a Hint, with a UUID which is not in the list of Physical Elements of the Escape Game.";

        if (physicalElements == null) { physicalElements = new ArrayList<>();}
        physicalElements.add(toAdd);
    }
    /**
     * @return Returns an empty Optional if the Hint has no Physical Elements. Returns an Optional containing an
     * ArrayList of the UUIDs of all its Physical Elements otherwise.
     */
    public Optional<ArrayList<UUID>> getPhysicalElements() {
        if (physicalElements == null) return Optional.empty();

        ArrayList<UUID> toReturn = new ArrayList<>(physicalElements);
        return Optional.of(toReturn);
    }
    /**
     * @precondition {@code physicalElements.contains(toDelete) && physicalElements != null}
     * @postcondition Deletes the UUID {@code toDelete} from {@code physicalElements} and sets {@code physicalElements}
     * to null, if it was the only UUId on that ArrayList.
     */
    public void deletePhysicalElement(UUID toDelete){
        assert physicalElements != null : "deletePhysicalElement: Trying to delete a Physical Element from the Physical " +
                "Elements list of a Hint, list which is empty";
        assert physicalElements.contains(toDelete) : "deletePhysicalElement: Trying to delete a Physical Element from " +
                "the Physical Elements list of a Hint, which doesn't contain that Physical Element UUID.";

        physicalElements.remove(toDelete);
        if (physicalElements.isEmpty()) physicalElements = null;
    }

    public String toString(){
        StringBuilder s = new StringBuilder("Hint : {\n\t");
        s.append("name: ").append(name);
        if (description != null){
            s.append(",\n\tdescription: ").append(description);
        }
        if(position != null) {
            s.append(",\n\tposition: [").append(position.getX()).append(", ").append(position.getY()).append("]");
        }
        if(physicalElements != null){
            s.append(",\n\tphysicalElements: [");
            physicalElements.forEach((id) -> s.append(id).append(", "));
            s.setLength(s.length() - 2);
            s.append("]");
        }
        s.append(" }");
        return s.toString();
    }
    public boolean repOK(){
        if (physicalElements != null) {
            if(physicalElements.isEmpty()){ return false; }
            Optional<ArrayList<UUID>> actualPhysicalElements = EscapeGame.getEscapeGame().getAllPhysicalElementsUUID();
                if (actualPhysicalElements.isEmpty()){ return false; }
                else {
                    for (var element : physicalElements){
                        if (element == null) return false;
                        if (!actualPhysicalElements.get().contains(element)) return false;
                    }
                }
            }
        if (position.getX() <= 0 && position.getY() <=0) return false;
        if (name == null || name.isBlank() || name.isEmpty()) return false;
        return true;
    }
}