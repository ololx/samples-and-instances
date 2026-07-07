package org.witsml.parsing.instances.javaxb.implementation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

/**
 * @project witsml-parsing-instances
 * @created 2021-11-19 07:49
 * <p>
 * @author Alexander A. Kropotin
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
public class DataSet {

    public static class DataSetPoint<T, R> {

        public DataSetPoint(T x, R y) {
            this.x = x;
            this.y = y;
        }

        private T x;

        private R y;

        public List<Object> toPoint() {
            return List.of(this.x, this.y);
        }
    }

    @JsonProperty("name")
    String name;

    @JsonIgnore
    List<DataSetPoint> dataPoints;

    @JsonProperty("data")
    public List<List<Object>> getData() {
        return new ArrayList<>(){{
            dataPoints.forEach(eachDataPoint -> add(eachDataPoint.toPoint()));
        }};
    }
}
