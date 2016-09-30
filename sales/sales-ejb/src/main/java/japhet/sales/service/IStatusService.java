package japhet.sales.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import japhet.sales.model.impl.Status;

@Local
public interface IStatusService extends Serializable {

	public List<Status> getAllAvailableStatus();
	
	public List<Status> getAllStatus();
	
}
