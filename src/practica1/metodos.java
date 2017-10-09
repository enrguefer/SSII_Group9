package practica1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.DigestInputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class metodos {
	
	@SuppressWarnings("resource")
	public static String compruebaSys() throws FileNotFoundException {
		@SuppressWarnings("unused")
		FileInputStream fis=null;
		try {
			fis = new FileInputStream("directorioArchivos\\");
			return "\\";
		}catch(Exception e){
			return "/";
		}
		
	}
	
	public static void cifraAES(File log,File log2) throws NoSuchAlgorithmException, 
			IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeyException, IOException, InvalidKeySpecException {
		
		//KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
	    //keyGenerator.init(128);
	    //Key key = keyGenerator.generateKey(); 	//hasta aquí todo de acuerdo
	   // SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		
		InputStreamReader leer_clave = new InputStreamReader(System.in);
        BufferedReader buff_clave = new BufferedReader(leer_clave);
        System.out.print("Escriba una clave de 16 caracteres: ");
        String clave = buff_clave.readLine();
        
       /* InputStreamReader opt = new InputStreamReader(System.in);
        BufferedReader buff_opt = new BufferedReader(opt);
        System.out.print("¿Cifrar o descifrar? (c/d): ");
        String op = buff_opt.readLine();
        */

	       SecretKeySpec kspec = new SecretKeySpec(clave.getBytes(),0,16,"AES");
//	       SecretKey ks = skf.generateSecret(kspec);
		
		
	    //key = new SecretKeySpec("una clave de 16 bytes".getBytes(),  0, 16, "AES");
	    
	    Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");	//correcto
	    
	    aes.init(Cipher.ENCRYPT_MODE, kspec);	//modification
	    
	    //aes.init(Cipher.DECRYPT_MODE, kspec);
	   // byte[] desencriptado = aes.doFinal(encriptado);
	    
	    //lectura de ficheros----
	    InputStream archivo = new FileInputStream(log);
        OutputStream fich_out = new FileOutputStream (log2);
	    
        byte[] buffer = new byte[1024];
        byte[] bloque_cifrado;
        String textoCifrado = new String();
        int fin_archivo = -1;
        int leidos;//numero de bytes leidos
     
        leidos = archivo.read(buffer);
     
        while( leidos != fin_archivo ) {
           bloque_cifrado = aes.update(buffer,0,leidos);
           textoCifrado = textoCifrado + new String(bloque_cifrado,"ISO-8859-1");
           leidos = archivo.read(buffer);         
        }
        
        archivo.close();
        
        bloque_cifrado = aes.doFinal();
        textoCifrado = textoCifrado + new String(bloque_cifrado,"ISO-8859-1");
        //ISO-8859-1 es ISO-Latin-1
     
        fich_out.write(textoCifrado.getBytes("ISO-8859-1"));//escribir fichero
     
        
        //----------------------
        
        
	   /* byte[] encriptado = aes.doFinal(log);
	      
	    for (byte b : encriptado)
	    	System.out.print(Integer.toHexString(0xFF & b));
	      		
	    System.out.println();*/
	/*      
	    aes.init(Cipher.DECRYPT_MODE, key);
	    byte[] desencriptado = aes.doFinal(encriptado);
	    
	    System.out.println(new String(desencriptado));*/
	}
	
	
}