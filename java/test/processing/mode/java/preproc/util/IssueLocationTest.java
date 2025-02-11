package processing.mode.java.preproc.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import processing.mode.java.preproc.PdeIssueEmitter;


public class IssueLocationTest {

  private String source;
  private PdeIssueEmitter.IssueLocation issueLocation;

  @Before
  public void setup() {
    source = "//Test\n" +
        "noFill();\n" +
        "/**\n" +
        "**/\n" +
        "ellipse(50,50,50,50)\n" +
        "\n" +
        "/**\n" +
        "Test\n" +
        "* Test\n" +
        "** Test\n" +
        "*/\n" +
        "\n" +
        "// Testing\n" +
        "\n";
  }
@Test
  public void getLineWithOffsetNotApplies() {
    issueLocation = PdeIssueEmitter.IssueLocationFactory.getLineWithOffset(
        new PdeIssueEmitter.IssueMessageSimplification("test message", false),
        15,
        0,
        source
    );
    // Expect to be same as initiation
    Assert.assertEquals(15, issueLocation.getLine());
    Assert.assertEquals(0, issueLocation.getCharPosition());
  }

  @Test
public void getLineWithEmptySource() {
    String emptySource = "";
    issueLocation = PdeIssueEmitter.IssueLocationFactory.getLineWithOffset(
        new PdeIssueEmitter.IssueMessageSimplification("test message", true),
        0,
        0,
        emptySource
    );

    // Expecting line 0 
    Assert.assertEquals(0, issueLocation.getLine());
    Assert.assertEquals(0, issueLocation.getCharPosition());
}

  @Test
public void getLineWithSpecialCharacters() {
    String sourceWithSpecialChars = "noFill();\n\t// comment with tab\nellipse(50,50,50,50)\n";
    issueLocation = PdeIssueEmitter.IssueLocationFactory.getLineWithOffset(
        new PdeIssueEmitter.IssueMessageSimplification("test message", true),
        2,
        0,
        sourceWithSpecialChars
    );

    // Should properly handle tab and give the correct line and position
    Assert.assertEquals(1, issueLocation.getLine());
    Assert.assertEquals(9, issueLocation.getCharPosition());
}

 @Test
public void getLineWithNegativeOffset() {
    issueLocation = PdeIssueEmitter.IssueLocationFactory.getLineWithOffset(
        new PdeIssueEmitter.IssueMessageSimplification("test message", true),
        -5,  // Negative offset
        0,
        source
    );

    
    Assert.assertEquals(-5, issueLocation.getLine());
    Assert.assertEquals(0, issueLocation.getCharPosition()); 
}



@Test
public void getLineWithLargeSource() {
    StringBuilder largeSource = new StringBuilder();
    for (int i = 0; i < 10000; i++) {
        largeSource.append("noFill();\n");
    }

    issueLocation = PdeIssueEmitter.IssueLocationFactory.getLineWithOffset(
        new PdeIssueEmitter.IssueMessageSimplification("test message", true),
        5000,
        0,
        largeSource.toString()
    );

    // Checking if the line is correctly identified, should be at the last char position of the previous line
    Assert.assertEquals(4999, issueLocation.getLine());  // 5000-based, so it's expected to be 5001
    Assert.assertEquals(9, issueLocation.getCharPosition());  // No special characters in the line
}
}