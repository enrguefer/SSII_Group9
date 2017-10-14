package practica1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class cuentaLineasFichero {

	public static int cuentaLineas(String dirHash) throws IOException{
		FileReader f = new FileReader(dirHash+metodos.compruebaSys()+"DailyKPI.txt"); 
        BufferedReader b1 = new BufferedReader(f);
        int lNumeroLineas = 0;
        
        while ((b1.readLine())!=null) {
          lNumeroLineas++;
        }
        return lNumeroLineas;
	}
}
