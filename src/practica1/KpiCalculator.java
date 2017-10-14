package practica1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class KpiCalculator {

	
	public static void CalculaKPI(int hashSuccess, int hashFailed, int total,String nHash,
			String dirHash) throws IOException{
		
		File incidencias=new File(dirHash+metodos.compruebaSys()+"DailyKPI.txt");	//cambiar el defecto en confg.
		BufferedWriter bw = new BufferedWriter(new FileWriter(incidencias,true));
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
		
		float kpi = (float)(hashSuccess+hashFailed)/total;//Calculo del KPI
		
		bw.write("=================================================\n");
		bw.write("Fecha y Hora: "+df.format(date)+"\n");
		bw.write("archivos totales: "+total+"\n");
		bw.write("fallos: "+hashFailed+"\n");
		bw.write("Resultado: "+kpi+"\n");
		bw.write("\n");
		bw.close();
	}
	
	public static void calculaKPIMensual() {
		
	}
	
	
	public static Boolean compruebaDiario(Boolean sm, DateFormat h, int contadorM) {
		Boolean res=true;
		
		if(!sm) {
			Date date = new Date();
			DateFormat hourFormat = new SimpleDateFormat("HH:mm");
			
			if(hourFormat.format(date).equals(h.format(date)))	//rutina diaria
				res=false;
			
			if(contadorM==30) {		//mensual
				res=false;
			}
			
			//System.out.println("Hora: "+hourFormat.format(date));
		}
		
		return res;
	}
	
	
}
