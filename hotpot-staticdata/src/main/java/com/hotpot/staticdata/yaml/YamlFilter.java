package com.hotpot.staticdata.yaml;

import com.hotpot.domain.Service;
import com.hotpot.domain.providers.ServiceObjectiveProvider.InvalidObjectiveError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YamlFilter {
    String field;
    List<String> values;

    public Predicate<Service> toPredicate() {
        Predicate<Service> nonNull = Objects::nonNull;
        if ("id".equals(field)) {
            return nonNull.and(s -> values.contains(s.getId().getValue()));
        } else if ("tier".equals(field)) {
            return nonNull.and(s -> s.getMetaData().getTier().isPresent()
                && values.contains(s.getMetaData().getTier().get().getValue()));
        }
        throw new InvalidObjectiveError(String.format("Filter field %s not supported.", field));
    }

}
