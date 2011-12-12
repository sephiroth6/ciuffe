package guiSrv;

import javax.swing.*;
import javax.swing.JPanel;

class FinestraSwing extends JFrame {

    FinestraSwing(String s, int x_Loc, int y_Loc, int x_Siz, int y_Siz, JPanel pann) {
        super(s);
        setLocation(x_Loc, y_Loc);
        setSize(x_Siz, y_Siz);
        getContentPane().add(pann);
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

}
