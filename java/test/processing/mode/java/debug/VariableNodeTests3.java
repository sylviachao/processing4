package processing.mode.java.debug;


import com.sun.jdi.StringReference;
import com.sun.jdi.*;

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
    public void testAllGetTypeCategoryandAllowingChildren() {
        VariableNode nodetest;
        Value valuetest;
        valuetest = buildMockValue("0");
        nodetest = new VariableNode("test1",null,valuetest);
        Assert.assertEquals(VariableNode.TYPE_UNKNOWN, nodetest.getType());
        Assert.assertEquals(true,nodetest.isLeaf());
        nodetest = new VariableNode("test2", "double", valuetest);
        Assert.assertEquals(VariableNode.TYPE_DOUBLE, nodetest.getType());
        Assert.assertEquals(true,nodetest.isLeaf());
        nodetest = new VariableNode("test3", "short", valuetest);
        Assert.assertEquals(VariableNode.TYPE_SHORT, nodetest.getType());
        Assert.assertEquals(true,nodetest.isLeaf());
        nodetest = new VariableNode("test4", "float", valuetest);
        Assert.assertEquals(VariableNode.TYPE_FLOAT, nodetest.getType());
        Assert.assertEquals(true,nodetest.isLeaf());
        nodetest = new VariableNode("test5", "long", valuetest);
        Assert.assertEquals(VariableNode.TYPE_LONG, nodetest.getType());
        Assert.assertEquals(true,nodetest.isLeaf());
        nodetest = new VariableNode("test6", "byte", valuetest);
        Assert.assertEquals(VariableNode.TYPE_BYTE, nodetest.getType());
        Assert.assertEquals(true,nodetest.isLeaf());

        StringReference mockString = Mockito.mock(StringReference.class);
        Mockito.when(mockString.value()).thenReturn("Hello");
        nodetest = new VariableNode("test7", "java.lang.String", mockString);
        Assert.assertEquals(VariableNode.TYPE_STRING, nodetest.getType());
        Assert.assertEquals("Hello",nodetest.getStringValue());
        Assert.assertEquals(true,nodetest.isLeaf());

        nodetest = new VariableNode("test8", "boolean", buildMockValue("false"));
        Assert.assertEquals(VariableNode.TYPE_BOOLEAN, nodetest.getType());
        Assert.assertEquals(true,nodetest.isLeaf());

        // Array testing
        ArrayReference mockArrayReference = Mockito.mock(ArrayReference.class);
        Mockito.when(mockArrayReference.length()).thenReturn(3);
        nodetest = new VariableNode("test9", "int[]", mockArrayReference);
        Assert.assertEquals(VariableNode.TYPE_ARRAY, nodetest.getType());
        // Test if the variablenode is array which is longer then 0, it could not be a leaf
        Assert.assertEquals(false,nodetest.isLeaf());
        Assert.assertEquals("test9",nodetest.toString());
        // Check for the previous part is the same for the ArrayReference
        Assert.assertEquals("Mock for ArrayReference",nodetest.getStringValue().substring(0,23));

        nodetest = new VariableNode("test10", "char", buildMockValue("a"));
        Assert.assertEquals(VariableNode.TYPE_CHAR, nodetest.getType());
        nodetest = new VariableNode("test11", "void", null);
        Assert.assertEquals(VariableNode.TYPE_VOID, nodetest.getType());
        Assert.assertEquals("null",nodetest.getStringValue());
        Assert.assertEquals(true,nodetest.isLeaf());

        nodetest = new VariableNode("test12", "customize_object", buildMockValue("return getInt();"));
        Assert.assertEquals(VariableNode.TYPE_OBJECT, nodetest.getType());
        Assert.assertEquals("instance of customize_object",nodetest.getStringValue());
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
        Assert.assertEquals(node,childNode.getParent());
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
        node.addChild(childNode);
        Assert.assertEquals(2, node.getChildCount());
        node.remove(childNode);
        Assert.assertEquals(1, node.getChildCount());
        node.remove(0);
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