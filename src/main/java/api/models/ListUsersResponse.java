package api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class ListUsersResponse {

    private Integer page;

    @JsonProperty("per_page")
    private Integer perPage;

    private Integer total;

    @JsonProperty("total_pages")
    private Integer totalPages;

    private List<User> data;

    private Support support;
}
