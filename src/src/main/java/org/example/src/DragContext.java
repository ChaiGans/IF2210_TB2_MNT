package org.example.src;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;

public class DragContext {
    private static final DragContext instance = new DragContext();
    private SimpleObjectProperty<Node> dragSource = new SimpleObjectProperty<>();

    private DragContext() {}

    public static DragContext getInstance() {
        return instance;
    }

    public Node getDragSource() {
        return dragSource.get();
    }

    public void setDragSource(Node source) {
        this.dragSource.set(source);
    }

    public SimpleObjectProperty<Node> dragSourceProperty() {
        return dragSource;
    }
}
