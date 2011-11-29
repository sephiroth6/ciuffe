package train;

import java.lang.Object.*;
import java.io.*;

public class Data implements Serializable {

    private String giorno;
    private String mese;
    private String anno;
    private String ora;
    private String minuti;

    public Data(String g, String m, String a, String o, String mi) {
        giorno = g;
        mese = m;
        anno = a;
        ora = o;
        minuti = mi;
        
    }
    public Data(){
        
    }

    public String getGiorno() {


        return giorno;
    }

    public String getMese() {
        return mese;
    }

    public String getAnno() {
        return anno;
    }

    public String getOra() {
        return ora;
    }

    public String getMinuti() {
        return minuti;
    }

    public void stampaData() {
        System.out.println(giorno + "/" + mese + "/" + anno + "-" + ora + ":" + minuti);
    }

    public static int convertiStringa(String s) {
        int temp;
        int aux = 0;
        int j = 1;
        int i;
        for (i = 0; i < s.length() - 1; i++) {
            j = j * 10;
        }
        for (i = 0; i < s.length(); i++) {
            temp = (int) s.charAt(i);
            temp = (temp - 48) * j;
            aux = aux + temp;
            j = j / 10;
        }
        return aux;
    }

    public void leggiStringa(String s) {


        giorno = s.substring(0, 2);
        mese = s.substring(3, 5);
        anno = s.substring(6, 10);
        ora = s.substring(11, 13);
        minuti = s.substring(14, 16);

        if (verificaData() == false) {
            giorno = null;
            mese = null;
            anno = null;
            ora = null;
            minuti = null;
        }

    }
    /*
    public boolean equalsData(Data t) {
    if (giorno.equals(t.returnGiorno()) && mese.equals(t.returnMese())
    && anno.equals(t.returnAnno())) {
    return true;
    }
    return false;
    }
    
    public boolean isPrecedente(Data t2) {
    int g1 = convertiData(giorno);
    int g2 = convertiData(t2.returnGiorno());
    int m1 = convertiData(mese);
    int m2 = convertiData(t2.returnMese());
    int a1 = convertiData(anno);
    int a2 = convertiData(t2.returnAnno());
    
    if (a1 < a2) {
    return true;
    } else if (a1 == a2 && m1 < m2) {
    return true;
    } else if (a1 == a2 && m1 == m2 && g1 < g2) {
    return true;
    }
    return false;
    } */

    public boolean verificaData() {

        if(giorno.equals("") || mese.equals("") || anno.equals("") || ora.equals("") || minuti.equals("")){
            System.out.println("sto al punto 1");
            return false;
        }
        int g = convertiStringa(giorno);
        int m = convertiStringa(mese);
        int a = convertiStringa(anno);
        int o = convertiStringa(ora);
        int mi = convertiStringa(minuti);


        if (g <= 0 || m <= 0 || a <= 0 || o <= 0 || mi < 0) {
             System.out.println("sto al punto 2");
            return false;
        } else if (o > 23 || mi > 59) {
             System.out.println("sto al punto 3");
            return false;
        } else if ((m == 11 || m == 4 || m == 6 || m == 9) && g > 30) {
             System.out.println("sto al punto 4");
            return false;
        } else if (m == 2 && g > 28) {
             System.out.println("sto al punto 5");
            return false;
        } else if ((m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) && g > 31) {
             System.out.println("sto al punto 6");
            return false;
        } else {
             System.out.println("sto al punto 7");
            return true;
        }
    }
}
