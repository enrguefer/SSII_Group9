package practica1;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class principal {

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		String rutaConf="configuracion.txt";
		List<String>datosFicheros=null;
		List<Integer>auxiliar=new ArrayList<Integer>();
		Boolean semaforoKPI=false;
		Integer contadorL=0;
		
		Date date=new Date();
		DateFormat hora=new SimpleDateFormat("13:15");	//rutina KPI
		int mensual=0;	//rutina mensual
		
		//System.out.println("hora que le paso: "+hora.format(date));
		//semaforoKPI=KpiCalculator.compruebaDiario(semaforoKPI, hora);
		
		//pruebas de cifrado
	/*	File log=new File("logs/l.txt");
		File log2=new File("logs/l2.txt");
		try {
			metodos.cifraAES(log, log2);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException
				| InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
				
		//------------------
		
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
				
				if(mensual>30)	//espera de 30 días de nuevo
					mensual=0;
				
				//comprobar fecha horas y tal del kpi
				if(semaforoKPI) {
					semaforoKPI=KpiCalculator.compruebaDiario(semaforoKPI,hora,mensual);
					if(!semaforoKPI)
						mensual++;
				}
				comprobarDir.comprobarHash(dirInicial, "SHA-256",datosFicheros.get(1),datosFicheros.get(2),
						0,0,auxiliar,dirInicial,semaforoKPI,mensual,contadorL);
				
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
