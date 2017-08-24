package avro.generator.common;

@FunctionalInterface
public interface checkedBiConsumer<TInputA, TInputB> {
	void accept(TInputA inputA, TInputB inputB) throws Exception;
}
