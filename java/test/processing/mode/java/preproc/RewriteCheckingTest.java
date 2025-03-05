package processing.mode.java.preproc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import processing.mode.java.TextTransform;
import org.antlr.v4.runtime.TokenStreamRewriter;

import java.util.List;

public class RewriteCheckingTest {

    private TokenStreamRewriter tokenStreamRewriter;
    private RewriteResultBuilder rewriteResultBuilder;

    @Before
    public void setUp() {
        tokenStreamRewriter = Mockito.mock(TokenStreamRewriter.class);
        rewriteResultBuilder = new RewriteResultBuilder();
    }


    @Test
    public void testNoEdits() {
        // Test when no edits are made
        PdeParseTreeListener.PrintWriterWithEditGen editGen = createGen(true);
        editGen.finish();  // No edits made, just finishing the process

        // Build the result from the rewriteResultBuilder
        RewriteResult result = rewriteResultBuilder.build();
        Assert.assertEquals(0,result.getLineOffset());

        // Verify that there are no edits in the result
        List<TextTransform.Edit> edits = result.getEdits();
        Assert.assertEquals(1, edits.size());  // No edits should be present
        Mockito.verify(tokenStreamRewriter).insertBefore(5,"");
    }

    @Test
    public void testaddMultipleEdits() {
        PdeParseTreeListener.PrintWriterWithEditGen editGen = createGen(true);  // true for insertBefore
        editGen.addCodeLine("line1");  // Add first line
        editGen.addEmptyLine();        // Add an empty line
        editGen.addCodeLine("line2");  // Add second line
        editGen.finish();              // Finalize the edit

        List<TextTransform.Edit> edits = rewriteResultBuilder.getEdits();
        Assert.assertEquals(1, edits.size());  // Only one edit will be added since they are all appended to the same StringBuilder
        //Output should be Edit{from=5:0, to=5:13, text='line1 line2'}, which is the length of 20
        Assert.assertEquals(45,edits.get(0).toString().length());
        // Verify that the content was added correctly before position 5
        Mockito.verify(tokenStreamRewriter).insertBefore(5, "line1\n\nline2\n");
    }


    @Test
    public void testAddCodeBeforeAndAfter() {
        // Test inserting code before and after
        PdeParseTreeListener.PrintWriterWithEditGen editGenBefore = createGen(true);  // Insert before
        editGenBefore.addCodeLine("line1");
        editGenBefore.finish();

        RewriteResult resultBefore = rewriteResultBuilder.build();
        Assert.assertEquals(1,resultBefore.getLineOffset());
        Mockito.verify(tokenStreamRewriter).insertBefore(5, "line1\n");

        rewriteResultBuilder = new RewriteResultBuilder(); // Reset builder

        PdeParseTreeListener.PrintWriterWithEditGen editGenAfter = createGen(false);  // Insert after
        editGenAfter.addCodeLine("line2");
        editGenAfter.finish();

        RewriteResult resultAfter = rewriteResultBuilder.build();
        List<TextTransform.Edit> editsAfter = resultAfter.getEdits();
        Mockito.verify(tokenStreamRewriter).insertAfter(5, "line2\n");
    }


    private PdeParseTreeListener.PrintWriterWithEditGen createGen(boolean before) {
        return new PdeParseTreeListener.PrintWriterWithEditGen(
                tokenStreamRewriter,
                rewriteResultBuilder,
                5,
                before
        );
    }
}

