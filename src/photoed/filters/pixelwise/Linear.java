package photoed.filters.pixelwise;

import java.util.stream.IntStream;

public class Linear extends Pixelwise{
	public Linear(double[][] matrixElements){
		super( (r,g,b) -> IntStream.range(0,3).mapToDouble( i -> 
			matrixElements[i][0]*r +
			matrixElements[i][1]*g +
			matrixElements[i][2]*b
		).toArray());
	}
	public Linear(double[][] inbasis,double[][] outbasis){
		this( multiply(
			transpose(outbasis),
			invert(transpose(inbasis))
		) );
	}

	private static double[][] multiply(double[][] A,double[][] B){
		double[][] out = new double[3][3];

		for (int i = 0; i < 3; i++) { // Row
		    for (int j = 0; j < 3; j++) { // bcolumn
			for (int k = 0; k < 3; k++) { // acolumn
			    out[i][j] += A[i][k] * B[k][j];
			}
		    }
		}

		return out;
	}
	private static double[][] invert(double[][] in){
		double[][] out = new double[3][3];

		double d = (
			+in[0][0]*in[1][1]*in[2][2]
			+in[1][0]*in[0][2]*in[2][1]
			+in[2][0]*in[0][1]*in[1][2]
			-in[2][0]*in[0][2]*in[1][1]
			-in[1][0]*in[0][1]*in[2][2]
			-in[0][0]*in[1][2]*in[2][1]
		);
		double t10 =  ( in[1][1]*in[2][2] -in[1][2]*in[2][1] )/d;
		double t11 = -( in[0][1]*in[2][2] -in[0][2]*in[2][1] )/d;
		double t12 =  ( in[0][1]*in[1][2] -in[0][2]*in[1][1] )/d;
		double t20 = -( in[1][0]*in[2][2] -in[2][0]*in[1][2] )/d;
		double t21 =  ( in[0][0]*in[2][2] -in[2][0]*in[0][2] )/d;
		double t22 = -( in[0][0]*in[1][2] -in[1][0]*in[0][2] )/d;
		double t30 =  ( in[1][0]*in[2][1] -in[2][0]*in[1][1] )/d;
		double t31 = -( in[0][0]*in[2][1] -in[2][0]*in[0][1] )/d;
		double t32 =  ( in[0][0]*in[1][1] -in[1][0]*in[0][1] )/d;


		out[0][0] = t10; out[0][1] = t11; out[0][2] = t12;
		out[1][0] = t20; out[1][1] = t21; out[1][2] = t22;
		out[2][0] = t30; out[2][1] = t31; out[2][2] = t32;
		return out;
	}
	private static double[][] transpose(double[][] in){
		double[][] out = new double[3][3];
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				out[i][j] = in[j][i];
			}
		}
		return out;
	}
}

