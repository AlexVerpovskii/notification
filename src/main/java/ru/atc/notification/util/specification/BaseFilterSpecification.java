package ru.atc.notification.util.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.atc.notification.util.filter.BaseFilter;

import java.io.Serializable;

public abstract class BaseFilterSpecification<Filter extends BaseFilter,
		SpecificationT extends Serializable>
		implements FilterSpecification<Filter, SpecificationT> {

	public static <SpecificationT> Specification<SpecificationT>
	has(String field, Object value) {
		return (root, query, cb) -> cb.equal(root.get(field), value);
	}
}