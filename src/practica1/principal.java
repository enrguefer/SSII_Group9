package practica1;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class principal {

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		String rutaConf="configuracion.txt";
		List<String>datosFicheros=null;
		
		try {
			datosFicheros=leerFicheroConfiguracion.leeFichero(rutaConf); //length->4.
		}catch(Exception e) {
			leerFicheroConfiguracion.restablecerFichero();
		}
		
		datosFicheros=leerFicheroConfiguracion.leeFichero(rutaConf);
		
		
		String dirInicial=datosFicheros.get(0);
		System.out.println("============GENERAMOS LOS HASH DE FICHEROS================");
		lecturaDirs.leeDirectorios(dirInicial,datosFicheros.get(1),datosFicheros.get(2));
		System.out.println("==========================================================");
		try {
			while(true){
				TimeUnit.SECONDS.sleep(Integer.parseInt(datosFicheros.get(3)));
				comprobarDir.comprobarHash(dirInicial, "SHA-256",datosFicheros.get(1),datosFicheros.get(2),0,0);
				TimeUnit.SECONDS.sleep(Integer.parseInt(datosFicheros.get(3)));
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
