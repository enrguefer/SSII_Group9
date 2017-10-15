package practica1;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class principal {

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException, 
	InvalidKeyException, NoSuchPaddingException, ClassNotFoundException {
		String rutaConf="configuracion.txt";
		List<String>datosFicheros=null;
		List<Integer>auxiliar=new ArrayList<Integer>();
		Boolean semaforoKPI=false;
		Boolean semaforoCifrado=false;
		Integer contadorL=0;
		SecretKey key=null;
		
		Date date=new Date();
		DateFormat hora=new SimpleDateFormat("13:15");	//rutina KPI
		int mensual=0;	//rutina mensual
		
		try {
			datosFicheros=leerFicheroConfiguracion.leeFichero(rutaConf); //length->4.
		}catch(Exception e) {
			leerFicheroConfiguracion.restablecerFichero();
		}
		
		datosFicheros=leerFicheroConfiguracion.leeFichero(rutaConf);
		
		System.out.println(pid.pid());
		
		String dirInicial=datosFicheros.get(0);
		System.out.println("============GENERAMOS LOS HASH DE FICHEROS================");
		lecturaDirs.leeDirectorios(dirInicial,datosFicheros.get(1),datosFicheros.get(2));
		System.out.println("==========================================================");
		try {
			while(true){
				TimeUnit.SECONDS.sleep(Integer.parseInt(datosFicheros.get(3)));
				
				if(mensual>30)	//espera de 30 días de nuevo
					mensual=0;
				
				//comprobar fecha horas y tal del kpi
				if(semaforoKPI) {
					semaforoKPI=KpiCalculator.compruebaDiario(semaforoKPI,hora,mensual);
					if(!semaforoKPI)
						mensual++;
				}
				
				if(semaforoCifrado)
					metodos.descifrar(key, datosFicheros.get(1));
				
				comprobarDir.comprobarHash(dirInicial, "SHA-256",datosFicheros.get(1),datosFicheros.get(2),
						0,0,auxiliar,dirInicial,semaforoKPI,mensual,contadorL);
				
				key=metodos.cifrar(datosFicheros.get(1), datosFicheros.get(2));
				semaforoCifrado=true;
				
				if(!semaforoKPI)
					semaforoKPI=true;
				
				contadorL+=180;	//cada 30 días se generan 180 líneas de texto nuevas a tratar.
				
				TimeUnit.SECONDS.sleep(Integer.parseInt(datosFicheros.get(3)));
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
