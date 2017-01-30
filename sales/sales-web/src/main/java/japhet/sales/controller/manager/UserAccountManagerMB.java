package japhet.sales.controller.manager;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import japhet.sales.controller.GenericMB;
import japhet.sales.internationalization.IInternationalizationService;
import japhet.sales.service.IUserService;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

@ManagedBean
@ViewScoped
public class UserAccountManagerMB extends GenericMB {

	/**
	 * Maven generated
	 */
	private static final long serialVersionUID = -5288053365897420779L;
	
	@Inject
	private Logger logger;
	
	//EJB's
	@EJB
	private IUserService userService;
	
	@EJB
	private IInternationalizationService internationalizationService;
	
	//Validation properties
	private final int MAX_MEDIA_SIZE = 30000;
	
	@PostConstruct
	private void init() {
		try {
			logger.info("Initializing user account manager...");
		} catch (Exception e) {
			logger.error("Error while initializing user account manager.", e);
			showErrorMessage(internationalizationService
					.getI18NMessage(CURRENT_LOCALE, getSTARTUP_MB_ERROR()), "");
		}
	}
	
	public List<String> getDeposits() {
		List<String> deposits = new ArrayList<>();
		deposits.add("182646");
		deposits.add("182878");
		deposits.add("182656");
		deposits.add("182686");
		return deposits;
	}

	public int getMAX_MEDIA_SIZE() {
		return MAX_MEDIA_SIZE;
	}

	public BarChartModel getModel() {

		BarChartModel model = new BarChartModel();

		ChartSeries chartSeries = new ChartSeries();

		chartSeries.setLabel("payback");
		chartSeries.set("Ene", 850);
		chartSeries.set("Feb", 1500);
		chartSeries.set("Mar", 1024);
		chartSeries.set("Abr", 1850);
		chartSeries.set("May", 1224);
		chartSeries.set("Jun", 1411);
		chartSeries.set("Jul", 976);
		chartSeries.set("Ago", 1621);
		chartSeries.set("Sep", 1995);
		chartSeries.set("Oct", 1358);
		chartSeries.set("Nov", 1987);
		chartSeries.set("Dic", 1521);

		model.addSeries(chartSeries);

		//model.setSeriesColors("000000");

		Axis xAxis = model.getAxis(AxisType.X);

		xAxis.setLabel("Meses");

		Axis yAxis = model.getAxis(AxisType.Y);

		yAxis.setLabel("Payback");

		yAxis.setMin(0);
		yAxis.setMax(2000);

		return model;

	}
}
