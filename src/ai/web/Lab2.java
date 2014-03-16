package ai.web;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

import ai.services.GeneticalService;
import ai.services.PlotService;
import ai.web.panels.ChartContainer;

import com.googlecode.wickedcharts.highcharts.options.Options;

public class Lab2 extends WebPage {
	
	@Inject
	private PlotService plotService;
	
	@Inject
	private GeneticalService geneticalService;
	private Model<Options> options;
	
	public Lab2() throws Exception {
//		options = new Model<Options>(plotService.);
		
		add(new ChartContainer("chartContainer", options));
	}
}
