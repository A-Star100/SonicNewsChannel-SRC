package gnu.kawa.swingviews;

import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.kawa.models.Button;
import gnu.kawa.models.Model;
import gnu.kawa.models.ModelListener;
import java.awt.Color;
import javax.swing.JButton;

public class SwingButton extends JButton implements ModelListener {
    Button model;

    public SwingButton(Button model2) {
        super(model2.getText());
        setModel(new SwModel(model2));
        this.model = model2;
        Object action = model2.getAction();
        if (action != null) {
            addActionListener(SwingDisplay.makeActionListener(action));
        }
        model2.addListener((ModelListener) this);
        Color fg = model2.getForeground();
        if (fg != null) {
            SwingButton.super.setBackground(fg);
        }
        Color bg = model2.getBackground();
        if (bg != null) {
            SwingButton.super.setBackground(bg);
        }
    }

    public void setText(String text) {
        Button button = this.model;
        if (button == null) {
            SwingButton.super.setText(text);
        } else {
            button.setText(text);
        }
    }

    public void setForeground(Color fg) {
        Button button = this.model;
        if (button == null) {
            SwingButton.super.setForeground(fg);
        } else {
            button.setForeground(fg);
        }
    }

    public void setBackground(Color bg) {
        Button button = this.model;
        if (button == null) {
            SwingButton.super.setBackground(bg);
        } else {
            button.setBackground(bg);
        }
    }

    public void modelUpdated(Model model2, Object key) {
        Button button;
        Button button2;
        Button button3;
        if (key == PropertyTypeConstants.PROPERTY_TYPE_TEXT && model2 == (button3 = this.model)) {
            SwingButton.super.setText(button3.getText());
        } else if (key == "foreground" && model2 == (button2 = this.model)) {
            SwingButton.super.setForeground(button2.getForeground());
        } else if (key == "background" && model2 == (button = this.model)) {
            SwingButton.super.setBackground(button.getBackground());
        }
    }
}
