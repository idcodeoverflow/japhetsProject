package japhet.sales.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import japhet.sales.except.BusinessServiceException;
import japhet.sales.service.IUtilService;

@LocalBean
@Stateless
public class UtilService implements IUtilService {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -6308913582271833776L;
	
	private final int END_OF_STREAM = -1;

	@Override
	public byte[] getBiteArrayFromStream(InputStream is) 
			throws BusinessServiceException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			int reads = is.read();
			while(reads != END_OF_STREAM){
				baos.write(reads);
				reads = is.read();
			}
		} catch (Exception e) {
			throw new BusinessServiceException(
					"Error converting the bytes array into a stream.", e);
		}
		return baos.toByteArray();
	}

}
