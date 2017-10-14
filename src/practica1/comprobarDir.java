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
import java.util.List;

import javax.xml.bind.DatatypeConverter;

public class comprobarDir{

	public static void comprobarHash(String dirInicial, String huella, String dirHash,String nHash,
			int hashSuccess, int hashFailed,List<Integer>aux,String orgDir,
			Boolean semaforoKPI, int mensual) throws NoSuchAlgorithmException, IOException{
			//System.out.println("DIRECTORIO : " +dirInicial);
			
			MessageDigest algorithm=MessageDigest.getInstance(huella);//CREAMOS LA HUELLA SHA-256
			File dir = new File(dirInicial);
			File dirlog=new File(dirHash+metodos.compruebaSys()+nHash);
			String[] ficheros = dir.list();
			
			//KpiCalculator.recogeValores(hashFailed, hashSuccess);
			
			if (ficheros == null){
				  System.out.println("No hay ficheros en el directorio especificado");
				
			}else {
				//System.out.println("ficheros: "+ficheros.length);
				for (int x=0;x<ficheros.length;x++){
					System.out.println(hashSuccess);
					if(aux.size()!=0) {
						if(aux.get(0)>hashSuccess) {
							hashSuccess=aux.get(0);
						}
					}
					
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

				        	 	//System.out.println("VAMOS A COMPARAR "+"["+ficheros[x]+","+DatatypeConverter.printHexBinary(digest)+"]_CON_"+cadena);
				        	 	if(cadena.equals("["+ficheros[x]+","+DatatypeConverter.printHexBinary(digest)+"]")){
				        	 		//System.out.println("NO HA CAMBIADO EL HASH");
				        	 		//System.out.println("\n");
				        	 		control=true;
				        	 		break;
				        	 	}else
				        	 		control=false;
			            }
				        
				        b1.close();
				        if(control){
				        	String z = "no ha cambiado el hash del fichero: "+fichero+"\n";
				        	//System.out.println(z);
				        	hashSuccess++;
				        	//System.out.println("Nº Success2: "+hashSuccess);
			        		//EscribeIncidencia.escribeIncidencia(z, "SUCCESS");
			        	}else{
			        		
			        		String y = "HA CAMBIADO EL HASH del fichero : "+fichero+", han modificado el archivo generando el hash: "+DatatypeConverter.printHexBinary(digest)+"\n";
			        		//System.out.println(y);
			        		hashFailed++;
			        		EscribeIncidencia.escribeIncidencia(y, "FAILED");
				        
			        	}

					}catch(java.io.FileNotFoundException e){
						//System.out.println("RECURSIVIDAD HASHSUCCESS: "+hashSuccess);
						comprobarHash(dirInicial+metodos.compruebaSys()+ficheros[x],huella,dirHash,nHash,hashSuccess,
								hashFailed,aux,orgDir,semaforoKPI,mensual);
					}
					
				}
				
			}aux.add(hashSuccess);
			//int totalFicheros = cuentaLineasFichero.cuentaLineas(dirHash, nHash);
			
			if(dirInicial.equals(orgDir) && !semaforoKPI){
				int totalFicheros =hashFailed+hashSuccess;
				System.out.println("Nº Success: "+hashSuccess);
				System.out.println("Nº Failed: "+hashFailed);
				System.out.println("Nº Files: "+totalFicheros);
				KpiCalculator.CalculaKPI(hashSuccess,hashFailed, totalFicheros,nHash,dirHash);
				
				if(mensual==30)
					KpiCalculator.CalculaKPI(hashSuccess,hashFailed, totalFicheros,nHash,dirHash);
			}
			
	}
}
