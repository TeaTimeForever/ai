package ai.web.panels;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.googlecode.wickedcharts.highcharts.options.Options;
import com.googlecode.wickedcharts.wicket6.highcharts.Chart;

public class ChartContainer extends Panel {

	public ChartContainer(String id, Model<Options> optionsModel) {
		super(id, optionsModel);
		add(new Chart("chart", optionsModel.getObject()));
	}
}
