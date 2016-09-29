package japhet.sales.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import japhet.sales.model.impl.SocialNetwork;

@Local
public interface ISocialNetwork extends Serializable {
	
	public List<SocialNetwork> getAllSocialNetworks();
	
	public List<SocialNetwork> getAllAvailableSocialNetworks();
	
}
