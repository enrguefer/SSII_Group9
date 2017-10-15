package practica1;

import java.lang.management.*;

public class pid {

   public static int pid() {
	   String id = ManagementFactory.getRuntimeMXBean().getName();
	   String[] ids = id.split("@");
	   return Integer.parseInt(ids[0]);
    }
	
}
