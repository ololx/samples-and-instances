/**
 * Copyright 2020 the project restful-test-instances authors
 * and the original author or authors annotated by {@author}
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.restful.data.storing.instances.model.detail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * The type Exception detail.
 */
@ApiModel(
        value = "ExceptionDetail",
        description = "Детализация ошибки"
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
public class ExceptionDetail {

    /**
     * The Status.
     */
    @ApiModelProperty(notes = "Код состояния HTTP")
    HttpStatus status;

    /**
     * The Timestamp.
     */
    @ApiModelProperty(notes = "Дата возникновения")
    Date timestamp;

    /**
     * The Comment.
     */
    @ApiModelProperty(notes = "Комментарий к исключению")
    String comment;

    /**
     * The Message.
     */
    @ApiModelProperty(notes = "Сообщение исключения")
    String message;

    /**
     * The Details.
     */
    @ApiModelProperty(notes = "Детализация запроса")
    String details;

    {
        this.timestamp = new Date();
    }
}
