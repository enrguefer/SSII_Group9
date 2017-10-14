package practica1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class KpiCalculator {

	
	public static void CalculaKPI(int hashSuccess, int hashFailed, int total,String nHash,
			String dirHash) throws IOException{
		
		File incidencias=new File(dirHash+metodos.compruebaSys()+"DailyKPI.txt");	//cambiar el defecto en confg.?
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
	
	public static void calculaKPIMensual(String nHash,String dirHash,Integer contadorL) throws IOException {
		String nameFile=dirHash+metodos.compruebaSys()+"DailyKPI.txt";
		
		Double res=0.0;
		int ratios=0;
		String linea;
		
		FileReader f = new FileReader(nameFile);
        BufferedReader b = new BufferedReader(f);
        
        for(Integer i=0;i<cuentaLineasFichero.cuentaLineas(dirHash);i++) {
        	
        	linea = b.readLine();
        	
        	if(contadorL>=i && linea!=null) {
        		res+=Double.parseDouble(tratamientoDatos(linea));	//agregamos los parámetros
            	ratios++;
        	}
        	
        	
        }
        
        
        try {
			b.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //escritura de fichero--
        
        File kpiMensual=new File(dirHash+metodos.compruebaSys()+"MonthlyKPI.txt");	//cambiar el defecto en confg.?
		BufferedWriter bw = new BufferedWriter(new FileWriter(kpiMensual,true));
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
		
		Double kpi = res/ratios;
		
		bw.write("=================================================\n");
		bw.write("Fecha y Hora: "+df.format(date)+"\n");
		bw.write("Resultado: "+kpi+"\n");
		bw.write("\n");
		bw.close();
		
        //----------------------
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
	
	public static String tratamientoDatos(String dato) {
		Boolean semaforo=false;
		String res="";
		
		if(dato.contains("Resultado:")) {
			
			for(int i=0;i<dato.length();i++) {
				char c=dato.charAt(i);
				//System.out.println(c);
				if(c==':') {
					//System.out.println(dato.charAt(i));
					semaforo=true;
				}else{
					if(semaforo && c !=' ') {
						res+=c;
					}
				}	
			}
		}
		
		return res;
		
	}
	
}
