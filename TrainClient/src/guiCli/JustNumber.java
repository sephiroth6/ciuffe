package guiCli;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
/**
 *
 * @author Angelo
 */
public class JustNumber extends PlainDocument {
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if(str!=null && str.matches("[\\d]*"))
            super.insertString(offs, str, a);
    }
}
