package pkg;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class TekstOut 
{
	private JTextPane textPane;
	static DefaultStyledDocument doc;
	public TekstOut(JPanel contentPane) 
	{
		
		doc = new DefaultStyledDocument() 
		{
			private static final long serialVersionUID = 1L;
			public void insertString(int offset, String str, AttributeSet a) throws BadLocationException
			{
				
				super.insertString(offset, str, a);
			}
		};
		
		textPane = new JTextPane(doc);
		JScrollPane jsp = new JScrollPane(textPane);
		jsp.setAutoscrolls(false);
		DefaultCaret caret = (DefaultCaret)textPane.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		contentPane.add(jsp);
		jsp.setBounds(450, 140 , 100, 160);
		textPane.setFont(new Font("Arial", Font.PLAIN, 18));
		textPane.setBackground(new Color(25, 25, 25));
		textPane.setEditable(false);


	}
	public static void dodajTekst(String a, Color color)
	{
		a += "\n";
		final StyleContext cont = StyleContext.getDefaultStyleContext();
		final AttributeSet atrib = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, color);
		try 
		{
			doc.insertString(0, a , atrib);
		} 
		catch (BadLocationException e) 
		{
			e.printStackTrace();
		}
	}
}