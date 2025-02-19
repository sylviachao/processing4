package processing.mode.java.debug;


import com.sun.jdi.StringReference;
import com.sun.jdi.Value;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import java.util.List;
import org.mockito.Mockito;



public class VariableNodeTests3 {


    private VariableNode node;
    private Value value = buildMockValue("42");

    @Before
    public void setUp() {

        node = new VariableNode("myVar", "int", value);
    }



    @Test
    public void testGetTypeName() {
        Assert.assertEquals("int", node.getTypeName());
    }

    @Test
    public void testGetType() {
        Assert.assertEquals(VariableNode.TYPE_INTEGER, node.getType());
    }

    @Test
    public void testGetName() {
        Assert.assertEquals("myVar", node.getName());
    }

    @Test
    public void testSetName() {
        node.setName("newName");
        Assert.assertEquals("newName", node.getName());
    }

    @Test
    public void testAddChild() {
        Value value = buildMockValue("5");
        VariableNode childNode = new VariableNode("childVar", "int", value);
        node.addChild(childNode);
        Assert.assertEquals(1, node.getChildCount());
        Assert.assertEquals(childNode, node.getChildAt(0));
    }

    @Test
    public void testAddChildren() {
        VariableNode childNode1 = new VariableNode("childVar1", "int", buildMockValue("10"));
        VariableNode childNode2 = new VariableNode("childVar2", "String", buildMockValue("Hello"));
        node.addChildren(List.of(childNode1, childNode2));
        Assert.assertEquals(2, node.getChildCount());
    }

    @Test
    public void testGetDescription() {
        String expectedDescription = "int myVar = 42";
        Assert.assertEquals(expectedDescription, node.getDescription());
    }
    @Test
    public void testRemoveChild() {
        VariableNode childNode = new VariableNode("childVar", "int", buildMockValue("5"));
        node.addChild(childNode);
        Assert.assertEquals(1, node.getChildCount());
        node.remove(childNode);
        Assert.assertEquals(0, node.getChildCount());
    }

    @Test
    public void testRemoveAllChildren() {
        VariableNode childNode1 = new VariableNode("childVar1", "int", buildMockValue("10"));
        VariableNode childNode2 = new VariableNode("childVar2", "String", buildMockValue("Hello"));
        node.addChildren(List.of(childNode1, childNode2));
        node.removeAllChildren();
        Assert.assertEquals(0, node.getChildCount());
    }

    @Test
    public void testEquals() {
        VariableNode equalNode = new VariableNode("myVar", "int", value);
        VariableNode differentNode = new VariableNode("myVar", "String", buildMockValue("Hello"));
        Assert.assertTrue(node.equals(equalNode));  // Should be equal
        Assert.assertFalse(node.equals(differentNode));  // Should not be equal
    }

    @Test
    public void testHashCode() {
        VariableNode equalNode = new VariableNode("myVar", "int", value);
        Assert.assertEquals(node.hashCode(), equalNode.hashCode());
    }

    private Value buildMockValue(String toStringValue) {
        Value value = Mockito.mock(Value.class);
        Mockito.when(value.toString()).thenReturn(toStringValue);
        return value;
    }

}