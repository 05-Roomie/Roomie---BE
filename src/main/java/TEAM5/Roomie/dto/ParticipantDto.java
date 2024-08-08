package TEAM5.Roomie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDto {
    private Integer id;
    private Integer postId;
    private Integer userId;
    private String name;
    private String phone;
}
