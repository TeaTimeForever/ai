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

public class HomePage extends WebPage {
	
	@Inject
	private PlotService plotService;
	
	@Inject
	private GeneticalService geneticalService;
	private Model<Options> options;
	private Model<Integer> bestFitness;
	
	public HomePage() throws Exception {
		options = new Model<Options>(plotService.initOptionsForLab1(500, geneticalService.POPULATION_SIZE, geneticalService.GEN_COUNT));
		bestFitness = new Model<Integer>(geneticalService.fitness(geneticalService.getBestChromosome()));
		
		add(new ChartContainer("chartContainer", options));
		add(new Label("fitness", bestFitness));
		add(new NextStepsLink("nextStep", 1));
		add(new NextStepsLink("next100Step", 100));
			
	}
	
	private class NextStepsLink extends AjaxLink<String> {
		private int stepsCount;

		public NextStepsLink(String id, int stepsCount) {
			super(id);
			this.stepsCount = stepsCount;
		}

		@Override
		public void onClick(AjaxRequestTarget target) {
			for(int i = 0; i < stepsCount; i++) {
				geneticalService.getNewPopulation();
			}
			Options newOptions = options.getObject();
			newOptions.clearSeries();
			newOptions.addSeries(plotService.prepareCongestionPlacesSeries());
			newOptions.addSeries(plotService.prepareChromosomeSeries());
			
			options.setObject(newOptions);
			bestFitness.setObject(geneticalService.fitness(geneticalService.getBestChromosome()));
			target.add(HomePage.this);
		}
	}
}
