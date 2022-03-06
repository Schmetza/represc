package com.example.represc.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.SVGPath;
import javafx.scene.transform.Scale;

public class GameMap extends StackPane {

    // 1 unit = 1 cm (arbitrary);
    private static final Integer mapSize = 100000;

    // Layout variables
    private Group overlay = new Group();
    private Group map = new Group();
    private Group content = new Group();
    private Pane grid = new Pane();

    // Scaling variables
    final double scalingStrength = 1.05;
    private Scale mapScale = new Scale();
    private static final double scaleMinimum = 0.05 ;
    private static final double scaleMaximum = 10 ;

    // Panning variables
    private static final class DragContext {
        public double mouseStartX;
        public double mouseStartY;
        public double initialTranslateX;
        public double initialTranslateY;
    }
    final DragContext dragContext = new DragContext();

    // Events flags variables
    boolean panningMode = false;
    boolean drawingMode = false;

    // Line Drawing variables
    private SVGPath path;
    private Polyline drawing;



    public GameMap() {
        //getStyleClass().add();
        setFocusTraversable(false);

        // Sets up the children of all the elements of GameMap
        this.getChildren().addAll(map);
        map.getChildren().addAll(grid, content);
        this.getChildren().add(overlay);

        map.resize(mapSize, mapSize);
        grid.resize(mapSize, mapSize);
        content.resize(mapSize, mapSize);
        // The comment down bellow can be used to see "grid" colored if needed while debugging.
        // grid.setStyle("-fx-background-color: blue");

        // Initialize scaling of the map
        mapScale.setPivotX(0);
        mapScale.setPivotY(0);
        map.getTransforms().addAll(mapScale);
        mapScale.setX(2);
        mapScale.setY(2);

        manageGrid();

        populate();

        // Sets all default Events
        content.setOnMouseEntered(mapNavigationEnter);
        this.setOnMousePressed(mapNavigationPressed);
        this.setOnDragDetected(dragDetected);
        this.setOnMouseDragged(mapNavigationDrag);
        this.setOnMouseReleased(mapNavigationRelease);

        this.setOnScroll(zoom);
    }

    //Zoom Events
    /**
     * @overview EventHandler in charge of the zoom on the map.
     * If the user tires to go over the max zoom or under the minimum zoom, sets it to the corresponding extrema.
     */
    EventHandler<ScrollEvent> zoom = new EventHandler<ScrollEvent>() {
        @Override
        public void handle(ScrollEvent scrollEvent) {
            if (scrollEvent.getDeltaY() == 0) return;
            double scaleMultiplicator;
            if (scrollEvent.getDeltaY() > 0) scaleMultiplicator = scalingStrength;
            else scaleMultiplicator = 1 / scalingStrength;

            double newX = mapScale.getX() * scaleMultiplicator;
            double newY = mapScale.getY() * scaleMultiplicator;
            if (newX > scaleMaximum) newX = scaleMaximum;
            if (newY > scaleMaximum) newY = scaleMaximum;
            if (newX < scaleMinimum) newX = scaleMinimum;
            if (newY < scaleMinimum) newY = scaleMinimum;

            //TODO Change the Pivot Point of the scaling
            mapScale.setX(newX);
            mapScale.setY(newY);
            manageGrid();
        }
    };

