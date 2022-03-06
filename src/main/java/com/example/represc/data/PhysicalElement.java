package com.example.represc.data;

import com.example.represc.utils.commonPhysicalElements.ElementType;
import javafx.scene.shape.SVGPath;

import java.util.*;


/**
 * @author Schmetz Arnaud
 * @version 1.0
 *
 * @overview Is defined as a Physical Element any person or physical object in the escape game.
 * It may be an element whose type is common, like the {@code DOOR}, {@code ROOM}, {@code PLAYER},... types
 * or any other physical object, which would then have the {@code CUSTOM} type {@link ElementType}.
 *
 * @specfield name: String // The name of the Physical Element. Must be neither, null, empty, nor blank.
 * @specfield shape: SVGPath // The shape of the Physical Element, its origin point is the origin of the map,
 * // thus meaning no position attribute is required to describe a Physical Element. Must be not null.
 * @specfield accessible: boolean // Expresses if the Physical Element is initially accessible to the player(s),
 * // at the beginning of the game. A Physical Element is accessible if one or multiple player(s) can interact with it.
 * @specfield type: ElementType // The type of the physical element. Must be not null. {@link ElementType}
 *
 * // facultative
 * @specfield description: String // The description of the Physical Element.
 * @specfield initialState: UUID // The initial State of the Physical Element.
 * @specfield states: HashMap: UUID to State // The list of states of the Physical Element.
 *
 * // All the followings must be true
 * @invariant {@code name != null && !name.isEmpty() && !name.isBlank()}
 * @invariant {@code shape != null}
 * @invariant {@code type != null}
 * @invariant {@code initialState == null} <=> {@code states == null}.
 * @invariant {@code initialState} must be the UUID of a State contained in {@code states}.
 * @invariant any entry of {@code states} must have a non-null UUID as a key and a valid State as a value.
 * @invariant If states isn't null, it is not empty.
 */
public class PhysicalElement {

    private String name;
    private String description; //facultative
    private SVGPath shape;
    private boolean accessible;
    private ElementType type;

    private UUID initialState; //facultative
    private HashMap<UUID,State> states ; //facultative


    /**
     * @overview A State is the state a Physical Element can have. States are primarily a tool used by Events {@link Event},
     * may it be as part of Triggers or Actions of those Events.
     * (For example, a door can have the states "locked" and "unlocked". If a locked door becomes unlocked, it may lead to the
     * next room going from not accessible, to accessible, allowing the players to go forward.)
     *
     * @specfield name: String // The name of the State. Must be neither, null, empty, nor blank.
     * @specfield accessible: boolean // Expresses whether the actual State of the Physical Element, can be switched to
     * this State.
     *
     * //facultative
     * @specfield description: String // The description of the State.
     *
     * @invariant {@code name != null && !name.isEmpty() && !name.isBlank()}
     * */
    public static class State{
        private String name;
        private String description; //facultative
        private boolean accessible;

        /**
         * @precondition {@code name != null && !name.isEmpty() && !name.isBlank()}
         * @postcondition initialises {@code this} with the given {@code name} and {@code accessible} values;
         * **/
        public State(String name, boolean accessible){
            assert name != null : "State: trying to create a State with a null String name";
            assert !name.isEmpty() : "State: trying to create a State with an empty String name";
            assert !name.isBlank() : "State: trying to create a State with a blank String name";

            this.name = name;
            this.accessible = accessible;
        }
        /**
         * @precondition {@code name != null && !name.isEmpty() && !name.isBlank() && description != null}
         * @postcondition initialises {@code this} with the given {@code name}, {@code accessible} and {@code description} values;
         * **/
        public State(String name, boolean accessible, String description){
            this(name, accessible);

            assert description != null : "State: Trying to create a State with a description, but leaving the description" +
                    "parameter null.";
            this.description = description;
        }

