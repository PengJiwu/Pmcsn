package rng;/* --------------------------------------------------------------------------
 * This is a Java library for generating random variates from six discrete 
 * distributions
 *
 *      Generator         Range (x)     Mean         Variance
 *
 *      bernoulli(p)      x = 0,1       p            p*(1-p)
 *      binomial(n, p)    x = 0,...,n   n*p          n*p*(1-p)
 *      equilikely(a, b)  x = a,...,b   (a+b)/2      ((b-a+1)*(b-a+1)-1)/12
 *      Geometric(p)      x = 0,...     p/(1-p)      p/((1-p)*(1-p))
 *      pascal(n, p)      x = 0,...     n*p/(1-p)    n*p/((1-p)*(1-p))
 *      poisson(m)        x = 0,...     m            m
 * 
 * and seven continuous distributions
 *
 *      uniform(a, b)     a < x < b     (a + b)/2    (b - a)*(b - a)/12 
 *      exponential(m)    x > 0         m            m*m
 *      erlang(n, b)      x > 0         n*b          n*b*b
 *      normal(m, s)      all x         m            s*s
 *      logNormal(a, b)   x > 0            see below
 *      chiSquare(n)      x > 0         n            2*n 
 *      student(n)        all x         0  (n > 1)   n/(n - 2)   (n > 2)
 *
 * For the a Lognormal(a, b) random variable, the mean and variance are
 *
 *                        mean = exp(a + 0.5*b*b)
 *                    variance = (exp(b*b) - 1) * exp(2*a + b*b)
 *
 * Name              : Rvgs.java  (Random Variate GeneratorS)
 * Authors           : Steve Park & Dave Geyer
 * Translated by     : Richard Dutton & Jun Wang
 * Language          : Java
 * Latest Revision   : 7-1-04
 * --------------------------------------------------------------------------
 */

import java.math.*;
import java.io.*;

public class Rvgs{
    
    /* Barry Lawson 8 Nov 2007 */
    //
    // There is no good way to use Rvgs along with selectStream because the
    // original implementation had the Rvgs constructor create its own copy
    // of an Rngs object.
    //
    // Instead, force the Rvgs constructor to have an already-created Rngs
    // object.

    Rngs rngs; 
    
//    public Rvgs(){
//   	  rngs = new Rngs();
//    }

    public Rvgs(Rngs givenRngs)
    {
      try {
        if (givenRngs == null)
         throw new NullPointerException();
      }
      catch(Exception e) {
        System.out.println("Rvgs constructor requires non-null argument");
      }
      rngs = givenRngs;

    }
    /* Barry Lawson 8 Nov 2007 */
    
    public long bernoulli(double p)
/* ========================================================
 * Returns 1 with probability p or 0 with probability 1 - p. 
 * NOTE: use 0.0 < p < 1.0                                   
 * ========================================================
 */ 
    {
	return ((rngs.random() < (1.0 - p)) ? 0 : 1);
    }

    public long binomial(long n, double p)
/* ================================================================ 
 * Returns a binomial distributed integer between 0 and n inclusive. 
 * NOTE: use n > 0 and 0.0 < p < 1.0
 * ================================================================
 */
    { 
	long i, x = 0;
	
	for (i = 0; i < n; i++)
	    x += bernoulli(p);
	return (x);
    }
    
    public long equilikely(long a, long b)
/* ===================================================================
 * Returns an equilikely distributed integer between a and b inclusive. 
 * NOTE: use a < b
 * ===================================================================
 */
    {
	return (a + (long) ((b - a + 1) * rngs.random()));
    }

    public long geometric(double p)
/* ====================================================
 * Returns a geometric distributed non-negative integer.
 * NOTE: use 0.0 < p < 1.0
 * ====================================================
 */
    {
	return ((long) (Math.log(1.0 - rngs.random()) / Math.log(p)));
    }

