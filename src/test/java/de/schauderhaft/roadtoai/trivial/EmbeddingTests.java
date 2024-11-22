package de.schauderhaft.roadtoai.trivial;

import org.junit.jupiter.api.Test;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@SpringBootTest(classes = DemoApplication.class)
class EmbeddingTests {

	@Autowired
	EmbeddingModel model;

	@Test
	void obtainEmbeddingModel() {

		float[] zwerg = model.embed("Zwerg");
		float[] dwarf = model.embed("dwarf");
		float[] qm = model.embed("quantum mechanics");
		float[] garbage = model.embed("aivrpasa;voni");

		System.out.println(Arrays.toString(zwerg));

		int n = 5;
		System.out.println(maxN(zwerg, n));
		System.out.println(maxN(dwarf, n));
		System.out.println(maxN(qm, n));
		System.out.println(maxN(garbage, n));

		System.out.println();

		System.out.println("zwerg - dwarf: " + distance(zwerg, dwarf));
		System.out.println("zwerg - qm: " + distance(zwerg, qm));
		System.out.println("zwerg - garbage: " + distance(zwerg, garbage));
		System.out.println("dwarf - qm: " + distance(dwarf, qm));
		System.out.println("dwarf - garbage: " + distance(dwarf, garbage));
		System.out.println("qm - garbage: " + distance(qm, garbage));
	}

	Map<Integer, Float> maxN(float[] input, int n) {
		Set<KeyValue> keyValues = new HashSet<>();

		for (int i = 0; i < input.length; i++) {
			keyValues.add(new KeyValue(i, input[i]));
		}

		Map<Integer, Float> result = new TreeMap<>();
		keyValues.stream()
				.sorted(Comparator.comparing(kv -> -Math.abs(kv.value)))
				.limit(n)
				.forEach(
						kv -> result.put(kv.key, kv.value)
				);
		return result;
	}

	float distance(float[] a, float[] b) {
		float sum = 0;
		for (int i = 0; i < a.length; i++) {
			sum += a[i]*b[i];
		}
		return sum;
	}

	record KeyValue(Integer key, Float value) {

	}
}
