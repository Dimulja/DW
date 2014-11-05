package dw;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;



public class Filewriter {

	public Writer getWriter(String fileName){
		
		Writer fw = null;

		try
		{
		  fw = new FileWriter( fileName );
		//  fw.write( "Zwei Jäger treffen sich..." );
		 // fw.append( System.getProperty("\n") ); // e.g. "\n"
		}
		catch ( IOException e ) {
		  System.err.println( "Konnte Datei nicht erstellen" );
		}
		finally {
		  if ( fw != null )
		    try { fw.close(); } catch ( IOException e ) { e.printStackTrace(); }
		}
		
		return fw;
		
	}
	


}
