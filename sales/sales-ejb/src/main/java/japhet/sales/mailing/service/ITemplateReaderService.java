/**
 * 
 */
package japhet.sales.mailing.service;

import java.io.Serializable;
import java.util.Map;

import javax.ejb.Local;

import japhet.sales.except.TemplateReaderException;
import japhet.sales.mailing.MailingTemplates;

/**
 * @author David Israel Garcia Alcazar
 *
 */
@Local
public interface ITemplateReaderService extends Serializable {

	public String readTemplate(MailingTemplates mailingTemplates, Map<String, Object> params) 
			throws TemplateReaderException;
}
