package ai.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.googlecode.wickedcharts.highcharts.options.Axis;
import com.googlecode.wickedcharts.highcharts.options.ChartOptions;
import com.googlecode.wickedcharts.highcharts.options.Marker;
import com.googlecode.wickedcharts.highcharts.options.Options;
import com.googlecode.wickedcharts.highcharts.options.PlotOptions;
import com.googlecode.wickedcharts.highcharts.options.PlotOptionsChoice;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;
import com.googlecode.wickedcharts.highcharts.options.State;
import com.googlecode.wickedcharts.highcharts.options.StatesChoice;
import com.googlecode.wickedcharts.highcharts.options.Symbol;
import com.googlecode.wickedcharts.highcharts.options.Title;
import com.googlecode.wickedcharts.highcharts.options.color.HexColor;
import com.googlecode.wickedcharts.highcharts.options.color.RgbaColor;
import com.googlecode.wickedcharts.highcharts.options.series.Point;
import com.googlecode.wickedcharts.highcharts.options.series.PointSeries;

@Component
public class PlotService {
	
	@Inject
	private GenerationService generationService;
	
	private ChartOptions getChartOption(){
		ChartOptions option = new ChartOptions();
		option
			.setDefaultSeriesType(SeriesType.SCATTER)
			.setBorderWidth(1)
			.setBorderColor(HexColor.fromString("#cccccc"))
			.setMarginLeft(110)
			.setMarginRight(50)
			.setBackgroundColor(HexColor.fromString("#eeeeee"))
	        .setPlotBackgroundColor(HexColor.fromString("#ffffff"));		
	        return option;
	}
	
	private PlotOptionsChoice getPlotOptionChoice() {
		PlotOptionsChoice option = new PlotOptionsChoice();
		option.setSeries(new PlotOptions().setShadow(Boolean.FALSE));
		return option;
	}
	
	private PointSeries getPointSeries(List<Point> points) {
		PointSeries pointSeries = new PointSeries();
		for(Point p : points) {
			pointSeries.addPoint(p);
		}
		
		return pointSeries;
		
	}
	
	public Options getOptionsForLab1(int placesCount, int populationCount, int seed) {
		Options options = new Options();
		options.setTitle(new Title("Karina Pilyushonoka #1"));
		options.setChartOptions(getChartOption());
		options.setPlotOptions(getPlotOptionChoice());
		
		options.setyAxis(new Axis()
        	.setLineColor(HexColor.fromString("#999999"))
        	.setLineWidth(1)
        	.setTickColor(HexColor.fromString("#666666"))
        	.setTickLength(3)
        	.setGridLineColor(HexColor.fromString("#dddddd"))
        	.setTitle(new Title("Y Axis")
            .setRotation(0)
            .setMargin(50)));
		
		options.setxAxis(new Axis()
	        .setMinPadding(0.75f)
	        .setMaxPadding(0.75f)
	        .setLineColor(HexColor.fromString("#999999"))
	        .setLineWidth(1)
	        .setTickColor(HexColor.fromString("#666666"))
	        .setTickLength(3)
	        .setTitle(new Title("X Axis")));
		
		options.addSeries(getPointSeries(
				generationService.generateCongestionPlaces(placesCount))
				.setMarker(new Marker()
					.setSymbol(new Symbol(Symbol.PredefinedSymbol.CIRCLE))
					.setRadius(1)
		            .setFillColor(new RgbaColor(24, 90, 169, 0.5f))
		            .setLineColor(new RgbaColor(24, 90, 169, 0.75f))
		            .setLineWidth(1)
		            .setColor(new RgbaColor(24, 90, 169, 1f))
		            .setStates(new StatesChoice().setHover(new State().setEnabled(Boolean.FALSE)))));
		
		options.addSeries(getPointSeries(
				generationService.initPopulation(populationCount, seed).get(0))
				.setMarker(new Marker()
					.setSymbol(new Symbol(Symbol.PredefinedSymbol.CIRCLE))
					.setRadius(2)
		            .setFillColor(new RgbaColor(238, 46, 47, 0.5f))
		            .setLineColor(new RgbaColor(238, 46, 47, 0.75f))
		            .setLineWidth(1)
		            .setColor(new RgbaColor(238, 46, 47, 1f))
		            .setStates(new StatesChoice().setHover(new State().setEnabled(Boolean.FALSE)))));
		
		return options;
	}
 

}
