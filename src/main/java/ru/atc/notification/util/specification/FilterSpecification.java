package ru.atc.notification.util.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.atc.notification.util.filter.BaseFilter;

import java.io.Serializable;

public interface FilterSpecification<Filter extends BaseFilter,
		SpecificationT extends Serializable> {

	Specification<SpecificationT> getSpecification(Filter filter);
}