/**
 * 
 */
package japhet.sales.mailing.service.impl;

import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import japhet.sales.except.TemplateReaderException;
import japhet.sales.mailing.MailingTemplates;
import japhet.sales.mailing.service.ITemplateReaderService;

/**
 * @author David Israel Garcia Alcazar
 *
 */
@LocalBean
@Stateless
public class TemplateReaderService implements ITemplateReaderService {

	/**
	 * Maven generated.
	 */
	private static final long serialVersionUID = 4947879728701632298L;

	/**
	 * This method looks for the specified template into the 
	 * templates directory and replaces the paramters by its values.
	 */
	public String readTemplate(MailingTemplates mailingTemplates, Map<String, Object> params) 
			throws TemplateReaderException {
		StringBuilder fileContent = new StringBuilder();
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream(mailingTemplates.getPath());
		Scanner sc = new Scanner(inputStream);
		//Read file contents
		while(sc.hasNext()){
			fileContent.append(sc.nextLine());
			fileContent.append("\n");
		}
		sc.close();
		//Set parameter values
		String strContent = fileContent.toString();
		for(String key : params.keySet()) {
			strContent = strContent.replaceAll(key, params.get(key).toString());
		}
		return strContent;
	}
}
