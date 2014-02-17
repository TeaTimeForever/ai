package ai.pages;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebPage;

import ai.services.PlotService;

import com.googlecode.wickedcharts.wicket6.highcharts.Chart;

public class HomePage extends WebPage {
	
	@Inject
	private PlotService plotService;
	
	public HomePage() throws Exception {
		add(new Chart("chart", plotService.getOptionsForLab1(500, 50, 5)));
	}
}
