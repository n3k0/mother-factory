package net.psybit.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * For use in pojo generation code
 * @author n3k0
 *
 */
public class SentenceWithParams {

	private String sentence;
	private boolean byConstructor;
	private List<Object> parameters = new ArrayList<>(0);

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public boolean isByConstructor() {
		return byConstructor;
	}

	public void setByConstructor(boolean byConstructor) {
		this.byConstructor = byConstructor;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SentenceWithParams [");
		if (sentence != null)
			builder.append("sentence=").append(sentence).append(", ");
		builder.append("byConstructor=").append(byConstructor).append(", ");
		if (parameters != null)
			builder.append("parameters=").append(parameters);
		builder.append("]");
		return builder.toString();
	}

	public Object[] getParameters() {
		return parameters.toArray(new Object[0]);
	}

	public void addParameters(Object... objects) {
		for (Object object : objects) {
			this.parameters.add(object);
		}
	}

	public void setParameter(int index, Object object) {
		this.parameters.add(index, object);
	}

	public void addParameters(List<Object> objects) {
		this.parameters.addAll(objects);
	}
}