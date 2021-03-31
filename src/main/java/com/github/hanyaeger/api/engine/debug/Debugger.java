package com.github.hanyaeger.api.engine.debug;

import com.google.inject.Inject;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import com.github.hanyaeger.api.engine.media.repositories.AudioRepository;
import com.github.hanyaeger.api.engine.media.repositories.ImageRepository;
import com.github.hanyaeger.api.javafx.debug.DebugLabelFactory;
import com.github.hanyaeger.api.engine.entities.EntityCollectionStatistics;
import com.github.hanyaeger.api.javafx.debug.DebugGridPaneFactory;
import com.github.hanyaeger.api.engine.entities.EntityCollection;
import javafx.scene.layout.Pane;

/**
 * The {@code Debugger} is used to gather and show in game debug information.
 */
public class Debugger implements StatisticsObserver {

    private static final String YAEGER_DEBUGGER_TITLE = "YAEGER DEBUGGER";
    private static final String PROCESSORS_AMOUNT = "Available processors:";
    private static final String MEMORY_ALLOCATED = "Total allocated memory:";
    private static final String MEMORY_USED = "Total used memory:";
    private static final String ENTITIES_DYNAMIC = "Dynamic Entities:";
    private static final String ENTITIES_STATIC = "Static Entities:";
    private static final String SUPPLIERS = "Suppliers:";
    private static final String GARBAGE = "Garbage:";
    private static final String KEY_LISTENERS = "Key listening Entities:";
    private static final String AUDIO_FILES = "Audio files";
    private static final String IMAGE_FILES = "Image files";

    private AudioRepository audioRepository;
    private ImageRepository imageRepository;

    private DebugGridPaneFactory debugGridPaneFactory;
    private DebugLabelFactory debugLabelFactory;

    private GridPane gridpane;
    private Label dynamicEntities;
    private Label staticEntities;
    private Label entitySpawners;
    private Label keyListeningEntities;
    private Label garbageEntities;
    private Label usedMemory;
    private Label allocatedMemory;

    private Label audioFiles;
    private Label imageFiles;

    public void setup(Pane pane) {
        createGridPane(pane);
    }

    /**
     * Ensure that the {@link Debugger} is brought to the top of the view stack.
     */
    public void toFront() {
        gridpane.toFront();
    }

    /**
     * Update the content of the {@link Debugger}.
     *
     * @param entityCollectionStatistics an {@link EntityCollectionStatistics} that contains all
     *                                   statistics related to the current state of the
     *                                   {@link EntityCollection}
     */
    @Override
    public void update(EntityCollectionStatistics entityCollectionStatistics) {
        if (!gridpane.isVisible()) {
            return;
        }

        dynamicEntities.setText(String.valueOf(entityCollectionStatistics.getUpdatables()));
        staticEntities.setText(String.valueOf(entityCollectionStatistics.getStatics()));
        keyListeningEntities.setText(String.valueOf(entityCollectionStatistics.getKeyListeners()));
        entitySpawners.setText(String.valueOf(entityCollectionStatistics.getSuppliers()));
        garbageEntities.setText(String.valueOf(entityCollectionStatistics.getGarbage()));

        allocatedMemory.setText(getTotalMemory());
        usedMemory.setText(getUsedMemory());

        audioFiles.setText(String.valueOf(audioRepository.size()));
        imageFiles.setText(String.valueOf(imageRepository.size()));
    }

    private void createGridPane(Pane pane) {
        gridpane = debugGridPaneFactory.create();

        addHeader();
        addSystemStatistics();
        addEntityStatistics();
        addResourcesStatistics();

        pane.getChildren().add(gridpane);
    }

    private void addHeader() {
        gridpane.add(debugLabelFactory.createLabel(YAEGER_DEBUGGER_TITLE), 0, 0, 2, 1);
    }

    private void addSystemStatistics() {
        addDebugLine(PROCESSORS_AMOUNT, String.valueOf(Runtime.getRuntime().availableProcessors()));

        allocatedMemory = addDebugLine(MEMORY_ALLOCATED, getTotalMemory());
        usedMemory = addDebugLine(MEMORY_USED, getUsedMemory());
    }

    private void addEntityStatistics() {
        dynamicEntities = addDebugLine(ENTITIES_DYNAMIC);
        staticEntities = addDebugLine(ENTITIES_STATIC);
        entitySpawners = addDebugLine(SUPPLIERS);
        garbageEntities = addDebugLine(GARBAGE);
        keyListeningEntities = addDebugLine(KEY_LISTENERS);
    }

    private void addResourcesStatistics() {
        audioFiles = addDebugLine(AUDIO_FILES);
        imageFiles = addDebugLine(IMAGE_FILES);
    }

    private Label addDebugLine(final String label) {
        return addDebugLine(label, "");
    }

    private Label addDebugLine(final String label, String value) {
        Label debugValue = debugLabelFactory.createValue(value);
        int nextRow = gridpane.getRowCount() + 1;
        gridpane.add(debugLabelFactory.createLabel(label), 0, nextRow);
        gridpane.add(debugValue, 1, nextRow);

        return debugValue;
    }

    private String getTotalMemory() {
        return String.valueOf(Runtime.getRuntime().totalMemory());
    }

    private String getUsedMemory() {
        return String.valueOf(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    }

    @Inject
    public void setAudioRepository(final AudioRepository audioRepository) {
        this.audioRepository = audioRepository;
    }

    @Inject
    public void setImageRepository(final ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Inject
    public void setDebugGridPaneFactory(final DebugGridPaneFactory debugGridPaneFactory) {
        this.debugGridPaneFactory = debugGridPaneFactory;
    }

    @Inject
    public void setDebugLabelFactory(final DebugLabelFactory debugLabelFactory) {
        this.debugLabelFactory = debugLabelFactory;
    }
}
