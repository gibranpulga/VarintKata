package org.gibran.VarintKata;

public class VarintKata {

	public String calculateForInt(int number) {
		int numberOfByteBlocks = getNumberOfBytesBlocksNeededForNumber(number);
		// keeping the bits on an array of "bit arrays" of 7 positions, this way
		// is easier to insert the MSBs later
		// using char for storing the bits for it´s facility
		char[][] arrayOfBitsArray = new char[numberOfByteBlocks][7];

		// index to know the current "bits array"
		int currentBitsArray = 0;
		for (int i = 0, j = Integer.toBinaryString(number).toCharArray().length - 1; i < numberOfByteBlocks * 7; i++, j--) {
			// if the current "bits array" is already full (7 bits) move to the
			// next
			if (!checkcurrentBitsArrayHasAvailablePosition(arrayOfBitsArray[currentBitsArray])) {
				currentBitsArray++;
			}

			char currentBit = '0';
			// if there is no more bits for the bits representation of number,
			// fill with 0 until the end of the "bits array"
			if (i > Integer.toBinaryString(number).toCharArray().length - 1) {
				currentBit = '0';
			} else {
				// using j as bits must come first at the end of the "bits
				// array", and if they aren´t enough to fill it, the left side
				// must be filled with 0
				currentBit = Integer.toBinaryString(number).toCharArray()[j];
			}

			// checking next empty position on the current "bits array"
			arrayOfBitsArray[currentBitsArray][checkNextEmptyPosition(
					arrayOfBitsArray[currentBitsArray])] = currentBit == '1' ? '1' : '0';
		}

		// joinning the "bits arrays" together and setting the MSBs
		char[] formattedBits = formatToOneDimensionalArrayAndInsertMSBs(arrayOfBitsArray, numberOfByteBlocks);

		// transforming the result to String
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < formattedBits.length; i++) {
			result.append(formattedBits[i]);
		}

		return result.toString();
	}

	// check next empty position on the current "bits arrays"
	private Integer checkNextEmptyPosition(char[] charArray) {
		for (int i = charArray.length - 1; i >= 0; i--) {
			if (charArray[i] == 0) {
				return i;
			}
		}
		return null;
	}

	// check if "bits arrays" still has available positions
	private boolean checkcurrentBitsArrayHasAvailablePosition(char[] charArray) {
		for (int i = charArray.length - 1; i >= 0; i--) {
			if (charArray[i] == 0) {
				return true;
			}
		}
		return false;
	}

	// group "bits arrays" into one and insert MSBs
	private char[] formatToOneDimensionalArrayAndInsertMSBs(char[][] bits, int numberOfByteBlocks) {
		char[] newBits = new char[numberOfByteBlocks * 8];

		int currentBit = 0;
		for (int i = 0; i < bits.length; i++) {
			for (int j = 0; j < bits[i].length; j++) {
				// setting the MSBs
				if (currentBit == 0 || (currentBit >= 8 && currentBit % 8 == 0)) {
					// Last block will always have MSB 0, other bytes 1
					if (currentBit == (numberOfByteBlocks - 1) * 8) {
						newBits[currentBit] = '0';
					} else {
						newBits[currentBit] = '1';
					}
					j--;
				} else {
					newBits[currentBit] = bits[i][j];
				}
				currentBit++;
			}
		}
		return newBits;
	}

	// get the number of byte blocks needed for the number
	public int getNumberOfBytesBlocksNeededForNumber(int number) {
		int numberOfByteBlocks = 0;
		int numberOfBitsNeeded = Integer.toBinaryString(number).length();

		if (numberOfBitsNeeded < 7) {
			numberOfByteBlocks = 1;
		} else {
			numberOfByteBlocks += numberOfBitsNeeded / 7;
		}

		// sometimes there are extra bits but less than 7 exactly, in this case
		// we still need another block, for instance for 19, 20 or 21 bits we
		// need 3 blocks, for 22 4 blocks, and so on
		if (numberOfBitsNeeded > 7 && numberOfBitsNeeded % 7 >= 1) {
			numberOfByteBlocks++;
		}

		return numberOfByteBlocks;
	}

	// calculate from varInt (as String) to int
	public Integer calculateForVarInt(String varInt) {
		int numberOfByteBlocks = varInt.length() / 8;
		Integer decimalNumber;
		if (numberOfByteBlocks == 1) {
			decimalNumber = Integer.parseInt(varInt, 2);
		} else {
			StringBuilder sb = new StringBuilder();

			int currentBlock = numberOfByteBlocks - 1;
			// starting as 1 to skip the MSB
			int currentBlockIndex = 1;
			// using different method from the other wat (int to varint), since don´t need to set MSB
			for (int i = 0; i < (numberOfByteBlocks * 8); i++) {
				// when currentBlockIndex arrives 8, reset it since this is a MSB and also begin the next block (backwards since it´s least revelant bit first)
				if (currentBlockIndex == 8) {
					currentBlockIndex = 1;
					currentBlock--;
				}
				// if the bit of the varint is the first (0) or a multiple of 8 then is the MSB and we should skip it
				if (i == 0 || (i >= 8 && i % 8 == 0)) {
				//	continue;
				} else {
					// simple formula to grab the bits from the 2nd one to the last of the current block
					sb.append(varInt.charAt((currentBlock * 8) + currentBlockIndex));
					currentBlockIndex++;
				}
			}
			decimalNumber = Integer.parseInt(sb.toString(), 2);
		}

		return decimalNumber;
	}

}
