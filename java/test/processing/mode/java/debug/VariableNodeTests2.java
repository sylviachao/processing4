package processing.mode.java.debug;

import com.sun.jdi.StringReference;
import com.sun.jdi.Value;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class VariableNodeTests2 {

    @Test
    public void describeIntMaxBoundary() {
        Value value = buildMockValue(Integer.toString(Integer.MAX_VALUE));
        VariableNode node = new VariableNode("test", "int", value);
        Assert.assertEquals(node.getStringValue(), Integer.toString(Integer.MAX_VALUE));
    }

    @Test
    public void describeIntMinBoundary() {
        Value value = buildMockValue(Integer.toString(Integer.MIN_VALUE));
        VariableNode node = new VariableNode("test", "int", value);
        Assert.assertEquals(node.getStringValue(), Integer.toString(Integer.MIN_VALUE));
    }

    @Test
    public void describeFloatMaxBoundary() {
        Value value = buildMockValue(Float.toString(Float.MAX_VALUE));
        VariableNode node = new VariableNode("test", "float", value);
        Assert.assertEquals(node.getStringValue(), Float.toString(Float.MAX_VALUE));
    }
    @Test
    public void describeFloatMinBoundary() {
        Value value = buildMockValue(Float.toString(Float.MIN_VALUE));
        VariableNode node = new VariableNode("test", "float", value);
        Assert.assertEquals(node.getStringValue(), Float.toString(Float.MIN_VALUE));
    }

    @Test
    public void describeStringEmpty() {
        Value value = buildMockString("");
        VariableNode node = new VariableNode("test", "java.lang.String", value);
        Assert.assertEquals(node.getStringValue(), "");
    }
    @Test
    public void describeEmptyArray() {
        Value value = buildMockValue("instance of int[0] (id=123)");
        VariableNode node = new VariableNode("test", "int[]", value);
        Assert.assertEquals(node.getStringValue(), "instance of int[0] (id=123)");
    }
    @Test
    public void describeEmptyMultidimensionalArray() {
        Value value = buildMockValue("instance of int[0][0] (id=123)");
        VariableNode node = new VariableNode("test", "int[][]", value);
        Assert.assertEquals(node.getStringValue(), "instance of int[0][0] (id=123)");
    }

    @Test
    public void describeBooleanTrue() {
        Value value = buildMockValue("true");
        VariableNode node = new VariableNode("test", "boolean", value);
        Assert.assertEquals(node.getStringValue(), "true");
    }

    @Test
    public void describeBooleanFalse() {
        Value value = buildMockValue("false");
        VariableNode node = new VariableNode("test", "boolean", value);
        Assert.assertEquals(node.getStringValue(), "false");
    }

    private Value buildMockValue(String toStringValue) {
        Value value = Mockito.mock(Value.class);
        Mockito.when(value.toString()).thenReturn(toStringValue);
        return value;
    }

    private StringReference buildMockString(String innerValue) {
        StringReference value = Mockito.mock(StringReference.class);
        Mockito.when(value.value()).thenReturn(innerValue);
        return value;
    }
}
