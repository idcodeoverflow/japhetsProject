package japhet.sales.service;

import java.io.InputStream;
import java.io.Serializable;

import javax.ejb.Local;

@Local
public interface IUtilService extends Serializable {
	
	byte[] getBiteArrayFromStream(InputStream is) throws Exception;
	
	
}
