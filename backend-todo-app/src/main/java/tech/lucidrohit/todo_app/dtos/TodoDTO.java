package tech.lucidrohit.todo_app.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data  // This will generate all getters, setters, toString, equals, and hashCode
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {
    private Long id;
    private String title;
    private String description;
}
