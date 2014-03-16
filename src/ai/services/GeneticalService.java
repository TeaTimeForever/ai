package ai.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jdistlib.Gamma;
import jdistlib.Normal;
import jdistlib.Uniform;

import org.springframework.stereotype.Component;

import com.googlecode.wickedcharts.highcharts.options.series.Point;

@Component
public class GeneticalService {

	private Random random;
	private List<Point> congestionPlaces;
	private List<List<Point>> currentPopulation;
	
	public final int CONGESTION_POINTS_COUNT = 500;
	public final int GEN_COUNT = 5;
	public final int POPULATION_SIZE = 50;
	public final int MAX_MUTATIONS_COUNT_PER_ITERATION = 1;
	public final int MAX_CROSSOVER_COUNT_PER_ITERATION = POPULATION_SIZE * 70 / 100;
	
	public List<Point> getCongestionPlaces(){
		return congestionPlaces;
	}
	
	public List<List<Point>> getPopulation(){
		return currentPopulation;
	}
	
	private List<Point> initCongestionPlaces(){
		Gamma g = new Gamma(7, 15);
		Normal n = new Normal(10, 10);
		Uniform u = new Uniform(0, 500);
		
		List<Point> points = new ArrayList<Point>();
		for(int i=0; i<CONGESTION_POINTS_COUNT/2; i++) {
			points.add(new Point(g.random(), u.random()));
		}
		for(int i=0; i<CONGESTION_POINTS_COUNT/2; i++) {
			points.add(new Point(n.random(), u.random()));
		}
		return points;
	}
	
	public void init(){
		random = new Random();
		congestionPlaces = initCongestionPlaces();
		currentPopulation = initPopulation(POPULATION_SIZE, GEN_COUNT);
	}
	
	public List<List<Point>> initPopulation(int populationSize, int countOfgens){
		Uniform u = new Uniform(0, 500);
		List<List<Point>> population = new ArrayList<List<Point>>();
		for(int i=0; i<populationSize; i++) {
			List<Point> chromosome = new ArrayList<Point>();
			for(int j=0; j<countOfgens; j++) {
				chromosome.add(new Point(u.random(), u.random()));
			}
			population.add(chromosome);
		}
		return population;
	}
	
	public List<List<Point>> getNewPopulation(){
		List<List<Point>> newPopulation = crossover(currentPopulation);
		newPopulation = mutation(newPopulation);
		currentPopulation = selection(newPopulation);
		return currentPopulation;
	}
	
	/**
	 * create randomize mutations (from 0 to 5)
	 * @param population
	 * @return changed population
	 */
	private List<List<Point>> mutation(List<List<Point>> population){
		for(int i=0; i<getRandomIndex(MAX_MUTATIONS_COUNT_PER_ITERATION); i++){
			int randomCh = getRandomIndex(population.size()-1);
			List<Point> randomChromosome = population.get(randomCh);
			randomChromosome.set(getRandomIndex(randomChromosome.size()-1), getNewPoint());
			population.set(randomCh, randomChromosome);
		}
		return population;
	}
	
	/**
	 * increase chromosomes count
	 * @param population - current population
	 * @return increase count of chromosomes
	 */
	private List<List<Point>> crossover(List<List<Point>> population){
		List<List<Point>> modified = new ArrayList<List<Point>>();
		for(int i=0; i < getRandomIndex(MAX_CROSSOVER_COUNT_PER_ITERATION); i++) {
			int randomCh1 = getRandomIndex(population.size()-1);
			int randomCh2 = getRandomIndex(population.size()-1);
			List<Point> chromosome1 = population.get(randomCh1);
			List<Point> chromosome2 = population.get(randomCh2);
			List<Point> child = new ArrayList<Point>();
			
			for(int j=0; j < chromosome1.size(); j++){
				child.add(new Point(chromosome1.get(j).getX(), chromosome2.get(j).getY()));
			}
			modified.add(child);
		}
		modified.addAll(population);
		return modified;
	}
	
	private Point getNewPoint(){
		return new Point(getRandomIndex(500), getRandomIndex(500));
	}
	
	private int getRandomIndex(int maxSize){
		return (int) (random.nextFloat() * maxSize);
	}
	
	/**
	 * 
	 * @param chromosome
	 * @return ATTENTION: the lower the better
	 */
	public int fitness(List<Point> chromosome){
		int fitnessSize = 0;
		for(Point p : congestionPlaces) {
			List<Double> distances = new ArrayList<Double>();
			for(Point gen : chromosome) {
				distances.add(distance(p, gen));
			}
			fitnessSize += minInList(distances);
		}
		return fitnessSize;
	}
	
	/**
	 * @param p1 - point from congestion array 
	 * @param gen - point from chromosome
	 * @return distance between points by Pythagoras
	 */
	private double distance(Point p1, Point gen){
		double a = p1.getX().doubleValue() - gen.getX().doubleValue();
		double b = p1.getY().doubleValue() - gen.getY().doubleValue();
		return Math.pow(Math.pow(a, 2) + Math.pow(b, 2), 0.5) ;
	}
	
	private double minInList(List<Double> numbs) {
		double min = numbs.get(0);
		for(Double n : numbs) {
			if(n < min) {min = n;}
		}
		return min;
	}
	
	public List<Point> getBestChromosome(){
		List<Point> best = currentPopulation.get(0);
		for(List<Point> chromosome : currentPopulation) {
			if(fitness(chromosome) < fitness(best)) {
				best = chromosome;
			}
		}
		return best;
	}
	
	private List<List<Point>> selection(List<List<Point>> modifiedPopulation){
		List<List<Point>> population = new ArrayList<List<Point>>();
		while(population.size() < POPULATION_SIZE) {
			List<Point> candidateChromosome1 = modifiedPopulation.get(getRandomIndex(modifiedPopulation.size()-1));
			List<Point> candidateChromosome2 = modifiedPopulation.get(getRandomIndex(modifiedPopulation.size()-1));
			
			if(fitness(candidateChromosome1) < fitness(candidateChromosome2)) {
				population.add(candidateChromosome1);
				modifiedPopulation.remove(candidateChromosome1);
			} else {
				population.add(candidateChromosome2);
				modifiedPopulation.remove(candidateChromosome2);
			}
		}
		return population;
	}
}