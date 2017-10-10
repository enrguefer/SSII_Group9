package practica1;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

public class Practica1 {

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		String dirInicial="directorioArchivos";
		
		lecturaDirs.leeDirectorios(dirInicial);//Solo se puede ejecutar 1 vez, crea el Hash de todos los archivos de dirInicial
		System.out.println("================================");
		try {
			TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		comprobarDir.comprobarHash(dirInicial, "SHA-256");//¿Como llamar al Init de comprobarHash?

	}

}
