package avro.generator.common;

public class Checked {
	@FunctionalInterface
	public interface BiConsumer<TInputA, TInputB> {
		void accept(TInputA inputA, TInputB inputB) throws Exception;
	}

	@FunctionalInterface
	public interface TriConsumer<TInputA, TInputB, TInputC> {
		void accept(TInputA inputA, TInputB inputB, TInputC inputc) throws Exception;
	}

	@FunctionalInterface
	public interface Function<TInput, TOutput> {
		TOutput apply(TInput input) throws Exception;
	}

	@FunctionalInterface
	public interface BiFunction<TInputA, TInputB, TOutput> {
		TOutput apply(TInputA inputA, TInputB inputB) throws Exception;
	}

	@FunctionalInterface
	public interface TriFunction<TInputA, TInputB, TInputC, TOutput> {
		TOutput apply(TInputA inputA, TInputB inputB, TInputC inputC) throws Exception;
	}
}
