package practica1;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class lecturaDirs {
	
	public static void leeDirectorios(String directorio) throws NoSuchAlgorithmException, IOException {
		MessageDigest algorithm=MessageDigest.getInstance("SHA-256");	//CREAMOS LA HUELLA SHA-256
		File dir = new File(directorio);	//DIRECTORIO QUE VAMOS A MONITORIZAR
		File dirlog=new File("logs"+metodos.compruebaSys()+"l.txt");
		
		BufferedWriter bw;
		
		String[] ficheros = dir.list();
		
		if(ficheros==null)
			System.out.println("Directorios vacios");
		
		else {
			
			bw = new BufferedWriter(new FileWriter(dirlog,true));
		
			for (int i=0;i<ficheros.length;i++) {
				try {
					FileInputStream fis = new FileInputStream(directorio+metodos.compruebaSys()+ficheros[i]);
					
					BufferedInputStream bis = new BufferedInputStream(fis);
					@SuppressWarnings("resource")
					DigestInputStream dis =  new DigestInputStream(bis,algorithm);//APLICAMOS LA HUELLA AL FICHERO
					
					byte [] b= new byte[dis.available()];
					dis.read(b,0,dis.available());
					algorithm= dis.getMessageDigest();
					byte[] digest = algorithm.digest();
					
					//escritura en logs ---
					
					if(i==ficheros.length-1)
						bw.write("["+ficheros[i]+","+DatatypeConverter.printHexBinary(digest)+"]\n");
					else
						bw.write("["+ficheros[i]+","+DatatypeConverter.printHexBinary(digest)+"],\n");
				    
				    //fin escritura ----
				    
					System.out.println("Fichero "+ficheros[i]+", HASH: "+DatatypeConverter.printHexBinary(digest));
					
				}catch(java.io.FileNotFoundException e) {
					leeDirectorios(directorio+metodos.compruebaSys()+ficheros[i]);
				}
			
			}//fin bloque for
			bw.close();
		}//fin else
	}
	
}
