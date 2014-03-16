package ai.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jdistlib.Normal;

import org.springframework.stereotype.Component;

import Jama.Matrix;
import ai.domain.DoubleLayerPerceprton;

import com.googlecode.wickedcharts.highcharts.options.series.Point;

@Component
public class NeuralNetworkService {

	public final int POINTS_COUNT = 400;
	private Map<Point, Integer> points;
	private DoubleLayerPerceprton perceptron;
	
	private void initPoints(){
		points = new HashMap<Point, Integer>();
		Normal n = new Normal(100, 20);
		
		for(int i = 0; i < POINTS_COUNT / 4; i++) {
			points.put(new Point(n.random(), n.random()), 0);
		}
		n = new Normal(150, 20);
		for(int i = 0; i < POINTS_COUNT / 4; i++) {
			points.put(new Point(n.random(), n.random()), 1);
		}
		n = new Normal(200, 20);
		for(int i = 0; i < POINTS_COUNT / 4; i++) {
			points.put(new Point(n.random(), n.random()), 2);
		}
		n = new Normal(250, 20);
		for(int i = 0; i < POINTS_COUNT / 4; i++) {
			points.put(new Point(n.random(), n.random()), 3);
		}
	}

	public void init() { 
		initPoints();
		perceptron = new DoubleLayerPerceprton(2, 3, 2);
		learn();
		exam();
	}
	
	private void learn(){
		System.out.println("learning");
		for(int i = 0; i < 1000; i++) {
			int j = 0;
			for(Entry<Point, Integer> e : points.entrySet()) {
				if(j++ % 2 == 0 ) {
					System.out.println(learn(e.getKey(), e.getValue()));
				}
			}
		}
	}
	
	private void exam(){
		System.out.println("examing");
		for(Point p : points.keySet()) {
			System.out.println(""+ exam(p) + "\t " + points.get(p) + (points.get(p) != exam(p)? "\t fail" : ""));
		}
	}
	
	private Matrix encodeMatrix(Point p){
		Matrix res = new Matrix(1,2);
		res.set(0, 0, p.getX().doubleValue()/400);
		res.set(0, 1, p.getY().doubleValue()/420);
		return res;
	}
	
	private Matrix encodeMatrix(int realClass){
		Matrix res = new Matrix(1,2);
		res.set(0, 0, (realClass >= 2)? 1 : 0);
		res.set(0, 1, realClass % 2);
		return res;
//		Matrix res = new Matrix(1,1); // for 2 classes
//		res.set(0, 0, realClass);
//		return res;
	}
	
	private int decodeMatrix(Matrix classs) {
		int res = 0;
		if(classs.get(0, 0) > 0.5) {
			res += 2;
		}
		if(classs.get(0, 1) > 0.5) {
			res += 1;
		}
		return res;
//		int res = 0; // for 2 classes
//		if(classs.get(0, 0) > 0.5) {
//			res += 1;
//		}
//		return res;
		
	} 
	
	private int exam(Point p){
		return decodeMatrix(perceptron.exam(encodeMatrix(p)));
	}
	
	public double learn(Point p, int realCLass) {
		return perceptron.learn(encodeMatrix(p), encodeMatrix(realCLass));
	}
	
	public List<Point> getPoints(){
		return Arrays.asList(points.keySet().toArray(new Point[0]));
	}
}