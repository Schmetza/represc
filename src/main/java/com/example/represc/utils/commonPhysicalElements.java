package com.example.represc.utils;

import java.util.ArrayList;
import java.util.HashMap;

public class commonPhysicalElements {

    public enum ElementType {
        ROOM,
        DOOR,
        KEY,
        LOCK,

        PLAYER, STAFF,
        CUSTOM

    }

    private static commonPhysicalElements instance = null;
    private static HashMap<ElementType, commonObject> List = new HashMap<>();

    public class commonObject {
        private String windowTitle = null;
        private ArrayList<objectState> objectStates = null;

        private commonObject(String windowTitle){
            this.windowTitle = windowTitle;
            this.objectStates = new ArrayList<objectState>();
        }

        private void addState(objectState state){
            this.objectStates.add(state);
        }

        public ArrayList<objectState> getStates(){
            return this.objectStates;
        }

        public String getWindowTitle() {
            return this.windowTitle;
        }
    }
    public class objectState{
        private String stateName;
        private String stateDescription;

        private objectState(String x, String y){
            this.stateName = x;
            this.stateDescription = y;
        }

        public String getStateName(){
            return this.stateName;
        }

        public String getStateDescription(){
            return this.stateDescription;
        }
    }

    static public commonPhysicalElements getCommonObjectsList(){
        if (instance==null) instance = new commonPhysicalElements();
        return instance;
    }

    private commonPhysicalElements(){
        commonObject room = new commonObject("newObject.room.title");
        List.put(ElementType.ROOM, room);

        commonObject door = new commonObject("newObject.door.title");
        door.addState(new objectState("newObject.door.locked.label", "newObject.door.locked.description"));
        door.addState(new objectState("newObject.door.unlocked.label", "newObject.door.unlocked.description"));
        List.put(ElementType.DOOR, door);

        commonObject lock = new commonObject("newObject.lock.title");
        lock.addState(new objectState("newObject.lock.locked.label", "newObject.lock.locked.description"));
        lock.addState(new objectState("newObject.lock.unlocked.label", "newObject.lock.unlocked.description"));
        List.put(ElementType.LOCK, lock);

        commonObject key = new commonObject("newObject.key.title");
        List.put(ElementType.KEY, key);

        commonObject object = new commonObject("newObject.object.title");
        List.put(ElementType.CUSTOM, object);

        commonObject player = new commonObject("newObject.player.title");
        List.put(ElementType.PLAYER, player);

        commonObject staff = new commonObject("newObject.staff.title");
        List.put(ElementType.STAFF, staff);
    }

    public commonObject getCommonObject(String toGet){
        assert List.get(toGet) != null : "Common object not in list of common objects";
        return List.get(toGet);
    }
}