import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        JFrame jf = new JFrame("JsonFormatter");
        jf.setSize(600, 600);

        JTextArea jta = new JTextArea();
        JPanel jp = new JPanel();
        JButton clear = new JButton("クリア");
        JButton but = new JButton("フォーマット");

        Vector<Integer> vec = new Vector<>();
        for (int i = 0; i < 8; i++) {
            vec.add(i);
        }
        JComboBox<Integer> jcb = new JComboBox<>(vec);
        jcb.setSelectedIndex(3);

        clear.addActionListener(e -> jta.setText(""));
        but.addActionListener(e -> jta.setText(format(jta.getText(), jcb.getSelectedIndex())));

        jp.add(clear);
        jp.add(but);
        jp.add(jcb);

        jf.getContentPane().add(jta);
        jf.getContentPane().add(jp, "South");

        jf.setVisible(true);
    }

    public static String format(String input, Integer sp) {
        input = input.trim().replaceAll("\n", "");
        int lv = 0;
        boolean esc = false;
        
        StringBuilder json = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '\"') {
                esc = !esc;
            }
            if (esc) {
                json.append(c);
                continue;
            }
            if (c == '{') {
                json.append(c);
                json.append("\n");
                lv++;
                json.append(" ".repeat(lv * sp));
            } else if (c == '}') {
                json.append("\n");
                lv--;
                json.append(" ".repeat(lv * sp));
                json.append(c);
            } else if (c == ',') {
                json.append(c);
                json.append("\n");
                json.append(" ".repeat(lv * sp));
            } else {
                json.append(c);
            }
        }

        copy(json.toString());

        return json.toString();
    }

    public static void copy(String json) {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection sel = new StringSelection(json);
        clip.setContents(sel, sel);
    }
}