        /**
         * @return Returns the name of the State as a string.
         * **/
        public String getName() {
            return name;
        }
        /**
         * @precondition {@code name != null && !name.isEmpty() && !name.isBlank()}}
         * @postcondition Sets the name of the State with the String {@code name}.
         */
        public void setName(String name) {
            assert name != null : "trying to set a State name with a null String name";
            assert !name.isEmpty() : "trying to set a State name with an empty String name";
            assert !name.isBlank() : "trying to set a State name with a blank String name";

            this.name = name;
        }

        /**
         * @return Returns an Optional Object containing :
         * the description of the State as a String if {@code description} is not null
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
         * @postcondition Sets the description of the State to be the String {@code description}.
         */
        public void setDescription(String description) {
            assert description != null : "setDescription: Trying to set a description, but leaving description parameter null.";

            this.description = description;
        }
        /**
         * @effects Sets the description of the State to {@code null}.
         * **/
        public void deleteDescription(){
            description = null;
        }

        /**
         * @return Tells if the State is accessible, returns a boolean value.
         * **/
        public boolean isAccessible() {
            return accessible;
        }
        /**
         * @effects Sets the accessible value of the State to be the boolean {@code accessible}.
         * **/
        public void setAccessible(boolean accessible) {
            this.accessible = accessible;
        }

        public String toString(){
            StringBuilder s = new StringBuilder("State : {\n\t");
            s.append("name: ").append(name);
            if (description != null){
                s.append(",\n\tdescription: ").append(description);
            }
            s.append(",\n\taccessible: ").append(accessible);
            s.append(" }");
            return s.toString();
        }
        public boolean repOK(){
            if(name == null || name.isEmpty() || name.isBlank()) return false;

            return true;
        }
    }



    /**
     * @precondition {@code name != null && !name.isEmpty() && !name.isBlank() && shape != null && type != null}
     * @postcondition initialise {@code this} with the given name, shape, accessible, type and {@code states} with
     * an empty Hashmap.
     * **/
    public PhysicalElement(String name, SVGPath shape, boolean accessible, ElementType type){
        assert name != null : "PhysicalElement: trying to create a Physical Element with a null String name";
        assert !name.isEmpty() : "PhysicalElement: trying to create a Physical Element with an empty String name";
        assert !name.isBlank() : "PhysicalElement: trying to create a Physical Element with a blank String name";
        assert shape != null : "PhysicalElement: trying to create a physical Element with a null shape";
        assert type != null : "PhysicalElement: trying to create a Physical Element with a null type";

        this.name = name;
        this.shape = shape;
        this.accessible = accessible;
        this.type = type;
    }

    /**
     * @precondition {@code name != null && !name.isEmpty() && !name.isBlank() && shape != null && type != null &&
     * description != null}
     * @postcondition initialise this with the given name, shape, accessible, type and description and
     * {@code states} with an empty Hashmap.
     * **/
    public PhysicalElement(String name, SVGPath shape, boolean accessible, ElementType type, String description){
        this(name, shape, accessible, type);
        assert description != null : "PhysicalElement: Trying to create a Physical ELement with a description, but " +
                "description parameter is null.";
        this.description = description;
    }


    /**
     * @return Returns the name of the Physical Element as a String.
     * **/
    public String getName() {
        return name;
    }
    /**
     * @precondition {@code name != null && !name.isEmpty() && !name.isBlank()}
     * @postcondition Sets the name of the Physical Element with the String {@code name}.
     * **/
    public void setName(String name) {
        assert name != null : "trying to set a Physical Element name with a null String name";
        assert !name.isEmpty() : "trying to set a Physical Element name with an empty String name";
        assert !name.isBlank() : "trying to set a Physical Element name with a blank String name";

        this.name = name;
    }
    /**
     * @precondition {@code description != null}
     * @postcondition Sets the description of the Physical Element to be the String {@code description}.
     * **/
    public void setDescription(String description) {
        assert description != null : "setDescription: trying to set the description of a Physical Element, but parameter " +
                "description is null.";
        this.description = description;
    }
    /**
     * @return Returns the description of the Physical Element as an Optional, containing a String, or being empty if
     * {@code description} is null.
     * **/
    public Optional<String> getDescription() {
        if (this.description!=null){
            return Optional.of(this.description);
        }
        return Optional.empty();
    }
    /**
     * @effects Changes the description of the physical element to {@code null}.
     * **/
    public void deleteDescription(){
        description = null;
    }

