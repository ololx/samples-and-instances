package io.github.innopolis.university.java.team.book.api.base.exception.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * project well-log
 * created 2021-12-16 18:33
 *
 * @author Alexander A. Kropotin
 */
@ApiModel(
        value = "Детализация ошибки",
        description = "Детализация ошибки сервиса"
)
@JsonPropertyOrder({
        "timestamp",
        "status",
        "comment",
        "message",
        "details",
        "trace"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionDetail {

    /**
     * The Status.
     */
    @ApiModelProperty(
            name = "Статус",
            value = "Код состояния HTTP",
            example = "404"
    )
    @JsonProperty(value = "status")
    HttpStatus status;

    /**
     * The Timestamp.
     */
    @ApiModelProperty(
            name = "Дата возникновения",
            value = "Дата возникновения",
            example = "2022-01-21T06:25:31.263Z"
    )
    @JsonProperty(value = "timestamp")
    Date timestamp;

    /**
     * The Stack trace.
     */
    @ApiModelProperty(
            name = "Трассировка",
            value = "Трассировка стэка"
    )
    @JsonProperty(value = "trace")
    String stackTrace;

    /**
     * The Comment.
     */
    @ApiModelProperty(
            name = "Комментарий к исключению",
            value = "Комментарий к конкретному исключению - генерирует ексепшен контроллер",
            example = "Неправильно указано Ф.И.О."
    )
    @JsonProperty(value = "comment")
    String comment;

    /**
     * The Message.
     */
    @ApiModelProperty(
            name = "Сообщение исключения",
            value = "Сообщение исключения - стандартное, которое было в Throwable объекте",
            example = "Couldn't extract ResultSet"
    )
    @JsonProperty(value = "message")
    String message;

    /**
     * The Details.
     */
    @ApiModelProperty(
            name = "Описание запроса",
            value = "Описание запроса, в результате которого повилось исключение"
    )
    @JsonProperty(value = "details")
    String details;

    {
        this.timestamp = new Date();
    }
}
