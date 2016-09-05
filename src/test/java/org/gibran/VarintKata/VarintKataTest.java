package org.gibran.VarintKata;
import static org.junit.Assert.*;

import org.junit.Test;

public class VarintKataTest {

	@Test
	public void shouldReturnVarint00000001ForInt1 () {
		VarintKata varint = new VarintKata();
		String result = varint.calculateForInt(1);
		assertEquals("00000001", result); 
	}
	
	@Test
	public void shouldReturnVarint00000010ForInt2 () {
		VarintKata varint = new VarintKata();
		String result = varint.calculateForInt(2);
		assertEquals("00000010", result); 
	}
	
	@Test
	public void shouldReturnVarint1010110000000010ForInt300 () {
		VarintKata varint = new VarintKata();
		String result = varint.calculateForInt(300);
		assertEquals("1010110000000010", result); 
	}
	
	@Test
	public void shouldReturnVarint101000001000110100000110ForInt100000  () {
		VarintKata varint = new VarintKata();
		String result = varint.calculateForInt(100000);
		assertEquals("101000001000110100000110", result); 
	}
	
	@Test
	public void shouldReturnInt1ForVarint00000001 () {
		VarintKata varint = new VarintKata();
		Integer result = varint.calculateForVarInt("00000001");
		assertTrue(1 == result); 
	}
	
	@Test
	public void shouldReturnInt2ForVarint00000010 () {
		VarintKata varint = new VarintKata();
		Integer result = varint.calculateForVarInt("00000010");
		assertTrue(2 == result); 
	}
	
	@Test
	public void shouldReturnInt300ForVarint1010110000000010 () {
		VarintKata varint = new VarintKata();
		Integer result = varint.calculateForVarInt("1010110000000010");
		assertTrue(300 == result); 
	}	
	
	@Test
	public void shouldReturnInt100000ForVarint101000001000110100000110() {
		VarintKata varint = new VarintKata();
		Integer result = varint.calculateForVarInt("101000001000110100000110");
		assertTrue(100000 == result); 
	}	
}
