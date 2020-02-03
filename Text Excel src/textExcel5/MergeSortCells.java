package textExcel5;

import java.util.Arrays;

// a merge sort algorithm using cell objects
public class MergeSortCells
{	
	// recursively splits the array of cells into halves until there are a set of single value arrays
	public static void mergeSort(Cell[] numbers)
	{
		int size = numbers.length;
		if (size > 1) {
			Cell[] halfOne = Arrays.copyOfRange(numbers, 0, (numbers.length/2));  
			Cell[] halfTwo = Arrays.copyOfRange(numbers, (numbers.length/2), numbers.length);

			mergeSort(halfOne);
			mergeSort(halfTwo);
			merge(halfOne, halfTwo, numbers);  
		}
	}

	// merge two cell arrays together
	private static void merge(Cell[] halfOne, Cell[] halfTwo, Cell[] numbers) {
		int index1 = 0; // index of halfOne array
		int index2 = 0; // index of halfTwo array

		for(int i = 0; i < numbers.length; i++) {
			// if first array cells value is less than the second array value then set the single cell array to the first array value
			if (index2 >= halfTwo.length || index1 < halfOne.length 
					&& Double.parseDouble(halfOne[index1].getDisplayValue()) 
					<= Double.parseDouble(halfTwo[index2].getDisplayValue())) {   

				numbers[i] = halfOne[index1]; //set the array's value at the index to a sorted value
				index1++;  // go to next halfOne array value
			}
			// if first array cells value is less than the second array value then set the single cell array to the second array value
			else { 
				numbers[i] = halfTwo[index2]; //set the array's value at the index to a sorted value
				index2++; // go to next halfTwo array value
			}
		}
	}


	public static void main(String[] args) {
	}
}