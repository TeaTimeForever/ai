package ai.services;

import java.util.ArrayList;
import java.util.List;

import jdistlib.Normal;

import org.springframework.stereotype.Component;

import com.googlecode.wickedcharts.highcharts.options.series.Point;

@Component
public class NeuralNetworkService {

	public final int POINTS_COUNT = 400;
	
	private List<Point> initPoints(){
		List<Point> points = new ArrayList<Point>();
		Normal n = new Normal(120, 50);
		
		for(int i = 0; i < POINTS_COUNT / 4; i++) {
			points.add(new Point(n.random(), n.random()));
		}
		n = new Normal(240, 50);
		for(int i = 0; i < POINTS_COUNT / 4; i++) {
			points.add(new Point(n.random(), n.random()));
		}
		n = new Normal(360, 50);
		for(int i = 0; i < POINTS_COUNT / 4; i++) {
			points.add(new Point(n.random(), n.random()));
		}
		n = new Normal(480, 50);
		for(int i = 0; i < POINTS_COUNT / 4; i++) {
			points.add(new Point(n.random(), n.random()));
		}
		return points;
	}

	public void init() {
		initPoints();
		
	}
}