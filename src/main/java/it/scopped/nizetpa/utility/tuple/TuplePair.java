package it.scopped.nizetpa.utility.tuple;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TuplePair<T, J> {
	private T first;
	private J second;
}