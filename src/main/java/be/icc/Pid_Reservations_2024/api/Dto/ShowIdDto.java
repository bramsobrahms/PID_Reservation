package be.icc.Pid_Reservations_2024.api.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class ShowIdDto {
    private Long id;
    private String title;
    private String posterUrl;
    private Integer duration;
    private Date created_in;
    private Boolean isBookable;
}
