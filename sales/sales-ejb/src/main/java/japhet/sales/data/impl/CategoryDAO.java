package japhet.sales.data.impl;

import javax.ejb.Stateless;

import japhet.sales.data.GenericDAO;
import japhet.sales.model.impl.Category;

@Stateless
public class CategoryDAO extends GenericDAO<Category, Short>{

	public CategoryDAO(Class<Category> type, Class<Short> key) {
		super(type, key);
	}
	
}
