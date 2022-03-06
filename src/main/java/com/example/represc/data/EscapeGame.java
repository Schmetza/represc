package com.example.represc.data;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class EscapeGame {
    private static EscapeGame instance = null;

    private static String name;
    private static String description; //facultative
    private static String difficulty; //facultative
    private static Integer playersMinimum; //facultative
    private static Integer playersMaximum; //facultative
    private static Duration estimatedDuration; //facultative

    private static HashMap<UUID, PhysicalElement> physicalElements; //facultative
    private static HashMap<UUID, Event> events; //facultative
    private static HashMap<UUID, Hint> hints; //facultative
    private static HashMap<UUID, Problem> problems; //facultative
    private static HashMap<UUID, Countdown> countdowns; //facultative


    static public EscapeGame getEscapeGame(){
        if (instance==null) {instance = new EscapeGame();}
        return instance;
    }

    /**
     * @precondition {@code name != null && !name.isEmpty() && !name.isBlank()}
     * @postcondition initialises {@code this} with the given {@code name}value.;
     * */
    private EscapeGame(){
    }


    /**
     * @return Returns the name of the EscapeGame as a string.
     * **/
    public String getName() {
        return name;
    }
    /**
     * @precondition {@code name != null && !name.isEmpty() && !name.isBlank()}}
     * @postcondition Sets the name of the EscapeGame with the String {@code name}.
     **/
    public void setName(String name) {
        assert name != null : "trying to set a State name with a null String name";
        assert !name.isEmpty() : "trying to set a State name with an empty String name";
        assert !name.isBlank() : "trying to set a State name with a blank String name";

        this.name = name;
    }


    /**
     * @return Returns the description of the EscapeGame as an Optional, containing a String, or being empty if
     * {@code description} is null.
     */
    public Optional<String> getDescription(){
        if (description != null) return Optional.of(description);
        return Optional.empty();
    }
    /**
     * @precondition {@code description != null}
     * @postcondition Sets the description of the EscapeGame to be the String {@code description}.
     */
    public void setDescription(String description) {
        assert description != null : "setDescription: Trying to set the description of an EscapeGame, " +
                "but leaving description parameter null.";

        this.description = description;
    }
    /**
     * @effects Sets the description of the EscapeGame to be {@code null}.
     * **/
    public void deleteDescription(){
        description = null;
    }


    /**
     * @return Returns the difficulty of the EscapeGame as an Optional, containing a String, or being empty if
     * {@code description} is null.
     */
    public Optional<String> getDifficulty(){
        if (difficulty != null) return Optional.of(difficulty);
        return Optional.empty();
    }
    /**
     * @precondition {@code difficulty != null}
     * @postcondition Sets the difficulty of the EscapeGame to be the String {@code description}.
     */
    public void setDifficulty(String difficulty) {
        assert difficulty != null : "setDifficulty: Trying to set the difficulty of an EscapeGame, " +
                "but leaving description parameter null.";

        EscapeGame.difficulty = difficulty;
    }
    /**
     * @effects Sets the description of the EscapeGame to be {@code null}.
     * **/
    public void deleteDifficulty(){
        difficulty = null;
    }

    /**
     * @return Returns the minimal number of players of the EscapeGame as an Optional, containing an Integer,
     * or being empty if {@code playersMinimum} is null.
     */
    public Optional<Integer> getPlayersMinimum(){
        if (playersMinimum == null) return Optional.empty();
        return Optional.of(playersMinimum);
    }
    /**
     * @precondition {@code (playersMaximum == null || newMinimum <= playersMaximum) && newMinimum >= 0}
     * @postcondition Sets the minimal number of players of the EscapeGame to be {@code newMinimum}.
     */
    public void setPlayersMinimum(int newMinimum){
        assert newMinimum >= 0 : "setPlayersMinimum: Trying to set a minimum of Player as a negative integer";
        assert playersMaximum == null || newMinimum <= playersMaximum : "setPlayersMinimum: Trying to set a minimum of Player bigger than the current maximum.";
        playersMinimum = newMinimum;
    }
    /**
     * @effects Sets {@code this.playersMinimum} to be null.
     */
    public void deletePlayersMinimum(){
        playersMinimum = null;
    }

    /**
     * @return Returns the maximal number of players of the EscapeGame as an Optional, containing an Integer,
     * or being empty if {@code playersMaximum} is null.
     */
    public Optional<Integer> getPlayersMaximum(){
        if (playersMaximum == null) return Optional.empty();
        return Optional.of(playersMaximum);
    }
    /**
     * @precondition {@code (if (playersMinimum !=null) newMaximum >= playersMinimum) && newMaximum >= 0}
     * @postcondition Sets the maximal number of players of the EscapeGame to be {@code newMaximum}.
     */
    public void setPlayersMaximum(int newMaximum){
        assert newMaximum >= 0 : "setPlayersMaximum: Trying to set a maximum of Players as a negative integer";
        assert playersMinimum == null || newMaximum >= playersMinimum : "setPlayersMaximum: Trying to set a maximum of " +
                "Players smaller than the current minimum.";
        playersMaximum = newMaximum;
    }
    /**
     * @effects Sets {@code this.playersMaximum} to be null.
     */
    public void deletePlayersMaximum(){
        playersMaximum = null;
    }

    /**
     * @precondition {@code minimum >= 0 && maximum >= 0 && minimum <= maximum}
     * @postcondition Sets {@code playersMinimum} to be {@code minimum} and {@code playersMaximum} to be {@code maximum}.
     */
    public void setPlayersRange(int minimum, int maximum){
        assert minimum >= 0 : "setPlayersRange: Trying to set the minimum number of players of an EscapeGame with a" +
                "negative value.";
        assert maximum >= 0 : "setPlayersRange: Trying to set the maximum number of players of an EscapeGame with a" +
                "negative value.";
        assert minimum <= maximum : "setPlayersRange: Trying to set the players maximum and minimum of an EscapeGame " +
                "with minimum > maximum";

        playersMaximum = maximum;
        playersMinimum = minimum;
    }


    /**
     * @return Returns the estimated duration of the EscapeGame as an Optional, containing a Duration or being empty if
     * {@code estimatedDuration} is null.
     */
    public Optional<Duration> getEstimatedDuration(){
        if (estimatedDuration == null) return Optional.empty();
        return Optional.of(estimatedDuration);
    }
    /**
     * @precondition {@code duration != null && !duration.isNegative()}
     */
    public void setEstimatedDuration(Duration duration){
        assert duration != null : "setEstimatedDuration: Trying to set the estimated duration of the EscapeGame " +
                "as a null value.";
        assert !duration.isNegative() : "setEstimationDuration: Trying to set the estimated duration of the Escape Game as" +
                "a negative duration";

        estimatedDuration = duration;
    }
    /**
     * @effects Sets {@code this.estimatedDuration} to be null.
     */
    public void deleteEstimatedDuration(){
        estimatedDuration = null;
    }


    /**
     * @precondition {@code toAdd != null}.
     * @postcondition initialises physicalElements to an empty Arraylist if it is null, then
     * adds {@code toAdd} to {@code physicalElements}.
     * @return Returns the UUId which is now the key of {@code toAdd} in {@code physicalElements}.
     */
    public UUID addPhysicalElement(PhysicalElement toAdd){
        assert toAdd != null : "addPhysicalElement: Trying to add a Physical Element which is null to the EscapeGame.";

        UUID id = UUID.randomUUID();

        if (physicalElements == null) physicalElements = new HashMap<>();
        physicalElements.put(id, toAdd);
        return id;
    }
    /**
     * @precondtion {@code physicalElements != null && toDelete != null && physicalElements.containsKey(toDelete)}.
     * @postcondition Deletes the Physical Element, which has the id toDelete from the Escape Game.
     */
    public void deletePhysicalElement(UUID toDelete){
        assert toDelete != null : "deletePhysicalElement: the UUID of the Physical Element to delete cannot be null.";
        assert physicalElements != null :"deletePhysicalElement: Trying to delete a Physical Element of the Escape Game" +
                "but physicalElements is null.";
        assert physicalElements.containsKey(toDelete) : "deletePhysicalElement: The UUID toDelete is not the key of any " +
                "one of the Physical Element of the Escape Game.";

        physicalElements.remove(toDelete);
    }
    /**
     * @precondition {@code key != null && physicalElements != null && physicalElements.containsKey(key)}
     * @return Returns the Physical Element of the EscapeGame, which is linked to the UUID {@code key} in physicalElements.
     */
    public PhysicalElement getPhysicalElement(UUID key){
        assert key != null : "getPhysicalElement: Trying to retrieve a Physical Element of the Escape Game with a UUID " +
                "which is null";
        assert physicalElements != null : "getPhysicalElement: Trying to retrieve a Physical Element of the Escape Game" +
                "but physicalElements is null.";
        assert physicalElements.containsKey(key) : "getPhysicalElement: Trying to retrieve a Physical Element of the Escape " +
                "Game with a UUID which is not present in physicalElements";

        return physicalElements.get(key);
    }
    /**
     * @return Returns an empty Optional if the Escape Room has no Physical Element. Returns an Optional containing an ArrayList
     * of the UUIDs of all its Physical elements otherwise.
     */
    public Optional<ArrayList<UUID>> getAllPhysicalElementsUUID(){
        if (physicalElements == null) return Optional.empty();

        ArrayList<UUID> list = new ArrayList<>();
        for (var element : physicalElements.entrySet()){
            list.add(element.getKey());
        }
        return Optional.of(list);
    }
    /**
     * @return Returns an empty Optional if the Escape Room has no Physical Element. Returns an Optional containing an ArrayList
     * of all its Physical Elements otherwise.
     */
    public Optional<ArrayList<PhysicalElement>> getAllPhysicalElements(){
        if (physicalElements == null) return Optional.empty();

        ArrayList<PhysicalElement> list = new ArrayList<>();
        for (var element : physicalElements.entrySet()){
            list.add(element.getValue());
        }
        return Optional.of(list);
    }
    /**
     * @postcondition {@code key != null}.
     * @return Returns a boolean, being true if the UUID key is used as the key of a Physical Element of the Escape Game
     * and false otherwise.
     */
    public boolean containsPhysicalElement(UUID key){
        assert key != null : "containsPhysicalElement: Trying to kow if a null UUID is a Physical Element of the Escape Game.";
        if (physicalElements == null) return false;
        return physicalElements.containsKey(key);
    }
}