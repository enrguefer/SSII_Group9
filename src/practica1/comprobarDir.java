package practica1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

public class comprobarDir{

	public static Map<String,Integer>comprobarHash(String dirInicial, String huella, String dirHash,String nHash,
			int hashSuccess, int hashFailed,List<Integer>aux,String orgDir,
			Boolean semaforoKPI, int mensual,List<Integer>aux2,SecretKey key,
			Map<String,Integer>almacenK,Map<String,Float>almacenRatio
			,Integer contKPID,Integer contKPIM)throws NoSuchAlgorithmException, IOException, 
			InvalidKeyException, NoSuchPaddingException,ClassNotFoundException{
			
			MessageDigest algorithm=MessageDigest.getInstance(huella);
			
			if(key!=null)
				metodos.descifrar(key, dirHash,nHash);	//desciframos para poder visualizar correctamente
			
			File dir = new File(dirInicial);
			File dirlog=new File(dirHash+metodos.compruebaSys()+nHash);
			String[] ficheros = dir.list();
			Boolean existencia=false;	//probar existencia de fichero, por si se ha borrado.
			
			if (ficheros == null){
				  System.out.println("No hay ficheros en el directorio especificado");
				
			}else {
				for (int x=0;x<ficheros.length;x++){
					
					if(aux.size()!=0) {		//ambos solucionan el problema del almacen e inicialización a cero
						if(aux.get(0)>hashSuccess) {
							hashSuccess=aux.get(0);
						}
					}if(aux2.size()!=0) {
						if(aux2.get(0)>hashFailed)
							hashFailed=aux2.get(0);
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
				        	//String z = "no ha cambiado el hash del fichero: "+fichero+"\n";
				        	hashSuccess++;
				        	existencia=true;
			        		//EscribeIncidencia.escribeIncidencia(z, "SUCCESS");
			        	}else{
			        		existencia=true;
			        		String y = "HA CAMBIADO EL HASH del fichero : "+fichero+", han modificado el archivo generando el hash: "+DatatypeConverter.printHexBinary(digest)+"\n";
			        		//System.out.println(y);
			        		hashFailed++;
			        		EscribeIncidencia.escribeIncidencia(y, "FAILED",dirHash);
				        
			        	}
				        if(!existencia) {
				        	String y = "SE HA PERDIDO UN FICHERO: "+fichero+", cuyo hash era: "+DatatypeConverter.printHexBinary(digest)+"\n";
				        	EscribeIncidencia.escribeIncidencia(y, "FAILED",dirHash);
				        	existencia=false;
				        	lecturaDirs.leeDirectorios(dirInicial,dirHash,nHash,huella);
				        }

					}catch(java.io.FileNotFoundException e){
						almacenK=comprobarHash(dirInicial+metodos.compruebaSys()+ficheros[x],huella,dirHash,nHash,hashSuccess,
								hashFailed,aux,orgDir,semaforoKPI,mensual,aux2,key,almacenK,almacenRatio,contKPID,contKPIM);
					}
					
				}
				
			}
			aux.add(hashSuccess);
			aux2.add(hashFailed);
    		almacenK.put("-", hashFailed);
    		almacenK.put("+", hashSuccess);
			
			if(dirInicial.equals(orgDir) && !semaforoKPI){
				int totalFicheros =hashFailed+hashSuccess;
				/*System.out.println("Nº Success: "+hashSuccess);
				System.out.println("Nº Failed: "+hashFailed);
				System.out.println("Nº Files: "+totalFicheros);*/
				float ratio=KpiCalculator.CalculaKPI(totalFicheros,nHash,dirHash,almacenK,contKPID);
				almacenRatio.put("*", ratio);
				almacenRatio.put("total", almacenRatio.get("total")+(float)1);
				
				if(mensual==30)
					KpiCalculator.calculaKPIMensual(nHash, dirHash,almacenRatio,contKPIM);
			}
			return almacenK;
	}
}
