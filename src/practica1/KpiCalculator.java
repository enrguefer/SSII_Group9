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

	
	public static void CalculaKPI(int hashSuccess, int hashFailed, int total) throws IOException{
		
		File incidencias=new File("logs"+metodos.compruebaSys()+"DailyKPI.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(incidencias,true));
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
		
		float kpi = (float)(hashSuccess+hashFailed)/total;//Calculo del KPI
		
		bw.write("=================================================\n");
		bw.write("KPI del dia: "+df.format(date)+"\n");
		bw.write(kpi+"\n");
		bw.write("\n");
		bw.close();
		
	}
}
