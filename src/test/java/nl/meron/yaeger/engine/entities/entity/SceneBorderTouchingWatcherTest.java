package nl.meron.yaeger.engine.entities.entity;

import javafx.geometry.BoundingBox;
import javafx.scene.Node;
import javafx.scene.Scene;
import nl.meron.yaeger.engine.Updatable;
import nl.meron.yaeger.engine.scenes.SceneBorder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SceneBorderTouchingWatcherTest {

    private final static double SCENE_HEIGHT = 100;
    private final static double SCENE_WIDTH = 100;
    private final static BoundingBox BOUNDS_IN_SCENE = new BoundingBox(10, 10, 10, 10);
    private final static BoundingBox BOUNDS_CROSSED_LEFT = new BoundingBox(-20, 10, 10, 10);
    private final static BoundingBox BOUNDS_CROSSED_RIGHT = new BoundingBox(110, 10, 10, 10);
    private final static BoundingBox BOUNDS_CROSSED_BOTTOM = new BoundingBox(10, 100, 10, 10);
    private final static BoundingBox BOUNDS_CROSSED_TOP = new BoundingBox(10, -20, 10, 10);
    private TestWatcher watcher;
    private Node node;
    private Scene scene;

    @BeforeEach
    void setup() {
        watcher = new TestWatcher();
        node = mock(Node.class, withSettings().withoutAnnotations());
        scene = mock(Scene.class);
        watcher.setGameNode(node);
    }

    @Test
    void testWatchForBoundaryTouchingReturnsAnUpdatable() {
        // Setup

        // Test
        var updatable = watcher.watchForBoundaryTouching();

        // Verify
        assertTrue(updatable instanceof Updatable);
    }

    @Test
    void testBoundaryNotTouched() {
        // Setup
        when(node.getBoundsInLocal()).thenReturn(BOUNDS_IN_SCENE);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        var updatable = watcher.watchForBoundaryTouching();

        // Test
        updatable.update(0);

        // Verify
        assertNull(watcher.borderTouched);
    }

    @Test
    void testBoundaryLeftCrossed() {
        // Setup
        when(node.getBoundsInLocal()).thenReturn(BOUNDS_CROSSED_LEFT);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        var updatable = watcher.watchForBoundaryTouching();

        // Test
        updatable.update(0);

        // Verify
        assertEquals(SceneBorder.LEFT, watcher.borderTouched);
    }

    @Test
    void testBoundaryRightCrossed() {
        // Setup
        when(node.getBoundsInLocal()).thenReturn(BOUNDS_CROSSED_RIGHT);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        var updatable = watcher.watchForBoundaryTouching();

        // Test
        updatable.update(0);

        // Verify
        assertEquals(SceneBorder.RIGHT, watcher.borderTouched);
    }

    @Test
    void testBoundaryBottomTouched() {
        // Setup
        when(node.getBoundsInLocal()).thenReturn(BOUNDS_CROSSED_BOTTOM);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        var updatable = watcher.watchForBoundaryTouching();

        // Test
        updatable.update(0);

        // Verify
        assertEquals(SceneBorder.BOTTOM, watcher.borderTouched);
    }

    @Test
    void testBoundaryTopTouched() {
        // Setup
        when(node.getBoundsInLocal()).thenReturn(BOUNDS_CROSSED_TOP);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        var updatable = watcher.watchForBoundaryTouching();

        // Test
        updatable.update(0);

        // Verify
        assertEquals(SceneBorder.TOP, watcher.borderTouched);
    }

    private class TestWatcher implements SceneBorderTouchingWatcher {

        private Node gameNode;
        SceneBorder borderTouched;

        @Override
        public void notifyBoundaryTouching(SceneBorder border) {
            borderTouched = border;
        }

        @Override
        public Node getGameNode() {
            return gameNode;
        }

        public void setGameNode(Node node) {
            this.gameNode = node;
        }
    }
}
