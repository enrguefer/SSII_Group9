package practica1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class comprobarDir{

	public static void comprobarHash(String dirInicial, String huella, String dirHash,String nHash) throws NoSuchAlgorithmException, IOException{
			//System.out.println("DIRECTORIO : " +directorio);
			MessageDigest algorithm=MessageDigest.getInstance(huella);//CREAMOS LA HUELLA SHA-256
			File dir = new File(dirInicial);
			File dirlog=new File(dirHash+metodos.compruebaSys()+nHash);
			String[] ficheros = dir.list();
			
			if (ficheros == null){
				  System.out.println("No hay ficheros en el directorio especificado");
				
			}else {
				for (int x=0;x<ficheros.length;x++){
			
					try{
						FileInputStream fis = new FileInputStream(dirInicial+metodos.compruebaSys()+ficheros[x]); 
					  	BufferedInputStream bis = new BufferedInputStream(fis);
						@SuppressWarnings("resource")
						DigestInputStream dis =  new DigestInputStream(bis,algorithm);//APLICAMOS LA HUELLA AL FICHERO
						byte [] b= new byte[dis.available()];
						dis.read(b,0,dis.available());
						algorithm= dis.getMessageDigest();
						byte[] digest = algorithm.digest();
					
						
						String cadena;
						FileReader f = new FileReader(dirlog); 
				        BufferedReader b1 = new BufferedReader(f);
				        Boolean control= null;
				        String fichero= ficheros[x];
				        while((cadena = b1.readLine())!=null) {
				        	 	if(!(cadena.endsWith("]"))){
				        		 cadena=cadena.substring(0,cadena.length()-1);
				        	 	}

				        	 	System.out.println("VAMOS A COMPARAR "+"["+ficheros[x]+","+DatatypeConverter.printHexBinary(digest)+"]_CON_"+cadena);
				        	 	if(cadena.equals("["+ficheros[x]+","+DatatypeConverter.printHexBinary(digest)+"]")){
				        	 		//System.out.println("NO HA CAMBIADO EL HASH");
				        	 		//System.out.println("\n");
				        	 		control=true;
				        	 		break;
				        	 	}else{
				        	 		control=false;
				        	 		
				        	 	}
				        
			            }
				        
				        b1.close();
				        if(control==true){
				        	String z = "no ha cambiado el hash del fichero: "+fichero+"\n";
				        	System.out.println(z);
			        		//EscribeIncidencia.escribeIncidencia(z, "SUCCESS");
			        	}else{
			        		
			        		String y = "HA CAMBIADO EL HASH del fichero : "+fichero+", han modificado el archivo generando el hash: "+DatatypeConverter.printHexBinary(digest)+"\n";
			        		System.out.println(y);
			        		EscribeIncidencia.escribeIncidencia(y, "FAILED");
				        
			        	}

					}catch(java.io.FileNotFoundException e){
						comprobarHash(dirInicial+metodos.compruebaSys()+ficheros[x],huella,dirHash,nHash);
					}
		
				}
			}
	}
}
