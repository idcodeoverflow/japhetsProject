package japhet.sales.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.except.BusinessServiceException;
import japhet.sales.service.IRandomPasswordService;

/**
 * 
 * @author David Israel Garcia Alcazar
 *
 */
@Startup
@Singleton
public class RandomPasswordService implements IRandomPasswordService {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = -3735853385222437503L;

	@Inject
	private Logger logger;
	
	//Logic attributes
	private List<Character> characters;
	
	@PostConstruct
	private void init() {
		try {
			final String INFO_MSG = "Initializing RandomPasswordService...";
			logger.info(INFO_MSG);
			characters = new ArrayList<>();
			//Add characters from A - Z
			this.addCharactersToList('A', 'Z', characters);
			//Add some special characters
			characters.add('$');
			characters.add('.');
			characters.add('-');
			characters.add('_');
			characters.add('/');
			characters.add('#');
			//Add characters from a - z
			this.addCharactersToList('a', 'z', characters);
			//Add numbers from 0 - 9
			this.addCharactersToList('0', '9', characters);
		} catch(Exception e) {
			final String ERROR_MSG = "An error has occurred while initializing RandomPasswordService.";
			logger.fatal(ERROR_MSG, e);
		}
	}
	
	/* (non-Javadoc)
	 * @see japhet.sales.service.IRandomPasswordService#generateRandomPassword(short)
	 */
	@Override
	public String generateRandomPassword(final short LENGTH) throws BusinessServiceException {
		StringBuilder strBuildr = new StringBuilder();
		char character = '0';
		short randomIndex = 0;
		final short LIST_SIZE = (short)characters.size();
		for(short index = 0; index < LENGTH; index++) {
			randomIndex = (short) ThreadLocalRandom.current().nextInt(0, LIST_SIZE);
			character = characters.get(randomIndex);
			strBuildr.append(character);
		}
		return strBuildr.toString();
	}
	
	/**
	 * 
	 * @param start initial character
	 * @param end last character
	 * @param characters Mutable List to be modified
	 * @throws Exception
	 */
	private final void addCharactersToList(char start, char end, List<Character> characters) 
			throws Exception {
		for(char c = start; c <= end; c++) {
			characters.add(c);
		}
	}
}
