package com.github.hanyaeger.core.debug;

import com.github.hanyaeger.core.entities.Debugger;
import com.github.hanyaeger.core.repositories.ImageRepository;
import com.github.hanyaeger.core.factories.debug.DebugGridPaneFactory;
import com.github.hanyaeger.core.factories.debug.DebugLabelFactory;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class DebuggerTest {

    private Pane pane;

    private ImageRepository imageRepository;

    private GridPane gridPane;

    private DebugGridPaneFactory debugGridPaneFactory;
    private DebugLabelFactory debugLabelFactory;

    private Debugger sut;

    @BeforeEach
    void setup() {
        pane = mock(Pane.class);
        ObservableList<Node> children = mock(ObservableList.class);
        when(pane.getChildren()).thenReturn(children);

        imageRepository = mock(ImageRepository.class);

        gridPane = mock(GridPane.class);
        debugGridPaneFactory = mock(DebugGridPaneFactory.class);
        when(debugGridPaneFactory.create()).thenReturn(gridPane);

        debugLabelFactory = mock(DebugLabelFactory.class);

        sut = new Debugger();
        sut.setImageRepository(imageRepository);
        sut.setDebugGridPaneFactory(debugGridPaneFactory);
        sut.setDebugLabelFactory(debugLabelFactory);
    }

    @Test
    void setupCreatesAGridPane() {
        // Setup

        // Test
        sut.setup(pane);

        // Verify
        verify(debugGridPaneFactory).create();
        verify(debugLabelFactory, atLeastOnce()).createLabel(anyString());
        verify(debugLabelFactory, atLeastOnce()).createValue(anyString());
    }

    @Test
    void toFrontSetsTheGridPaneToFront() {
        // Setup
        sut.setup(pane);

        // Test
        sut.toFront();

        // Verify
        verify(gridPane).toFront();
    }
}