package com.hotpot.lib.yaml;

import com.hotpot.domain.Service;
import com.hotpot.domain.providers.ServiceObjectiveProvider.InvalidObjectiveError;
import lombok.Data;

import java.util.List;
import java.util.function.Predicate;

@Data
public class YamlFilter {
    String field;
    List<String> values;

    public Predicate<Service> toPredicate() {
        if ("id".equals(field)) {
            return s -> values.contains(s.getId().getValue());
        } else if ("tier".equals(field)) {
            return s -> s.getMetaData().getTier().isPresent() && values.contains(s.getMetaData().getTier().get().getValue());
        }
        throw new InvalidObjectiveError(String.format("Filter field %s not supported.", field));
    }

}
