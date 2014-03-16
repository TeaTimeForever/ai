package ai.domain;

import Jama.Matrix;

public class DoubleLayerPerceprton {
	private Matrix input;
	private Matrix inputbias;
	private Matrix w1;
	private Matrix w1bias;
	private Matrix w2;
	private Matrix w2bias;
	private Matrix hiddenNeuralLayer;
	private Matrix hiddenNeuralLayerbias;
	private Matrix output;
	
	private final double sigmoidCoeff = 1;
	private final double learnCoeff = 1;
	
	private Matrix secondLayerError;
	//                               n                     k                        m
	public DoubleLayerPerceprton(int inputParamsCount, int firstLayerNeurCount, int outputLayerNeurCount) {
		w1bias = Matrix.random(inputParamsCount+1, firstLayerNeurCount);
		w1 = cutMatrix(w1bias);
		
		w2bias = Matrix.random(firstLayerNeurCount+1, outputLayerNeurCount);
		w2 = cutMatrix(w2bias);
	}
	
	private Matrix cutMatrix(Matrix from) {
		return from.getMatrix(0, from.getRowDimension()-2, 0, from.getColumnDimension()-1);
	}
	
	private Matrix completeInputMatrix(Matrix from) {
		Matrix to = new Matrix(1, from.getColumnDimension()+1);
		int i;
		for(i= 0; i< from.getColumnDimension(); i++) {
			to.set(0, i, from.get(0, i));
		}
		to.set(0, i, 1);
		return to;
	}
	
	public Matrix exam(Matrix input) {
		this.input = input;
		propogate();
		return output;
	}
	
	public double learn(Matrix input, Matrix estOutput){
		this.input = input;
		propogate();
		secondLayerError = output.minus(estOutput).transpose();
		backPropogate();
		return secondLayerError.transpose().times(secondLayerError).get(0, 0);
	}
	
	private void backPropogate(){
		Matrix d2 = new Matrix(output.getColumnDimension(), output.getColumnDimension());
		for(int i =0; i< output.getColumnDimension(); i++) {
			d2.set(i, i, output.get(0, i)*(1-output.get(0, i)));
		}
		Matrix d1 = new Matrix(hiddenNeuralLayer.getColumnDimension(), hiddenNeuralLayer.getColumnDimension());
		for(int i =0; i< hiddenNeuralLayer.getColumnDimension(); i++) {
			d1.set(i, i, hiddenNeuralLayer.get(0, i)*(1-hiddenNeuralLayer.get(0, i)));
		}
		
		Matrix delta2 = d2.times(secondLayerError);
		Matrix delta1 = d1.times(w2).times(delta2);
		
		Matrix deltaW1bias = delta1.times(inputbias).times(-learnCoeff).transpose();
		Matrix deltaW2bias = delta2.times(hiddenNeuralLayerbias).times(-learnCoeff).transpose();

		w1bias = w1bias.plus(deltaW1bias);
		w2bias = w2bias.plus(deltaW2bias);
		
		w1 = cutMatrix(w1bias);
		w2 = cutMatrix(w2bias);
	}
	
	private void propogate() {
		inputbias = completeInputMatrix(input);
		hiddenNeuralLayer = f(inputbias.times(w1bias));
		hiddenNeuralLayerbias = completeInputMatrix(hiddenNeuralLayer);
		output = f(hiddenNeuralLayerbias.times(w2bias));
	}
	
	private double f(double inputParam) {
		return 1/(1+ Math.exp(-sigmoidCoeff*inputParam));
	}
	
	private Matrix f(Matrix inputVect) {
		Matrix res = new Matrix(inputVect.getRowDimension(), inputVect.getColumnDimension());
		for(int i=0; i< res.getRowDimension(); i++){
			for(int j=0; j< res.getColumnDimension(); j++){
				res.set(i, j, f(inputVect.get(i, j)));
			}
		}
		return res;
	}
}