    /**
     * @return Returns a copy of the shape of the Physical Element as a SVGPath.
     * **/
    public SVGPath getShape() {
        SVGPath toReturn  = new SVGPath();
        toReturn.setContent(shape.getContent());
        return toReturn;

    }
    /**
     * @precondition {@code shape != null}
     * @postcondition Sets the shape of the Physical Element with the SVGPath {@code shape}.
     * **/
    public void setShape(SVGPath shape) {
        assert shape!= null : "setShape: Trying to set the shape of a Physical Element with a null value";

        this.shape = shape;
    }

    /**
     * @return Tells if the Physical Element is accessible as a boolean value.
     * **/
    public boolean isAccessible() {
        return accessible;
    }
    /**
     * @effects Changes the accessible value of the Physical Element with the boolean {@code accessible}.
     * **/
    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
    }

    /**
     * @return Returns the type of the Physical Element as a String.
     * **/
    public ElementType getType() {
        return type;
    }
    /**
     * @precondition {@code type != null}
     * @postcondition Sets the type of the Physical Element with the Type {@code type}.
     * **/
    public void setType(ElementType type) {
        assert type != null : "Trying to set the type of a Physical Element with a null value.";
        this.type = type;
    }

    /**
     * @precondition {@code id != null}
     * @return Returns a boolean indicating if {@code this} contains a State having {@code id} as a key.
     */
    public boolean doesContainState(UUID id){
        assert id != null : "doesContainState: Trying to know if a Physical Element has a State, based on a null UUID";
        if(states == null){
            return false;
        }
        return states.containsKey(id);
    }
    /**
     * @precondition {@code id != null && states != null && states.get(id)}
     * @return Returns the State of the Physical Element having the UUID {@code key}.
     * **/
    public State getState(UUID id) {
        assert id != null : "getState: Trying to get one State of a physical Element, using a null UUID.";
        assert states != null : "getState: Trying to get one State of a physical Element, which has no State.";
        assert states.containsKey(id) : "getState: Trying to get one State of a Physical Element, which doesn't have a" +
                "State with the given UUID id.";

        return states.get(id);
    }
    /**
     * @return Returns an empty Optional if the Physical Element has no State. Returns an Optional containing an ArrayList
     * of the UUIDs of all its states otherwise.
     */
    public Optional<ArrayList<UUID>> getAllStatesUUID(){
        if (states == null) return Optional.empty();

        ArrayList<UUID> list = new ArrayList<>();
        for (var state : states.entrySet()){
            list.add(state.getKey());
        }
        return Optional.of(list);
    }
    /**
     * @return Returns an empty Optional if the Physical Element has no State. Returns an Optional containing an ArrayList
     * of all its States otherwise.
     */
    public Optional<ArrayList<State>> gateAllStates(){
        if (states == null) return Optional.empty();

        ArrayList<State> list = new ArrayList<>();
        for (var state : states.entrySet()){
            list.add(state.getValue());
        }
        return Optional.of(list);
    }
    /**
     * @precondition {@toAdd != null}
     * @postcondition Adds the State {@code toAdd} to the list of states of the Physical Element.
     * If that element doesn't have any State, initialize the ArrayList of {@code states} and initializes {@code initialState}
     * to the UUID of {@code toAdd}.
     * @return Returns the UUID of the added State {@code toAdd}.
     * **/
    public UUID addState(State toAdd) {
        assert toAdd != null : "addState: Trying to add a State which is null.";
        UUID id = UUID.randomUUID();

        if (states == null){
            states = new HashMap<>();
            states.put(id, toAdd);
            initialState = id;
        } else {
            states.put(id, toAdd);}
        return id;
    }
    /**
     * @return Returns an empty Optional if the Physical Element has no initial State (and therefore no State). Returns
     * an Optional containing the UUId of the initial State otherwise.
     */
    public Optional<UUID> getInitialStateUUID(){
        if (initialState == null) {return Optional.empty();}
        return Optional.of(initialState);
    }
    /**
     * @precondition {@code states.containsKey(key) && states != null}
     * @postcondition Sets the initial State of the State to be the one given by the UUId key.
     * */
    public void setInitialState(UUID key) {
        assert states != null: "setInitialState: This physical Element has no State";
        assert states.containsKey(key): "setInitialState: This UUID is not the UUID of any of the Physical " +
                "Element's states.";
        this.initialState = key;
    }

    /**
     * @precondition {@code toDelete != null && states.containsKey(toDelete) && initialState != toDelete}
     * @postcondition removes the State {@code toDelete} from the list of states of the element.
     * */
    public void deleteState(UUID toDelete){
        assert toDelete != null : "deleteState: the UUID of the State to delete cannot be null.";
        assert states.containsKey(toDelete) : "deleteState: The State to delete is not one of the states of this object.";
        assert initialState != toDelete : "deleteState: The State to delete cannot be the initialState.";
        this.states.remove(toDelete);
    }
    /**
     * @precondition {@code newInitial != null && newInitial != initialState && states.containsKey(newInitial) &&
     * states.size() > 1}
     * @effects removes {@code initialState} from the list of states of the element and sets {@code initialState} to
     * point to {@code newInitial}.
     * */
    public void deleteInitialState(UUID newInitial){
        assert newInitial != null : "deleteInitialState: The newInitial UUID is null.";
        assert newInitial != initialState : "deleteInitialState: newInitial is equal to initialState.";
        assert states.containsKey(newInitial) : "deleteInitialState: The new initial State is not one of the states of this" +
                "Physical Element.";
        assert states.size() > 1 : "deleteInitialState: The state is the last one possessed by the Physical Element " +
                "( use deleteOnlyState() ).";

        states.remove(initialState);
        initialState = newInitial;
    }
    /**
     * @precondition {@code states.size() == 1}.
     * @postcondition Deletes the only state of the Physical Element{@code states == null && initialState == null}.
     */
    public void deleteOnlyState(){
        assert states.size() == 1 : "deleteOnlyState(): trying to delete a State, which is not the last one, from a " +
                "Physical Element.";
        states = null;
        initialState = null;
    }

    public String toString(){
        StringBuilder s = new StringBuilder("PhysicalElement : {\n\t");
        s.append("name: ").append(name);
        if (description != null){
            s.append(",\n\tdescription: ").append(description);
        }
        s.append(",\n\tshape: ").append(shape.getContent());
        s.append(",\n\taccessible: ").append(accessible);
        s.append(",\n\ttype: ").append(type);

        if (initialState != null) {
            s.append(",\n\tinitialState: ").append(initialState);
        }

        if (states != null){
            s.append(",\n\nstates : {");
            for (var state : states.entrySet()){
                s.append("\n[");
                s.append(state.getKey().toString()).append(": ");
                s.append(state.getValue().toString());
                s.append("],");
            }
            s.setLength(s.length() - 1);
            s.append("}");
        }
        s.append(" }");
        return s.toString();
    }
    public boolean repOK(){
        if(name == null || name.isEmpty() || name.isBlank()) return false;
        if(shape == null) return false;
        if(type == null) return false;

        if( (!(initialState==null) && states == null) || (initialState==null && states != null )) return false;
        if(states != null){
            if (states.isEmpty()) {return false;}
            for (var state : states.entrySet()){
                if (state.getKey() == null){ return false ; }
                if (!state.getValue().repOK()){ return false; }
            }
        }

        return true;
    }
}