package ai.services;

import java.util.ArrayList;
import java.util.List;

import jdistlib.Gamma;
import jdistlib.Normal;
import jdistlib.Uniform;

import org.springframework.stereotype.Component;

import com.googlecode.wickedcharts.highcharts.options.series.Point;

@Component
public class GenerationService {
	
	// http://www.vosesoftware.com/ModelRiskHelp/
	public List<Point> generateCongestionPlaces(int count){
		Gamma g = new Gamma(7, 15);
		Normal n = new Normal(10, 10);
		Uniform u = new Uniform(0, 500);
		
		List<Point> points = new ArrayList<Point>();
		for(int i=0; i<count/2; i++) {
			points.add(new Point(g.random(), u.random()));
		}
		for(int i=0; i<count/2; i++) {
			points.add(new Point(n.random(), u.random()));
		}
		return points;
	}
	
	public List<List<Point>> initPopulation(int count, int seed){
		Uniform u = new Uniform(0, 500);
		List<List<Point>> population = new ArrayList<List<Point>>();
		for(int i=0; i<count; i++) {
			List<Point> chromosome = new ArrayList<Point>();
			for(int j=0; j<seed; j++) {
				chromosome.add(new Point(u.random(), u.random()));
			}
		}
		return population;
	}
}