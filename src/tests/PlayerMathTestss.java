package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import utils.PlayerMath;

public class PlayerMathTestss {

	
	@Test
	public void test() {
		/*
		 * 
		 */
		final long micros = 1942841224;
		double micToMin = PlayerMath.microToMin(micros);
		double micToSec = PlayerMath.microToSec(micros);
		double delta = 0.001;
		
		assertEquals(22.841224, micToSec,delta);
		assertEquals(32.38068706667, micToMin,delta);
		
		/*
		 * 
		 */
		final long currentMicros = 19216;
		final long lengthMicros = 25000;
		int presentege = PlayerMath.timeProgress(lengthMicros, currentMicros);
		System.out.println(presentege);
		assertEquals(76, presentege,delta);
		
	}

}
