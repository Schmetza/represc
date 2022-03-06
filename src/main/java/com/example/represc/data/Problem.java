package com.example.represc.data;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Schmetz Arnaud
 * @version 1.0
 *
 * @overview A problem is a problem or task players are faced with. This Problem may be linked to Physical Element(s)
 * {@link PhysicalElement}.
 * For example : a problem could involve finding a code to open a safe. The safe and the Physical Element on which the
 * code is, are then part of to the Problem.
 *
 *
 * @specfield name: String // The name of the Problem. Must be neither, null, empty, nor blank.
 *
 * // facultative
 * @specfield description: String // The description of the Problem.
 * @specfield physicalElements: ArrayList of UUIDs // The UUIDs of the Physical Elements which are linked to the Problem.
 *
 * @invariant {@code name != null && !name.isEmpty() && !name.isBlank()}
 * @invariant If physicalElements != null : it is not empty.
 * @invariant if physicalElements != null : (every UUID of physicalElement must be different from null && must be the
 * UUID of a Physical Element of the escape Game).
 */
public class Problem {

    private String name;
    private String description; //facultative
    private ArrayList<UUID> physicalElements; //facultative


    /**
     * @precondition {@code name != null && !name.isEmpty() && !name.isBlank()}
     * @postcondition initialise {@code this} with the given name.
     */
    public Problem(String name){
        assert name != null : "trying to create a Problem with a null String name";
        assert !name.isEmpty() : "trying to create a Problem with an empty String name";
        assert !name.isBlank() : "trying to create a Problem with a blank String name";

        this.name = name;
    }

    /**
     * @return Returns the name of the Problem as a string.
     * **/
    public String getName() {
        return name;
    }
    /**
     * @precondition {@code name != null && !name.isEmpty() && !name.isBlank()}
     * @postcondition Sets the name of the Problem with the String {@code name}.
     * **/
    public void setName(String name) {
        assert name != null : "trying to set a Problem's name with a null String name";
        assert !name.isEmpty() : "trying to set a Problem's name with an empty String name";
        assert !name.isBlank() : "trying to set a Problem's name with a blank String name";

        this.name = name;
    }


    /**
     * @return Returns an Optional Object containing :
     * the description of the Problem as a String if {@code description} is not null
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
     * @postcondition Sets the description of the Problem to be the String {@code description}.
     */
    public void setDescription(String description) {
        assert description != null : "setDescription: trying to set the description of a Problem, but parameter description " +
                "is null.";
        this.description = description;
    }
    /**
     * @effects Sets the description of the Problem to {@code null}.
     * **/
    public void deleteDescription(){
        description = null;
    }

    /**
     * @precondition {@toAdd != null}
     * @postcondition Adds the Physical Element {@code toAdd} to the list of Physical Elements of the Problem.
     * If that Problem doesn't have any Physical Element, initialize the ArrayList {@code physicalElements}.
     * to the UUID of {@code toAdd}.
     */
    public void addPhysicalElement(UUID toAdd){
        assert toAdd != null : "addPhysicalElement: Tries to add a Physical Element to a Problem with a null UUID";
        assert EscapeGame.getEscapeGame().containsPhysicalElement(toAdd): "addPhysicalElement: Tries to add a Physical " +
                "Element to a Problem, with a UUID which is not in the list of Physical Elements of the Escape Game.";

        if (physicalElements == null) physicalElements = new ArrayList<>();
        physicalElements.add(toAdd);
    }
    /**
     * @return Returns an empty Optional if the Problem has no Physical Elements. Returns an Optional containing an ArrayList
     * of the UUIDs of all its Physical Elements otherwise.
     */
    public Optional<ArrayList<UUID>> getPhysicalElementsUUID() {
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
                "Elements list of a Problem, which is empty";
        assert physicalElements.contains(toDelete) : "deletePhysicalElement: Trying to delete a Physical Element from " +
                "the Physical Elements list of a Problem, which doesn't contain that Physical Element UUID.";
        physicalElements.remove(toDelete);
        if (physicalElements.isEmpty()) physicalElements = null;
    }

    public String toString(){
        StringBuilder s = new StringBuilder("Problem : {\n\t");
        s.append("name: ").append(name);
        if (description != null){
            s.append(",\n\tdescription: ").append(description);
        }
        if (physicalElements != null){
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
            if(physicalElements.isEmpty()) {return false;}

            Optional<ArrayList<UUID>> actualPhysicalElements = EscapeGame.getEscapeGame().getAllPhysicalElementsUUID();
            if (actualPhysicalElements.isEmpty()){ return false; }
            else {
                for (var element : physicalElements){
                    if (element == null) return false;
                    if (!actualPhysicalElements.get().contains(element)) return false;
                }
            }
        }
        if (name == null || name.isBlank() || name.isEmpty()) return false;
        return true;
    }
}
