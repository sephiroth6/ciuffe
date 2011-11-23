/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package guiSrv;

import java.awt.Color;
import javax.swing.JToggleButton;

/**
 *
 * @author angelo
 */






public class framing {
    
    private FinestraSwing frame;



        private void close() {
            if (frame != null) {
                frame.setVisible(false);
                frame.pack();
                frame = null;
            }
    }
          private void setFrame(FinestraSwing frame) {
		this.frame = frame;
    }





          private static void postiDispo (JToggleButton ... b) {

        for (int i = 0; i < b.length; i++){
            b[i].setBackground(Color.RED);
            //if(b[i].isSelected())
                b[i].setEnabled(false);

        }

    }

}
