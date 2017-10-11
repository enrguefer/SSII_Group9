package practica1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class leerFicheroConfiguracion {
	
	public static List<String> leeFichero (String nameFile) throws IOException{
		List<String>res=new ArrayList<String>();
		String linea;
		
		FileReader f = new FileReader(nameFile);
        BufferedReader b = new BufferedReader(f);
        
        while((linea = b.readLine())!=null) {
            //System.out.println(linea);
        	res.add(tratamientoDatos(linea));	//agregamos los parámetros
        }
        
        try {
			b.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if(res.size()>4) {
        	System.out.println("El tamño del fichero de configuración es mayor del esperado. Se restaurará por defecto");
        	restablecerFichero();
        }
        	
        
		return res;
	}
	
	public static String tratamientoDatos(String dato) {
		Boolean semaforo=false;
		String res="";
		
		if(dato.contains("1)") || dato.contains("2)")) {
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
		} //fin lectura 1 y 2.
		
		
		if(dato.contains("3)")) {
			for(int i=0;i<dato.length();i++) {
				char c=dato.charAt(i);
				//System.out.println(c);
				if(c==':') {
					//System.out.println(dato.charAt(i));
					semaforo=true;
				}else{
					if(semaforo && c !=' ') {
						if(c=='.')
							break;	//por si acaso pone la extensión
						res+=c;
					}
				}	
			}res+=".txt";
		}//fin lectura 3
		
		if(dato.contains("4)")) {
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
			}try {
				Integer.parseInt(res);
			}catch(Exception e) {
				System.out.println("no es un número.");
			}
		}//fin lectura 4
		
		
		return res;
	}
	
	public static void restablecerFichero() throws IOException {
		File f = new File("configuracion.txt");
		BufferedWriter bw;
		bw = new BufferedWriter(new FileWriter(f));
		bw.write("1)Ruta que analiza:directorioArchivos");
		bw.newLine();
		bw.write("2)Directorio del fichero hash:logs");
		bw.newLine();
		bw.write("3)Nombre del fichero hash sin extensión:logHash");
		bw.newLine();
		bw.write("4)Frecuencia en segundos para comprobar fichero hash:35");
		bw.close();
	}
	
	

}
