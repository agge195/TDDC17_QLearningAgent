public class StateAndReward {

	private static double maxAngle = 2.0;
	private static double minAngle = -2.0;
	private static int nrAngles = 10;
	
	private static double maxdiff = 1.0;
	
	private static double maxvy = 2.5;
	private static double minvy = -1.0;
	private static int nrvy = 5;
	
	private static double maxvx = 1.5;
	private static double minvx = -2.0;
	private static int nrvx = 3;
	
	// vx - velocity x
	//vy - velocity y
	
	/* State discretization function for the angle controller */
	public static String getStateAngle(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */		
		int angleState = discretize(angle, nrAngles, minAngle, maxAngle);		
		
		String state = Integer.toString(angleState);
		//System.out.println("state: " + state);
		return state;
	}

	/* Reward function for the angle controller */
	public static double getRewardAngle(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */

		if (angle >= Math.abs(maxAngle))
		{
			return 0;
		}
		
		double reward = 20/Math.pow(20, Math.abs(angle));

		return reward;

	}
	
	public static double getRewardVelocity(double vx, double vy)
	{
		double diff = Math.abs(vx) - Math.abs(vy);
		
		//double bonus = 10/Math.pow(10, Math.abs(diff));
		
		double reward = 0;
		
		double testVY = 20/Math.pow(20, Math.abs(vy));
		
		double testVX = 20/Math.pow(20, Math.abs(vx));
		
//		System.out.println("vx: " + vx);
		// 0-60 score
		
		if(Math.abs(vy) >= maxvy || Math.abs(vx) >= maxvx)
			return 0;
		
//		if(diff <= maxdiff)
//			reward = 60/Math.pow(60, Math.abs(diff));
		
		reward = testVX + testVY;
		
	//System.out.println("rewardV: " + reward);
		return reward;	

	}
	
	/* State discretization function for the full hover controller */
	public static String getStateHover(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */
		int vxState = discretize(vx, nrvx, minvx, maxvx);
		int vyState = discretize(vy, nrvy, minvy, maxvy);
		
		String angleState = getStateAngle(angle, vx, vy);
		
		String state = "Angle: " + angleState + " vx: " 
		+ Integer.toString(vxState) + " vy: " + Integer.toString(vyState);
		
		//System.out.println(state);
		
		return state;
	}

	/* Reward function for the full hover controller */
	public static double getRewardHover(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */
		//double totDiff = angle - vx - vy;	
		double angleReward = getRewardAngle(angle, vx, vy);
		double velReward = getRewardVelocity(vx, vy);
		
		double reward = angleReward + angleReward*velReward;
		
		
		System.out.println("total reward: " + reward);
		return reward;
	}

	// ///////////////////////////////////////////////////////////
	// discretize() performs a uniform discretization of the
	// value parameter.
	// It returns an integer between 0 and nrValues-1.
	// The min and max parameters are used to specify the interval
	// for the discretization.
	// If the value is lower than min, 0 is returned
	// If the value is higher than min, nrValues-1 is returned
	// otherwise a value between 1 and nrValues-2 is returned.
	//
	// Use discretize2() if you want a discretization method that does
	// not handle values lower than min and higher than max.
	// ///////////////////////////////////////////////////////////
	public static int discretize(double value, int nrValues, double min,
			double max) {
		
		if (nrValues < 2) {
			return 0;
		}

		double diff = max - min;

		if (value < min) {
			return 0;
		}
		if (value > max) {
			return nrValues - 1;
		}

		double tempValue = value - min;
		double ratio = tempValue / diff;

		return (int) (ratio * (nrValues - 2)) + 1;
	}

	// ///////////////////////////////////////////////////////////
	// discretize2() performs a uniform discretization of the
	// value parameter.
	// It returns an integer between 0 and nrValues-1.
	// The min and max parameters are used to specify the interval
	// for the discretization.
	// If the value is lower than min, 0 is returned
	// If the value is higher than min, nrValues-1 is returned
	// otherwise a value between 0 and nrValues-1 is returned.
	// ///////////////////////////////////////////////////////////
	public static int discretize2(double value, int nrValues, double min,
			double max) {
		double diff = max - min;

		if (value < min) {
			return 0;
		}
		if (value > max) {
			return nrValues - 1;
		}

		double tempValue = value - min;
		double ratio = tempValue / diff;

		return (int) (ratio * nrValues);
	}

}
