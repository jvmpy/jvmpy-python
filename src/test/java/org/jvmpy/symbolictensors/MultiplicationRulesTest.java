package org.jvmpy.symbolictensors;

import org.junit.Assert;
import org.junit.Test;

import static org.jvmpy.python.Python.tuple;

public class MultiplicationRulesTest {
	
	@Test
	public void testA() {
		
		Size first = new Size(new Size(3), new Size(4)).names_(tuple("3","4"));
		Size second = new Size(new Size(4), new Size(5)).names_(tuple("4","5"));
		
		Size[] results = MultiplicationRules.matmul(first, second);
		Size result = results[results.length - 1];
		
		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.dimensions().length);
		Assert.assertEquals((int)3, (int)result.get(0));
		Assert.assertEquals((int)5, (int)result.get(1));
	}

	@Test
	public void testB() {
		
		Size first = new Size(new Size(3, 1), new Size(4,5)).names_(tuple("3", "1", "4","5"));
		Size second = new Size(new Size(4), new Size(5, 6)).names_(tuple("4","5", "6"));

		Size[] results = MultiplicationRules.matmul(first, second);
		Size result = results[results.length - 1];

		Assert.assertEquals(4, result.dimensions().length);
		
		Assert.assertNotNull(result);
		Assert.assertEquals((int)3, (int)result.get(0));
		Assert.assertEquals((int)4, (int)result.get(1));
		Assert.assertEquals((int)4, (int)result.get(2));
		Assert.assertEquals((int)6, (int)result.get(3));
	}
	
	@Test
	public void testC() {
		
		Size first = new Size(new Size(3, 1, 4), new Size(5)).names_(tuple("3", "1", "4","5"));
		Size second = new Size(new Size(1), new Size(4, 5, 6)).names_(tuple("1", "4","5", "6"));

		Size[] results = MultiplicationRules.matmul(first, second);
		Size result = results[results.length - 1];

		Assert.assertEquals(4, result.dimensions().length);
		
		Assert.assertNotNull(result);
		Assert.assertEquals((int)3, (int)result.get(0));
		Assert.assertEquals((int)4, (int)result.get(1));
		Assert.assertEquals((int)4, (int)result.get(2));
		Assert.assertEquals((int)6, (int)result.get(3));
	}

}
