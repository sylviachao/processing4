package processing.core;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class PGraphicsTest {

    private processing.core.PGraphics pGraphics;

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
        pGraphics.beginDrawTestable();
        pGraphics.dispose();
        assertFalse("Without the setting for primary and image server, the disposed does not required.", pGraphics.isAsyncImageSaverDisposed());
    }


}

