package processing.core;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class PGraphicsTest {

    private PGraphics pGraphics;

    // Set up the PGraphics instance before each test
    @Before
    public void setUp() {
        pGraphics = new PGraphics();
    }

    // Test for beginDrawTestable method
    @Test
    public void testBeginDrawTestable() {
        pGraphics.beginDrawTestable();
        assertTrue("beginDrawTestable should set drawingStarted to true.", pGraphics.isDrawingStarted());
    }

    // Test for endDraw method
    @Test
    public void testEndDraw() {
        pGraphics.beginDrawTestable();  // Start drawing
        pGraphics.endDraw();
        assertFalse("endDraw should set drawingStarted to false.", pGraphics.isDrawingStarted());
    }

    // Test for dispose method
    @Test
    public void testDispose() {
        pGraphics.dispose();
        assertTrue("Async image saver should be disposed correctly.", pGraphics.isAsyncImageSaverDisposed());
    }

    // Test for cache management methods
    @Test
    public void testCacheManagement() {
        PImage mockImage = org.mockito.Mockito.mock(PImage.class); // Mock a PImage
        Object mockStorage = new Object();

        pGraphics.setCache(mockImage, mockStorage);
        assertEquals("Cache should store and retrieve correctly.", mockStorage, pGraphics.getCache(mockImage));

        pGraphics.removeCache(mockImage);
        assertNull("Cache should remove items correctly.", pGraphics.getCache(mockImage));
    }
}

