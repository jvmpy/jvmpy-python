package org.jvmpy.symbolictensors;

import static org.jvmpy.python.Python.tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jvmpy.python.Tuple;
import org.jvmpy.symbolictensors.Size;

/**
 *  Prototype SizeMatcher - all the ugly code re: size matching in one class to be refactored - TODO
 */
public class SizeMatcher {

	public static boolean isSizeMatch(Size first, Size second) {
		List<Size> firsts = getAllAlternates(first);
		List<Size> seconds = getAllAlternates(second);
		for (int f = 0; f < firsts.size(); f++) {
			for (int s = 0; s < seconds.size(); s++) {
				boolean directDimensionsMatch = isDirectDimensionsMatch(first, second);
				if (directDimensionsMatch) {
					if (isDirectDimensionsMatch(first, second)) {
						if (isDirectNamesMatch(first, second)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private static boolean isDirectDimensionsMatch(Size first, Size second) {
		return IntStream.of(first.dimensions()).boxed().collect(Collectors.toList()).equals(IntStream.of(first.dimensions()).boxed().collect(Collectors.toList()));
	}

	private static boolean isDirectNamesMatch(Size first, Size second) {
		List<String> firstNames = toScopeIndependentNamesList(first.dimensionNames().asList());
		List<String> secondNames = toScopeIndependentNamesList(second.dimensionNames().asList());

		if (firstNames.size() == secondNames.size()) {
			removeNones(firstNames, secondNames);
		}

		boolean match = firstNames.equals(secondNames);
		if (!match) {

			if (secondNames.size() >= 2 && secondNames.get(0).equals("example") && firstNames.equals(Arrays.asList("example", "feature"))) {
				return true;
			} else if (firstNames.size() >= 2 && firstNames.get(0).equals("example") && secondNames.equals(Arrays.asList("example", "feature"))) {
				return true;
			} else if (secondNames.size() >= 2 && secondNames.get(0).equals("feature") && firstNames.equals(Arrays.asList("feature", "example"))) {
				return true;
			} else if (firstNames.size() >= 2 && firstNames.get(0).equals("feature") && secondNames.equals(Arrays.asList("feature", "example"))) {
				return true;
			} else if (secondNames.size() >= 2 && secondNames.get(0).equals("feature") && firstNames.equals(Arrays.asList("feature", "feature"))) {
				return true;
			} else if (firstNames.size() >= 2 && firstNames.get(0).equals("feature") && secondNames.equals(Arrays.asList("feature", "feature"))) {
				return true;
			}
			return false;
		} else {
			return true;
		}
	}

	private static void removeNones(List<String> firstNames, List<String> secondNames) {
		List<String> firsts = new ArrayList<>();
		List<String> seconds = new ArrayList<>();

		for (int i = 0; i < firstNames.size(); i++) {
			if (!firstNames.get(i).equals("None") && !secondNames.get(i).equals("None")) {
				firsts.add(firstNames.get(i));
				seconds.add(secondNames.get(i));
			}
		}
		firstNames.clear();
		secondNames.clear();
		firsts.addAll(firstNames);
		seconds.addAll(secondNames);
	}

	private static List<String> toScopeIndependentNamesList(List<String> strings) {
		List<String> returnValues = new ArrayList<>();
		for (String s : strings) {
			s = s.replaceAll("input_", "");
			s = s.replaceAll("output_", "");
			returnValues.add(s);
		}

		return returnValues;
	}

	private static List<Size> getAllAlternates(Size size) {
		return populateAllAlternates(size, new ArrayList<>());
	}

	private static List<Size> populateAllAlternates(Size size, List<Size> allAlternates) {
		allAlternates.add(size);
		for (Size s : size.getAlternates()) {
			populateAllAlternates(s, allAlternates);
		}
		return allAlternates;
	}





}