    public long pascal(long n, double p)
/* ================================================= 
 * Returns a Pascal distributed non-negative integer. 
 * NOTE: use n > 0 and 0.0 < p < 1.0
 * =================================================
 */
    { 
	long i, x = 0;

	for (i = 0; i < n; i++)
	    x += geometric(p);
	return (x);
    }

    public long poisson(double m)
/* ================================================== 
 * Returns a Poisson distributed non-negative integer. 
 * NOTE: use m > 0
 * ==================================================
 */
    { 
	double t = 0.0;
	long   x = 0;
	
	while (t < m) {
	    t += exponential(1.0);
	    x++;
	}
	return (x - 1);
    }

    public double uniform(double a, double b)
/* =========================================================== 
 * Returns a uniformly distributed real number between a and b. 
 * NOTE: use a < b
 * ===========================================================
 */
    { 
	return (a + (b - a) * rngs.random());
    }

    public double exponential(double m)
/* =========================================================
 * Returns an exponentially distributed positive real number. 
 * NOTE: use m > 0.0
 * =========================================================
 */

	{
	return (-m * Math.log(1.0 - rngs.random()));
    }

    public double erlang(long n, double b)
/* ================================================== 
 * Returns an Erlang distributed positive real number.
 * NOTE: use n > 0 and b > 0.0
 * ==================================================
 */
    { 
	long   i;
	double x = 0.0;
	
	for (i = 0; i < n; i++) 
	    x += exponential(b);
	return (x);
    }
    
    public double normal(double m, double s)
/* ========================================================================
 * Returns a normal (Gaussian) distributed real number.
 * NOTE: use s > 0.0
 *
 * Uses a very accurate approximation of the normal idf due to Odeh & Evans, 
 * J. Applied Statistics, 1974, vol 23, pp 96-97.
 * ========================================================================
 */
    { 
	final double p0 = 0.322232431088;     final double q0 = 0.099348462606;
	final double p1 = 1.0;                final double q1 = 0.588581570495;
	final double p2 = 0.342242088547;     final double q2 = 0.531103462366;
	final double p3 = 0.204231210245e-1;  final double q3 = 0.103537752850;
	final double p4 = 0.453642210148e-4;  final double q4 = 0.385607006340e-2;
	double u, t, p, q, z;
	
	u   = rngs.random();
	if (u < 0.5)
	    t = Math.sqrt(-2.0 * Math.log(u));
	else
	    t = Math.sqrt(-2.0 * Math.log(1.0 - u));
	p   = p0 + t * (p1 + t * (p2 + t * (p3 + t * p4)));
	q   = q0 + t * (q1 + t * (q2 + t * (q3 + t * q4)));
	if (u < 0.5)
	    z = (p / q) - t;
	else
	    z = t - (p / q);
	return (m + s * z);
    }

    public double logNormal(double a, double b)
/* ==================================================== 
 * Returns a lognormal distributed positive real number. 
 * NOTE: use b > 0.0
 * ====================================================
 */
    {
	return (Math.exp(a + b * normal(0.0, 1.0)));
    }

    public double chiSquare(long n)
/* =====================================================
 * Returns a chi-square distributed positive real number. 
 * NOTE: use n > 0
 * =====================================================
 */
    { 
	long   i;
	double z, x = 0.0;
	
	for (i = 0; i < n; i++) {
	    z  = normal(0.0, 1.0);
	    x += z * z;
	}
	return (x);
    }

    public double student(long n)
/* =========================================== 
 * Returns a student-t distributed real number.
 * NOTE: use n > 0
 * ===========================================
 */
    {
	return (normal(0.0, 1.0) / Math.sqrt(chiSquare(n) / n));
    }



	public double streamExponential(double m, int stream)
/* =========================================================
 * Returns an exponentially distributed positive real number.
 * NOTE: use m > 0.0
 * =========================================================
 */
	{
		rngs.selectStream(stream);
		return (-m * Math.log(1.0 - rngs.random()));
	}
}
