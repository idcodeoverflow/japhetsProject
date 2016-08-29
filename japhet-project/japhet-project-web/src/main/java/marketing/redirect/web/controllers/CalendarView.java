package marketing.redirect.web.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean
public class CalendarView {
         
    private Date inlineDate;
         
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
     
    public void click() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
         
        requestContext.update("uploadContentForm:display");
        requestContext.execute("PF('dlg').show()");
    }
 
    public Date getInlineDate() {
        return inlineDate;
    }
 
    public void setInlineDate(Date inlineDate) {
        this.inlineDate = inlineDate;
    }
 
}