    // Panning Events
    /**
     * @effects Sets the cursor to be an open hand when event is handled
     */
    EventHandler<MouseEvent> mapNavigationEnter = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            content.setCursor(Cursor.OPEN_HAND);
        }
    };
    /**
     * @effects When event is handled, stores the current position of the mouse and translations of the map
     * to be ready in case of a panning.
     */
    EventHandler<MouseEvent> mapNavigationPressed = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            dragContext.mouseStartX = mouseEvent.getX();
            dragContext.mouseStartY = mouseEvent.getY();
            dragContext.initialTranslateX =
                    map.getTranslateX();
            dragContext.initialTranslateY =
                    map.getTranslateY();

            //drawFree = true;
        }
    };
    /**
     * @effects If a drag is detected on the map, sets panningMode to true and starts a full drag. Sets the cursor to
     * closed hand
     */
    EventHandler<MouseEvent> dragDetected = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            panningMode = true;
            //drawFree = false;
            map.setCursor(Cursor.CLOSED_HAND);
            startFullDrag();
        }
    };
    EventHandler<MouseEvent> mapNavigationDrag = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {

                double newTranslateX = dragContext.initialTranslateX + mouseEvent.getX() - dragContext.mouseStartX;
                double newTranslateY = dragContext.initialTranslateY + mouseEvent.getY() - dragContext.mouseStartY;

                // Makes sure the map can't get out of the view
                if (newTranslateX >= 0) {
                    newTranslateX = 0;
                }
                if (newTranslateX <= ((-1 * mapSize)* mapScale.getX())) {
                    newTranslateX = ((-1 *mapSize)* mapScale.getX());
                    newTranslateX = newTranslateX + (widthProperty().get()* mapScale.getX());
                }
                if (newTranslateY >= 0) {
                    newTranslateY = 0;
                }
                if (newTranslateY <= ((-1 * mapSize)* mapScale.getY())) {
                    newTranslateY = ((-1 *mapSize)* mapScale.getY());
                    newTranslateY = newTranslateY + (heightProperty().get()* mapScale.getY());
                }

                map.setTranslateX(newTranslateX);
                map.setTranslateY(newTranslateY);

                manageGrid();
            }
        }
    };
    EventHandler<MouseEvent> mapNavigationRelease = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getButton() == MouseButton.PRIMARY){
                if (panningMode) {
                    panningMode = false;
                    mouseEvent.consume();
                    if (drawingMode){
                        map.setCursor(Cursor.CROSSHAIR);
                    } else {map.setCursor(Cursor.OPEN_HAND);}
                }

            }
        }
    };


    /**
     * @effects redraws the grid after computing with cell unit it should use and which stroke size.
     */
    public void manageGrid(){

        int gridUnit;
        double strokeWidth;

        grid.getChildren().clear();

        int scaleRounded = Math.toIntExact(Math.round(mapScale.getX()));
        //TODO find mathematical function
        switch(scaleRounded){
            case 0:
                gridUnit = 1000;
                strokeWidth = 2;
                break ;
            case 1:
                gridUnit = 100;
                strokeWidth = 1;
                break ;
            case 2:
                gridUnit = 100;
                strokeWidth = 0.7;
                break ;
            case 3:
                gridUnit = 100;
                strokeWidth = 0.5;
                break ;
            case 4:
                gridUnit = 100;
                strokeWidth = 0.2;
                break ;
            default:
                gridUnit = 10;
                strokeWidth = 0.1;
                break ;
        }
        if (mapScale.getX()<0.5){strokeWidth = 5;}
        if (mapScale.getX()<0.1){strokeWidth = 10; gridUnit= 5000;}

        // Finds the x and y where the first lines of the grid should appear
        int xRelativeToParent = (int) ((map.getTranslateX() * -1) / mapScale.getX());
        int verticalLine = ((xRelativeToParent / gridUnit) * gridUnit) + gridUnit;
        int yRelativeToParent = (int) ((map.getTranslateY() * -1) / mapScale.getY());
        int horizontalLine = ((yRelativeToParent / gridUnit) * gridUnit) + gridUnit;

        //TODO Compute number of lines
        for (int i = 0; i <=30; i++){
            Line vLine = new Line();
            Line hLine = new Line();
            vLine.setStrokeWidth(strokeWidth); ;
            hLine.setStrokeWidth(strokeWidth);
            grid.getChildren().addAll(vLine, hLine);

            vLine.setStartX(verticalLine);
            vLine.setEndX(verticalLine);
            vLine.setStartY(0);
            vLine.setEndY(mapSize);

            hLine.setStartY(horizontalLine);
            hLine.setEndY(horizontalLine);
            hLine.setStartX(0);
            hLine.setEndX(mapSize);

            verticalLine += gridUnit;
            horizontalLine += gridUnit;
        }
    }
    public void populate() {


        return;
    }


    public void drawByLine() {
        if (drawingMode){return;}

        map.setCursor(Cursor.CROSSHAIR);
        path = new SVGPath();
        drawing = new Polyline();

        map.getChildren().add(drawing);
        map.addEventFilter(MouseEvent.MOUSE_RELEASED, drawClick);
        drawingMode = true;


        Button confirm = new Button("Confirmer");
        Button cancel = new Button("Annuler");

        overlay.getChildren().add(new VBox(confirm, cancel));
    }

    public void drawRoom(){
        //set context
        drawByLine();
    }






    /**
     * @overview Event Handlers used to navigate inside the Escape Room visual representation on the main view.
     */



    EventHandler<ActionEvent> stopDrawing = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            drawingMode = false;
        }
    };












    EventHandler<MouseEvent> drawClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {

            if (!panningMode && drawingMode){
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    drawing.getPoints().addAll(mouseEvent.getX(), mouseEvent.getY());
                }

                if (mouseEvent.getButton() == MouseButton.SECONDARY){
                    int size = drawing.getPoints().size();
                    drawing.getPoints().remove(drawing.getPoints().size()-1);
                    drawing.getPoints().remove(drawing.getPoints().size()-1);
                }
            }
        }
    };

    private boolean doesCrossWall(double x, double y){
        return false;
    }
}